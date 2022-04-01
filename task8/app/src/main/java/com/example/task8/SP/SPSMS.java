package com.example.task8.SP;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.task8.entity.SmsInfo;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;

public class SPSMS {
    //将短信息保存到xml文件
    public static boolean saveSMSInfo(Context context, List<SmsInfo> smsInfos){
        SharedPreferences sp = context.getSharedPreferences("SMSdata",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        //SharedPreferences只能保存Map型的数据，必须要做其他转换才行
        //存入json串
        Gson gson = new Gson();
        String jsonStr=gson.toJson(smsInfos);
        editor.putString("sms",jsonStr);
        for (int i=0;i<smsInfos.size();i++){
            editor.putString("phoneNumber","手机号码："+smsInfos.get(i).getAddress()+"\n");
            editor.putString("SMSContent","短信内容："+smsInfos.get(i).getBody()+"\n");
        }
        editor.commit();//commit方法将数据写入文件
        return true;
    }
}
