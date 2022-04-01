package com.example.mylistview;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/*
 * Created by 399 on 2021/11/24
 * 将信息传给listview
 * */

public class InfoActivity extends AppCompatActivity {

    //声明控件
    TextView tv_name;
    TextView tv_job;
    TextView tv_age;
    TextView tv_sex;
    TextView tv_phone_number;
    ImageView iv_head_portrait;
    ImageButton ib_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        //初始化控件
        tv_name = findViewById(R.id.tv_name);
        tv_job = findViewById(R.id.tv_job);
        tv_age = findViewById(R.id.tv_age);
        tv_sex = findViewById(R.id.tv_sex);
        tv_phone_number = findViewById(R.id.tv_phone_number);
        iv_head_portrait=findViewById(R.id.iv_head_portrait);
        ib_back=findViewById(R.id.ib_back);

        //返回按钮设置监听
        ib_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentback = new Intent(InfoActivity.this,MainActivity.class);
                startActivity(intentback);
            }
        });

        //接受注册页面传来的数据并设置给控件
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        tv_name.setText(bundle.getString("name"));
        tv_job.setText(bundle.getString("job"));
        tv_age.setText(bundle.getString("age"));
        tv_sex.setText(bundle.getString("sex"));
        tv_phone_number.setText(bundle.getString("phone_number"));
        iv_head_portrait.setImageResource(bundle.getInt("head_portrait"));
    }
}