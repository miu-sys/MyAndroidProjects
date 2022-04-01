package com.example.mylistview.adapter;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mylistview.R;
import com.example.mylistview.entity.Person;

import java.util.ArrayList;
import java.util.List;

/*
 * task 6 created by 399 on 2021-11-24
 *自定义适配器
 * */
public class PersonAdapter extends BaseAdapter {
    //人物信息数据
    private List<Person> pdata = new ArrayList<Person>();
    //上下文
    private Context context;

    //构造方法
    public PersonAdapter(List<Person> pdata, Context context) {
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
        public ImageView myimg;
        public TextView myname;
        public TextView myjob;
        public TextView myage;
        public TextView mysex;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        //定义一个ViewHolder对象
        ViewHolder holder;
        //判断view是否为空，view对应列表项
        if (view == null) {
            //新建
            holder = new ViewHolder();
            view = LayoutInflater.from(context).inflate(R.layout.simple_list_item_1, viewGroup, false);

            holder.myimg = (ImageView) view.findViewById(R.id.item_img);
            holder.myname = (TextView) view.findViewById(R.id.item_name);
            holder.myjob = (TextView) view.findViewById(R.id.item_job);
            holder.myage = (TextView) view.findViewById(R.id.item_age);
            holder.mysex=(TextView)view.findViewById(R.id.item_sex) ;

            view.setTag(holder);
        } else {
            //复用列表项
            holder = (ViewHolder) view.getTag();
        }
        //设置列表项数据
        holder.myimg.setImageResource(pdata.get(i).getImg());
        holder.myname.setText(pdata.get(i).getName());
        holder.myage.setText(pdata.get(i).getAge());
        holder.mysex.setText(pdata.get(i).getSex());
        holder.myjob.setText(pdata.get(i).getJob());
        return view;

    }

}
