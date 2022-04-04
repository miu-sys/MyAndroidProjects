package com.example.musicplayer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

/**
 * 指定音乐路径
 * 实现音乐的播放和暂停
 * on 2022.04.01
 */


public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    //控件
    private Button bt_play,bt_pause,bt_continue,bt_quit;//播放、暂停、继续播放、退出按钮
    private static SeekBar pb_music;//音乐播放器进度条
    private static TextView tv_progress,tv_total;//音乐播放当前进度、音乐总时间


    //对象
    MusicService.MyBinder myBinder;

    //类
    private MyConn myConn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //初始化控件
        bt_play =  findViewById(R.id.bt_play);
        bt_pause =  findViewById(R.id.bt_pause);
        bt_continue =  findViewById(R.id.bt_continue);
        bt_quit = findViewById(R.id.bt_quit);

        pb_music=(SeekBar)findViewById((R.id.sb));

        tv_progress = findViewById(R.id.tv_progress);
        tv_total = findViewById(R.id.tv_total);

        //设置按钮监听
        bt_play.setOnClickListener(this);
        bt_pause.setOnClickListener(this);
        bt_continue.setOnClickListener(this);
        bt_quit.setOnClickListener(this);

        //播放音乐
        myConn = new MyConn();
        Intent intent = new Intent(this, MusicService.class);
        bindService(intent, myConn, BIND_AUTO_CREATE);

        //播放进度条
        pb_music.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if(b){
                    myBinder.jumpTo(i);//跳转到进度条
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                myBinder.pause();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                myBinder.continuePlay();
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_play://点击播放按钮
                myBinder.play();
                break;
            case R.id.bt_pause://点击暂停按钮
                myBinder.pause();
                break;
            case R.id.bt_continue://点击继续播放按钮
                myBinder.continuePlay();
                break;
            case R.id.bt_quit://点击退出按钮
                finish();
                break;
            default:
                break;
        }

    }

    private class MyConn implements ServiceConnection {

        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            myBinder = (MusicService.MyBinder) iBinder;//强制类型转换
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    }

    @Override
    protected void onDestroy() {
        unbindService(myConn);//解除绑定
        super.onDestroy();
    }

    //获取从service传递过来的消息
    public static Handler handler=new Handler(Looper.myLooper()){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            Bundle bundle = msg.getData();//获取从子线程发送过来的音乐播放器
            int duration = bundle.getInt("duration");//歌曲总时长
            int currentPosition = bundle.getInt("currentPosition");//当前播放进度

            pb_music.setMax(duration);//设置进度条播放总时长
            pb_music.setProgress(currentPosition);//设置进度条当前播放时长

            //格式化获取的时间
            String totalTime = msToMinSec(duration);
            String currentTime = msToMinSec(currentPosition);

            //将格式化的时间设置到UI控件
            tv_progress.setText(currentTime);
            tv_total.setText(totalTime);

        }
    };

    //将获取到的时间格式化为00:00
    public static String msToMinSec(int ms){
        int sec = ms / 1000;
        int min = sec / 60;
        sec -= min*60;
        return  String.format("%02d:%02d",min,sec);

    }
}