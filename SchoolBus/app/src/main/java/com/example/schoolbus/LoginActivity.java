package com.example.schoolbus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.schoolbus.SharedPreferences.SPLogin;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Map;

/**
 * 登录页面
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    //UI控件
    private Button btn_quit,btn_forgetPD,btn_login;
    private EditText et_username,et_password;
    private CheckBox cb_rememberPd;
    private com.google.android.material.textfield.TextInputLayout tf_username,tf_password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //去掉标题
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);

        inti();
        checkedInput();




    }

    /**
     * 校验用户输入的用户名和密码是否超出长度限制
     * */
    private void checkedInput() {
        tf_username = findViewById(R.id.tf_username);
        tf_password = findViewById(R.id.tf_password);

        //开启错误提示
        tf_username.setErrorEnabled(true);
        tf_password.setErrorEnabled(true);
        //开启计数
        //tf_username.setCounterEnabled(true);
        //tf_password.setCounterEnabled(true);
        //设置输入最大长度
        tf_username.setCounterMaxLength(10);
        tf_password.setCounterMaxLength(10);

        //密码输入框文本发生变化
        tf_password.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // 文本变化前调用
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // 文本发生变化时调用
            }

            @Override
            public void afterTextChanged(Editable s) {
                // 文本发生变化后调用
                if(tf_password.getEditText().getText().toString().trim().length()>10){
                    tf_password.setError("密码长度超出限制");
                }else{
                    tf_password.setError(null);
                }
            }
        });
        //用户名输入框文本发生变化
        tf_username.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // 文本变化前调用
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // 文本发生变化时调用
            }

            @Override
            public void afterTextChanged(Editable s) {
                // 文本发生变化后调用
                if(tf_username.getEditText().getText().toString().trim().length()>10){
                    tf_username.setError("用户名长度超出限制");
                }else{
                    tf_username.setError(null);
                }
            }
        });

    }

    /**
     * 按钮监听事件
     * */
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_login://登录
                userLogin();
                break;
            case R.id.btn_quit://退出
                android.os.Process.killProcess(android.os.Process.myPid());//获取PID
                System.exit(0);//常规java、c#的标准退出法，返回值为0代表正常退出
                break;
            case R.id.btn_forgetPD://忘记密码
                Toast.makeText(this,"该功能尚待开发",Toast.LENGTH_SHORT).show();
                break;
        }
    }


    /**
     * 初始化UI控件等
    * */
    private void inti() {

        //UI
        btn_quit=findViewById(R.id.btn_quit);
        btn_forgetPD=findViewById(R.id.btn_forgetPD);
        btn_login=findViewById(R.id.btn_login);
        et_username=findViewById(R.id.et_username);
        et_password=findViewById(R.id.et_password);
        cb_rememberPd=findViewById(R.id.cb_rememberPd);

        //自动填充密码
        Map<String,String> userInfo = SPLogin.getUserInfo(this);
        if(userInfo!=null){
            et_username.setText(userInfo.get("username"));
            et_password.setText(userInfo.get("password"));
        }

        //监听
        btn_quit.setOnClickListener(this);
        btn_forgetPD.setOnClickListener(this);
        btn_login.setOnClickListener(this);



    }

    /**
     * 用户登录
     * 写死用户名root密码1234
     * */
    private void userLogin(){
        String username=et_username.getText().toString().trim();
        String password=et_password.getText().toString().trim();
        if(TextUtils.isEmpty(username)){
            Toast.makeText(this,"请输入用户名",Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(password)){
            Toast.makeText(this,"请输入密码",Toast.LENGTH_SHORT).show();
            return;
        }
        if (username.equals("root") && password.equals("1234")){
            if (cb_rememberPd.isChecked()){//如果记住密码复选框被勾选，进行记住密码的操作
                SPLogin.saveUserInfo(this,username,password);
            }
            Toast.makeText(this,"执行跳转到登录界面",Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this,"用户名或密码错误",Toast.LENGTH_SHORT).show();
        }

    }




}