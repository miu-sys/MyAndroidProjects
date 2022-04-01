package com.example.task2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class pageBActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText etY;
    private Button btnY;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_bactivity);

        //初始化
        init();
    }

    //初始化
    private void init() {
        etY=findViewById(R.id.etY);
        btnY=findViewById(R.id.btnY);
        //设置监听
        btnY.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(this,pageCActivity.class);
        Bundle bundle = getIntent().getExtras();
        bundle.putString("numberY",etY.getText().toString().trim());
        intent.putExtras(bundle);
        startActivity(intent);

    }
}