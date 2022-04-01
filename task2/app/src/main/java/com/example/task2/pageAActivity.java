package com.example.task2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/*
* task 2 created by 399 on 2021-09-24
* */

public class pageAActivity extends AppCompatActivity implements View.OnClickListener{
    //声明控件
    private EditText etX;
    private Button btnX;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_aactivity);

        //初始化控件
        init();
    }

    //初始化控件
    private void init() {
        etX=findViewById(R.id.etX);
        btnX=findViewById(R.id.btnX);
        //设置监听
        btnX.setOnClickListener(pageAActivity.this);
    }

    //重写onclick方法
    @Override
    public void onClick(View view) {
        Intent intent = new Intent(this,pageBActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("numberX",etX.getText().toString().trim());
        intent.putExtras(bundle);
        startActivity(intent);
    }
}