/************************************************************************
����ͨѶ��ʾ����main��ڣ��ù���ʾ���ǿɿ�ƽ̨��
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

#include "stdafx.h"
#include "voiceRecog.h"
#include "testVoiceRecogFromWav.h"
#include "testVoiceRecogFromRecorder.h"

#pragma comment(lib, "voiceRecogDemo.lib")


int main(int argc, char* argv[])
{
	//��wav�ļ�������Ƶ�ź�
	test_voiceRecog_from_wav(argc, argv);

	//��ʵʱ¼���źż�����Ƶ�ź�
	//test_recorderVoiceRecog();

	return 0;
}

