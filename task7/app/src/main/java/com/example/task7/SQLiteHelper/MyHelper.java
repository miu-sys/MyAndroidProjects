package com.example.task7.SQLiteHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class MyHelper extends SQLiteOpenHelper {
    // 步骤1：设置数据库和表名常量
    private static final String DATABASE_NAME = "students.db";
    private static final String TABLE_NAME = "students";
    private static final int DATABASE_VERSION = 1;

    //步骤2:构造函数私有化
    private MyHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    //步骤3:对外提供函数
    private static SQLiteOpenHelper mInstance;
    public static synchronized SQLiteOpenHelper getInstance(Context context){
        if (mInstance==null){
            mInstance=new MyHelper(context,DATABASE_NAME,null,DATABASE_VERSION);//以后想要数据库升级，修改版本号为2、3...
        }
        return mInstance;
    }

    //数据库初始化用的，初始化函数只执行一次
    //如果修改了onCreate的内容，一定要删除database文件夹再重新执行！！！
    @Override
    public void onCreate(SQLiteDatabase db) {
        /*步骤4：数据库表的创建
        创建表persons(加s) _id name
        主键：primary key 必须唯一的,类型必须是integer
        自动增长数字：例如：1 2 3 4 5 6 autoincrement*/

        String strSQL = "create table " + TABLE_NAME
                + " (_id integer primary key autoincrement,"
                + " phonenumber varchar(100), name varchar(100),"
                + " address varchar(100))";
        db.execSQL(strSQL);

        //给数据库初始化一些数据
        for (int i = 0; i <= 9; i++) {
            ContentValues values = new ContentValues();
            values.put("phonenumber", "1351234000" + i);
            values.put("name", "赵" + i);
            values.put("address", "成都市");
            db.insert("students", null, values);
        }
    }

    //数据库升级用的
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}