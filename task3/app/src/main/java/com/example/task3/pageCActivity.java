package com.example.task3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class pageCActivity extends AppCompatActivity {

    private EditText etY;
    private Button btnSubY;
    //定义返回页面A的结果码-成功
    private static final int BACK_PAGE_AC_SUCCESS_CODE = 21;
    //定义返回页面A的结果码-失败
    private static final int BACK_PAGE_AC_FAIL_CODE = 22;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_cactivity);
        etY=findViewById(R.id.etY);
        btnSubY=findViewById(R.id.btnSubY);
        btnSubY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(pageCActivity.this,pageAActivity.class);
                //Y输入为空时，返回失败请求码
                if(TextUtils.isEmpty(etY.getText().toString().trim()))
                {
                    setResult(BACK_PAGE_AC_FAIL_CODE,intent);
                }
                //Y输入不为空时，返回成功请求码，传回Y值
                else
                {
                    intent.putExtra("numberY",etY.getText().toString().trim());
                    setResult(BACK_PAGE_AC_SUCCESS_CODE,intent);
                }
                finish();
            }
        });
    }
}