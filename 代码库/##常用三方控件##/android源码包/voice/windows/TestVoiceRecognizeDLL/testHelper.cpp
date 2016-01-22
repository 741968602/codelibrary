/************************************************************************
����ͨѶ��ʾ����test�����࣬�ù���ʾ���ǿɿ�ƽ̨��
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

#include "testHelper.h"
#include <assert.h>
#include <stdarg.h>

int readWave(const char *_fname, struct WavData *_wavData)
{
	FILE *fp;

	fp = fopen(_fname,"rb");
	if (fp)
	{
		char id[5];          // 5���ֽڴ洢�ռ�洢'RIFF'��'\0'�������Ϊ��������strcmp
		unsigned long size;  // �洢�ļ���С
		short format_tag, channels, block_align, bits_per_sample;    // 16λ����
		unsigned long format_length, sample_rate, avg_bytes_sec, data_size; // 32λ����

		fread(id, sizeof(char), 4, fp); // ��ȡ'RIFF'
		id[4] = '\0';
		if (!strcmp(id, "RIFF"))
		{ 
			fread(&size, sizeof(unsigned long), 1, fp); // ��ȡ�ļ���С
			fread(id, sizeof(char), 4, fp);         // ��ȡ'WAVE'
			id[4] = '\0';

			if (!strcmp(id,"WAVE"))
			{ 
				fread(id, sizeof(char), 4, fp);     // ��ȡ4�ֽ� "fmt ";
				fread(&format_length, sizeof(unsigned long),1,fp);
				fread(&format_tag, sizeof(short), 1, fp); // ��ȡ�ļ�tag
				fread(&channels, sizeof(short),1,fp);    // ��ȡͨ����Ŀ
				fread(&sample_rate, sizeof(unsigned long), 1, fp);   // ��ȡ�����ʴ�С


				fread(&avg_bytes_sec, sizeof(unsigned long), 1, fp); // ��ȡÿ��������
				fread(&block_align, sizeof(short), 1, fp);     // ��ȡ�����
				fread(&bits_per_sample, sizeof(short), 1, fp);       // ��ȡÿһ������С
				fread(id, sizeof(char), 4, fp);                      // ����'data'
				fread(&data_size, sizeof(unsigned long), 1, fp);     // ��ȡ���ݴ�С
				_wavData->size = data_size;
				_wavData->data = (char *) malloc(sizeof(char)*data_size); // �����ڴ�ռ�
				fread(_wavData->data, sizeof(char), data_size, fp);       // ��ȡ����

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

