/************************************************************************
声波通讯库示例，test辅助类，该工程示例是可跨平台的
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

#include "testHelper.h"
#include <assert.h>
#include <stdarg.h>

int readWave(const char *_fname, struct WavData *_wavData)
{
	FILE *fp;

	fp = fopen(_fname,"rb");
	if (fp)
	{
		char id[5];          // 5个字节存储空间存储'RIFF'和'\0'，这个是为方便利用strcmp
		unsigned long size;  // 存储文件大小
		short format_tag, channels, block_align, bits_per_sample;    // 16位数据
		unsigned long format_length, sample_rate, avg_bytes_sec, data_size; // 32位数据

		fread(id, sizeof(char), 4, fp); // 读取'RIFF'
		id[4] = '\0';
		if (!strcmp(id, "RIFF"))
		{ 
			fread(&size, sizeof(unsigned long), 1, fp); // 读取文件大小
			fread(id, sizeof(char), 4, fp);         // 读取'WAVE'
			id[4] = '\0';

			if (!strcmp(id,"WAVE"))
			{ 
				fread(id, sizeof(char), 4, fp);     // 读取4字节 "fmt ";
				fread(&format_length, sizeof(unsigned long),1,fp);
				fread(&format_tag, sizeof(short), 1, fp); // 读取文件tag
				fread(&channels, sizeof(short),1,fp);    // 读取通道数目
				fread(&sample_rate, sizeof(unsigned long), 1, fp);   // 读取采样率大小


				fread(&avg_bytes_sec, sizeof(unsigned long), 1, fp); // 读取每秒数据量
				fread(&block_align, sizeof(short), 1, fp);     // 读取块对齐
				fread(&bits_per_sample, sizeof(short), 1, fp);       // 读取每一样本大小
				fread(id, sizeof(char), 4, fp);                      // 读入'data'
				fread(&data_size, sizeof(unsigned long), 1, fp);     // 读取数据大小
				_wavData->size = data_size;
				_wavData->data = (char *) malloc(sizeof(char)*data_size); // 申请内存空间
				fread(_wavData->data, sizeof(char), data_size, fp);       // 读取数据

			}
			else
			{
				printf("Error: RIFF file but not a wave file\n");
			}
		}
		else
		{
			printf("Error: not a RIFF file\n");
		}
		fclose(fp);
		return 1;
	}
	return 0;
}

