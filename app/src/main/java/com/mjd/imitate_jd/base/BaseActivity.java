package com.mjd.imitate_jd.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.gyf.barlibrary.ImmersionBar;

public abstract class BaseActivity extends AppCompatActivity {
    private ImmersionBar mImmersionBar;
    /*
    *@NAME:BaseActivity
    *@Author :老街旧人
    *@Date :2018/8/10 0010,20:07
    *@Describe :  基类中------onCreate创建必须是一个参数的
    */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mImmersionBar = ImmersionBar.with(this);
        mImmersionBar.init();   //所有子类都将继承这些相同的属性
        // 初始化ui
        initView();
        // 初始化数据
        initData();
        // 添加监听器
        initListener();
    }

    protected abstract void initListener();

    protected abstract void initData();

    protected abstract void initView(

    );


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mImmersionBar != null)
            mImmersionBar.destroy();
    }
}
