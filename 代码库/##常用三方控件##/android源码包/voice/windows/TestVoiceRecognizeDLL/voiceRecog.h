/************************************************************************
声波通讯库示例，声波通讯库c解码接口
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

#ifdef VOICE_RECOG_DLL
#define VOICERECOGNIZEDLL_API __declspec(dllexport)
#else
#ifdef WIN32
#define VOICERECOGNIZEDLL_API __declspec(dllimport)
#else
#define VOICERECOGNIZEDLL_API
#endif
#endif

//// 此类是从 VoiceRecognizeDLL.dll 导出的
//class VOICERECOGNIZEDLL_API CVoiceRecognizeDLL {
//public:
//	CVoiceRecognizeDLL(void);
//	// TODO: 在此添加您的方法。
//};
//
//extern VOICERECOGNIZEDLL_API int nVoiceRecognizeDLL;
//
//VOICERECOGNIZEDLL_API int fnVoiceRecognizeDLL(void);


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
		CPUUsePriority = 1//不占内存，但CPU消耗比较大一些
		, MemoryUsePriority = 2//不占CPU，但内存消耗大一些
	};

	typedef enum {vr_false = 0, vr_true = 1} vr_bool;

	typedef void (*vr_pRecognizerStartListener)(void);
	//_result如果为VR_SUCCESS，则表示识别成功，否则为错误码，成功的话_data才有数据
	typedef void (*vr_pRecognizerEndListener)(int _result, char *_data, int _dataLen);

	//创建声波识别器
	VOICERECOGNIZEDLL_API void *vr_createVoiceRecognizer(DecoderPriority _decoderPriority = CPUUsePriority);

	//销毁识别器
	VOICERECOGNIZEDLL_API void vr_destroyVoiceRecognizer(void *_recognizer);

	//设置识别到信号的监听器
	VOICERECOGNIZEDLL_API void vr_setRecognizerListener(void *_recognizer, vr_pRecognizerStartListener _startListener, vr_pRecognizerEndListener _endListener);

	//开始识别
	//这里一般是线程，这个函数在停止识别之前不会返回
	VOICERECOGNIZEDLL_API void vr_runRecognizer(void *_recognizer);

	//停止识别，该函数调用后vr_runRecognizer会返回
	//该函数只是向识别线程发出退出信号，判断识别器是否真正已经退出要使用以下的vr_isRecognizerStopped函数
	VOICERECOGNIZEDLL_API void vr_stopRecognize(void *_recognizer);

	//判断识别器线程是否已经退出
	VOICERECOGNIZEDLL_API vr_bool vr_isRecognizerStopped(void *_recognizer);

	//要求输入数据要求为44100，单声道，16bits采样精度，小端编码的音频数据
	//小端编码不用特别处理，一般你录到的数据都是小端编码的
	VOICERECOGNIZEDLL_API int vr_writeData(void *_recognizer, char *_data, int _dataLen);

#ifdef __cplusplus
}
#endif

#endif
