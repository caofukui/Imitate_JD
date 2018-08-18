package com.mjd.imitate_jd.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.gyf.barlibrary.ImmersionBar;
import com.mjd.imitate_jd.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ShowActivity extends AppCompatActivity {

    private static final int CODE = 1;
    @BindView(R.id.show_tv_time)
    TextView showTvTime;
    private int time=3;
    private Handler handler=new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if (CODE==1){
                if (time>0){
                    time--;
                    showTvTime.setText(time+"S");
                    handler.sendEmptyMessageDelayed(CODE,1000);
                }else {
                    Intent intent = new Intent(ShowActivity.this, HomeActivity.class);
                    startActivity(intent);
                    // 进入主程序关闭初始动画界面
                    finish();
                }
            }
            return false;
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);
        ImmersionBar.with(this).init();
        ButterKnife.bind(this);
        handler.sendEmptyMessageDelayed(CODE,1000);


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ImmersionBar.with(this).destroy(); //必须调用该方法，防止内存泄漏
    }
}
