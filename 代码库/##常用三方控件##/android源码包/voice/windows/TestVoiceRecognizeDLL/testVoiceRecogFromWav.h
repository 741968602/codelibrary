/************************************************************************
声波通讯库示例，从wav文件中读取音频信号进行解码，该工程示例是可跨平台的
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

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#ifdef WIN32
#include <Windows.h>
#include <process.h>
#else
#include<pthread.h>
#include <unistd.h>
#endif
#include "voiceRecog.h"
#include "testHelper.h"

const char *wavRecogErrorMsg(int _recogStatus)
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

//当次声波通讯解码结束的回调函数
void waveRecognizerEnd(int _recogStatus, char *_data, int _dataLen)
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
		printf("------------------recognize invalid data, errorCode:%d, error:%s\n", _recogStatus, wavRecogErrorMsg(_recogStatus));
	}
}

//识别到有声波信号时开始解码回调函数
void waveRecognizerStart()
{
	printf("------------------recognize start\n");
}

//WIN32与linux所需的线程函数原型有点不一样
#ifdef WIN32
DWORD WINAPI waveRunVoiceRecognize( LPVOID _recognizer)  
{
#else
void *waveRunVoiceRecognize( void * _recognizer) 
{
	printf("voice recognizer thread start:%d\n", getpid());
#endif
	vr_runRecognizer(_recognizer);
	return 0;
}

//从wav文件中装载数据进入声波通讯解码器
void test_voiceRecog_from_wav(int argc, char* argv[])
{
	char *wavFile = (char *)"data.wav";
	if(argc > 1)
	{
		wavFile = argv[1];
	}

	//读入wav文件
	struct WavData wavData;
	memset(&wavData, 0, sizeof(wavData));
	readWave(wavFile, &wavData);
	printf("%s data size:%d\n", wavFile, (int)wavData.size);

	//创建声波通讯解码器，并开始运行
	void *recognizer = vr_createVoiceRecognizer(MemoryUsePriority);
	vr_setRecognizerListener(recognizer, waveRecognizerStart, waveRecognizerEnd);
#ifdef WIN32
	HANDLE recogThread = CreateThread( NULL, 0, waveRunVoiceRecognize, recognizer, 0, 0 );
	//_beginthread(waveRunVoiceRecognize, 0, recognizer);
#else
	pthread_t recogThread;
	pthread_create(&recogThread, NULL, waveRunVoiceRecognize, recognizer);
	sleep(1);//linux线程开始得慢，等待线程执行，不然子线程还没开始，程序就执行完了
	//printf("voice recognizer thread id:%lu\n", (recogThread));
#endif

	//char *newData = (char *)malloc(wavData.size + 44100 * 2 * 2);//多分配n秒的空数据
	//memset(newData, 0, wavData.size + 44100 * 2 * 2);
	//memcpy(newData, wavData.data, wavData.size);
	//free(wavData.data);
	//wavData.data = newData;
	//wavData.size = wavData.size + 44100 * 2 * 2;

	////往声波通讯解码器写入数据，这里可以反复写
	//while(true)
	//{
	//	vr_writeData(recognizer, wavData.data, wavData.size);
	//	Sleep(2000);
	//}
	vr_writeData(recognizer, wavData.data, wavData.size);

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

	printf("press enter key to exit.......\n");
	char c;
	scanf("%c", &c);
}


