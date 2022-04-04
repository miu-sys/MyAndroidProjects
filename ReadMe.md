# 2021-09学期安卓课练习及作业工程

扔到github备份

初学者练习作品，优点是注释比较详细，有错见谅

每个task的描述如下


## task2

>目的

* Activity的创建、启动，无返回值的跳转
* 参数传递实现方法
* 清单文件AndroidManifest.xml的编写

>内容

1. 启动页显示页面A，输入数字X（例如2），点击A页面的提交按钮
2. 跳转至页面B，输入数字Y（例如3），点击B页面的提交按钮
3. 跳转至页面C，在页面C显示X+Y的结果（例如2+3=5）


## task3

>目的

* 带返回值的Activity的创建、启动、跳转

>内容

1. 显示启动页A，A内有数字X（其中X的值由B页面给定）
2. 页面A内有数字Y（其中Y的值由页面C给定）
3. 页面A点击计算按钮，得到X+Y的结果

<img src="https://cdn.jsdelivr.net/gh/miu-sys/myImageBed@img1//20220401android/20220401115203.png" height="500"/>


## task4

>目的

* 熟悉几种安卓布局，例如线性布局和相对布局

* 需要注意的是，把字符串资源放在`app-res-values-strings.xml`文件里，然后在布局文件`activity_main.xml`进行引用，方便后续修改（颜色资源也是一样，放colors.xml文件里），例如：
在strings.xml里
```java
<resources>
    <string name="app_name">work4</string>
    <string name="strTitle">中国家里蹲大学</string>
</resources>
```

在布局文件`activity_main.xml`里引用
```java
<TextView
    style="@style/ms_title"
    android:text="@string/strTitle">
</TextView>
```

* 如果有重复的style同上，把样式文件放在`app-res-values-styles-styles.xml`文件夹里

styles.xml
```
<?xml version="1.0" encoding="utf-8"?>
<resources>

    <style name="ms_title">
        <item name="android:layout_width">wrap_content</item>
        <item name="android:layout_height">wrap_content</item>
        <item name="android:layout_marginStart">50dp</item>
        <item name="android:textSize">32sp</item>
        <item name="android:textStyle">bold</item>
    </style>
    
</resources>

```

activity_main.xml

```java
<TextView
    style="@style/ms_title"
    android:text="@string/strTitle">
</TextView>
```

>内容


<img src="https://cdn.jsdelivr.net/gh/miu-sys/myImageBed@img1//20220401120915.png" height="500"/>


## task5

>目的

* Android常见布局
* 常见UI组件使用
* UI组件对监听事件的响应机制

>内容 写一个注册页面

页面A
- 点击提交按钮
    - 必须所有选项有值才能提交
    - 两次密码必须一致
    - 提交注册信息，并跳转至页面B
- 点击重置按钮
    - 重置已填写信息

页面B
- 显示A传过来的值

<img src="https://cdn.jsdelivr.net/gh/miu-sys/myImageBed@img1//20220401123841.png" height="500"/>

## task6

>目的

* GridView、ListView
* 简单事件监听

>内容 企业内部人员信息展示页

页面A
* listview数据显示页
  - 子布局可以包含人物头像、姓名、职称等信息，布局自定
  - 点击该项跳转至页面B

页面B
* 显示该人员的更为详细的信息

<img src="https://cdn.jsdelivr.net/gh/miu-sys/myImageBed@img1//20220401124654.png" height="500"/>

<img src="https://cdn.jsdelivr.net/gh/miu-sys/myImageBed@img1//20220401124727.png" height="500"/>

## task7

>目的

* listview
* sqlite

>内容 利用Listview开发一个简单的户通讯录

1. 可以添加用户，包括：姓名，号码，地址（长按，通过对话框或者菜单实现添加）
2. 添加用户时，新号码不能与已有号码冲突
3. 用列表展示联系人的姓名，号码，地址
4. 可以更新选中的联系人信息（姓名，号码，地址）
5. 更新用户号码时，新号码不能与其他人员的号码冲突
6. 可以删除选中的联系人（长按通过对话框或者菜单实现删除）
7. 可根据姓名、手机号实现模糊查找
8. 数据保存在SQLite数据库

* 页面A
    * 默认页面，主要组件是listview,实现查找、和数据的显示
    * listview点击跳转至页面B

* 页面B，实现用户的添加、修改

<img src="https://cdn.jsdelivr.net/gh/miu-sys/myImageBed@img1//20220401125513.png" height="500"/>

<img src="https://cdn.jsdelivr.net/gh/miu-sys/myImageBed@img1//20220401125542.png" height="500"/>


## task8

>目的

* 通过ContentResolver读取系统ContentProvider的方法
* SharedPreferences等文件存储的应用


>内容 手机短信息的本地备份

* 通过ContentResolver读取手机短信息
* 手机短信息备份在手机内部，文件格式为xml


## SchoolBus

>目的

* 进一步熟悉android开发
* 使用现成UI框架
[Material Design](https://material.io/components/text-fields)

>内容 简单的启动页、登录页

<img src="https://cdn.jsdelivr.net/gh/miu-sys/myImageBed@img1//20220401131520.png" height="500"/>
