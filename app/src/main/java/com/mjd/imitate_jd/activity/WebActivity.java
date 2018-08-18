package com.mjd.imitate_jd.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.widget.Button;

import com.mjd.imitate_jd.R;
import com.mjd.imitate_jd.app.MyApp;
import com.mjd.imitate_jd.base.BaseActivity;
import com.mjd.imitate_jd.utils.UserManage;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WebActivity extends BaseActivity {


    @BindView(R.id.web_back)
    Button webBack;

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_web);
        ButterKnife.bind(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick(R.id.web_back)
    public void onViewClicked() {
        showDialog();
    }
    private void showDialog() {

        //*****注意在fragment中使用AlertDialog时getActivity()
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("退出")
                .setMessage("您确定要退出吗")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        UserManage.clear(WebActivity.this);
                        Intent intent = new Intent(MyApp.getContext(), HomeActivity.class);
                        intent.putExtra("flag","我的");
                        startActivity(intent);
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                }).show();
    }
}
