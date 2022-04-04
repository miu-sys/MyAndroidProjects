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
import android.os.IBinder;
import android.view.View;
import android.widget.EditText;
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
    private TextView tv_play;//音乐播放按钮
    private TextView tv_pause;//音乐暂停播放按钮

    //对象
    MusicService.MyBinder myBinder;

    //类
    private MyConn myConn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //初始化控件
        tv_play = (TextView) findViewById(R.id.tv_play);
        tv_pause = (TextView) findViewById(R.id.tv_pause);

        //设置按钮监听
        tv_play.setOnClickListener(this);
        tv_pause.setOnClickListener(this);

        //播放音乐
        myConn = new MyConn();
        Intent intent = new Intent(this, MusicService.class);
        bindService(intent, myConn, BIND_AUTO_CREATE);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_play://点击播放按钮
                myBinder.playMusic();
                break;
            case R.id.tv_pause://点击暂停按钮
                myBinder.pause();
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

}