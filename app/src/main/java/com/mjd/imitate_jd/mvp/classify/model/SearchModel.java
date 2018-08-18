package com.mjd.imitate_jd.mvp.classify.model;

import com.mjd.imitate_jd.bean.SearchBean;
import com.mjd.imitate_jd.utils.Retrofit_RXJava;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class SearchModel {

    public void getSearch(String keywords, int page, String sort, final ISearchModel iSearchModel){
        Observable<SearchBean> observable = Retrofit_RXJava.getInstance().getInterface().getSearch(keywords, page, sort);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<SearchBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(SearchBean searchBean) {
                        iSearchModel.onSuccess(searchBean);
                    }
                });
    }

}
