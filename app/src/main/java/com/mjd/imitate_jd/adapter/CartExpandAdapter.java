package com.mjd.imitate_jd.adapter;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.mjd.imitate_jd.R;
import com.mjd.imitate_jd.app.MyApp;
import com.mjd.imitate_jd.bean.CartBean;
import com.mjd.imitate_jd.bean.DeleteBean;
import com.mjd.imitate_jd.bean.UpdateBean;
import com.mjd.imitate_jd.utils.Retrofit_RXJava;
import com.mjd.imitate_jd.utils.UserManage;

import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
*@date: 2018/8/16 0016
*@author: 老街旧人
*@Description:   bargainPrice和price改为double类型
*@version:
**/
public class CartExpandAdapter extends BaseExpandableListAdapter {


    private List<CartBean.DataBean> list;
    private Callback callback;
    private int goodsMoney,goodsNum;

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    public CartExpandAdapter(List<CartBean.DataBean> list,Callback callback) {
        this.list = list;
        this.callback=callback;
    }

    /**
     * 统计小计 获取选中的个数
     */
    public void getcountsMoneyAndNum(){
        goodsMoney=0;
        goodsNum=0;
        if (list==null){
            return;
        }else {
            for (CartBean.DataBean li:list) {
                List<CartBean.DataBean.ListBean> l = li.getList();
                for (CartBean.DataBean.ListBean l1:l) {
                    //判断只有复选框选中时才计算
                    if (l1.getSelected()==1){
                        goodsNum+=l1.getNum();
                        goodsMoney+=(l1.getNum()*l1.getBargainPrice());
                    }
                }
            }
        }
    }

    //将所有的单选框设为全选或全不选
    public void setIsSelect(boolean ischecked){
        for (CartBean.DataBean li:list) {
            List<CartBean.DataBean.ListBean> list1 = li.getList();
            for (CartBean.DataBean.ListBean l:list1) {
                if (ischecked){
                    l.setSelected(1);
                }else {
                    l.setSelected(0);
                }
            }
        }
        notifyDataSetChanged();
    }

    @Override
    public int getGroupCount() {
        return list.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return list.get(groupPosition).getList().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return list.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return list.get(groupPosition).getList().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupViewHolder groupViewHolder=null;
        if(convertView==null){
            convertView=LayoutInflater.from(MyApp.getContext()).inflate(R.layout.cart_group_item,parent,false);
            groupViewHolder=new GroupViewHolder();
            groupViewHolder.textView=convertView.findViewById(R.id.cart_group_title);
            convertView.setTag(groupViewHolder);
        }else{
            groupViewHolder= (GroupViewHolder) convertView.getTag();
        }
        groupViewHolder.textView.setText(list.get(groupPosition).getSellerName());
        return convertView;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, final ViewGroup parent) {
        ChildViewHolder childViewHolder=null;
        if (convertView==null){
            convertView=LayoutInflater.from(MyApp.getContext()).inflate(R.layout.cart_child_item,parent,false);
            childViewHolder=new ChildViewHolder();
            childViewHolder.checkBox=convertView.findViewById(R.id.cart_child_box);
            childViewHolder.imageView=convertView.findViewById(R.id.cart_child_img);
            childViewHolder.tvTitile=convertView.findViewById(R.id.cart_child_title);
            childViewHolder.tvPrice=convertView.findViewById(R.id.cart_child_price);
            childViewHolder.tvJian=convertView.findViewById(R.id.cart_jian);
            childViewHolder.tvJia=convertView.findViewById(R.id.cart_jia);
            childViewHolder.tvNum=convertView.findViewById(R.id.cart_sum);
            convertView.setTag(childViewHolder);
        }else {
            childViewHolder= (ChildViewHolder) convertView.getTag();
        }
        final List<CartBean.DataBean.ListBean> listBeans = this.list.get(groupPosition).getList();

        //默认显示复选框是否选中
        int selected = listBeans.get(childPosition).getSelected();
        if(selected==1){
            childViewHolder.checkBox.setChecked(true);
        }else{
            childViewHolder.checkBox.setChecked(false);
        }
        Glide.with(MyApp.getContext()).load(listBeans.get(childPosition).getImages().split("\\|")[0]).into(childViewHolder.imageView);
        childViewHolder.tvTitile.setText(listBeans.get(childPosition).getTitle());
        //设置默认的数量
        childViewHolder.tvNum.setText(listBeans.get(childPosition).getNum()+"");

        //因为是double类型所以要加“”，基本类中字段中有小数点都改成double类型
        childViewHolder.tvPrice.setText(listBeans.get(childPosition).getBargainPrice()+"");


        //避免点击字条目中的加减号选中或是取消字条目的复选框
        childViewHolder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selected1 = listBeans.get(childPosition).getSelected();
                if (selected1==0){
                    listBeans.get(childPosition).setSelected(1);
                }else if (selected1==1){
                    listBeans.get(childPosition).setSelected(0);
                }
                //刷新适配器，使显示器上的值改变
                notifyDataSetChanged();
            }
        });

        //购物车减
        childViewHolder.tvJian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int num = listBeans.get(childPosition).getNum();
                if (num>0){
                    num--;
                    listBeans.get(childPosition).setNum(num);
                    //强转为String类型
                    String uid = UserManage.getInstance().getUserInfo(MyApp.getContext()).getUid();
                    String sellerid = String.valueOf(listBeans.get(childPosition).getSellerid());
                    String pid = String.valueOf(listBeans.get(childPosition).getPid());
                    String selected1 = String.valueOf(listBeans.get(childPosition).getSelected());
                    String num1 = String.valueOf(listBeans.get(childPosition).getNum());
                    //进行网络请求更新数据
                    updata(uid,sellerid,pid,selected1,num1);
                }else {
                    deleteCallback.onDelete(listBeans.get(childPosition).getPid());
                }
                //刷新适配器，使显示器上的值改变
                notifyDataSetChanged();
            }
        });
        //购物车加
        childViewHolder.tvJia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int num = listBeans.get(childPosition).getNum();
                num++;
                listBeans.get(childPosition).setNum(num);
                String uid = UserManage.getInstance().getUserInfo(MyApp.getContext()).getUid();
                String sellerid = String.valueOf(listBeans.get(childPosition).getSellerid());
                String pid = String.valueOf(listBeans.get(childPosition).getPid());
                String selected1 = String.valueOf(listBeans.get(childPosition).getSelected());
                String num1 = String.valueOf(listBeans.get(childPosition).getNum());
                //进行网络请求更新数据
                updata(uid,sellerid,pid,selected1,num1);
                //刷新适配器，使显示器上的值改变
                notifyDataSetChanged();
            }
        });

        //删除
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteCallback.onDelete(listBeans.get(childPosition).getPid());
            }
        });

        return convertView;
    }

    private void updata(String uid, String sellerid, String pid, String selected1, String num1) {
        Observable<UpdateBean> observable = Retrofit_RXJava.getInstance().getInterface().getUpdate(uid, sellerid, pid, selected1, num1);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<UpdateBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(UpdateBean updateBean) {
                        //Toast.makeText(MyApp.getContext(),""+updateBean.getMsg(),Toast.LENGTH_SHORT).show();
                    }
                });

    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    public class GroupViewHolder{
        TextView textView;
    }

    public class ChildViewHolder{
        CheckBox checkBox;
        ImageView imageView;
        TextView tvTitile;
        TextView tvPrice;
        TextView tvJian;
        TextView tvNum;
        TextView tvJia;

    }

    public interface Callback{
        void buyGoodsSumListener(int goodsMoney,int goodsNum);
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
        getcountsMoneyAndNum();
        callback.buyGoodsSumListener(goodsMoney,goodsNum);
    }
    private DeleteCallback deleteCallback;

    public void setDeleteCallback(DeleteCallback deleteCallback) {
        this.deleteCallback = deleteCallback;
    }

    public interface DeleteCallback{
        void onDelete(int pid);
    }

}
