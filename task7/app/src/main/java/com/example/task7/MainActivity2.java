package com.example.task7;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.task7.SQLiteHelper.MyHelper;
import com.example.task7.SQLiteHelper.StudentDao;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity2 extends AppCompatActivity implements View.OnClickListener {

    //声明属性
    //region

    //电话号码格式
    public final static String PHONE_PATTERN = "^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$";

    //UI控件属性
    private TextView tv_amendTitle;
    private EditText et_name, et_phone, et_address;
    private Button btn_cancel, btn_save;
    private ImageView img_back;

    //SQL相关
    private MyHelper myHelper;
    private SQLiteDatabase db;
    private StudentDao studentDao;

    //用户输入的字符串
    private String str_name, str_phonenumber, str_address;

    //接收A页面的数据
    Intent intent;
    Bundle bundle;

    //接收到的数据
    int i_id;//接收到的唯一id号
    int way;//判断修改或者新建

    //endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_info);

        inti();//初始化

    }

    //初始化
    private void inti() {
        tv_amendTitle=findViewById(R.id.tv_amendTitle);
        et_name = findViewById(R.id.et_name);
        et_phone = findViewById(R.id.et_phone);
        et_address = findViewById(R.id.et_address);
        btn_save = findViewById(R.id.btn_save);
        btn_cancel = findViewById(R.id.btn_cancel);
        img_back = findViewById(R.id.img_back);
        //设置监听
        btn_save.setOnClickListener(this);
        btn_cancel.setOnClickListener(this);
        img_back.setOnClickListener(this);

        //创建Helper对象
        myHelper = (MyHelper) MyHelper.getInstance(this);
        studentDao=new StudentDao(myHelper,this,null,null);
        //获取A页面传过来的数据
        intent = getIntent();
        bundle = intent.getExtras();
        way=bundle.getInt("way");
        if(way==1){//way==1，修改页面
            //接受注册页面传来的数据并设置给控件
            getDataForUpdate();
        }else if(way==2){//way==2，添加页面
            tv_amendTitle.setText("添加页面");
            btn_save.setText("添加");
        }
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_save://添加或者修改
                if (way==1){//修改
                    amendStuData();
                }else if(way==2) {//添加
                    addData();
                }
                break;

            case R.id.btn_cancel://取消选项
                //返回上一页面
                finish();
                break;

            case R.id.img_back://返回上一页面
                finish();
                break;
        }
    }

    /**
     * 保存按钮，保存（update）修改内容
     * 获取edittext内容，必须不为空,电话号码格式正确，并不重复
     * 获取该数据ID，根据ID匹配数据库数据条目，并更新数据。
     */
    private void amendStuData() {
        if(canNext()){//更新数据库
            String sql = "update students set name=?,phonenumber=?,address=? where _id=?";
            //修改
            studentDao.myExeSQL(sql,new Object[]{str_name,str_phonenumber,str_address,i_id});
            //提示用户修改成功
            Toast.makeText(this, "修改成功！", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 添加信息
     * 注意对比电话号码格式，电话号码不能重复
     */
    private void addData() {
        if(canNext()){
            String sql = "insert into students values(null,?,?,?)";
            //添加
            studentDao.myExeSQL(sql,new Object[]{str_phonenumber,str_name,str_address});
            Toast.makeText(this, "添加成功！", Toast.LENGTH_SHORT).show();
            et_name.setText("");
            et_address.setText("");
            et_phone.setText("");
        }
    }








    /**
     * 接受A页面传来的数据并设置给控件
     */
    private void getDataForUpdate() {
        intent = getIntent();
        bundle = intent.getExtras();
        i_id = bundle.getInt("_id");
        et_name.setText(bundle.getString("name"));
        et_phone.setText(bundle.getString("phone_number"));
        et_address.setText(bundle.getString("address"));
    }

    /**
     * 获取用户输入的字符串并判空
     * @return true 用户输入的没有空值, else false 用户输入的有空值
     */
    private boolean getUserInput() {
        str_name = et_name.getText().toString().trim();
        str_phonenumber = et_phone.getText().toString().trim();
        str_address = et_address.getText().toString().trim();
        return (!str_name.isEmpty() && !str_phonenumber.isEmpty() && !str_address.isEmpty());
    }

    /**
     * 正则表达式匹配判断
     * @param patternStr 匹配规则
     * @param input 需要做匹配操作的字符串
     * @return true if matched, else false
     */
    public static boolean isMatchered(String patternStr, CharSequence input) {
        Pattern pattern = Pattern.compile(patternStr);
        Matcher matcher = pattern.matcher(input);
        if (matcher.find()) {
            return true;
        }
        return false;
    }

    /**
     * 用户添加修改的信息是否正确
     * 确保用户输入的没有空值，电话号码格式正确，电话号码不与已有项重复
     */
    private boolean canNext(){
        getUserInput();//获取用户输入的字符串
        if (!getUserInput())//如果用户输入的有空值,提示用户
        {
            Toast.makeText(this, "您还有数据没有输入", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(!isMatchered(PHONE_PATTERN,str_phonenumber)){//用户输入的电话号码格式不正确
            Toast.makeText(this, "您输入的电话号码格式不正确", Toast.LENGTH_SHORT).show();
            return false;
        }
        //与数据库比对是否有重复的电话号码
        String sql="select * from students where _id!="+i_id;
        boolean b = studentDao.myComparison(sql,str_phonenumber);
        if (!b){//如果电话号码与已有的重复
            Toast.makeText(this, "您输入的电话号码已经存在", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

}