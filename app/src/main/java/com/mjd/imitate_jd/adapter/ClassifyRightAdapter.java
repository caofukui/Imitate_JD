package com.mjd.imitate_jd.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

import com.mjd.imitate_jd.R;
import com.mjd.imitate_jd.app.MyApp;
import com.mjd.imitate_jd.bean.ClassifyChildBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ClassifyRightAdapter extends RecyclerView.Adapter {
    private List<ClassifyChildBean.DataBean> list;

    public ClassifyRightAdapter(List<ClassifyChildBean.DataBean> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(MyApp.getContext()).inflate(R.layout.classify_rigth_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof ViewHolder){
            ((ViewHolder) holder).classifyTvRightItem.setText(list.get(position).getName());
            ((ViewHolder) holder).classifyGvRightItem.setAdapter(new ClassifyGridViewAdapter(list.get(position).getList()));
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.classify_tv_right_item)
        TextView classifyTvRightItem;
        @BindView(R.id.classify_gv_right_item)
        GridView classifyGvRightItem;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

}
