package com.example.task8.entity;

public class SmsInfo {
    private int _id;                    // 短信的主键
    private String address;           // 发送地址
    private int type;                  // 类型
    private String body;              // 短信内容
    private long date;                // 时间
    // 构造方法
    public SmsInfo(int _id, String address, int type, String body, long date) {
        this._id = _id;
        this.address = address;
        this.type = type;
        this.body = body;
        this.date = date;
    }
    public int get_id() {
        return _id;
    }
    public void set_id(int _id) {
        this._id = _id;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public int getType() {
        return type;
    }
    public void setType(int type) {
        this.type = type;
    }
    public String getBody() {
        return body;
    }
    public void setBody(String body) {
        this.body = body;
    }
    public long getDate() {
        return date;
    }
    public void setDate(long date) {
        this.date = date;
    }
}

