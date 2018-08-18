package com.mjd.imitate_jd.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.mjd.imitate_jd.R;
import com.mjd.imitate_jd.base.BaseActivity;
import com.mjd.imitate_jd.bean.MessageEvent;
import com.mjd.imitate_jd.bean.RegBean;
import com.mjd.imitate_jd.mvp.my.reg.presenter.RegPresenter;
import com.mjd.imitate_jd.mvp.my.reg.view.IRegView;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegActivity extends BaseActivity implements IRegView{

    @BindView(R.id.reg_iv_close)
    ImageView regIvClose;
    @BindView(R.id.reg_tv_kefu)
    TextView regTvKefu;
    @BindView(R.id.reg_sdv_header)
    SimpleDraweeView regSdvHeader;
    @BindView(R.id.reg_et_mobile)
    EditText regEtMobile;
    @BindView(R.id.reg_et_pwd)
    EditText regEtPwd;
    @BindView(R.id.reg_btn_reg)
    Button regBtnReg;
    private RegPresenter regPresenter;
    private String mobile1;
    private String pwd1;
    private String url="https://ww1.sinaimg.cn/large/0065oQSqly1ftzsj15hgvj30sg15hkbw.jpg";

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_reg);
        ButterKnife.bind(this);
        regPresenter=new RegPresenter((IRegView) this);
    }

    @OnClick({R.id.reg_iv_close, R.id.reg_tv_kefu, R.id.reg_sdv_header, R.id.reg_btn_reg})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.reg_iv_close:
                finish();
                break;
            case R.id.reg_tv_kefu:
                break;
            case R.id.reg_sdv_header:
                break;
            case R.id.reg_btn_reg:
                mobile1 = regEtMobile.getText().toString();
                pwd1 = regEtPwd.getText().toString();
                if (!TextUtils.isEmpty(mobile1) && !TextUtils.isEmpty(pwd1)){
                    regPresenter.setDatas(mobile1,pwd1);
                }
                break;
        }
    }

    @Override
    public void onSuccess(RegBean regBean) {
        Toast.makeText(RegActivity.this,""+regBean.getMsg(),Toast.LENGTH_SHORT).show();
        if ("注册成功".equals(regBean.getMsg())){
            //发送
            EventBus.getDefault().post(new MessageEvent(mobile1,pwd1,url));
            finish();
        }else {
            Toast.makeText(RegActivity.this,""+regBean.getMsg(),Toast.LENGTH_SHORT).show();
        }

    }
}
