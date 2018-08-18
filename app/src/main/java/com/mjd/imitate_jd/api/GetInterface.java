package com.mjd.imitate_jd.api;

import com.mjd.imitate_jd.bean.AddCarBean;
import com.mjd.imitate_jd.bean.CartBean;
import com.mjd.imitate_jd.bean.ClassifyBean;
import com.mjd.imitate_jd.bean.ClassifyChildBean;
import com.mjd.imitate_jd.bean.DeleteBean;
import com.mjd.imitate_jd.bean.DetailsBean;
import com.mjd.imitate_jd.bean.HomesBean;
import com.mjd.imitate_jd.bean.LoginBean;
import com.mjd.imitate_jd.bean.RegBean;
import com.mjd.imitate_jd.bean.SearchBean;
import com.mjd.imitate_jd.bean.UpdateBean;

import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

public interface GetInterface {
    ////https://www.zhaoapi.cn/user/reg?mobile=12345678901&password=123456
    //注册
    @POST("user/reg")
    Observable<RegBean> getReg(@Query("mobile") String mobile,@Query("password") String password);
    //https://www.zhaoapi.cn/user/login?mobile=18311256817&password=123456
    //登录
    @POST("user/login")
    Observable<LoginBean> getLogin(@Query("mobile") String mobile,@Query("password") String password);

    //https://www.zhaoapi.cn/home/getHome
    @GET("home/getHome")
    Observable<HomesBean> getHomes();

    //商品分类
    //https://www.zhaoapi.cn/product/getCatagory
    @GET("product/getCatagory")
    Observable<ClassifyBean> getClassify();


    //商品子分类
    //https://www.zhaoapi.cn/product/getProductCatagory?cid=1
    @POST("product/getProductCatagory")
    Observable<ClassifyChildBean> getClassifyChild(@Query("cid") String cid);

    //详情
    //https://www.zhaoapi.cn/product/getProductDetail?pid=6
    @POST("product/getProductDetail")
    Observable<DetailsBean> getDetails(@Query("pid") String pid);


    //搜索
    //https://www.zhaoapi.cn/product/searchProducts?keywords=%E7%AC%94%E8%AE%B0%E6%9C%AC&page=1&sort=0
    @POST("product/searchProducts")
    Observable<SearchBean> getSearch(@Query("keywords") String keywords,@Query("page") int page,@Query("sort") String sort);

    //https://www.zhaoapi.cn/product/addCart?uid=16949&pid=6&token=0
    //添加购物车
    @POST("product/addCart")
    Observable<AddCarBean> getAddCar(@Query("uid") String uid,@Query("pid") String pid,@Query("token") String token);
    //https://www.zhaoapi.cn/product/getCarts?uid=16949&token=1
    //查询购物车
    @POST("product/getCarts")
    Observable<CartBean> getCart(@Query("uid") String uid,@Query("token") String token);

    //更新购物车
    //https://www.zhaoapi.cn/product/updateCarts?uid=16949&sellerid=1&pid=45&selected=1&num=10
    @POST("product/updateCarts")
    Observable<UpdateBean> getUpdate(@Query("uid") String uid,@Query("sellerid") String sellerid,@Query("pid") String pid,@Query("selected") String selected,@Query("num") String num);
    //删除购物车
    //https://www.zhaoapi.cn/product/deleteCart?uid=16949&pid=1
    @POST("product/deleteCart")
    Observable<DeleteBean> getDelete(@Query("uid") String uid,@Query("pid") String pid);



}
