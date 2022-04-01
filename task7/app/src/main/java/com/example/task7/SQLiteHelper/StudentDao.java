package com.example.task7.SQLiteHelper;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.example.task7.Adapter.StudentAdapter;
import com.example.task7.Entity.Students;

import java.util.ArrayList;
import java.util.List;

public class StudentDao {
    //SQL相关
    private MyHelper myHelper;
    private SQLiteDatabase db;
    private Context context;

    //listview相关
    private ListView lv_data;//创建listview控件
    private StudentAdapter stuAdapter;//定义一个适配器
    private List<Students> studentsList = new ArrayList<Students>();//定义一个信息显示列表，作为数据源

    public StudentDao(MyHelper myHelper, Context context, StudentAdapter stuAdapter, List<Students> studentsList) {
        this.myHelper = myHelper;
        this.context = context;
        this.stuAdapter = stuAdapter;
        this.studentsList = studentsList;
    }

    /**
    * 查询的通用操作
     * @param s 查询的列名
     * @param str_userInput 用户输入的字符串
    * */
    public void myExeQuery(String s,String str_userInput){
        myHelper= (MyHelper) MyHelper.getInstance(context);
        db=myHelper.getReadableDatabase();
        String sql="select * from students where "+s+" like '%" + str_userInput + "%'";
        Cursor cursor = db.rawQuery(sql, null);
        if (db.isOpen()) {
/*            String sss=String.valueOf(cursor.getCount());
            Toast.makeText(context, "获取到数据"+sss+"条", Toast.LENGTH_SHORT).show();*/
            if(cursor.getCount()==0){
                Toast.makeText(context, "没有数据", Toast.LENGTH_SHORT).show();
            }else {
                studentsList.clear();//清空之前的查询数据条目
                while (cursor.moveToNext()) {
                    int _id = cursor.getInt(Integer.valueOf(cursor.getColumnIndex("_id")));
                    String phonenumber = cursor.getString(Integer.valueOf(cursor.getColumnIndex("phonenumber")));
                    String name = cursor.getString(Integer.valueOf(cursor.getColumnIndex("name")));
                    String address = cursor.getString(Integer.valueOf(cursor.getColumnIndex("address")));
                    Students student = new Students(_id, phonenumber, name, address);
                    studentsList.add(student);
                    stuAdapter.notifyDataSetChanged(); //通知UI线程刷新界面ListView
                }
            }
        }
        cursor.close();
        //db.close();???
    }

    /**
     * 修改、删除、新增的通用操作
     * @param sql sql语句
     * @param objects 对象
     * */
    public void myExeSQL(String sql,Object[] objects){
        db=myHelper.getWritableDatabase();
        if(db.isOpen()){
            db.execSQL(sql,objects);
        }
    }

    /**
     * 与数据库字符串对比的通用操作
     * @param sql sql语句
     * @return true 没有重复的对象，false 有重复对象
     * @param str 要比对的字符串
     * */
    public boolean myComparison(String sql,String str){
        db=myHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        if (db.isOpen()) {
                while (cursor.moveToNext()) {
                    String item = cursor.getString(Integer.valueOf(cursor.getColumnIndex("phonenumber")));
                    if (str.equals(item)){
                        return false;
                    }
                }
        cursor.close();
        db.close();
        return true;
        }else{
            Toast.makeText(context, "数据库打开失败", Toast.LENGTH_SHORT).show();
            return false;
        }

    }
}
