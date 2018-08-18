package com.mjd.imitate_jd.mvp.classify.model;

import com.mjd.imitate_jd.bean.ClassifyBean;
import com.mjd.imitate_jd.bean.ClassifyChildBean;
import com.mjd.imitate_jd.bean.DetailsBean;

public interface IClassifyModel {
    void onSuccess(ClassifyBean classifyBean);
    void onChildSuccess(ClassifyChildBean classifyChildBean);

}
