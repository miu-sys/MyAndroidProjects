package com.example.musicplayer;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;

public class MusicService extends Service {

    public MediaPlayer mediaPlayer;

    @Override
    public void onCreate() {
        mediaPlayer = new MediaPlayer();
        mediaPlayer=MediaPlayer.create(getApplicationContext(),R.raw.ericaceae);//设置音乐播放路径
        super.onCreate();
    }

    //自定义的类，用于向Activity提供方法的接口
    class MyBinder extends Binder {
        //播放音乐
        public void playMusic() {
            if(getCurrentProgress()!=0){//如果是暂停后播放，跳转到当前播放进度
                int position = getCurrentProgress();//获取当前播放进度
                mediaPlayer.seekTo(position);//跳转到当前播放位置
            }
            mediaPlayer.start();
        }

        //暂停播放
        public void pause() {
            if(mediaPlayer!=null&&mediaPlayer.isPlaying()){
                mediaPlayer.pause();
            }else if(mediaPlayer!=null&&(!mediaPlayer.isPlaying())){//再次点击暂停按钮也可以重新播放
                mediaPlayer.start();
            }
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
}