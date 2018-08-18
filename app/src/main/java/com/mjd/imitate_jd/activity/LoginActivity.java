package com.mjd.imitate_jd.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.facebook.drawee.view.SimpleDraweeView;
import com.mjd.imitate_jd.R;
import com.mjd.imitate_jd.app.MyApp;
import com.mjd.imitate_jd.base.BaseActivity;
import com.mjd.imitate_jd.bean.LoginBean;
import com.mjd.imitate_jd.bean.MessageEvent;
import com.mjd.imitate_jd.mvp.my.login.presenter.LoginPresenter;
import com.mjd.imitate_jd.mvp.my.login.view.ILoginView;
import com.mjd.imitate_jd.utils.RegularUtil;
import com.mjd.imitate_jd.utils.UserManage;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity implements ILoginView {

    private static final String TAG = LoginActivity.class.getSimpleName();
    @BindView(R.id.login_iv_close)
    ImageView loginIvClose;
    @BindView(R.id.login_tv_kefu)
    TextView loginTvKefu;
    @BindView(R.id.login_sdv_header)
    SimpleDraweeView loginSdvHeader;
    @BindView(R.id.login_et_mobile)
    EditText loginEtMobile;
    @BindView(R.id.login_et_pwd)
    EditText loginEtPwd;
    @BindView(R.id.login_btn_login)
    Button loginBtnLogin;
    @BindView(R.id.login_tv_duanxin)
    TextView loginTvDuanxin;
    @BindView(R.id.login_tv_newuser)
    TextView loginTvNewuser;
    @BindView(R.id.login_ll_weilogin)
    LinearLayout loginLlWeilogin;
    @BindView(R.id.login_ll_qqlogin)
    LinearLayout loginLlQqlogin;
    @BindView(R.id.login_togglePwd)
    ToggleButton loginTogglePwd;
    @BindView(R.id.login_tv_wj)
    TextView loginTvWj;
    private String mobile1;
    private String pwd1;
    private LoginPresenter loginPresenter;
    private String url = "https://ww1.sinaimg.cn/large/0065oQSqly1ftzsj15hgvj30sg15hkbw.jpg";
    private String flag2;
    private String pid;

    @Override
    protected void initListener() {
        loginTogglePwd.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    //如果选中，显示密码
                    loginEtPwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    //否则隐藏密码
                    loginEtPwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });
    }

    @Override
    protected void initData() {
        //注册eventbus事件,,,注意在任何地方都可以写this
        EventBus.getDefault().register(this);

    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        loginPresenter = new LoginPresenter((ILoginView) this);
        Intent intent = getIntent();
        flag2 = intent.getStringExtra("flag2");
        pid = intent.getStringExtra("pid");
    }

    @OnClick({R.id.login_iv_close, R.id.login_tv_kefu, R.id.login_sdv_header, R.id.login_btn_login, R.id.login_tv_duanxin, R.id.login_tv_newuser, R.id.login_ll_weilogin, R.id.login_ll_qqlogin})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.login_iv_close:
                finish();
                break;
            case R.id.login_tv_kefu:
                break;
            case R.id.login_sdv_header:
                break;
            case R.id.login_btn_login:
                mobile1 = loginEtMobile.getText().toString();
                pwd1 = loginEtPwd.getText().toString();
                if (!TextUtils.isEmpty(mobile1) && !TextUtils.isEmpty(pwd1)) {
                    if (RegularUtil.isMobile(mobile1) && RegularUtil.isPassword(pwd1)){
                        loginPresenter.setDatas(mobile1, pwd1);
                    }else {
                        Toast.makeText(LoginActivity.this,"手机号或密码格式不正确",Toast.LENGTH_SHORT).show();
                    }

                }
                break;
            case R.id.login_tv_duanxin:
                break;
            case R.id.login_tv_newuser:
                startActivity(new Intent(LoginActivity.this, RegActivity.class));
                break;
            case R.id.login_ll_weilogin:
                break;
            case R.id.login_ll_qqlogin:
                break;
        }
    }

    @Override
    public void onSuccess(LoginBean loginBean) {
        Toast.makeText(MyApp.getContext(), loginBean.getMsg(), Toast.LENGTH_SHORT).show();
        if ("登录成功".equals(loginBean.getMsg())) {
            Log.d(TAG,""+loginBean.getData().getPassword());
            Log.d(TAG,""+loginBean.getData().getMobile());
            //向数据库中存储数据
            UserManage.getInstance().saveUserInfo(LoginActivity.this, loginBean.getData().getMobile(),pwd1,loginBean.getData().getUid());
            //发送eventbus
            EventBus.getDefault().post(new MessageEvent(mobile1, null, url));
            if ("详情".equals(flag2)){
                Intent intent = new Intent(LoginActivity.this, DetailsActivity.class);
                intent.putExtra("flag3","登录");
                intent.putExtra("pid",pid);
                startActivity(intent);
                finish();
            }else {
                finish();
            }

        } else {
            Toast.makeText(LoginActivity.this, "" + loginBean.getMsg(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        loginPresenter.onDestroys();
        //取消eventbus事件
        EventBus.getDefault().unregister(this);
    }

    /*
     *@Params :
     *@Author :老街旧人
     *@Date :2018/8/9 0009
     *@Describe :处理事件
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMoonEvent(MessageEvent messageEvent) {
        loginSdvHeader.setImageURI(messageEvent.getUrl());
        loginEtMobile.setText(messageEvent.getMobile());
        loginEtPwd.setText(messageEvent.getPassword());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
