/************************************************************************
 声波通讯库示例，android的声波通讯编码，解码的示例
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

package com.example.encoderTest;

import android.content.Context;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import voice.decoder.VoiceRecognition;
import voice.decoder.VoiceRecognitionListener;
import voice.encoder.VoicePlayer;

public class MyActivity extends Activity {
    private final static int MSG_RECG_TEXT = 1;
    private final static int MSG_ERROR_PLAY_TEXT = 2;
    class MyHandler extends Handler
    {
        private TextView mRecognisedTextView;
        public MyHandler(TextView textView) {
            mRecognisedTextView = textView;
//            directiveTextView = _directiveTextView;
        }
        @Override
        public void handleMessage(Message msg) {
            if(msg.what == MSG_RECG_TEXT)
            {
                String s = (String)msg.obj;
                mRecognisedTextView.setText(s);
                Toast toast = Toast.makeText(MyActivity.this, s, Toast.LENGTH_SHORT);
                toast.show();
            }
            else if(msg.what == MSG_ERROR_PLAY_TEXT)
            {
                String s = (String)msg.obj;
                Toast toast = Toast.makeText(MyActivity.this, s + " 不是有效的十六进制字符串", Toast.LENGTH_SHORT);
                toast.show();
            }
            super.handleMessage(msg);
        }
    }

    VoicePlayer player;//声波通讯播放器
    VoiceRecognition recognition;//声波通讯识别器
    Handler handler;
    EditText toPlayText;
    ToneGenerator toneGenerator =
            new ToneGenerator(
                    AudioManager.STREAM_SYSTEM, ToneGenerator.MAX_VOLUME);
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        //创建声波通讯播放器
        player = new VoicePlayer();
        //创建声波通讯识别器
        recognition = new VoiceRecognition();
        recognition.setListener(new VoiceRecognitionListener() {
            @Override
            public void onRecognitionStart() {
            }

            public void onRecognitionEnd(int _recogStatus, String _val) {
                if(_recogStatus == VoiceRecognition.Status_Success)
                {
                    handler.sendMessage(handler.obtainMessage(MSG_RECG_TEXT, _val));
                    toneGenerator.startTone(ToneGenerator.TONE_PROP_BEEP);
                }
//                else
//                {
//                    handler.sendMessage(handler.obtainMessage(MSG_RECG_TEXT, "errorCode:" + _recogStatus));
//                }
            }
        });

        toPlayText = (EditText) this.findViewById(R.id.toPlayText);
        toPlayText.setText("0123456789");
        //开始播放
        Button playStart = (Button) this.findViewById(R.id.startPlay);
        playStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                String toPlayString = toPlayText.getText().toString().trim();
                boolean validPlayString = toPlayString.length() > 0;
                if(validPlayString)
                {
                    for(int i = 0; i < toPlayString.length(); i ++)
                    {
                        char c = toPlayString.charAt(i);
                        if(!((c >= '0' && c <= '9') || (c >= 'a' && c <= 'f')))
                        {
                            validPlayString = false;
                            break;
                        }
                    }
                }
                if(!validPlayString)
                {
                    handler.sendMessage(handler.obtainMessage(MSG_ERROR_PLAY_TEXT, toPlayString));
                    return;
                }
                player.play(toPlayString, 50, 1000);//播放50次，间隔为1000ms
            }
        });

        //停止播放
        Button playStop = (Button) this.findViewById(R.id.stopPlay);
        playStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                player.stop();
            }
        });

        final TextView recognisedTextView = (TextView) findViewById(R.id.regtext);
        handler = new MyHandler(recognisedTextView);
        //开始识别
        Button recognitionStart = (Button) this.findViewById(R.id.startReg);
        recognitionStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                recognition.start();
            }
        });

        //停止识别
        Button recognitionStop = (Button) this.findViewById(R.id.stopReg);
        recognitionStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                recognisedTextView.setText("");
                recognition.stop();
            }
        });
        autoSetAudioVolumn();
    }

    //把音量设为60%
    public void autoSetAudioVolumn()
    {
        AudioManager mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        int max = mAudioManager.getStreamMaxVolume( AudioManager.STREAM_MUSIC );
        mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, (int)(max*0.6), 0);
    }
}
