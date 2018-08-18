package com.mjd.imitate_jd.activity;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.mjd.imitate_jd.R;
import com.mjd.imitate_jd.base.BaseActivity;
import com.mjd.imitate_jd.fragment.CarFragment;
import com.mjd.imitate_jd.fragment.ClassifyFragment;
import com.mjd.imitate_jd.fragment.FindFragment;
import com.mjd.imitate_jd.fragment.HomeFragment;
import com.mjd.imitate_jd.fragment.MyFragment;

import java.util.ArrayList;
import java.util.List;


public class HomeActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener {

    private RadioGroup home_rg;
    private List<Fragment> fragmentList;
    private FragmentManager fragmentManager;
    private String flag;

    @Override
    protected void initListener() {
        home_rg.setOnCheckedChangeListener(this);
        //接收值进行判断
        Intent intent = getIntent();
       // Toast.makeText(HomeActivity.this,""+intent.getStringExtra("flag"),Toast.LENGTH_SHORT).show();
        flag=intent.getStringExtra("flag");
        if ("跳转".equals(flag)){
            //跳转到购物页面
            home_rg.check(R.id.home_rbtn_car);
        }else if ("我的".equals(flag)){
            //跳转到我的页面
            home_rg.check(R.id.home_rbtn_my);
        }else {
            //默认显示首页
            home_rg.check(R.id.home_rbtn_home);
        }
    }

    @Override
    protected void initData() {
        fragmentList = new ArrayList<>();
        fragmentList.add(new HomeFragment());
        fragmentList.add(new ClassifyFragment());
        fragmentList.add(new FindFragment());
        fragmentList.add(new CarFragment());
        fragmentList.add(new MyFragment());
        fragmentManager = getSupportFragmentManager();

    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_home);
        home_rg=findViewById(R.id.home_rg);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.home_rbtn_home:
                changeFragment(fragmentList.get(0));
                break;
            case R.id.home_rbtn2_classify:
                changeFragment(fragmentList.get(1));
                break;
            case R.id.home_rbtn_find:
                changeFragment(fragmentList.get(2));
                break;
            case R.id.home_rbtn_car:
                changeFragment(fragmentList.get(3));
                break;
            case R.id.home_rbtn_my:
                changeFragment(fragmentList.get(4));
                break;
        }
    }
    private void changeFragment(Fragment fragment) {
        fragmentManager.beginTransaction().replace(R.id.home_frame,fragment).commit();
    }
}
