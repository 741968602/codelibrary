/************************************************************************
声波通讯库示例，从实时录音数据获取音频信号进行解码，该工程示例是可跨平台的
声波通讯库特征：
准确性95%以上，其实一般是不会出错的。
接口非常简单，有完整的示例，3分钟就可以让你的应用增加声波通讯功能
抗干扰性强，基本上无论外界怎么干扰，信号都是准确的
基本的编码为16进制，而通过编码可传输任何字符
性能非常强，没有运行不了的平台，而且通过内存池优化，长时间解码不再分配新内存，可7*24小时运行
可支持任何平台，常见的平台android, iphone, windows, linux, arm, mipsel都有示例
详情可查看：http://blog.csdn.net/softlgh
作者: 夜行侠 QQ:3116009971 邮件：3116009971@qq.com
************************************************************************/

#ifdef WIN32
#include <Windows.h>
#include <process.h>
#else
#include<pthread.h>
#include <unistd.h>
#define scanf_s scanf 
#endif
#include "voiceRecog.h"
#include "record.h"

const char *recorderRecogErrorMsg(int _recogStatus)
{
	char *r = (char *)"unknow error";
	switch(_recogStatus)
	{
	case VR_ECCError:
		r = "ecc error";
		break;
	case VR_NotEnoughSignal:
		r = "not enough signal";
		break;
	case VR_NotHeaderOrTail:
		r = "signal no header or tail";
		break;
	case VR_RecogCountZero:
		r = "trial has expires, please try again";
		break;
	}
	return r;
}

//识别到声波通讯有信号时开始解码回调函数
void recorderRecognizerStart()
{
	printf("------------------recognize start\n");
}

//当次解码结束的回调函数
void recorderRecognizerEnd(int _recogStatus, char *_data, int _dataLen)
{
	if (_recogStatus == VR_SUCCESS)
	{
		char buf[51];
		memcpy(buf, _data, _dataLen);
		buf[_dataLen] = 0;
		printf("------------------recognized data:%s\n", buf);
	}
	else
	{
		printf("------------------recognize invalid data, errorCode:%d, error:%s\n", _recogStatus, recorderRecogErrorMsg(_recogStatus));
	}
}

#ifdef WIN32
void runRecorderVoiceRecognize( void * _recognizer)  
#else
void *runRecorderVoiceRecognize( void * _recognizer) 
#endif
{
	vr_runRecognizer(_recognizer);
}

//录音回调函数，把从录音机获得的数据写入声波通讯解码器
int recorderShortWrite(void *_writer, const void *_data, unsigned long _sampleCout)
{
	char *data = (char *)_data;
	void *recognizer = _writer;
	return vr_writeData(recognizer, data, (int)_sampleCout);
}

//从录音设备获取音频信号来做声波通讯解码
void test_recorderVoiceRecog()
{
	//创建声波通讯解码器，并设置监听器
	void *recognizer = vr_createVoiceRecognizer();
	vr_setRecognizerListener(recognizer, recorderRecognizerStart, recorderRecognizerEnd);
	//创建录音机
	void *recorder = NULL;
	int r = initRecorder(44100, 1, 16, 512, &recorder);//要求录取short数据
	if(r != 0)
	{
		printf("recorder init error:%d", r);
		return;
	}
	//开始录音
	//r = startRecord(recorder, recognizer, recorderFloatWrite);//float数据
	r = startRecord(recorder, recognizer, recorderShortWrite);//short数据
	if(r != 0)
	{
		printf("recorder record error:%d", r);
		return;
	}
	//开始识别
#ifdef WIN32
	//CreateThread( NULL, 0, runRecorderVoiceRecognize, recognizer, 0, 0 );
	_beginthread(runRecorderVoiceRecognize, 0, recognizer);
#else
	pthread_t ntid;
	pthread_create(&ntid, NULL, runRecorderVoiceRecognize, recognizer);
#endif
	printf("recognize start ！！！\n");
	char c = 0;
	do 
	{
		printf("press q to end recognize\n");
		scanf_s("%c", &c);
	} while (c != 'q');

	//停止录音
	r = stopRecord(recorder);
	if(r != 0)
	{
		printf("recorder stop record error:%d", r);
	}
	r = releaseRecorder(recorder);
	if(r != 0)
	{
		printf("recorder release error:%d", r);
	}

	//通知声波通讯解码器停止，并等待解码器真正退出
	do 
	{
		vr_stopRecognize(recognizer);
		printf("recognizer is quiting\n");
#ifdef WIN32
		Sleep(1000);
#else
		sleep(1);
#endif
	} while (!vr_isRecognizerStopped(recognizer));

	//销毁解码器
	vr_destroyVoiceRecognizer(recognizer);
}
