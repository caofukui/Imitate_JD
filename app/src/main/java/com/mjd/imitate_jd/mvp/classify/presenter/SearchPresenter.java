package com.mjd.imitate_jd.mvp.classify.presenter;

import com.mjd.imitate_jd.bean.SearchBean;
import com.mjd.imitate_jd.fragment.HomeFragment;
import com.mjd.imitate_jd.mvp.classify.model.ISearchModel;
import com.mjd.imitate_jd.mvp.classify.model.SearchModel;
import com.mjd.imitate_jd.mvp.classify.view.ISearchView;
import com.mjd.imitate_jd.mvp.home.view.IHomeView;

public class SearchPresenter implements ISearchPresenter {
    private ISearchView iSearchView;
    private SearchModel searchModel;
    public SearchPresenter(ISearchView iSearchView) {
        this.iSearchView=iSearchView;
        this.searchModel=new SearchModel();
    }

    public void setSearch(String keywords, int page, String sort){
        searchModel.getSearch(keywords, page, sort, new ISearchModel() {
            @Override
            public void onSuccess(SearchBean searchBean) {
                iSearchView.onSuccess(searchBean);
            }
        });
    }


    @Override
    public void onDestorys() {
        if (iSearchView!=null){
            iSearchView=null;
        }
    }
}
