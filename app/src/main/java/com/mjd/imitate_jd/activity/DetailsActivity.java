package com.mjd.imitate_jd.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mjd.imitate_jd.R;
import com.mjd.imitate_jd.adapter.DetailsPagerAdapter;
import com.mjd.imitate_jd.app.MyApp;
import com.mjd.imitate_jd.base.BaseActivity;
import com.mjd.imitate_jd.bean.AddCarBean;
import com.mjd.imitate_jd.bean.DetailsBean;
import com.mjd.imitate_jd.mvp.classify.presenter.DetaisPresenter;
import com.mjd.imitate_jd.mvp.classify.view.IDetailsView;
import com.mjd.imitate_jd.utils.UserManage;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DetailsActivity extends BaseActivity implements IDetailsView {

    @BindView(R.id.details_vp)
    ViewPager detailsVp;
    @BindView(R.id.details_tv_title)
    TextView detailsTvTitle;
    @BindView(R.id.details_tv_price)
    TextView detailsTvPrice;
    @BindView(R.id.details_btn1_car)
    Button detailsBtn1Car;
    @BindView(R.id.details_btn2_addcar)
    Button detailsBtn2Addcar;
    @BindView(R.id.details_btn3_buy)
    Button detailsBtn3Buy;
    @BindView(R.id.details_ll)
    LinearLayout detailsLl;

    private String pid;
    private DetaisPresenter detaisPresenter;
    private List<String> list = new ArrayList<>();
    private String token = "1";
    private Intent intent;


    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        intent = getIntent();
        pid = intent.getStringExtra("pid");
        detaisPresenter.setDetails(pid);
    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);
        detaisPresenter = new DetaisPresenter(this);
        Intent intent = getIntent();
        String flag3 = intent.getStringExtra("flag3");
        if ("登录".equals(flag3)){
            String pid = intent.getStringExtra("pid");
            detaisPresenter.setDetails(pid);
        }
    }

    @Override
    public void onDetailsSuccess(DetailsBean detailsBean) {
        detailsTvTitle.setText(detailsBean.getData().getTitle());
        detailsTvPrice.setText("¥" + detailsBean.getData().getPrice() + "");
        list.add(detailsBean.getData().getImages().split("\\|")[0]);
        list.add(detailsBean.getData().getImages().split("\\|")[1]);
        list.add(detailsBean.getData().getImages().split("\\|")[2]);
        detailsVp.setAdapter(new DetailsPagerAdapter(list));
    }

    @Override
    public void onSuccess(AddCarBean addCarBean) {
        Toast.makeText(DetailsActivity.this, "" + addCarBean.getMsg(), Toast.LENGTH_SHORT).show();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.details_btn1_car, R.id.details_btn2_addcar, R.id.details_btn3_buy})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.details_btn1_car:
                Intent intent = new Intent(DetailsActivity.this, HomeActivity.class);
                intent.putExtra("flag","跳转");
                startActivity(intent);
                finish();
                break;
            case R.id.details_btn2_addcar:
                if (UserManage.getInstance().hasUserInfo(MyApp.getContext()))//自动登录判断，SharePrefences中有数据，
                {
                    String uid = UserManage.getInstance().getUserInfo(MyApp.getContext()).getUid();
                    detaisPresenter.setAddCar(uid,pid,token);
                } else {
                    Intent intent1 = new Intent(DetailsActivity.this, LoginActivity.class);
                    intent1.putExtra("flag2","详情");
                    intent1.putExtra("pid",pid);
                    startActivity(intent1);
                    finish();
                }
                break;
            case R.id.details_btn3_buy:
                break;
        }
    }

}
