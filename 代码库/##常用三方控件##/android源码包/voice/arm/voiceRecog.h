/************************************************************************
����ͨѶ�⣬c�ӿڣ�ʹ�÷�����windows�ļ�����ʾ��
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

#ifndef VOICE_RECOG_H
#define VOICE_RECOG_H

#ifdef __cplusplus
extern "C" {
#endif
	enum VRErrorCode
	{
		VR_SUCCESS = 0, VR_ECCError = -2, VR_NotEnoughSignal = 100
		, VR_NotHeaderOrTail = 101, VR_RecogCountZero = 102
	};

	enum DecoderPriority
	{
		CPUUsePriority = 1//��ռ�ڴ棬��CPU���ıȽϴ�һЩ
		, MemoryUsePriority = 2//��ռCPU�����ڴ����Ĵ�һЩ
	};

	typedef enum {vr_false = 0, vr_true = 1} vr_bool;

	typedef void (*vr_pRecognizerStartListener)(void);
	//_result���ΪVR_SUCCESS�����ʾʶ��ɹ�������Ϊ�����룬�ɹ��Ļ�_data��������
	typedef void (*vr_pRecognizerEndListener)(int _result, char *_data, int _dataLen);

	//��������ʶ����
	void *vr_createVoiceRecognizer(DecoderPriority _decoderPriority = CPUUsePriority);

	//����ʶ����
	void vr_destroyVoiceRecognizer(void *_recognizer);

	//����ʶ���źŵļ�����
	void vr_setRecognizerListener(void *_recognizer, vr_pRecognizerStartListener _startListener, vr_pRecognizerEndListener _endListener);

	//��ʼʶ��
	//����һ�����̣߳����������ֹͣʶ��֮ǰ���᷵��
	void vr_runRecognizer(void *_recognizer);

	//ֹͣʶ�𣬸ú������ú�vr_runRecognizer�᷵��
	//�ú���ֻ����ʶ���̷߳����˳��źţ��ж�ʶ�����Ƿ������Ѿ��˳�Ҫʹ�����µ�vr_isRecognizerStopped����
	void vr_stopRecognize(void *_recognizer);

	//�ж�ʶ�����߳��Ƿ��Ѿ��˳�
	vr_bool vr_isRecognizerStopped(void *_recognizer);

	//Ҫ����������Ҫ��Ϊ44100����������16bits�������ȣ�С�˱������Ƶ����
	//С�˱��벻���ر���һ����¼�������ݶ���С�˱����
	int vr_writeData(void *_recognizer, char *_data, int _dataLen);

#ifdef __cplusplus
}
#endif

#endif
