package com.mjd.imitate_jd.fragment;


import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.mjd.imitate_jd.R;
import com.mjd.imitate_jd.activity.LoginActivity;
import com.mjd.imitate_jd.activity.WebActivity;
import com.mjd.imitate_jd.app.MyApp;
import com.mjd.imitate_jd.base.BaseFragment;
import com.mjd.imitate_jd.bean.LoginBean;
import com.mjd.imitate_jd.bean.MessageEvent;
import com.mjd.imitate_jd.utils.Retrofit_RXJava;
import com.mjd.imitate_jd.utils.UserManage;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyFragment extends BaseFragment implements View.OnClickListener {


    private static final String TAG = MyFragment.class.getSimpleName();
    private ImageView myIvMessage;
    private SimpleDraweeView mySdvHeader;
    private TextView myTvLoginAndReg;

    private void fuzhi() {
        String userName = UserManage.getInstance().getUserInfo(MyApp.getContext()).getUserName();
        final String password = UserManage.getInstance().getUserInfo(MyApp.getContext()).getPassword();
        String uid = UserManage.getInstance().getUserInfo(MyApp.getContext()).getUid();
        Log.d(TAG,""+userName);
        Log.d(TAG,""+password);
        Log.d(TAG,""+uid);
        Observable<LoginBean> observable = Retrofit_RXJava.getInstance().getInterface().getLogin(userName,password);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<LoginBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG,""+e.getMessage());
                        Toast.makeText(MyApp.getContext(),"666"+e.getMessage(),Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNext(LoginBean loginBean) {
                        //Toast.makeText(MyApp.getContext(),"666"+loginBean.getData().getMobile(),Toast.LENGTH_SHORT).show();
                        ////向数据库中存储数据
                        UserManage.getInstance().saveUserInfo(MyApp.getContext(), loginBean.getData().getMobile(), password,loginBean.getData().getUid());
                        myTvLoginAndReg.setText(loginBean.getData().getMobile());
                        mySdvHeader.setImageURI("https://ww1.sinaimg.cn/large/0065oQSqly1ftdtot8zd3j30ju0pt137.jpg");
                    }
                });
    }


    public MyFragment() {
        // Required empty public constructor
    }


    @Override
    protected void initListener() {
        myTvLoginAndReg.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        if (UserManage.getInstance().hasUserInfo(MyApp.getContext()))//自动登录判断，SharePrefences中有数据，
        {
            fuzhi();
        } else {

        }
    }

    @Override
    protected void initViews(View view) {
        //注册eventbus事件,,,register---this
        EventBus.getDefault().register(this);
        myTvLoginAndReg=view.findViewById(R.id.my_tv_login_and_reg);
        mySdvHeader=view.findViewById(R.id.my_sdv_header);
        myIvMessage=view.findViewById(R.id.my_iv_message);
    }

    @Override
    protected int privideLayoutId() {
        return R.layout.fragment_my;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        //ImmersionBar.with(getActivity()).destroy(); //必须调用该方法，防止内存泄漏
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
        mySdvHeader.setImageURI(messageEvent.getUrl());
        myTvLoginAndReg.setText(messageEvent.getMobile());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.my_tv_login_and_reg:
                if (UserManage.getInstance().hasUserInfo(MyApp.getContext()))//自动登录判断，SharePrefences中有数据，
                {
                    startActivity(new Intent(getActivity(),WebActivity.class));
                    getActivity().finish();
                } else {
                    startActivity(new Intent(MyApp.getContext(), LoginActivity.class));
                }

                break;
        }
    }
}
