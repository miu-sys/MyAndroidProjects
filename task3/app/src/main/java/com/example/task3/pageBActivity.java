package com.example.task3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class pageBActivity extends AppCompatActivity {

    private EditText etX;
    private Button btnSubX;
    //定义返回页面A的结果码-成功
    private static final int BACK_PAGE_AB_SUCCESS_CODE = 11;
    //定义返回页面A的结果码-失败
    private static final int BACK_PAGE_AB_FAIL_CODE = 12;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_bactivity);
        etX=findViewById(R.id.etX);
        btnSubX=findViewById(R.id.btnSubX);
        btnSubX.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(pageBActivity.this,pageAActivity.class);
                //X输入为空时，返回失败结果码
                if(TextUtils.isEmpty(etX.getText().toString().trim()))
                {
                    setResult(BACK_PAGE_AB_FAIL_CODE,intent);
                }
                //X输入不为空时，返回成功结果码，传回X值
                else
                    {
                        intent.putExtra("numberX",etX.getText().toString().trim());
                        setResult(BACK_PAGE_AB_SUCCESS_CODE,intent);
                    }
                finish();
            }
        });

    }
}