package com.example.task7.Entity;
/*
 * Created by 399 on 2021/12/04
 *学生信息实体类
 * */

public class Students {
    //学生信息属性
    private Integer stuid;//ID
    private String phoneNumber;//电话号码
    private String name;//姓名
    private String address;//地址

    public Students(Integer stuid, String phoneNumber,String name, String address) {
        this.stuid = stuid;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    public Integer getStuid() {
        return stuid;
    }

    public void setStuid(Integer stuid) {
        this.stuid = stuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
