package com.example.logininterface;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

/*
 * task 5 created by 399 on 2021-10-25
 */

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //定义属性
    //region
    //用户的输入文本框
    EditText et_username,et_paw,et_paw_again,et_individual_resume;

    //性别选择单选框
    RadioGroup rad_select_sex;
    RadioButton rad_woman,rad_man;

    //兴趣选择复选框
    LinearLayout linear_interest;//线性布局-包裹兴趣选择复选框
    CheckBox cb_basketball,cb_football,cb_watchingTV,cb_tourism;

    //重置和提交按钮
    Button btn_reset,btn_submit;

    //存储用户输入的文本
    String str_username,str_paw,str_paw_again,str_individual_resume,str_sex,str_interest;

    //判断是否可以跳转页面
    boolean canSubmit=false;

    //endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //初始化UI
        initUI();

        //监听事件
        btn_reset.setOnClickListener(this);
        btn_submit.setOnClickListener(this);
    }

    //按钮点击事件
    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.btn_reset){
            //重置textview
            resetET();
            //重置radiobutton
            resetOrGetTextByRB(0);
            //重置checkbox
            resetOrGetTextByCB(0);
        }else if (view.getId()==R.id.btn_submit){
            //获取用户输入
            getUserInput();
            //判断是否可以跳转页面，用户输入为空、密码不相等不能跳转页面
            canNotSubmit();
            //满足两个条件后，封装数据、跳转页面
            canSubmit();
        }

    }

    //初始化UI
    private void initUI(){
        et_username = findViewById(R.id.et_username);
        et_paw = findViewById(R.id.et_paw);
        et_paw_again = findViewById(R.id.et_paw_again);
        et_individual_resume = findViewById(R.id.et_individual_resume);

        rad_select_sex = findViewById(R.id.rad_select_sex);
        rad_woman = findViewById(R.id.rad_woman);
        rad_man = findViewById(R.id.rad_man);

        linear_interest = findViewById(R.id.linear_interest);
        cb_basketball = findViewById(R.id.cb_basketball);
        cb_football = findViewById(R.id.cb_football);
        cb_watchingTV = findViewById(R.id.cb_watchingTV);
        cb_tourism = findViewById(R.id.cb_tourism);

        btn_reset = findViewById(R.id.btn_reset);
        btn_submit = findViewById(R.id.btn_submit);
    }

    //获取用户输入
    private void getUserInput(){
        //获取EditText输入内容
        str_username = et_username.getText().toString().trim();
        str_paw = et_paw.getText().toString().trim();
        str_paw_again = et_paw_again.getText().toString().trim();
        str_individual_resume = et_individual_resume.getText().toString().trim();
        //获取radiobutton文字
        str_sex = resetOrGetTextByRB(1);
        //获取checkbox文字
        str_interest = resetOrGetTextByCB(1);
    }

    //用户输入的是否有空格，返回值布尔
    private boolean inputTextIsEmpty(){
        boolean isStringNull = !str_username.isEmpty() && !str_paw.isEmpty() && !str_paw_again.isEmpty()
                && !str_individual_resume.isEmpty() && !str_sex.isEmpty() && !str_interest.isEmpty();
        return isStringNull;
    }

    //重置textview
    private void resetET() {
        et_username.setText("");
        et_paw.setText("");
        et_paw_again.setText("");
        et_individual_resume.setText("");
    }

    //用户输入为空、密码不相等不能跳转页面
    private void canNotSubmit(){
        if (inputTextIsEmpty()) {
            //判断密码是否相等
            if (str_paw.equals(str_paw_again)) {
                canSubmit = true;
                Toast.makeText(MainActivity.this, "欢迎您，用户：" + str_username + "  !", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(MainActivity.this, "两次输入的密码不一致，请重新输入密码", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(MainActivity.this, "您有信息未填写！", Toast.LENGTH_SHORT).show();
        }
    }

    //封装数据、跳转页面
    private void canSubmit(){
        if (canSubmit) {
            Intent intent = new Intent(MainActivity.this, InfoActivity.class);
            Bundle bundle = new Bundle();
            //封装数据
            bundle.putString("username", str_username);
            bundle.putString("paw", str_paw);
            bundle.putString("individual_resume", str_individual_resume);
            bundle.putString("sex", str_sex);
            bundle.putString("interest", str_interest);
            intent.putExtras(bundle);
            //执行页面跳转
            startActivity(intent);
        }
    }

    /*
    *参数n==0,重置单选框;n==1,获取选中单选框条目文字
    * */
    private String resetOrGetTextByRB(int n) {
        String t = "";//存放选中的单选框的文字
        for (int i = 0; i < rad_select_sex.getChildCount(); i++) {
            RadioButton rb = (RadioButton) rad_select_sex.getChildAt(i);
            if (rb.isChecked()) {
                if (n == 0) {
                    rb.setChecked(false);
                } else if (n == 1) {
                    t = rb.getText().toString();
                }
            }
        }
        return t;
    }

    /*
    *参数n==0,重置checkbox;n==1,获取选中checkbox条目文字
    * */
    private String resetOrGetTextByCB(int n) {
        String t = "";
        for (int i = 0; i < linear_interest.getChildCount(); i++) {
            CheckBox cb = (CheckBox) linear_interest.getChildAt(i);
            if (cb.isChecked()) {
                if (n == 0) {
                    cb.setChecked(false);
                } else if (n == 1) {
                    t += cb.getText().toString() + "    ";
                }
            }
        }
        return t;
    }



}
