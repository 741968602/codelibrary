/************************************************************************
����ͨѶ��ʾ������wav�ļ��ж�ȡ��Ƶ�źŽ��н��룬�ù���ʾ���ǿɿ�ƽ̨��
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

//��������ͨѶ��������Ļص�����
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

//ʶ���������ź�ʱ��ʼ����ص�����
void waveRecognizerStart()
{
	printf("------------------recognize start\n");
}

//WIN32��linux������̺߳���ԭ���е㲻һ��
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

//��wav�ļ���װ�����ݽ�������ͨѶ������
void test_voiceRecog_from_wav(int argc, char* argv[])
{
	char *wavFile = (char *)"data.wav";
	if(argc > 1)
	{
		wavFile = argv[1];
	}

	//����wav�ļ�
	struct WavData wavData;
	memset(&wavData, 0, sizeof(wavData));
	readWave(wavFile, &wavData);
	printf("%s data size:%d\n", wavFile, (int)wavData.size);

	//��������ͨѶ������������ʼ����
	void *recognizer = vr_createVoiceRecognizer(MemoryUsePriority);
	vr_setRecognizerListener(recognizer, waveRecognizerStart, waveRecognizerEnd);
#ifdef WIN32
	HANDLE recogThread = CreateThread( NULL, 0, waveRunVoiceRecognize, recognizer, 0, 0 );
	//_beginthread(waveRunVoiceRecognize, 0, recognizer);
#else
	pthread_t recogThread;
	pthread_create(&recogThread, NULL, waveRunVoiceRecognize, recognizer);
	sleep(1);//linux�߳̿�ʼ�������ȴ��߳�ִ�У���Ȼ���̻߳�û��ʼ�������ִ������
	//printf("voice recognizer thread id:%lu\n", (recogThread));
#endif

	//char *newData = (char *)malloc(wavData.size + 44100 * 2 * 2);//�����n��Ŀ�����
	//memset(newData, 0, wavData.size + 44100 * 2 * 2);
	//memcpy(newData, wavData.data, wavData.size);
	//free(wavData.data);
	//wavData.data = newData;
	//wavData.size = wavData.size + 44100 * 2 * 2;

	////������ͨѶ������д�����ݣ�������Է���д
	//while(true)
	//{
	//	vr_writeData(recognizer, wavData.data, wavData.size);
	//	Sleep(2000);
	//}
	vr_writeData(recognizer, wavData.data, wavData.size);

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

	printf("press enter key to exit.......\n");
	char c;
	scanf("%c", &c);
}


