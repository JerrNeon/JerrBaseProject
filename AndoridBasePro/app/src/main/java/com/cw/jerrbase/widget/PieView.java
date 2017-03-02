package com.cw.jerrbase.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.cw.jerrbase.bean.PieData;

import java.util.ArrayList;
import java.util.List;

/**
 * @version V1.0
 * @ClassName: ${CLASS_NAME}
 * @Description: (这里用一句话描述这个类的作用)
 * @create by: chenwei
 * @date 2016/10/18 17:21
 */
public class PieView extends View {

    private int[] colors = {0x9FCCFF00, 0xFF6495ED, 0xFFE32636, 0xFF800000, 0xFF808000, 0xFFFF8C69, 0xFF808080,
            0xFFE6B800, 0xFF7CFC00};

    private int mWidth, mHeight;

    private Paint mPaint = new Paint();

    private float mCurrent = 0;

    private List<PieData> datas = new ArrayList();

    public PieView(Context context) {
        super(context);
    }

    public PieView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setAntiAlias(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (null == datas) return;
        float strage = mCurrent;
        canvas.translate(mWidth / 2, mHeight / 2);
        float r = (float) (Math.min(mWidth, mHeight) / 2 * 0.8);
        RectF rectF = new RectF(-r, -r, r, r);
        for (int i = 0; i < datas.size(); i++) {
            PieData data = datas.get(i);
            mPaint.setColor(data.getColor());
            canvas.drawArc(rectF, strage, data.getStrage(), true, mPaint);
            strage += data.getStrage();
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
    }

    public void setStrage(int strage) {
        mCurrent = strage;
        invalidate();
    }

    public void setDatas(List<PieData> datas) {
        this.datas = datas;
        int sumValue = 0;
        for (int i = 0; i < datas.size(); i++) {
            sumValue += datas.get(i).getValue();
        }
        for (int i = 0; i < datas.size(); i++) {
            PieData data = datas.get(i);
            data.setColor(colors[i % colors.length]);
            data.setPercent((float)data.getValue() / sumValue);
            data.setStrage(data.getPercent() * 360);
        }
        invalidate();
    }

}
