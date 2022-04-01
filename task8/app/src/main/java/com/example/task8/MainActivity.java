package com.example.task8;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.text.method.ScrollingMovementMethod;
import android.util.Xml;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.task8.SP.SPSMS;
import com.example.task8.entity.SmsInfo;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tv_sms;
    private Button btn_readSMS;
    private Button btn_writeSMS;
    private String text = "";
    private List<SmsInfo> smsInfos;
    private SPSMS spsms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();//初始化
    }

    //点击按钮
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_readSMS:
                readSMS();
                showInfo();
                break;

            case R.id.btn_writeSMS:
                WriteToLocal();
                //writeSMS();
                break;

        }
    }


    /**
     * 读取系统短信
     */
    private List<SmsInfo> readSMS() {
        smsInfos.clear();//首先清除list
        //查询系统信息的uri
        Uri uri = Uri.parse("content://sms/");
        //获取ContentResolver对象
        ContentResolver resolver = getContentResolver();
        //通过ContentResolver对象查询系统短信
        Cursor cursor = resolver.query(uri,new String[]{"_id","address","type","body","date"},null,null,null);
        if(cursor!=null&&cursor.getCount()>0){
            while(cursor.moveToNext()){
                int _id = cursor.getInt(0);
                String address = cursor.getString(1);
                int type = cursor.getInt(2);
                String body = cursor.getString(3);
                long date = cursor.getLong(4);
                SmsInfo smsInfo = new SmsInfo(_id,address,type,body,date);
                smsInfos.add(smsInfo);
            }
            cursor.close();
            Toast.makeText(this, "读取成功！", Toast.LENGTH_SHORT).show();
        }
        return smsInfos;
    }

    /**
     * 将查询到的短信内容显示到界面上
     */
    private void showInfo(){
        tv_sms.setTextColor(getResources().getColor(R.color.black));
        if (!tv_sms.getText().toString().equals("")) {
            //有内容，清空text
            text = "";
        }
        for(int i=0;i<smsInfos.size();i++){
            text += "手机号码："+smsInfos.get(i).getAddress()+"\n";
            text += "短信内容："+smsInfos.get(i).getBody()+"\n\n\n";
        }
            tv_sms.setText(text);
    }

    /**
     * 保存数据方法二：序列化到本地
     */
    private void WriteToLocal(){
        //获得一个序列化对象
        XmlSerializer xmlSerializer=Xml. newSerializer();
        /*将生成的 xml文件保存到存储空间中名字为"sms.xml"
        *getFilesDir()内部存储空间。getExternalFilesDir()外部存储空间。
        * 外部存储空间保存在手机系统目录Android/data/com.example.task8/files文件夹下
         * 内容存储空间在编译器View-Tools windows-Device File Explorer窗口的/data/data/com.example.task8/files文件夹下
         * */

        File file= new File(getFilesDir() , "sms.xml");
        FileOutputStream fos;
        try {
            fos = new FileOutputStream(file);
            xmlSerializer.setOutput(fos, "utf-8");
            xmlSerializer.startDocument( "utf-8", true);
            xmlSerializer.startTag( null, "smss");

            xmlSerializer.startTag( null, "count");
            xmlSerializer.text( smsInfos.size()+ "");
            xmlSerializer.endTag( null, "count");

            for(SmsInfo smsData: smsInfos){

                xmlSerializer.startTag( null, "sms");

                xmlSerializer.startTag( null, "_id");
                xmlSerializer.text(smsData.get_id()+ "");
                System. out.println( "smsData.get_id()=" +smsData.get_id());
                xmlSerializer.endTag( null, "_id");

                xmlSerializer.startTag( null, "type");
                xmlSerializer.text(smsData.getType()+ "");
                System. out.println( "smsData.getType=" +smsData.getType());
                xmlSerializer.endTag( null, "type");

                xmlSerializer.startTag( null, "address");
                xmlSerializer.text(smsData.getAddress()+ "");
                System. out.println( "smsData.getAddress()=" +smsData.getAddress());
                xmlSerializer.endTag( null, "address");

                xmlSerializer.startTag( null, "body");
                xmlSerializer.text(smsData.getBody()+ "");
                System. out.println( "smsData.getBody()=" +smsData.getBody());
                xmlSerializer.endTag( null, "body");

                xmlSerializer.startTag( null, "date");
                xmlSerializer.text(smsData.getDate()+ "");
                System. out.println( "smsData.getDate()=" +smsData.getDate());
                xmlSerializer.endTag( null, "date");

                xmlSerializer.endTag( null, "sms");
            }
            xmlSerializer.endTag( null, "smss");
            xmlSerializer.endDocument();

            fos.flush();
            fos.close();
            Toast. makeText( this, "备份完成", Toast.LENGTH_SHORT ).show();
        } catch (FileNotFoundException e) {
            Toast. makeText( this, "FileNotFoundException", Toast.LENGTH_SHORT ).show();
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            Toast. makeText( this, "IllegalArgumentException", Toast.LENGTH_SHORT ).show();
            e.printStackTrace();
        } catch (IllegalStateException e) {
            Toast. makeText( this, "IllegalStateException", Toast.LENGTH_SHORT ).show();
            e.printStackTrace();
        } catch (IOException e) {
            Toast. makeText( this, "IOException", Toast.LENGTH_SHORT ).show();
            e.printStackTrace();
        }

    }

    /**
     * 保存数据方法一：通过SharedPreferences将短信保存到xml文件进行备份
     */
    private void writeSMS() {
        //保存用户信息
        boolean isSaveSuccess = spsms.saveSMSInfo(this,smsInfos);;
        if(isSaveSuccess){
            Toast.makeText(this,"保存成功",Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this,"保存失败",Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 初始化UI控件、申请权限
     */
    public void init(){
        smsInfos = new ArrayList<SmsInfo>();
        spsms = new SPSMS();
        tv_sms = (TextView) findViewById(R.id.tv_sms);
        tv_sms.setMovementMethod(ScrollingMovementMethod.getInstance());//设置textview为可滑动
        btn_readSMS = (Button)findViewById(R.id.btn_readSMS);
        btn_writeSMS = (Button)findViewById(R.id.btn_writeSMS);
        //设置监听
        btn_readSMS.setOnClickListener(this);
        btn_writeSMS.setOnClickListener(this);
        queryAuthority();//申请权限
    }

    /**
     * 判断，如果系统没有该权限，就申请权限
     */
    private void queryAuthority() {
        int hasPermission = 0;
        int hasPermission1 = 0;
        int hasPermission2 = 0;
        int hasPermission3 = 0;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {//检查权限
            hasPermission = checkSelfPermission(Manifest.permission.READ_SMS);
            hasPermission1 = checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE);
            hasPermission2 = checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (hasPermission != PackageManager.PERMISSION_GRANTED) {//如果没有授予该权限
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.READ_SMS}, 123);
            }
            return;
        }
        if (hasPermission1 != PackageManager.PERMISSION_GRANTED) {//如果没有授予该权限
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 123);
            }
            return;
        }
        if (hasPermission2 != PackageManager.PERMISSION_GRANTED) {//如果没有授予该权限
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 123);
            }
            return;
        }
    }

    /**
     *如果没有成功申请到权限，提示用户缺少权限
     */
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 123:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    queryAuthority();
                } else {
                    Toast.makeText(MainActivity.this, "缺少权限", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }

    }
}