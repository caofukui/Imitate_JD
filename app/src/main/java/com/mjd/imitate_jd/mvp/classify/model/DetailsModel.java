package com.mjd.imitate_jd.mvp.classify.model;

import com.mjd.imitate_jd.bean.AddCarBean;
import com.mjd.imitate_jd.bean.DetailsBean;
import com.mjd.imitate_jd.utils.Retrofit_RXJava;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class DetailsModel {


    public void getDetails(String pid, final IDetailsModel iDetailsModel){
        Observable<DetailsBean> observable = Retrofit_RXJava.getInstance().getInterface().getDetails(pid);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<DetailsBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(DetailsBean detailsBean) {
                        iDetailsModel.onDetailsSuccess(detailsBean);
                    }
                });
    }

    public void getAddCar(String uid, String pid, String token, final IDetailsModel iDetailsModel){
        Observable<AddCarBean> observable = Retrofit_RXJava.getInstance().getInterface().getAddCar(uid, pid, token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<AddCarBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(AddCarBean addCarBean) {
                        iDetailsModel.onSuccess(addCarBean);
                    }
                });
    }
}
