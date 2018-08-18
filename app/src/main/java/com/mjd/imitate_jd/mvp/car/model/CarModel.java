package com.mjd.imitate_jd.mvp.car.model;


import android.util.Log;
import android.widget.Toast;

import com.mjd.imitate_jd.app.MyApp;
import com.mjd.imitate_jd.bean.CartBean;
import com.mjd.imitate_jd.utils.Retrofit_RXJava;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class CarModel {

    private static final String TAG = CarModel.class.getSimpleName();

    public void getCart(String uid, String token, final ICarModel iCarModel){
        Observable<CartBean> observable = Retrofit_RXJava.getInstance().getInterface().getCart(uid, token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CartBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(MyApp.getContext(),""+e.getMessage(),Toast.LENGTH_SHORT).show();
                        Log.d(TAG,""+e.getMessage());
                    }

                    @Override
                    public void onNext(CartBean cartBean) {
                        iCarModel.OnSetSuccess(cartBean);
                    }
                });
    }

}
