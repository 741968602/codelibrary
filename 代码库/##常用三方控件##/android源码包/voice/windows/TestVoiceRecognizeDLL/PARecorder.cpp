/************************************************************************
����ͨѶ��ʾ����PA��ʵ�ֵ�¼�����ӿڣ��ÿ��ǿ�ƽ̨��
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

#include "record.h"
#include <stdio.h>
#include <stdlib.h>
//#include <syslib.h>
#include "portaudio.h"

#pragma comment(lib, "portaudio_x86.lib")

#define SAMPLE_RATE  (44100)
#define FRAMES_PER_BUFFER (512)
#define NUM_SECONDS     (5)
#define NUM_CHANNELS    (2)
#define DITHER_FLAG     (0) /**/
#define WRITE_TO_FILE   (0)

/* Select sample format. */
#if 1
#define PA_SAMPLE_TYPE  paFloat32
typedef float SAMPLE;
#define SAMPLE_SILENCE  (0.0f)
#define PRINTF_S_FORMAT "%.8f"
#elif 1
#define PA_SAMPLE_TYPE  paInt16
typedef short SAMPLE;
#define SAMPLE_SILENCE  (0)
#define PRINTF_S_FORMAT "%d"
#elif 0
#define PA_SAMPLE_TYPE  paInt8
typedef char SAMPLE;
#define SAMPLE_SILENCE  (0)
#define PRINTF_S_FORMAT "%d"
#else
#define PA_SAMPLE_TYPE  paUInt8
typedef unsigned char SAMPLE;
#define SAMPLE_SILENCE  (128)
#define PRINTF_S_FORMAT "%d"
#endif

struct PARecorder
{
	PaStream* stream;
	PaStreamParameters  inputParameters,
		outputParameters;
	int sampleRateInHz, channel, audioFormat, bufferSize;
	void *writer;
	r_pwrite write;
};

/* This routine will be called by the PortAudio engine when audio is needed.
** It may be called at interrupt level on some machines so don't do anything
** that could mess up the system like calling malloc() or free().
*/
static int recordCallback( const void *inputBuffer, void *outputBuffer,
	unsigned long framesPerBuffer,
	const PaStreamCallbackTimeInfo* timeInfo,
	PaStreamCallbackFlags statusFlags,
	void *userData )
{
	//void *recognizer = userData;
	PARecorder *recorder = (PARecorder *)userData;
	int r = recorder->write(recorder->writer, inputBuffer, framesPerBuffer);
	if (r >= 0)
	{
		return paContinue;
	}
	else
	{
		return paComplete;
	}
}

int initRecorder(int _sampleRateInHz, int _channel, int _audioFormat, int _bufferSize, void **_precorder)
{
	PaError err = Pa_Initialize();
	if( err != paNoError ) 
	{
		Pa_Terminate();
	}
	PARecorder *recorder = new PARecorder();
	recorder->stream = NULL;
	recorder->sampleRateInHz = _sampleRateInHz;
	recorder->channel = _channel;
	recorder->audioFormat = _audioFormat;
	recorder->bufferSize = _bufferSize;
	recorder->writer = NULL;
	*_precorder = recorder;

	return err;
}

int startRecord(void *_recorder, void *_writer, r_pwrite _pwrite)
{
	PARecorder* recorder = (PARecorder*)_recorder;
	recorder->write = _pwrite;
	recorder->writer = _writer;
	PaStreamParameters *inputParameters = &recorder->inputParameters;
	inputParameters->device = Pa_GetDefaultInputDevice(); /* default input device */
	if (inputParameters->device == paNoDevice) {
		fprintf(stderr,"Error: No default input device.\n");
		Pa_Terminate();
		return -1;//������Ҫ��PA��������Ų��ظ�
	}
	inputParameters->channelCount = recorder->channel;
	if(recorder->audioFormat == 0)inputParameters->sampleFormat = paFloat32;
	else inputParameters->sampleFormat = paInt16;
	//inputParameters->sampleFormat = PA_SAMPLE_TYPE;
	inputParameters->suggestedLatency = Pa_GetDeviceInfo( inputParameters->device )->defaultLowInputLatency;
	inputParameters->hostApiSpecificStreamInfo = NULL;

	/* Record some audio. -------------------------------------------- */
	PaError err = Pa_OpenStream(
		&recorder->stream,
		&recorder->inputParameters,
		NULL,                  /* &outputParameters, */
		recorder->sampleRateInHz,
		recorder->bufferSize,
		paClipOff,      /* we won't output out of range samples so don't bother clipping them */
		recordCallback,
		recorder );
	if (err == paNoError)
	{
		err = Pa_StartStream( recorder->stream );
	}
	if( err != paNoError ) 
	{
		delete recorder;
	}
	return err;
}

int stopRecord(void *_recorder)
{
	PaError err = paNoError;
	if(_recorder != NULL)
	{
		PARecorder *recorder = (PARecorder *)_recorder;
		PaError err = Pa_CloseStream( recorder->stream );
	}
	return err;
}

int releaseRecorder(void *_recorder)
{
	PaError err = paNoError;
	err = Pa_Terminate();
	if(_recorder != NULL)
	{
		PARecorder *recorder = (PARecorder *)_recorder;
		delete recorder;
	}
	return err;
}


