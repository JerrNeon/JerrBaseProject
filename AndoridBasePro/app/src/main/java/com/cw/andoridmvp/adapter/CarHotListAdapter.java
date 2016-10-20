package com.cw.andoridmvp.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cw.andoridmvp.R;
import com.cw.andoridmvp.base.adapter.BaseListAdapter;
import com.cw.andoridmvp.base.adapter.ToolViewHolder;
import com.cw.andoridmvp.base.glide.GlideUtil;
import com.cw.andoridmvp.bean.MarketMainModel;
import com.cw.andoridmvp.util.QMUtil;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.List;

/**
 * Created by SEED on 2016/2/19.
 */
public class CarHotListAdapter extends BaseListAdapter<MarketMainModel> {


    public CarHotListAdapter(Context context, List<MarketMainModel> list) {
        super(context, list);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_postcar;
    }

    @Override
    public void getView(int position, ToolViewHolder holder, MarketMainModel bean) {
        MarketMainModel model = getItem(position);
        ImageView iv_product_icon = holder.getChildView(R.id.iv_postcar_img);//车型图标
        TextView tv_product_title = holder.getChildView(R.id.tv_postcar_name);//车型
        TextView tv_product_price = holder.getChildView(R.id.tv_postcar_price);//价格
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


        GlideUtil.displayImage(mContext, model.getImg(), iv_product_icon);
        if (QMUtil.isNotEmpty(model.getName())) {
            tv_product_title.setText(model.getName());
        }
        if (QMUtil.isEmpty(model.getCustomcar())) {
            if (QMUtil.isNotEmpty(model.getColor1()) || QMUtil.isNotEmpty(model.getColor2())) {
                tv_product_color.setText(model.getColor1() + "/" + model.getColor2());
            }
        } else {
            try {
                JSONArray customsArr = new JSONArray(model.getCustomcar());
                if (customsArr.length() == 3)
                    tv_product_color.setText(customsArr.optString(1) + "/" + customsArr.optString(2));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
//        double price = Utils.strTodouble(model.getPrice());
//        tv_product_price.setText(price == 0 ? "电议" : String.format("%.2f", price) + "万");
//        if (QMUtil.isNotEmpty(model.getTime())) {
//            tv_product_date.setText(Utils.getStrTimeNoH(model.getTime() + ""));
//        }
        if (QMUtil.isNotEmpty(model.getAddressfrom())) {
            iv_product_addr.setVisibility(View.VISIBLE);
            tv_product_fromaddr.setText(model.getAddressfrom());
        } else {
            iv_product_addr.setVisibility(View.GONE);
            tv_product_fromaddr.setText("");
        }
        if (QMUtil.isNotEmpty(model.getAddressto())) {
            tv_product_toaddr.setText(model.getAddressto().toString());
        }
        //double guideprice = Utils.strTodouble(model.getGuideprice1());
//        String feerule;
//        if (QMUtil.isEmpty(model.getFeerule()))
//            feerule = "电议";
//        else {
//            if ("下点".equals(model.getFeerule()) && QMUtil.isNotEmpty(model.getFeevalue()))
//                feerule = String.format("下%s点", model.getFeevalue());
//            else if ("优惠".equals(model.getFeerule())) {
//                feerule = String.format("优惠%s万", MathUtil.sub(guideprice, price) + "");
//            } else if ("加".equals(model.getFeerule())) {
//                feerule = String.format("加%s万", MathUtil.sub(price, guideprice) + "");
//            } else
//                feerule = model.getFeerule();
//        }
//        if (guideprice == 0)
//            tv_productcar_unit.setText(feerule);
//        else {
//            String prePrice = String.format("%.2f", guideprice) + "万";
//            tv_productcar_unit.setText(String.format("%s/%s", prePrice, feerule));
//        }
        if (QMUtil.isNotEmpty(model.getSpecifications()) && QMUtil.isNotEmpty(model.getCarstatus())) {
            tv_product_spec.setText(String.format("%s/%s", model.getSpecifications(), model.getCarstatus()));
        }
        if (QMUtil.isEmpty(model.getRemark())) {
            ll_productcar_remark.setVisibility(View.GONE);
        } else {
            ll_productcar_remark.setVisibility(View.VISIBLE);
            tv_productcar_remark.setText(model.getRemark());
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