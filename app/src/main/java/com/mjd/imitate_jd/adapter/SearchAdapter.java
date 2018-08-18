package com.mjd.imitate_jd.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.mjd.imitate_jd.R;
import com.mjd.imitate_jd.app.MyApp;
import com.mjd.imitate_jd.bean.SearchBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchAdapter extends RecyclerView.Adapter {
    private List<SearchBean.DataBean> list;

    public SearchAdapter(List<SearchBean.DataBean> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(MyApp.getContext()).inflate(R.layout.search_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof ViewHolder){
            ((ViewHolder) holder).searchSdvItem.setImageURI(list.get(position).getImages().split("\\|")[0]);
            ((ViewHolder) holder).searchTvTitleItem.setText(list.get(position).getTitle());
            ((ViewHolder) holder).searchTvPriceItem.setText("¥"+list.get(position).getPrice());

            //给子布局设置点击事件
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemOnClickListener.itemOnClickListener(holder,position);
                }
            });
        }


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.search_sdv_item)
        SimpleDraweeView searchSdvItem;
        @BindView(R.id.search_tv_title_item)
        TextView searchTvTitleItem;
        @BindView(R.id.search_tv_price_item)
        TextView searchTvPriceItem;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    private setItemOnClickListener itemOnClickListener;

    public void setItemOnClickListener(setItemOnClickListener itemOnClickListener) {
        this.itemOnClickListener = itemOnClickListener;
    }

    public interface setItemOnClickListener{
        void itemOnClickListener(RecyclerView.ViewHolder view, int position);
    }

}
