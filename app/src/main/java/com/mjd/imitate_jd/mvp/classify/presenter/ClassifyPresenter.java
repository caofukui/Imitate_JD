package com.mjd.imitate_jd.mvp.classify.presenter;

import com.mjd.imitate_jd.activity.DetailsActivity;
import com.mjd.imitate_jd.bean.ClassifyBean;
import com.mjd.imitate_jd.bean.ClassifyChildBean;
import com.mjd.imitate_jd.bean.DetailsBean;
import com.mjd.imitate_jd.fragment.ClassifyFragment;
import com.mjd.imitate_jd.mvp.classify.model.ClassifyModel;
import com.mjd.imitate_jd.mvp.classify.model.IClassifyModel;
import com.mjd.imitate_jd.mvp.classify.view.IClassifyView;

public class ClassifyPresenter implements IClassifyPresenter {
    private IClassifyView iClassifyView;
    private ClassifyModel classifyModel;
    public ClassifyPresenter(IClassifyView iClassifyView) {
        this.iClassifyView=iClassifyView;
        this.classifyModel=new ClassifyModel();
    }

    public void setClassify(){
        classifyModel.getClassify(new IClassifyModel() {
            @Override
            public void onSuccess(ClassifyBean classifyBean) {
                iClassifyView.onSuccess(classifyBean);
            }

            @Override
            public void onChildSuccess(ClassifyChildBean classifyChildBean) {

            }
        });
    }

    public void setChildClassify(String cid){
        classifyModel.getClassifyChild(new IClassifyView() {
            @Override
            public void onSuccess(ClassifyBean classifyBean) {

            }

            @Override
            public void onChildSuccess(ClassifyChildBean classifyChildBean) {
                iClassifyView.onChildSuccess(classifyChildBean);
            }

        },cid);
    }




    @Override
    public void onDestroys() {
        if (iClassifyView!=null){
            iClassifyView=null;
        }
    }
}
