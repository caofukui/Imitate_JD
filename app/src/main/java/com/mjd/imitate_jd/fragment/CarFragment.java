package com.mjd.imitate_jd.fragment;


import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mjd.imitate_jd.R;
import com.mjd.imitate_jd.activity.DetailsActivity;
import com.mjd.imitate_jd.activity.LoginActivity;
import com.mjd.imitate_jd.adapter.CartExpandAdapter;
import com.mjd.imitate_jd.app.MyApp;
import com.mjd.imitate_jd.base.BaseFragment;
import com.mjd.imitate_jd.bean.CartBean;
import com.mjd.imitate_jd.bean.DeleteBean;
import com.mjd.imitate_jd.mvp.car.presenter.CarPresenter;
import com.mjd.imitate_jd.mvp.car.view.ICarView;
import com.mjd.imitate_jd.utils.Retrofit_RXJava;
import com.mjd.imitate_jd.utils.UserManage;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * A simple {@link Fragment} subclass.
 */
public class CarFragment extends BaseFragment implements ICarView, View.OnClickListener {
    private ImageView cart_iv_back;
    private ExpandableListView cart_expand;
    private CheckBox cart_check;
    private TextView cart_tv_xuan,cart_tv_price;
    private Button cart_btn_xiaban;
    private CarPresenter carPresenter;
    private String uid;
    private String token="1";
    private CartExpandAdapter cartExpandAdapter;
    private boolean ischecked=true;

    public CarFragment() {
        // Required empty public constructor
    }

    @Override
    protected void initListener() {
        cart_check.setOnClickListener(this);
        cart_btn_xiaban.setOnClickListener(this);
        //设置父条目不可点击
        cart_expand.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                return true;
            }
        });




        cart_iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });




    }

    private void showDialog() {

        //*****注意在fragment中使用AlertDialog时getActivity()
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("title")
                .setMessage("message")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                }).show();
    }

    @Override
    protected void initData() {
        carPresenter=new CarPresenter(this);
        if (UserManage.getInstance().hasUserInfo(MyApp.getContext()))//自动登录判断，SharePrefences中有数据，
        {
            uid = UserManage.getInstance().getUserInfo(MyApp.getContext()).getUid();
            carPresenter.getCart(uid,token);
        } else {
            startActivity(new Intent(MyApp.getContext(), LoginActivity.class));
        }


    }

    @Override
    protected void initViews(View view) {
        cart_iv_back=view.findViewById(R.id.cart_iv_back);
        cart_expand=view.findViewById(R.id.cart_expand);
        cart_check=view.findViewById(R.id.cart_check);
        cart_tv_xuan=view.findViewById(R.id.cart_tv_xuan);
        cart_tv_price=view.findViewById(R.id.cart_tv_price);
        cart_btn_xiaban=view.findViewById(R.id.cart_btn_xiaban);
        //设置属性去掉默认向下的箭头
        cart_expand.setGroupIndicator(null);
    }

    @Override
    protected int privideLayoutId() {
        return R.layout.fragment_car;
    }

    @Override
    public void OnSuccess(CartBean cartBean) {
        if (cartBean!=null){
            Toast.makeText(MyApp.getContext(),""+cartBean.getMsg(),Toast.LENGTH_SHORT).show();
            cartExpandAdapter = new CartExpandAdapter(cartBean.getData(), new CartExpandAdapter.Callback() {
                @Override
                public void buyGoodsSumListener(int goodsMoney, int goodsNum) {
                    cart_tv_xuan.setText("数量："+goodsNum);
                    cart_tv_price.setText("小计："+goodsMoney);
                }
            });
            cart_expand.setAdapter(cartExpandAdapter);
            //设置子条目不点击展示
            int count = cart_expand.getCount();
            for (int i = 0; i < count; i++) {
                cart_expand.expandGroup(i);
            }

            //删除
            cartExpandAdapter.setDeleteCallback(new CartExpandAdapter.DeleteCallback() {
                @Override
                public void onDelete(final int pid) {

            //*****注意在fragment中使用AlertDialog时getActivity()
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("删除")
                    .setMessage("您确定要删除吗")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            final String uid = UserManage.getInstance().getUserInfo(MyApp.getContext()).getUid();
                            String pid1=String.valueOf(pid);
                            Observable<DeleteBean> observable = Retrofit_RXJava.getInstance().getInterface().getDelete(uid, pid1);
                            observable.subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(new Observer<DeleteBean>() {
                                        @Override
                                        public void onCompleted() {

                                        }

                                        @Override
                                        public void onError(Throwable e) {

                                        }

                                        @Override
                                        public void onNext(DeleteBean deleteBean) {
                                            //删除成功以后再查询一次购物车
                                            carPresenter.getCart(uid,token);
                                        }
                                    });
                        }
                    })
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                        }
                    }).show();
                }
            });

        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.cart_check:
                quanxuan();
                break;
            case R.id.cart_btn_xiaban:

                break;
        }
    }

    private void quanxuan() {
        if (ischecked){
            cartExpandAdapter.setIsSelect(ischecked);
            cart_check.setChecked(ischecked);
            ischecked=false;
        }else {
            cartExpandAdapter.setIsSelect(ischecked);
            cart_check.setChecked(ischecked);
            ischecked=true;

        }
    }
}
