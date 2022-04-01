package com.example.task7;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.task7.Adapter.StudentAdapter;
import com.example.task7.Entity.MyAlertDialog;
import com.example.task7.SQLiteHelper.MyHelper;
import com.example.task7.Entity.Students;
import com.example.task7.SQLiteHelper.StudentDao;


import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener,
        AdapterView.OnItemLongClickListener{

    //声明属性
    //region

    //用户输入控件
    private RadioButton rb_useName, rb_usePhoneNumber;
    private EditText et_userInput;
    private Button btn_search;
    //获取用户输入字符串
    private String str_userInput;

    //popup_content控件
    private View contentView;
    private PopupWindow window;
    private Button pop_add,pop_update,pop_del,pop_quit;

    //SQL相关
    private MyHelper myHelper;
    private StudentDao stuDao;

    //listview相关
    private ListView lv_data;//创建listview控件
    private StudentAdapter stuAdapter;//定义一个适配器
    private List<Students> studentsList = new ArrayList<Students>();//定义一个信息显示列表，作为数据源
    private int long_click_position;//长按点击返回的条目数

    //endregion


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //初始化
        init();
    }

    @Override
    protected void onStart() {
        //初始化listview
        stuDao.myExeQuery("name","");
        super.onStart();
    }



    //搜索按钮监、弹出菜单按钮
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_search:
                //判断单选框
                if (rb_useName.isChecked()) {
                    str_userInput = et_userInput.getText().toString();//根据姓名进行模糊搜索
                    stuDao.myExeQuery("name",str_userInput);
                } else if (rb_usePhoneNumber.isChecked()) {
                    str_userInput = et_userInput.getText().toString();//根据电话号码进行模糊搜索
                    stuDao.myExeQuery("phonenumber",str_userInput);
                }
                break;

            case R.id.pop_add:
                nextPageForInsert();//复用页面B进行新建
                window.dismiss();
                break;

            case R.id.pop_update:
                nextPageForUpdate(long_click_position);//跳转到页面B进行修改
                window.dismiss();
                break;

            case R.id.pop_del:
                creatAlertDialog("您确认删除吗?",0);//确认删除
                window.dismiss();
                break;

            case R.id.pop_quit:
                window.dismiss();
                break;
        }
    }


    //初始化
    private void init() {
        //初始化控件
        rb_useName = findViewById(R.id.rb_useName);
        rb_usePhoneNumber = findViewById(R.id.rb_usePhoneNumber);
        et_userInput = findViewById(R.id.et_userInput);
        btn_search = findViewById(R.id.btn_search);

        //将弹出布局文件popup_content转换为view对象
        contentView = LayoutInflater.from(MainActivity.this).inflate(R.layout.popup_content, null, false);

        //popup_content
        pop_add=contentView.findViewById(R.id.pop_add);
        pop_update=contentView.findViewById(R.id.pop_update);
        pop_del=contentView.findViewById(R.id.pop_del);
        pop_quit=contentView.findViewById(R.id.pop_quit);

        //设置监听
        btn_search.setOnClickListener(this);
        pop_add.setOnClickListener(this);
        pop_update.setOnClickListener(this);
        pop_del.setOnClickListener(this);
        pop_quit.setOnClickListener(this);


        //初始化listview
        lv_data = findViewById(R.id.lv_data);

        //创建适配器
        stuAdapter = new StudentAdapter(studentsList, MainActivity.this);
        //设置适配器到ListView
        lv_data.setAdapter(stuAdapter);

        //为listview添加点击事件监听
        lv_data.setOnItemClickListener(this);
        //为listview添加长按事件监听
        lv_data.setOnItemLongClickListener(this);

        //创建stuDao对象
        stuDao=new StudentDao(myHelper,this,stuAdapter,studentsList);

    }


    /**
     * listview长按事件
     * 长按跳出PopupWindow，展示添加、修改、删除、退出按钮
     * @param position 长按点击的listview的ID
     */
    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        long_click_position=position;//将长按点击的ID返回给int long_click_position
        //创建PopupWindow控件，将contentView加入其中
        window = new PopupWindow(contentView, 600, LinearLayout.LayoutParams.WRAP_CONTENT, true);
        window.setTouchable(true); //设置PopupWindow控件可点击
        //弹出显示（可设置位置）
        window.showAtLocation(getWindow().getDecorView(), Gravity.CENTER, 0, 0);
        return true;
    }

    /**
     * 创建删除确认对话框
     */
    public void creatAlertDialog(String msg,int i) {
        //确定按钮响应的单击操作
        DialogInterface.OnClickListener okListenner = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //删除操作
                delItem(long_click_position);
            }
        };
        //建立对话框
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        //设置提示信息
        builder.setTitle(msg);
        //设置按钮
        builder.setPositiveButton("确定",okListenner);
        builder.setNegativeButton("取消",null);
        //显示
        builder.show();
    }

    /**
     *删除选中的条目
     * @param data_position 长按listview的ID
     */
    public void delItem(int data_position){
        Students student = studentsList.get(data_position);
        int sql_id=student.getStuid();//获取listviewID对应的数据库数据的ID
        //进行删除操作
        String sql = "delete from students where _id=?";
        stuDao.myExeSQL(sql,new Object[]{sql_id});
        stuDao.myExeQuery("name","");
    }

    /**
     *listview点击事件
     * @param i 点击listview的ID
     */
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        nextPageForUpdate(i);
    }

    /**
     * 修改信息
     *跳转到下一个页面,并将listview点击的条目的值传过去
     * @param data_position 点击listview的ID
     */
    //根据list位置跳转到下一个页面
    public void nextPageForUpdate(int data_position){
        //封装数据
        //获取点击的条目对象
        Students students = studentsList.get(data_position);
        //新建intent、bundle对象
        Intent intent = new Intent(MainActivity.this, MainActivity2.class);
        Bundle bundle = new Bundle();
        //封装数据
        bundle.putInt("way",1);//way==1，编辑页面
        bundle.putInt("_id", students.getStuid());
        bundle.putString("name", students.getName());
        bundle.putString("phone_number", students.getPhoneNumber());
        bundle.putString("address", students.getAddress());
        intent.putExtras(bundle);
        //跳转页面
        startActivity(intent);
    }


    /**
     * 新建信息
     *跳转到下一个页面，复用页面B实现新建功能
     */
    public void nextPageForInsert(){
        //新建intent、bundle对象
        Intent intent = new Intent(MainActivity.this, MainActivity2.class);
        Bundle bundle = new Bundle();
        //封装数据
        bundle.putInt("way",2);//way==1，添加页面
        intent.putExtras(bundle);
        //跳转页面
        startActivity(intent);
    }

}