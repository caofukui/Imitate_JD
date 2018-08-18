package com.mjd.imitate_jd.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.facebook.drawee.view.SimpleDraweeView;
import com.mjd.imitate_jd.R;
import com.mjd.imitate_jd.activity.WebActivity;
import com.mjd.imitate_jd.app.MyApp;
import com.mjd.imitate_jd.bean.HomesBean;
import com.stx.xhb.xbanner.XBanner;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;
import com.youth.banner.loader.ImageLoaderInterface;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Home_RecylerView_Adapter extends RecyclerView.Adapter implements ViewPager.OnPageChangeListener, OnBannerListener {
    //getItemViewType多条目判断定义int类型的常量必须从0开始
    private static final int ONE_ITEM = 0;
    private static final int TWO_ITEM = 1;
    private static final int THREE_ITEM = 2;
    private static final int FOUR_ITEM = 3;
    private static final int FIVE_ITEM = 4;
    private HomesBean homesBean;
    private List<String> images;
    private List<String> titles;

    private ViewGroup points;//小圆点指示器
    private ImageView[] ivPoints;//小圆点图片集合
    private ViewPager viewPager;
    private int totalPage;//总的页数
    private int mPageSize = 10;//每页显示的最大数量
    private List<HomesBean.DataBean.FenleiBean> listDatas;//总的数据源
    private List<View> viewPagerList;//GridView作为一个View对象添加到ViewPager集合中
    private int currentPage;//当前页


    public Home_RecylerView_Adapter(HomesBean homesBean) {
        this.homesBean = homesBean;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == ONE_ITEM) {
            View view = LayoutInflater.from(MyApp.getContext()).inflate(R.layout.home_xbanner_item, parent, false);
            return new BannerViewHolder(view);
        } else if (viewType == TWO_ITEM) {
            View view = LayoutInflater.from(MyApp.getContext()).inflate(R.layout.home_img_item, parent, false);
            return new SimpleDraweeViewViewHolder(view);
        } else if (viewType == THREE_ITEM) {
            View view = LayoutInflater.from(MyApp.getContext()).inflate(R.layout.home_recylerview_item, parent, false);
            return new FenLeiViewHolder(view);
        } else if (viewType == FOUR_ITEM) {
            View view = LayoutInflater.from(MyApp.getContext()).inflate(R.layout.home_miaosha_item, parent, false);
            return new MiaoShaViewHolder(view);
        } else if (viewType == FIVE_ITEM) {
            View view = LayoutInflater.from(MyApp.getContext()).inflate(R.layout.home_tuijian_item, parent, false);
            return new TuiJianViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof BannerViewHolder) {
            images = new ArrayList<>();
            titles = new ArrayList<>();
            if (images.size() == 0 && titles.size() == 0) {
                for (int i = 0; i < homesBean.getData().getBanner().size(); i++) {
                    images.add(homesBean.getData().getBanner().get(i).getIcon());
                    titles.add(homesBean.getData().getBanner().get(i).getTitle());

                }
            }
            //设置内置样式，共有六种可以点入方法内逐一体验使用。
            ((BannerViewHolder) holder).homeXbItem.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE);
            //设置图片加载器，图片加载器在下方
            ((BannerViewHolder) holder).homeXbItem.setImageLoader(new Home_RecylerView_Adapter.MyLoader());
            //设置图片网址或地址的集合
            ((BannerViewHolder) holder).homeXbItem.setImages(images);
            //设置轮播图的标题集合
            ((BannerViewHolder) holder).homeXbItem.setBannerTitles(titles);
            //设置轮播间隔时间
            ((BannerViewHolder) holder).homeXbItem.setDelayTime(3000);
            //设置是否为自动轮播，默认是“是”。
            ((BannerViewHolder) holder).homeXbItem.isAutoPlay(true);
            //设置指示器的位置，小点点，左中右。
            ((BannerViewHolder) holder).homeXbItem.setIndicatorGravity(BannerConfig.CENTER)
                    //以上内容都可写成链式布局，这是轮播图的监听。比较重要。方法在下面。
                    .setOnBannerListener(this)
                    //必须最后调用的方法，启动轮播图。
                    .start();

        } else if (holder instanceof SimpleDraweeViewViewHolder) {
            ((SimpleDraweeViewViewHolder) holder).homeImgSdvItem.setImageURI("https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=3180365179,966790830&fm=27&gp=0.jpg");
        } else if (holder instanceof FenLeiViewHolder) {
            LayoutInflater inflater = LayoutInflater.from(MyApp.getContext());
            //总的页数，取整（这里有三种类型：Math.ceil(3.5)=4:向上取整，只要有小数都+1  Math.floor(3.5)=3：向下取整  Math.round(3.5)=4:四舍五入）
            totalPage = (int) Math.ceil(homesBean.getData().getFenlei().size() * 1.0 / mPageSize);
            viewPagerList = new ArrayList<>();
            for (int i = 0; i < totalPage; i++) {
                //每个页面都是inflate出一个新实例
                GridView gridView = (GridView) inflater.inflate(R.layout.home_grid_item, viewPager, false);
                gridView.setAdapter(new MyGridViewAdapter(MyApp.getContext(), homesBean.getData().getFenlei(), i, mPageSize));
                //添加item点击监听
            /*gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    int pos = position + currentPage*mPageSize;
                    Log.i("TAG","position的值为："+position + "-->pos的值为："+pos);
                    Toast.makeText(MainActivity.this,"你点击了 "+listDatas.get(pos).getProName(),Toast.LENGTH_SHORT).show();
                }
            });*/
                //每一个GridView作为一个View对象添加到ViewPager集合中
                viewPagerList.add(gridView);
            }
            //设置ViewPager适配器
            ((FenLeiViewHolder) holder).viewPager.setAdapter(new MyViewPagerAdapter(viewPagerList));
            //小圆点指示器
            ivPoints = new ImageView[totalPage];
            for (int i = 0; i < ivPoints.length; i++) {
                ImageView imageView = new ImageView(MyApp.getContext());
                //设置图片的宽高
                imageView.setLayoutParams(new ViewGroup.LayoutParams(10, 10));
                if (i == 0) {
                    imageView.setBackgroundResource(R.drawable.page__selected_indicator);
                } else {
                    imageView.setBackgroundResource(R.drawable.page__normal_indicator);
                }
                ivPoints[i] = imageView;
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                layoutParams.leftMargin = 20;//设置点点点view的左边距
                layoutParams.rightMargin = 20;//设置点点点view的右边距
                ((FenLeiViewHolder) holder).points.addView(imageView, layoutParams);
            }
            //设置ViewPager滑动监听
            ((FenLeiViewHolder) holder).viewPager.addOnPageChangeListener(this);


        } else if (holder instanceof MiaoShaViewHolder) {
            ((MiaoShaViewHolder) holder).homeMiaoshaTvTitile.setText("------" + homesBean.getData().getMiaosha().getName() + "------");
            ((MiaoShaViewHolder) holder).homeMiaoshaItem.setLayoutManager(new GridLayoutManager(MyApp.getContext(), 2));
            ((MiaoShaViewHolder) holder).homeMiaoshaItem.setAdapter(new MiaoShaAdapter(homesBean.getData().getMiaosha().getList()));
            //添加自定义分割线
            DividerItemDecoration divider = new DividerItemDecoration(MyApp.getContext(),DividerItemDecoration.VERTICAL);
            divider.setDrawable(ContextCompat.getDrawable(MyApp.getContext(),R.drawable.custom_divider));
            ((MiaoShaViewHolder) holder).homeMiaoshaItem.addItemDecoration(divider);

        }else if (holder instanceof TuiJianViewHolder){
            ((TuiJianViewHolder) holder).homeTuijianTvTitile.setText("------" +homesBean.getData().getTuijian().getName()+ "------");
            ((TuiJianViewHolder) holder).homeTuijianItem.setLayoutManager(new GridLayoutManager(MyApp.getContext(),2));
            ((TuiJianViewHolder) holder).homeTuijianItem.setAdapter(new TuiJianAdapter(homesBean.getData().getTuijian().getList()));
            //添加自定义分割线
            DividerItemDecoration divider = new DividerItemDecoration(MyApp.getContext(),DividerItemDecoration.VERTICAL);
            divider.setDrawable(ContextCompat.getDrawable(MyApp.getContext(),R.drawable.custom_divider));
            ((TuiJianViewHolder) holder).homeTuijianItem.addItemDecoration(divider);

        }
    }

    @Override
    public int getItemCount() {
        return homesBean.getData().getBanner().size()+1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return ONE_ITEM;
        } else if (position == 1) {
            return TWO_ITEM;
        } else if (position == 2) {
            return THREE_ITEM;
        } else if (position == 3) {
            return FOUR_ITEM;
        } else if (position == 4) {
            return FIVE_ITEM;
        }

        return super.getItemViewType(position);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        //改变小圆圈指示器的切换效果
        setImageBackground(position);
        currentPage = position;
    }

    private void setImageBackground(int selectItems) {
        for (int i = 0; i < ivPoints.length; i++) {
            if (i == selectItems) {
                ivPoints[i].setBackgroundResource(R.drawable.page__selected_indicator);
            } else {
                ivPoints[i].setBackgroundResource(R.drawable.page__normal_indicator);
            }
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void OnBannerClick(int position) {

    }

    static class BannerViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.home_xb_item)
        Banner homeXbItem;

        BannerViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    static class SimpleDraweeViewViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.home_img_sdv_item)
        SimpleDraweeView homeImgSdvItem;

        SimpleDraweeViewViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }


    static class FenLeiViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.viewPager)
        ViewPager viewPager;
        @BindView(R.id.points)
        LinearLayout points;

        FenLeiViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }


    static class MiaoShaViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.home_miaosha_tv_titile)
        TextView homeMiaoshaTvTitile;
        @BindView(R.id.home_miaosha_item)
        RecyclerView homeMiaoshaItem;

        MiaoShaViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    static class TuiJianViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.home_tuijian_tv_titile)
        TextView homeTuijianTvTitile;
        @BindView(R.id.home_tuijian_item)
        RecyclerView homeTuijianItem;

        TuiJianViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public class MyLoader extends ImageLoader {

        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            Glide.with(context).load((String) path).into(imageView);
        }
    }
}
