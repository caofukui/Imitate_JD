package com.mjd.imitate_jd.mvp.classify.presenter;


import com.mjd.imitate_jd.bean.AddCarBean;
import com.mjd.imitate_jd.bean.DetailsBean;
import com.mjd.imitate_jd.mvp.classify.model.DetailsModel;
import com.mjd.imitate_jd.mvp.classify.model.IDetailsModel;
import com.mjd.imitate_jd.mvp.classify.view.IDetailsView;

public class DetaisPresenter implements IDetaisPresenter {
    private IDetailsView iDetailsView;
    private DetailsModel detailsModel;
    public DetaisPresenter(IDetailsView iDetailsView) {
        this.iDetailsView=iDetailsView;
        this.detailsModel=new DetailsModel();
    }

    public void setDetails(String pid){
        detailsModel.getDetails(pid, new IDetailsModel() {
            @Override
            public void onDetailsSuccess(DetailsBean detailsBean) {
                iDetailsView.onDetailsSuccess(detailsBean);
            }

            @Override
            public void onSuccess(AddCarBean addCarBean) {

            }
        });
    }


    public void setAddCar(String uid, String pid, String token){
        detailsModel.getAddCar(uid, pid, token, new IDetailsModel() {
            @Override
            public void onDetailsSuccess(DetailsBean detailsBean) {

            }

            @Override
            public void onSuccess(AddCarBean addCarBean) {
                iDetailsView.onSuccess(addCarBean);
            }
        });
    }

    @Override
    public void onDestroys() {
        if (iDetailsView!=null){
            iDetailsView=null;
        }
    }
}
