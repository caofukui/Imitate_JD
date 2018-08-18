package com.mjd.imitate_jd.mvp.my.login.model;

import android.widget.Toast;

import com.mjd.imitate_jd.app.MyApp;
import com.mjd.imitate_jd.bean.LoginBean;
import com.mjd.imitate_jd.bean.RegBean;
import com.mjd.imitate_jd.utils.Retrofit_RXJava;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class LoginModel {

    public void getDatas(String mobile, String password, final ILoginModel iLoginModel){
        Observable<LoginBean> compose = Retrofit_RXJava.getInstance().getInterface().getLogin(mobile, password);
        // 生产线程
        compose.subscribeOn(Schedulers.io())
                // 消费线程
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<LoginBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(LoginBean loginBean) {

                        iLoginModel.onSetSuccess(loginBean);
                    }
                });
    }

}
