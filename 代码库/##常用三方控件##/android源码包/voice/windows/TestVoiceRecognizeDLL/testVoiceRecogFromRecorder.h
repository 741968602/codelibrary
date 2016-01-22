/************************************************************************
����ͨѶ��ʾ������ʵʱ¼�����ݻ�ȡ��Ƶ�źŽ��н��룬�ù���ʾ���ǿɿ�ƽ̨��
����ͨѶ��������
׼ȷ��95%���ϣ���ʵһ���ǲ������ġ�
�ӿڷǳ��򵥣���������ʾ����3���ӾͿ��������Ӧ����������ͨѶ����
��������ǿ�����������������ô���ţ��źŶ���׼ȷ��
�����ı���Ϊ16���ƣ���ͨ������ɴ����κ��ַ�
���ܷǳ�ǿ��û�����в��˵�ƽ̨������ͨ���ڴ���Ż�����ʱ����벻�ٷ������ڴ棬��7*24Сʱ����
��֧���κ�ƽ̨��������ƽ̨android, iphone, windows, linux, arm, mipsel����ʾ��
����ɲ鿴��http://blog.csdn.net/softlgh
����: ҹ���� QQ:3116009971 �ʼ���3116009971@qq.com
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

//ʶ������ͨѶ���ź�ʱ��ʼ����ص�����
void recorderRecognizerStart()
{
	printf("------------------recognize start\n");
}

//���ν�������Ļص�����
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

//¼���ص��������Ѵ�¼������õ�����д������ͨѶ������
int recorderShortWrite(void *_writer, const void *_data, unsigned long _sampleCout)
{
	char *data = (char *)_data;
	void *recognizer = _writer;
	return vr_writeData(recognizer, data, (int)_sampleCout);
}

//��¼���豸��ȡ��Ƶ�ź���������ͨѶ����
void test_recorderVoiceRecog()
{
	//��������ͨѶ�������������ü�����
	void *recognizer = vr_createVoiceRecognizer();
	vr_setRecognizerListener(recognizer, recorderRecognizerStart, recorderRecognizerEnd);
	//����¼����
	void *recorder = NULL;
	int r = initRecorder(44100, 1, 16, 512, &recorder);//Ҫ��¼ȡshort����
	if(r != 0)
	{
		printf("recorder init error:%d", r);
		return;
	}
	//��ʼ¼��
	//r = startRecord(recorder, recognizer, recorderFloatWrite);//float����
	r = startRecord(recorder, recognizer, recorderShortWrite);//short����
	if(r != 0)
	{
		printf("recorder record error:%d", r);
		return;
	}
	//��ʼʶ��
#ifdef WIN32
	//CreateThread( NULL, 0, runRecorderVoiceRecognize, recognizer, 0, 0 );
	_beginthread(runRecorderVoiceRecognize, 0, recognizer);
#else
	pthread_t ntid;
	pthread_create(&ntid, NULL, runRecorderVoiceRecognize, recognizer);
#endif
	printf("recognize start ������\n");
	char c = 0;
	do 
	{
		printf("press q to end recognize\n");
		scanf_s("%c", &c);
	} while (c != 'q');

	//ֹͣ¼��
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

	//֪ͨ����ͨѶ������ֹͣ�����ȴ������������˳�
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

	//���ٽ�����
	vr_destroyVoiceRecognizer(recognizer);
}
