package com.mjd.imitate_jd.mvp.home.model;

import com.mjd.imitate_jd.bean.HomesBean;
import com.mjd.imitate_jd.bean.SearchBean;
import com.mjd.imitate_jd.mvp.classify.model.ISearchModel;
import com.mjd.imitate_jd.utils.Retrofit_RXJava;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class HomeModel {

    public void getHomes(final IHomeModel iHomeModel){
        Observable<HomesBean> observable = Retrofit_RXJava.getInstance().getInterface().getHomes();
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<HomesBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(HomesBean homesBean) {
                        iHomeModel.onSetSuccess(homesBean);
                    }
                });
    }
}
