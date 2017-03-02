package com.cw.jerrbase.bean;

/**
 * @version V1.0
 * @ClassName: ${CLASS_NAME}
 * @Description: (这里用一句话描述这个类的作用)
 * @create by: chenwei
 * @date 2016/10/18 17:29
 */
public class PieData {

    private int value;
    private int color;

    private float percent;
    private float strage;

    public PieData(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public PieData setValue(int value) {
        this.value = value;
        return this;
    }

    public int getColor() {
        return color;
    }

    public PieData setColor(int color) {
        this.color = color;
        return this;
    }

    public float getPercent() {
        return percent;
    }

    public PieData setPercent(float percent) {
        this.percent = percent;
        return this;
    }

    public float getStrage() {
        return strage;
    }

    public PieData setStrage(float strage) {
        this.strage = strage;
        return this;
    }

}
