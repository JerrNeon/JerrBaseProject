package com.cw.andoridmvp.widget;

import android.animation.AnimatorSet;
import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cw.andoridmvp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @version V1.0
 * @ClassName: ${CLASS_NAME}
 * @Description: (清除动画)
 * @create by: chenwei
 * @date 2017/2/27 18:26
 */
public class CleanView extends FrameLayout {

    private NoScrollGridView mNoScrollGridView;
    private ImageView mIvCleanIndicator;

    private BaseAdapter mAdapter;
    private List<String> mList = new ArrayList<>();

    public CleanView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initClean(context);
        initData(context);
    }

    private void initClean(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.view_clean, this, true);
        LinearLayout llclean = (LinearLayout) view.findViewById(R.id.ll_expanded_clean);
        llclean.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                anim2();
            }
        });
        mNoScrollGridView = (NoScrollGridView) view.findViewById(R.id.ll_expanded_gv);
        mIvCleanIndicator = (ImageView) view.findViewById(R.id.iv_expanded_cleanindicator);
    }

    private void initData(final Context context) {
        for (int i = 0; i < 12; i++) {
            mList.add("子项" + i);
        }
        mAdapter = new MyAdapter(context, mList);
        mNoScrollGridView.setAdapter(mAdapter);
    }

    private void anim2() {
        Keyframe keyframe1 = Keyframe.ofFloat(0, -20);
        Keyframe keyframe2 = Keyframe.ofFloat(0.1f, 20);
        Keyframe keyframe3 = Keyframe.ofFloat(0.2f, -20);
        Keyframe keyframe4 = Keyframe.ofFloat(0.3f, 20);
        Keyframe keyframe5 = Keyframe.ofFloat(0.4f, -20);
        Keyframe keyframe6 = Keyframe.ofFloat(0.5f, 20);
        Keyframe keyframe7 = Keyframe.ofFloat(0.6f, -20);
        Keyframe keyframe8 = Keyframe.ofFloat(0.7f, 20);
        Keyframe keyframe9 = Keyframe.ofFloat(0.8f, -20);
        Keyframe keyframe10 = Keyframe.ofFloat(0.9f, 20);
        Keyframe keyframe11 = Keyframe.ofFloat(1, 0);
        //左右旋转
        PropertyValuesHolder propertyValuesHolder = PropertyValuesHolder.ofKeyframe("rotation",
                keyframe1, keyframe2, keyframe3, keyframe4, keyframe5, keyframe6, keyframe7, keyframe8, keyframe9, keyframe10, keyframe11);
        //向左移动
        PropertyValuesHolder propertyValuesHolder1 = PropertyValuesHolder.ofFloat("translationX", 0, -mIvCleanIndicator.getLeft());
        PropertyValuesHolder propertyValuesHolder2 = PropertyValuesHolder.ofFloat("translationY", 0, 50);
        PropertyValuesHolder propertyValuesHolder3 = PropertyValuesHolder.ofFloat("translationX", -mIvCleanIndicator.getLeft(), 0);
        PropertyValuesHolder propertyValuesHolder4 = PropertyValuesHolder.ofFloat("translationY", 50, 100);
        PropertyValuesHolder propertyValuesHolder5 = PropertyValuesHolder.ofFloat("translationX", 0, -mIvCleanIndicator.getLeft());

        ObjectAnimator objectAnimator1 = ObjectAnimator.ofPropertyValuesHolder(mIvCleanIndicator, propertyValuesHolder, propertyValuesHolder1);
        ObjectAnimator objectAnimator2 = ObjectAnimator.ofPropertyValuesHolder(mIvCleanIndicator, propertyValuesHolder, propertyValuesHolder2);
        ObjectAnimator objectAnimator3 = ObjectAnimator.ofPropertyValuesHolder(mIvCleanIndicator, propertyValuesHolder, propertyValuesHolder3);
        ObjectAnimator objectAnimator4 = ObjectAnimator.ofPropertyValuesHolder(mIvCleanIndicator, propertyValuesHolder, propertyValuesHolder4);
        ObjectAnimator objectAnimator5 = ObjectAnimator.ofPropertyValuesHolder(mIvCleanIndicator, propertyValuesHolder, propertyValuesHolder5);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(3000);
        animatorSet.playSequentially(objectAnimator1, objectAnimator2, objectAnimator3, objectAnimator4, objectAnimator5);
        animatorSet.start();

    }

    class MyAdapter extends BaseAdapter {

        private Context context;
        private List<String> list = null;

        public MyAdapter(Context context, List<String> list) {
            this.context = context;
            this.list = list;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public String getItem(int i) {
            return list.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            TextView tv = new TextView(context);
            tv.setLayoutParams(new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, AbsListView.LayoutParams.WRAP_CONTENT));
            tv.setGravity(Gravity.CENTER);
            tv.setTextSize(16);
            tv.setText(getItem(i));
            return tv;
        }
    }
}
