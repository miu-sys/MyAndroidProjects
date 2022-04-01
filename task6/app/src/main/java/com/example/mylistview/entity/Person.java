package com.example.mylistview.entity;
/*
* Created by 399 on 2021/11/24
*人物信息实体类
* */
public class Person {
    //人物信息属性
    private String name;//姓名
    private int img;//头像
    private String age;//年龄
    private String sex;//性别
    private String job;//职称
    private String phoneNumber;//电话号码

    //构造方法
    public Person(String name, int img, String age, String sex, String job, String phoneNumber) {
        this.name = name;
        this.img = img;
        this.age = age;
        this.sex = sex;
        this.job = job;
        this.phoneNumber = phoneNumber;
    }


    //set、get
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
