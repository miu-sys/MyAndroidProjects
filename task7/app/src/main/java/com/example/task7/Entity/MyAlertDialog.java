package com.example.task7.Entity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.Toast;

import com.example.task7.MainActivity;

public class MyAlertDialog {

    private Context context;
    public MyAlertDialog(Context context) {
        this.context = context;
    }

    /**
     * 创建删除或者修改的确认对话框
     * @param msg 用户提示信息：您确认删除/修改吗？
     * @param okListenner 点击对话框确认按钮的监听事件
     */
    public void creatAlertDialog(String msg,DialogInterface.OnClickListener okListenner) {
        //建立对话框
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        //设置提示信息
        builder.setTitle(msg);
        //设置按钮
        builder.setPositiveButton("确定",okListenner);
        builder.setNegativeButton("取消",null);
        //显示
        builder.show();
    }
}
