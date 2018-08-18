package com.mjd.imitate_jd.mvp.my.login.presenter;

import com.mjd.imitate_jd.activity.LoginActivity;
import com.mjd.imitate_jd.bean.LoginBean;
import com.mjd.imitate_jd.mvp.my.login.model.ILoginModel;
import com.mjd.imitate_jd.mvp.my.login.model.LoginModel;
import com.mjd.imitate_jd.mvp.my.login.view.ILoginView;

public class LoginPresenter implements ILoginPresenter {
    private ILoginView iLoginView;
    private LoginModel loginModel;
    public LoginPresenter(ILoginView iLoginView) {
        this.iLoginView=iLoginView;
        this.loginModel=new LoginModel();
    }

    public void setDatas(String mobile,String password){
        loginModel.getDatas(mobile, password, new ILoginModel() {
            @Override
            public void onSetSuccess(LoginBean loginBean) {
                iLoginView.onSuccess(loginBean);
            }
        });
    }


    @Override
    public void onDestroys() {
        if (iLoginView!=null){
            iLoginView=null;
        }
    }
}
