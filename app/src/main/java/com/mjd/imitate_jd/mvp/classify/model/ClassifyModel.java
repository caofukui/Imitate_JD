package com.mjd.imitate_jd.mvp.classify.model;

import com.mjd.imitate_jd.bean.ClassifyBean;
import com.mjd.imitate_jd.bean.ClassifyChildBean;
import com.mjd.imitate_jd.bean.DetailsBean;
import com.mjd.imitate_jd.mvp.classify.view.IClassifyView;
import com.mjd.imitate_jd.utils.Retrofit_RXJava;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ClassifyModel {

    public void getClassify(final IClassifyModel iClassifyModel){
        Observable<ClassifyBean> observable = Retrofit_RXJava.getInstance().getInterface().getClassify();
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ClassifyBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(ClassifyBean classifyBean) {
                        iClassifyModel.onSuccess(classifyBean);
                    }
                });
    }

    public void getClassifyChild(final IClassifyView iClassifyView, String cid){
        Observable<ClassifyChildBean> observable = Retrofit_RXJava.getInstance().getInterface().getClassifyChild(cid);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ClassifyChildBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(ClassifyChildBean classifyChildBean) {
                        iClassifyView.onChildSuccess(classifyChildBean);
                    }
                });
    }




}
