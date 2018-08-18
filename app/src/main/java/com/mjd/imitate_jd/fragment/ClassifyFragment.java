package com.mjd.imitate_jd.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.mjd.imitate_jd.R;
import com.mjd.imitate_jd.activity.DetailsActivity;
import com.mjd.imitate_jd.adapter.ClassifyLeftAdapter;
import com.mjd.imitate_jd.adapter.ClassifyRightAdapter;
import com.mjd.imitate_jd.app.MyApp;
import com.mjd.imitate_jd.base.BaseFragment;
import com.mjd.imitate_jd.bean.ClassifyBean;
import com.mjd.imitate_jd.bean.ClassifyChildBean;
import com.mjd.imitate_jd.mvp.classify.presenter.ClassifyPresenter;
import com.mjd.imitate_jd.mvp.classify.view.IClassifyView;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ClassifyFragment extends BaseFragment implements IClassifyView, OnBannerListener {
    private RecyclerView classfiy_left_rv,classfiy_right_rv;
    private ClassifyPresenter classifyPresenter;
    private String cid="1";
    private ClassifyLeftAdapter classifyLeftAdapter;
    private ClassifyRightAdapter classifyRightAdapter;
    private Banner classfiy_left_banner;
    private ArrayList<String> list_path;
    private ArrayList<String> list_title;

    public ClassifyFragment() {
        // Required empty public constructor
    }


    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        classifyPresenter=new ClassifyPresenter(this);
        classifyPresenter.setClassify();
        classifyPresenter.setChildClassify(cid);
        //放图片地址的集合
        list_path = new ArrayList<>();
        //放标题的集合
        list_title = new ArrayList<>();

        list_path.add("http://ww4.sinaimg.cn/large/006uZZy8jw1faic21363tj30ci08ct96.jpg");
        list_path.add("http://ww4.sinaimg.cn/large/006uZZy8jw1faic259ohaj30ci08c74r.jpg");
        list_path.add("http://ww4.sinaimg.cn/large/006uZZy8jw1faic2b16zuj30ci08cwf4.jpg");
        list_path.add("http://ww4.sinaimg.cn/large/006uZZy8jw1faic2e7vsaj30ci08cglz.jpg");
        list_title.add("好好学习");
        list_title.add("天天向上");
        list_title.add("热爱劳动");
        list_title.add("不搞对象");
        //设置内置样式，共有六种可以点入方法内逐一体验使用。
        classfiy_left_banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE);
        //设置图片加载器，图片加载器在下方
        classfiy_left_banner.setImageLoader(new MyLoader());
        //设置图片网址或地址的集合
        classfiy_left_banner.setImages(list_path);
        //设置轮播图的标题集合
        classfiy_left_banner.setBannerTitles(list_title);
        //设置轮播间隔时间
        classfiy_left_banner.setDelayTime(3000);
        //设置是否为自动轮播，默认是“是”。
        classfiy_left_banner.isAutoPlay(true);
        //设置指示器的位置，小点点，左中右。
        classfiy_left_banner.setIndicatorGravity(BannerConfig.CENTER)
                //以上内容都可写成链式布局，这是轮播图的监听。比较重要。方法在下面。
                .setOnBannerListener(this)
                //必须最后调用的方法，启动轮播图。
                .start();
    }

    @Override
    protected void initViews(View view) {
        classfiy_left_rv=view.findViewById(R.id.classfiy_left_rv);
        classfiy_right_rv=view.findViewById(R.id.classfiy_right_rv);
        classfiy_left_banner=view.findViewById(R.id.classfiy_left_banner);
        classfiy_left_rv.setLayoutManager(new LinearLayoutManager(MyApp.getContext(),LinearLayoutManager.VERTICAL,false));
        classfiy_right_rv.setLayoutManager(new LinearLayoutManager(MyApp.getContext(),LinearLayoutManager.VERTICAL,false));
    }

    @Override
    protected int privideLayoutId() {
        return R.layout.fragment_classify;
    }

    @Override
    public void onSuccess(final ClassifyBean classifyBean) {
        classifyLeftAdapter = new ClassifyLeftAdapter(classifyBean.getData());
        classfiy_left_rv.setAdapter(classifyLeftAdapter);
        classifyLeftAdapter.setItemClickListener(new ClassifyLeftAdapter.setItemClickListener() {
            @Override
            public void itemClickListener(RecyclerView.ViewHolder view, int position) {
                classifyPresenter.setChildClassify(classifyBean.getData().get(position).getCid());
                /*classifyLeftAdapter.setCurrentItem(position);
                classifyLeftAdapter.notifyDataSetChanged();*/
            }
        });

    }

    @Override
    public void onChildSuccess(final ClassifyChildBean classifyChildBean) {
        classifyRightAdapter = new ClassifyRightAdapter(classifyChildBean.getData());
        classfiy_right_rv.setAdapter(classifyRightAdapter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        classifyPresenter.onDestroys();
    }

    @Override
    public void OnBannerClick(int position) {
        Log.i("tag", "你点了第"+position+"张轮播图");
    }

    //自定义的图片加载器
    private class MyLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            Glide.with(MyApp.getContext()).load((String) path).into(imageView);
        }
    }

}
