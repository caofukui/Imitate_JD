package com.mjd.imitate_jd.fragment;


import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.mjd.imitate_jd.R;
import com.mjd.imitate_jd.activity.DetailsActivity;
import com.mjd.imitate_jd.activity.SearchActivity;
import com.mjd.imitate_jd.adapter.Home_RecylerView_Adapter;
import com.mjd.imitate_jd.adapter.SearchAdapter;
import com.mjd.imitate_jd.app.MyApp;
import com.mjd.imitate_jd.base.BaseFragment;
import com.mjd.imitate_jd.bean.HomesBean;
import com.mjd.imitate_jd.bean.SearchBean;
import com.mjd.imitate_jd.mvp.classify.presenter.SearchPresenter;
import com.mjd.imitate_jd.mvp.home.presenter.HomePresenter;
import com.mjd.imitate_jd.mvp.home.view.IHomeView;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends BaseFragment implements IHomeView, View.OnClickListener {
    private RecyclerView home_recyler_zs;
    private SimpleDraweeView home__sdv_sao;
    private ImageView home__iv_sou;
    private EditText home__et_message;
    private ImageView home__iv_ren;
    private SimpleDraweeView home_sdv_message;
    private HomePresenter homePresenter;
    private Home_RecylerView_Adapter homeRecylerViewAdapter;
    private String name;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    protected void initListener() {
        home__iv_sou.setOnClickListener(this);
        home__iv_ren.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        homePresenter=new HomePresenter(this);
        homePresenter.getHomes();
    }

    @Override
    protected void initViews(View view) {
        home_recyler_zs=view.findViewById(R.id.home_recyler_zs);
        home__sdv_sao=view.findViewById(R.id.home__sdv_sao);
        home__iv_sou=view.findViewById(R.id.home__iv_sou);
        home__et_message=view.findViewById(R.id.home__et_message);
        home__iv_ren=view.findViewById(R.id.home__iv_ren);
        home_sdv_message=view.findViewById(R.id.home_sdv_message);
        home_recyler_zs.setLayoutManager(new LinearLayoutManager(MyApp.getContext(),LinearLayoutManager.VERTICAL,false));

    }

    @Override
    protected int privideLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        homePresenter.onDestorys();
    }

    @Override
    public void onSuccess(HomesBean homesBean) {
        homeRecylerViewAdapter = new Home_RecylerView_Adapter(homesBean);
        home_recyler_zs.setAdapter(homeRecylerViewAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.home__iv_sou:
                name= home__et_message.getText().toString();
                //跳到搜索页面
                Intent intent = new Intent(MyApp.getContext(), SearchActivity.class);
                intent.putExtra("name",name);
                MyApp.getContext().startActivity(intent);
             break;

            case R.id.home__iv_ren:
                home__et_message.getText().clear();
                break;

        }
    }
}
