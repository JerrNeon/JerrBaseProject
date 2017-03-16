package com.cw.jerrbase.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cw.jerrbase.R;
import com.cw.jerrbase.base.adapter.BaseListAdapter;
import com.cw.jerrbase.base.adapter.BaseListHolder;
import com.cw.jerrbase.bean.MarketMainModel;
import com.cw.jerrbase.util.MathUtil;

import org.json.JSONArray;
import org.json.JSONException;

/**
 * Created by SEED on 2016/2/19.
 */
public class CarHotListAdapter extends BaseListAdapter<MarketMainModel> {

    public CarHotListAdapter(Context context) {
        super(context);
    }

    public CarHotListAdapter(Context context, Fragment fragment) {
        super(context, fragment);
    }

    @Override
    public int getLayoutResourceId() {
        return R.layout.fragment_postcar;
    }

    @Override
    public void getView(int position, BaseListHolder holder, MarketMainModel bean) {
        TextView tv_product_spec = holder.getChildView(R.id.tv_postcar_spec);//规格
        TextView tv_productcar_unit = holder.getChildView(R.id.tv_postcar_unit);//单位
        TextView tv_product_color = holder.getChildView(R.id.tv_postcar_color);//颜色
        TextView tv_product_fromaddr = holder.getChildView(R.id.tv_postcar_fromaddr);//来源地址
        TextView tv_product_toaddr = holder.getChildView(R.id.tv_postcar_toaddr);//目的地址
        ImageView iv_product_addr = holder.getChildView(R.id.iv_postcar_addr);//地址图标
        TextView tv_productcar_remark = holder.getChildView(R.id.tv_postcar_remark);//备注
        //RatingBar tv_productcar_rb = holder.getChildView(R.id.tv_postcar_rb);//信誉度
        TextView tv_product_date = holder.getChildView(R.id.tv_postcar_date);//时间
        LinearLayout ll_productcar_remark = holder.getChildView(R.id.ll_postcar_remark);

        holder.displayImage(R.id.iv_postcar_img, bean.getImg());//车型图标
        holder.setText(R.id.tv_postcar_name, checkStr(bean.getName()));//车型
        if (checkObject(bean.getCustomcar()))
            holder.setText(R.id.tv_postcar_color, checkStr(bean.getColor1()) + "/" + checkStr(bean.getColor2()));
        else {
            try {
                JSONArray customsArr = new JSONArray(bean.getCustomcar());
                if (customsArr.length() == 3)
                    holder.setText(R.id.tv_postcar_color, checkStr(customsArr.optString(1)) + "/" + checkStr(customsArr.optString(2)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        //(String.format("%.2f", price)
        double price = str2Double(bean.getPrice());
        holder.setText(R.id.tv_postcar_price, price == 0 ? "电议" : formatPrice(bean.getPrice()) + "万");//价格
        holder.setText(R.id.tv_postcar_date, formatTime(bean.getTime()));//时间
        if (checkObject(bean.getAddressfrom())) {
            holder.setVisibity(R.id.iv_postcar_addr, View.GONE);
            holder.setVisibity(R.id.tv_postcar_fromaddr, View.GONE);
        } else {
            holder.setVisibity(R.id.iv_postcar_addr, View.VISIBLE);
            holder.setText(R.id.tv_postcar_fromaddr, bean.getAddressfrom());
        }
        holder.setText(R.id.tv_postcar_toaddr, bean.getAddressto());
        double guideprice = str2Double(bean.getGuideprice1());
        String feerule;
        if (checkObject(bean.getFeerule()))
            feerule = "电议";
        else {
            if ("下点".equals(bean.getFeerule()) && !checkObject(bean.getFeevalue()))
                feerule = String.format("下%s点", bean.getFeevalue());
            else if ("优惠".equals(bean.getFeerule())) {
                feerule = String.format("优惠%s万", MathUtil.sub(guideprice, price) + "");
            } else if ("加".equals(bean.getFeerule())) {
                feerule = String.format("加%s万", MathUtil.sub(price, guideprice) + "");
            } else
                feerule = bean.getFeerule();
        }
        if (guideprice == 0)
            holder.setText(R.id.tv_postcar_unit,feerule);
        else {
            String prePrice = String.format("%.2f", guideprice) + "万";
            tv_productcar_unit.setText(String.format("%s/%s", prePrice, feerule));
        }
        if (!checkObject(bean.getSpecifications()) && !checkObject(bean.getCarstatus())) {
            holder.setText(R.id.tv_postcar_spec, String.format("%s/%s", bean.getSpecifications(), bean.getCarstatus()));
        }
        if (checkObject(bean.getRemark())) {
            holder.setVisibity(R.id.ll_postcar_remark, View.GONE);
        } else {
            holder.setVisibity(R.id.ll_postcar_remark, View.VISIBLE);
            holder.setText(R.id.tv_postcar_remark, bean.getRemark());
        }
//        if (QMUtil.isEmpty(model.getStarlevelname()))
//            tv_productcar_rb.setRating(0);
//        else {
//            if (model.getStarlevelname().contains("一") || model.getStarlevelname().contains("1"))
//                tv_productcar_rb.setRating(1);
//            else if (model.getStarlevelname().contains("二") || model.getStarlevelname().contains("2"))
//                tv_productcar_rb.setRating(2);
//            else if (model.getStarlevelname().contains("三") || model.getStarlevelname().contains("3"))
//                tv_productcar_rb.setRating(3);
//            else if (model.getStarlevelname().contains("四") || model.getStarlevelname().contains("4"))
//                tv_productcar_rb.setRating(4);
//            else if (model.getStarlevelname().contains("五") || model.getStarlevelname().contains("5"))
//                tv_productcar_rb.setRating(5);
//            else
//                tv_productcar_rb.setRating(0);
//
//        }
    }
}