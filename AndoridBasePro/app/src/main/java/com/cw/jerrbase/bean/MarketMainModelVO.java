package com.cw.jerrbase.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

import java.io.Serializable;

/**
 * @version V1.0
 * @ClassName: ${CLASS_NAME}
 * @Description: (首页搜索)
 * @create by: chenwei
 * @date 2016/9/14 10:56
 */
@Entity
public class MarketMainModelVO implements Serializable {

    private static final long serialVersionUID = 23837587814483930L;
    
    @Id
    private long id;//ID
    private String name;//车型名称
    private String img;//图片路径(列表小图片，如果是车源则读取品牌对应的logo，如果是寻车则为固定内容app默认)
    private String color1;//外观颜色
    private String color2;//内饰颜色
    private String specifications;//规格
    private String starlevelname;//信誉度（星级）
    private String remark;//备注
    private long time;//日期
    private String price;//价格(万)
    private String guideprice1;//指导价下限(万)
    private String guideprice2;//指导价上限(万)
    private String adprice;//订金
    private String cardiscount;//折扣(优惠)
    private String carstatus;//车辆状态（寻车无返回为空，车源有直接返回）
    private String addressfrom;//发货地区（寻车无返回为空）
    private String addressto;//销售地区
    private String type;//类型（1车源 2寻车）
    private String licensearea;//上牌地点

    private String customcar;//自定义车型(返回的是个json字符串，第一个为自定义车型内容，第二个为外观颜色，第三个为内饰颜色)
    private String feerule;//价格规则(下点，直接报价，优惠，加)
    private String feevalue;//下点的数量

    @Generated(hash = 1504032918)
    public MarketMainModelVO(long id, String name, String img, String color1,
                             String color2, String specifications, String starlevelname,
                             String remark, long time, String price, String guideprice1,
                             String guideprice2, String adprice, String cardiscount,
                             String carstatus, String addressfrom, String addressto, String type,
                             String licensearea, String customcar, String feerule, String feevalue) {
        this.id = id;
        this.name = name;
        this.img = img;
        this.color1 = color1;
        this.color2 = color2;
        this.specifications = specifications;
        this.starlevelname = starlevelname;
        this.remark = remark;
        this.time = time;
        this.price = price;
        this.guideprice1 = guideprice1;
        this.guideprice2 = guideprice2;
        this.adprice = adprice;
        this.cardiscount = cardiscount;
        this.carstatus = carstatus;
        this.addressfrom = addressfrom;
        this.addressto = addressto;
        this.type = type;
        this.licensearea = licensearea;
        this.customcar = customcar;
        this.feerule = feerule;
        this.feevalue = feevalue;
    }

    @Generated(hash = 1906261345)
    public MarketMainModelVO() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getColor1() {
        return color1;
    }

    public void setColor1(String color1) {
        this.color1 = color1;
    }

    public String getColor2() {
        return color2;
    }

    public void setColor2(String color2) {
        this.color2 = color2;
    }

    public String getSpecifications() {
        return specifications;
    }

    public void setSpecifications(String specifications) {
        this.specifications = specifications;
    }

    public String getStarlevelname() {
        return starlevelname;
    }

    public void setStarlevelname(String starlevelname) {
        this.starlevelname = starlevelname;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getGuideprice1() {
        return guideprice1;
    }

    public void setGuideprice1(String guideprice1) {
        this.guideprice1 = guideprice1;
    }

    public String getGuideprice2() {
        return guideprice2;
    }

    public void setGuideprice2(String guideprice2) {
        this.guideprice2 = guideprice2;
    }

    public String getAdprice() {
        return adprice;
    }

    public void setAdprice(String adprice) {
        this.adprice = adprice;
    }

    public String getCardiscount() {
        return cardiscount;
    }

    public void setCardiscount(String cardiscount) {
        this.cardiscount = cardiscount;
    }

    public String getCarstatus() {
        return carstatus;
    }

    public void setCarstatus(String carstatus) {
        this.carstatus = carstatus;
    }

    public String getAddressfrom() {
        return addressfrom;
    }

    public void setAddressfrom(String addressfrom) {
        this.addressfrom = addressfrom;
    }

    public String getAddressto() {
        return addressto;
    }

    public void setAddressto(String addressto) {
        this.addressto = addressto;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCustomcar() {
        return customcar;
    }

    public void setCustomcar(String customcar) {
        this.customcar = customcar;
    }

    public String getFeerule() {
        return feerule;
    }

    public void setFeerule(String feerule) {
        this.feerule = feerule;
    }

    public String getFeevalue() {
        return feevalue;
    }

    public void setFeevalue(String feevalue) {
        this.feevalue = feevalue;
    }

    public String getLicensearea() {
        return licensearea;
    }

    public void setLicensearea(String licensearea) {
        this.licensearea = licensearea;
    }
}
