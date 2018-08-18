package com.mjd.imitate_jd.mvp.home.presenter;

import com.mjd.imitate_jd.bean.HomesBean;
import com.mjd.imitate_jd.bean.SearchBean;
import com.mjd.imitate_jd.mvp.classify.model.ISearchModel;
import com.mjd.imitate_jd.mvp.home.model.HomeModel;
import com.mjd.imitate_jd.mvp.home.model.IHomeModel;
import com.mjd.imitate_jd.mvp.home.view.IHomeView;

public class HomePresenter implements IHomePresenter {
    private IHomeView iHomeView;
    private HomeModel homeModel;

    public HomePresenter(IHomeView iHomeView) {
        this.iHomeView=iHomeView;
        homeModel=new HomeModel();
    }

    public void getHomes(){
        homeModel.getHomes(new IHomeModel() {
            @Override
            public void onSetSuccess(HomesBean homesBean) {
                iHomeView.onSuccess(homesBean);
            }
        });
    }

    @Override
    public void onDestorys() {
        if (iHomeView!=null){
            iHomeView=null;
        }
    }
}
