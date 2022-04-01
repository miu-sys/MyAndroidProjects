package com.example.logininterface;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class InfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        //初始化控件
        TextView tv_username=findViewById(R.id.text_username);
        TextView tv_paw=findViewById(R.id.text_paw);
        TextView tv_sex=findViewById(R.id.text_sex);
        TextView tv_interest=findViewById(R.id.text_interest);
        TextView tv_individual_resume=findViewById(R.id.text_individual_resume);

        //接受注册页面传来的数据
        Intent intent =getIntent();
        Bundle bundle=intent.getExtras();
        tv_username.setText(bundle.getString("username"));
        tv_paw.setText(bundle.getString("paw"));
        tv_individual_resume.setText(bundle.getString("individual_resume"));

        tv_sex.setText(bundle.getString("sex"));
        tv_interest.setText(bundle.getString("interest"));

    }
}