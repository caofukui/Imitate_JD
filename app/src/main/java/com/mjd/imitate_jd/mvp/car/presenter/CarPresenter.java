package com.mjd.imitate_jd.mvp.car.presenter;

import com.mjd.imitate_jd.bean.CartBean;
import com.mjd.imitate_jd.fragment.CarFragment;
import com.mjd.imitate_jd.mvp.car.model.CarModel;
import com.mjd.imitate_jd.mvp.car.model.ICarModel;
import com.mjd.imitate_jd.mvp.car.view.ICarView;

public class CarPresenter implements ICarPresenter {

    private ICarView iCarView;
    private CarModel carModel;

    public CarPresenter(ICarView iCarView) {
        this.iCarView=iCarView;
        this.carModel=new CarModel();
    }

    public void getCart(String uid, String token){
        carModel.getCart(uid, token, new ICarModel() {
            @Override
            public void OnSetSuccess(CartBean cartBean) {
                iCarView.OnSuccess(cartBean);
            }
        });
    }

    @Override
    public void onDestorys() {
        if (iCarView!=null){
            iCarView=null;
        }
    }
}
