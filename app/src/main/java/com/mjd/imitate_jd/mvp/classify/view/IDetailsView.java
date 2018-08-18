package com.mjd.imitate_jd.mvp.classify.view;

import com.mjd.imitate_jd.bean.AddCarBean;
import com.mjd.imitate_jd.bean.DetailsBean;

public interface IDetailsView {
    void onDetailsSuccess(DetailsBean detailsBean);
    void onSuccess(AddCarBean addCarBean);
}
