package com.example.task3;

/*
* task 3 created by 399 on 2021-10-11
* */
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class pageAActivity extends AppCompatActivity implements View.OnClickListener{
    //声明控件
    private Button btnInputX;
    private Button btnInputY;
    private Button btnSum;
    private TextView textX;
    private TextView textY;
    private TextView textSum;
    private int x=0,y=0,z=0;

    //定义打开页面B的请求码
    private static final int REQUEST_PAGE_B_CODE = 1;
    //定义打开页面C的请求码
    private static final int REQUEST_PAGE_C_CODE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_aactivity);
        //初始化控件
        btnInputX=findViewById(R.id.btnInputX);
        btnInputY=findViewById(R.id.btnInputY);
        btnSum=findViewById(R.id.btnInputSum);
        textX=findViewById(R.id.textX);
        textY=findViewById(R.id.textY);
        textSum=findViewById(R.id.textSum);
        //设置监听
        btnInputX.setOnClickListener(this);
        btnInputY.setOnClickListener(this);
        btnSum.setOnClickListener(this);
    }
    public void onClick(View view)
    {
        Intent intent=new Intent();
        switch (view.getId())
        {
            case R.id.btnInputX:
                intent.setClass(this,pageBActivity.class);
                startActivityForResult(intent,REQUEST_PAGE_B_CODE);
                break;
            case R.id.btnInputY:
                intent.setClass(this,pageCActivity.class);
                startActivityForResult(intent,REQUEST_PAGE_C_CODE);
                break;
            case R.id.btnInputSum:
                z=x+y;
                textSum.setText(String.valueOf(z));
                break;
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent intent) {
        super.onActivityResult(requestCode, resultCode,intent);
        if(intent == null){
            return;
        }
        switch (requestCode){
            case REQUEST_PAGE_B_CODE:
                if(resultCode==11){
                    x=Integer.parseInt(intent.getStringExtra("numberX"));
                    textX.setText((intent.getStringExtra("numberX")));
                    Toast.makeText(this, "输入X成功！", Toast.LENGTH_SHORT).show();
                }
                if(resultCode==12){
                    textX.setText(null);
                    Toast.makeText(this, "输入X失败！", Toast.LENGTH_SHORT).show();
                }
                break;
            case REQUEST_PAGE_C_CODE:
                if(resultCode==21){
                    y=Integer.parseInt(intent.getStringExtra("numberY"));
                    textY.setText((intent.getStringExtra("numberY")));
                    Toast.makeText(this, "输入Y成功！", Toast.LENGTH_SHORT).show();
                }
                if(resultCode==22){
                    textY.setText(null);
                    Toast.makeText(this, "输入Y失败！", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
