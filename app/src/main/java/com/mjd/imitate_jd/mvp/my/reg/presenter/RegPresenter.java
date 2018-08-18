package com.mjd.imitate_jd.mvp.my.reg.presenter;

import com.mjd.imitate_jd.activity.RegActivity;
import com.mjd.imitate_jd.bean.RegBean;
import com.mjd.imitate_jd.mvp.my.reg.model.IRegModel;
import com.mjd.imitate_jd.mvp.my.reg.model.RegModel;
import com.mjd.imitate_jd.mvp.my.reg.view.IRegView;

public class RegPresenter implements IRegPresenter {
    private IRegView iRegView;
    private RegModel regModel;
    public RegPresenter(IRegView iRegView) {
        this.iRegView=iRegView;
        regModel=new RegModel();
    }

    public void setDatas(String mobile,String password){
        regModel.getDatas(mobile, password, new IRegModel() {
            @Override
            public void onSetSuccess(RegBean regBean) {
                iRegView.onSuccess(regBean);
            }
        });
    }

    @Override
    public void onDestroys() {
        if (iRegView!=null){
            iRegView=null;
        }
    }
}
