package com.example.musicplayer;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;

import java.util.Timer;
import java.util.TimerTask;

public class MusicService extends Service {

    public MediaPlayer mediaPlayer;//多媒体对象
    public Timer timer;//时钟对象

    @Override
    public void onCreate() {
        mediaPlayer = new MediaPlayer();
        mediaPlayer=MediaPlayer.create(getApplicationContext(),R.raw.qianqiu);//设置音乐播放路径
        super.onCreate();
    }

    //自定义的类，用于向Activity提供方法的接口
    class MyBinder extends Binder {
        //播放音乐
        public void play() {
            if (mediaPlayer != null){
                mediaPlayer.start();
                addTimer();
            }
        }

        //暂停播放
        public void pause() {
            mediaPlayer.pause();
        }

        //继续播放
        public void continuePlay() {
            int position = getCurrentProgress();//获取当前播放进度
            mediaPlayer.seekTo(position);//跳转到当前播放位置
            mediaPlayer.start();//开始播放
        }

        /**
         *跳转播放进度
         * @param position 播放进度
         */
        void jumpTo(int position){
            mediaPlayer.seekTo(position);//跳转到当前播放位置
        }

    }

    //获取当前进度
    public int getCurrentProgress() {
        //如果正在播放，获取当前进度
        if(mediaPlayer!=null&mediaPlayer.isPlaying()){
            return mediaPlayer.getCurrentPosition();
        }else if(mediaPlayer!=null&(!mediaPlayer.isPlaying())){//没有正在播放，也可以获取当前进度
            return mediaPlayer.getCurrentPosition();
        }
        return 0;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return new MyBinder();
    }


    @Override
    public void onDestroy() {
        if(mediaPlayer!=null){
            mediaPlayer.stop();//停止播放
            mediaPlayer.release();//释放媒体资源
            mediaPlayer=null;
        }
        super.onDestroy();
    }

    //计时器
    public void addTimer(){
        if (timer == null){
            timer = new Timer();
            TimerTask task = new TimerTask() {
                @Override
                public void run() {
                    int duration = mediaPlayer.getDuration();//获取歌曲总时长
                    int currentPosition = mediaPlayer.getCurrentPosition();//获取歌曲当前时长
                    Message msg = MainActivity.handler.obtainMessage();//创建消息对象
                    //将需要的音乐数据封装进Bundle中
                    Bundle bundle = new Bundle();
                    bundle.putInt("duration",duration);
                    bundle.putInt("currentPosition",currentPosition);
                    msg.setData(bundle);
                    //将消息发送到主线程消息队列
                    MainActivity.handler.sendMessage(msg);
                }
            };
            //开始计时任务后的5ms，第一次执行task任务，以后每500ms执行一次
            timer.schedule(task,5,500);
        }
    }

}