package com.mjd.imitate_jd.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.mjd.imitate_jd.R;
import com.mjd.imitate_jd.adapter.SearchAdapter;
import com.mjd.imitate_jd.app.MyApp;
import com.mjd.imitate_jd.base.BaseActivity;
import com.mjd.imitate_jd.bean.SearchBean;
import com.mjd.imitate_jd.mvp.classify.presenter.SearchPresenter;
import com.mjd.imitate_jd.mvp.classify.view.ISearchView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchActivity extends BaseActivity implements View.OnClickListener,ISearchView, RadioGroup.OnCheckedChangeListener {

    @BindView(R.id.search_rbtn_zonghe)
    RadioButton searchRbtnZonghe;
    @BindView(R.id.search_rbtn_xiaoliang)
    RadioButton searchRbtnXiaoliang;
    @BindView(R.id.search_rbtn_price)
    RadioButton searchRbtnPrice;
    @BindView(R.id.search_rg)
    RadioGroup searchRg;
    @BindView(R.id.search_xrv)
    XRecyclerView searchXrv;
    private ImageView home__iv_sou,home__iv_ren;
    private EditText home__et_message;

    private SearchPresenter searchPresenter;
    private String keywords;
    private int page=1;
    private String sort="0";
    private SearchAdapter searchAdapter;
    private SimpleDraweeView home_sdv_message,home_sdv_message02;

    @Override
    protected void initListener() {
        home__iv_sou.setOnClickListener(this);
        home__iv_ren.setOnClickListener(this);
        home_sdv_message.setOnClickListener(this);
        home_sdv_message02.setOnClickListener(this);
        searchRg.setOnCheckedChangeListener(this);
    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        keywords = intent.getStringExtra("name");
        home__et_message.setText(keywords);
        //Toast.makeText(SearchActivity.this,""+name,Toast.LENGTH_SHORT).show();
        searchPresenter.setSearch(keywords,page,sort);

    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        home__iv_sou=findViewById(R.id.home__iv_sou);
        home__et_message=findViewById(R.id.home__et_message);
        home__iv_ren=findViewById(R.id.home__iv_ren);
        home_sdv_message=findViewById(R.id.home_sdv_message);
        home_sdv_message02=findViewById(R.id.home_sdv_message02);
        searchPresenter=new SearchPresenter((ISearchView) this);
        searchXrv.setLayoutManager(new LinearLayoutManager(MyApp.getContext(),LinearLayoutManager.VERTICAL,false));
        searchXrv.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                page++;
                searchPresenter.setSearch(keywords,page,sort);
                searchXrv.refreshComplete();
            }

            @Override
            public void onLoadMore() {
                page=0;
                searchPresenter.setSearch(keywords,page,sort);
                searchXrv.loadMoreComplete();
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.home__iv_sou:
                keywords = home__et_message.getText().toString();
                searchPresenter.setSearch(keywords,page,sort);
                break;
            case R.id.home__iv_ren:
                home__et_message.getText().clear();
                break;

            case R.id.home_sdv_message:
                searchXrv.setLayoutManager(new GridLayoutManager(MyApp.getContext(),2));
                home_sdv_message.setVisibility(View.GONE);
                home_sdv_message02.setVisibility(View.VISIBLE);
                break;
            case R.id.home_sdv_message02:
                searchXrv.setLayoutManager(new LinearLayoutManager(MyApp.getContext(),LinearLayoutManager.VERTICAL,false));
                home_sdv_message.setVisibility(View.VISIBLE);
                home_sdv_message02.setVisibility(View.GONE);
                break;
        }
    }

    @Override
    public void onSuccess(final SearchBean searchBean) {
        searchAdapter = new SearchAdapter(searchBean.getData());
        searchXrv.setAdapter(searchAdapter);
        searchAdapter.setItemOnClickListener(new SearchAdapter.setItemOnClickListener() {
            @Override
            public void itemOnClickListener(RecyclerView.ViewHolder view, int position) {
                Intent intent = new Intent(MyApp.getContext(), DetailsActivity.class);
                intent.putExtra("pid",searchBean.getData().get(position).getPid());
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        searchPresenter.onDestorys();
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId){
            case R.id.search_rbtn_zonghe:
                sort="0";
                searchPresenter.setSearch(keywords,page,sort);
                break;
            case R.id.search_rbtn_xiaoliang:
                sort="1";
                searchPresenter.setSearch(keywords,page,sort);
                break;
            case R.id.search_rbtn_price:
                sort="2";
                searchPresenter.setSearch(keywords,page,sort);
                break;

        }
    }

}
