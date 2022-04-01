package com.example.schoolbus;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 启动页面
 */
public class StartActivity extends AppCompatActivity {

    private Animation topAnim,bottomAnim;
    private ImageView img_login_logo;
    private TextView tv_name_logo, tv_describe;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //去掉标题栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_start);

        //动画
        topAnim = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        bottomAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);

        //初始化
        img_login_logo = findViewById(R.id.img_login_logo);
        tv_name_logo = findViewById(R.id.tv_name_logo);
        tv_describe = findViewById(R.id.tv_describe);

        //设置动画
        img_login_logo.setAnimation(topAnim);
        tv_name_logo.setAnimation(bottomAnim);
        tv_describe.setAnimation(bottomAnim);

        //intent延迟跳转，定时刷新UI，一般只用于启动页跳转
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //跳转到主界面
                Intent  intent = new Intent(StartActivity.this,LoginActivity.class);

                Pair[] pairs = new Pair[2];
                pairs[0] = new Pair<View,String>(img_login_logo,"login_image");
                pairs[1] = new Pair<View,String>(tv_name_logo,"login_text");
                //执行跳转动画
                ActivityOptions options= ActivityOptions.makeSceneTransitionAnimation(StartActivity.this,pairs);
                startActivity(intent,options.toBundle());
                StartActivity.this.finish();//关闭MainActivity，将其回收，否则按返回键会返回此界面
            }
        },500*2);


    }




}