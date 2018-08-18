package com.mjd.imitate_jd.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.mjd.imitate_jd.R;
import com.mjd.imitate_jd.activity.DetailsActivity;
import com.mjd.imitate_jd.activity.SearchActivity;
import com.mjd.imitate_jd.app.MyApp;
import com.mjd.imitate_jd.bean.ClassifyChildBean;

import java.util.List;

public class ClassifyGridViewAdapter extends BaseAdapter {
    private List<ClassifyChildBean.DataBean.ListBean> list;

    public ClassifyGridViewAdapter(List<ClassifyChildBean.DataBean.ListBean> list) {
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder=null;
        if (convertView==null){
            convertView=LayoutInflater.from(MyApp.getContext()).inflate(R.layout.classify_rigth_grid_item,parent,false);
            holder=new ViewHolder();
            holder.simpleDraweeView=convertView.findViewById(R.id.classify_right_sdv_item_item);
            holder.textView=convertView.findViewById(R.id.classify_right_tv_item_item);
            convertView.setTag(holder);
        }else{
            holder= (ViewHolder) convertView.getTag();
        }
        holder.textView.setText(list.get(position).getName());
        holder.simpleDraweeView.setImageURI(list.get(position).getIcon());
        holder.simpleDraweeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyApp.getContext(),SearchActivity.class);
                intent.putExtra("name",list.get(position).getName());
                MyApp.getContext().startActivity(intent);
            }
        });
        return convertView;
    }

    class ViewHolder{
        SimpleDraweeView simpleDraweeView;
        TextView textView;
    }

}
