package com.example.mylistview;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mylistview.adapter.PersonAdapter;
import com.example.mylistview.entity.Person;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/*
 * Created by 399 on 2021/11/24
 * */

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private static final String TAG = "MainActivity";
    //定义数组，用于存储人物信息数据
    public String[] names = {"赵一", "王二", "林三酒", "李四", "钱五", "孙小六",
            "诸葛青", "西门吹雪", "周九", "陈拾", "吴十一", "王十二"};
    public int[] imgs = {R.mipmap.tx1, R.mipmap.tx2, R.mipmap.tx3, R.mipmap.tx4, R.mipmap.tx5, R.mipmap.tx6,
            R.mipmap.tx1, R.mipmap.tx2, R.mipmap.tx3, R.mipmap.tx4, R.mipmap.tx5, R.mipmap.tx6};
    public String[] ages = {"22", "18", "28", "36", "26", "18",
            "18", "24", "18", "32", "48", "18"};
    public String[] sexs = {"男", "女", "女", "男", "男", "男",
            "男", "女", "女", "男", "男", "男"};
    public String[] jobs={"总经理","副总经理","办公部部长","法务部部长","宣传部部长","客服部部长",
            "开发部部长","办公部职员","宣传部职员","客服部职员","法务部职员","法务部职员",};
    public String[] phoneNumber = {"123-456", "123-002", "123-555", "123-666", "123-006", "123-400", "123-899",
            "123-776", "123-716", "123-111", "123-888", "123-058", "123-444"};
    //创建listview控件
    private ListView lv_info;
    //定义一个适配器
    private PersonAdapter myAdatper;
    //定义一个信息显示列表，作为数据源
    private List<Person> Info = new ArrayList<Person>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //初始化控件
        lv_info = findViewById(R.id.lv_info);
        //初始化数据
        initDataInfo();
        //创建适配器
        myAdatper = new PersonAdapter(Info, MainActivity.this);
        //设置适配器到ListView
        lv_info.setAdapter(myAdatper);
        //添加监听
        lv_info.setOnItemClickListener(this);
    }

    //实现点击方法
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        //获取点击的条目对象
        Person person = Info.get(i);
        //新建intent、bundle对象
        Intent intent=new Intent(MainActivity.this,InfoActivity.class);
        Bundle bundle=new Bundle();
        //封装数据
        bundle.putString("name",person.getName());
        bundle.putString("job",person.getJob());
        bundle.putString("age",person.getAge());
        bundle.putString("sex",person.getSex());
        bundle.putString("phone_number",person.getPhoneNumber());
        bundle.putInt("head_portrait",person.getImg());
        intent.putExtras(bundle);
        //执行页面跳转
        startActivity(intent);
    }

    //初始化数据
    private void initDataInfo() {
        for (int i = 0; i < names.length; i++) {
            //新建Person对象，存放人物信息数据（头像、姓名）
            Person person = new Person(names[i], imgs[i], ages[i], sexs[i],jobs[i],phoneNumber[i]);
            //将人物信息数据加入到数据列表中
            Info.add(person);
        }
    }


}