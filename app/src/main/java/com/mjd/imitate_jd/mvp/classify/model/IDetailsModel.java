package com.mjd.imitate_jd.mvp.classify.model;

import com.mjd.imitate_jd.bean.AddCarBean;
import com.mjd.imitate_jd.bean.DetailsBean;

public interface IDetailsModel {
    void onDetailsSuccess(DetailsBean detailsBean);
    void onSuccess(AddCarBean addCarBean);
}
