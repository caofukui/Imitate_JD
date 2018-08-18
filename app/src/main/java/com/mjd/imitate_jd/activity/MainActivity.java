package com.mjd.imitate_jd.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.mjd.imitate_jd.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = new Intent(this, ShowActivity.class);
        startActivity(intent);
        // 进入主程序关闭初始动画界面
        finish();
    }
}
