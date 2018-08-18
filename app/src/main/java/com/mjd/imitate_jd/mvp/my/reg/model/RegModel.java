package com.mjd.imitate_jd.mvp.my.reg.model;

import com.mjd.imitate_jd.bean.RegBean;
import com.mjd.imitate_jd.utils.Retrofit_RXJava;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class RegModel {

    public void getDatas(String mobile, String password, final IRegModel iRegModel){
        Observable<RegBean> compose = Retrofit_RXJava.getInstance().getInterface().getReg(mobile, password);
        // 生产线程
        compose.subscribeOn(Schedulers.io())
                // 消费线程
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<RegBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(RegBean regBean) {
                        iRegModel.onSetSuccess(regBean);
                    }
                });
    }
}
