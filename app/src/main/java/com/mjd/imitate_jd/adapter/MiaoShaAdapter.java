package com.mjd.imitate_jd.adapter;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.mjd.imitate_jd.R;
import com.mjd.imitate_jd.activity.DetailsActivity;
import com.mjd.imitate_jd.activity.WebActivity;
import com.mjd.imitate_jd.app.MyApp;
import com.mjd.imitate_jd.bean.HomesBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MiaoShaAdapter extends RecyclerView.Adapter {
    private List<HomesBean.DataBean.MiaoshaBean.ListBean> list;

    public MiaoShaAdapter(List<HomesBean.DataBean.MiaoshaBean.ListBean> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(MyApp.getContext()).inflate(R.layout.home_miaosha_item_grid, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof ViewHolder){
            ((ViewHolder) holder).homeMiaoshaSdvItem.setImageURI(list.get(position).getImages().split("\\|")[0]);
            ((ViewHolder) holder).homeMiaoshaTvItem.setText(list.get(position).getTitle());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MyApp.getContext(), DetailsActivity.class);
                    intent.putExtra("pid",list.get(position).getPid());
                    MyApp.getContext().startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.home_miaosha_sdv_item)
        SimpleDraweeView homeMiaoshaSdvItem;
        @BindView(R.id.home_miaosha_tv_item)
        TextView homeMiaoshaTvItem;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
