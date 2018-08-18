package com.mjd.imitate_jd.mvp.classify.view;

import com.mjd.imitate_jd.bean.ClassifyBean;
import com.mjd.imitate_jd.bean.ClassifyChildBean;
import com.mjd.imitate_jd.bean.DetailsBean;

public interface IClassifyView {
    void onSuccess(ClassifyBean classifyBean);
    void onChildSuccess(ClassifyChildBean classifyChildBean);

}
