package com.mjd.imitate_jd.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mjd.imitate_jd.R;
import com.mjd.imitate_jd.app.MyApp;
import com.mjd.imitate_jd.bean.ClassifyBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ClassifyLeftAdapter extends RecyclerView.Adapter {
    private List<ClassifyBean.DataBean> list;

    public ClassifyLeftAdapter(List<ClassifyBean.DataBean> list) {
        this.list = list;
    }
   /* //当前Item被点击的位置
    private int currentItem;
    public void setCurrentItem(int currentItem) {
        this.currentItem = currentItem;
    }*/

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(MyApp.getContext()).inflate(R.layout.classify_left_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof ViewHolder){
            ((ViewHolder) holder).classifyTvItem.setText(list.get(position).getName());
            ((ViewHolder) holder).classifyTvItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemClickListener.itemClickListener(holder,position);

                }
            });
            /*if (currentItem == position) {
                //如果被点击，设置当前TextView被选中
                ((ViewHolder) holder).classifyTvItem.setSelected(true);
            } else {
                //如果没有被点击，设置当前TextView未被选中
                ((ViewHolder) holder).classifyTvItem.setSelected(false);
            }*/
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.classify_tv_item)
        TextView classifyTvItem;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    private setItemClickListener itemClickListener;

    public void setItemClickListener(setItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public interface setItemClickListener{
        void itemClickListener(RecyclerView.ViewHolder view, int position);
    }

}
