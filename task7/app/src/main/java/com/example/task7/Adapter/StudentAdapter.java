package com.example.task7.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.task7.R;
import com.example.task7.Entity.Students;

import java.util.ArrayList;
import java.util.List;

/*
 * Created by 399 on 2021/12/04
 *自定义适配器
 * */

public class StudentAdapter extends BaseAdapter {
    //人物信息数据
    private List<Students> pdata = new ArrayList<Students>();
    //上下文
    private Context context;

    //构造方法
    public StudentAdapter(List<Students> pdata, Context context) {
        this.pdata = pdata;
        this.context = context;
    }

    @Override
    public int getCount() {
        return pdata.size();//获取列表数据个数
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;//返回列表项在数据中的索引
    }

    //优化ListView
    //定义一个ViewHolder静态类
    static class ViewHolder {
        //定义控件属性，对应列表项数据
        public TextView myid;
        public TextView myname;
        public TextView myphonenumber;
        public TextView myaddress;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        //定义一个ViewHolder对象
        ViewHolder holder;
        //判断view是否为空，view对应列表项
        if (view == null) {
            //新建
            holder = new ViewHolder();
            view = LayoutInflater.from(context).inflate(R.layout.students_list_item, viewGroup, false);

            holder.myid = (TextView) view.findViewById(R.id.item_title);
            holder.myname = (TextView) view.findViewById(R.id.item_name);
            holder.myphonenumber = (TextView) view.findViewById(R.id.item_phonenumber);
            holder.myaddress = (TextView) view.findViewById(R.id.item_address);
            view.setTag(holder);
        } else {
            //复用列表项
            holder = (ViewHolder) view.getTag();
        }
        //设置列表项数据
        //注意获取到的列表项数据的数据类型！！！！！！
        holder.myid.setText(pdata.get(i).getStuid().toString());
        holder.myname.setText(pdata.get(i).getName());
        holder.myphonenumber.setText(pdata.get(i).getPhoneNumber());
        holder.myaddress.setText(pdata.get(i).getAddress());

        return view;

    }

}