package com.cw.jerrbase.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.ToxicBakery.viewpager.transforms.ABaseTransformer;
import com.ToxicBakery.viewpager.transforms.AccordionTransformer;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.cw.jerrbase.R;
import com.cw.jerrbase.adapter.BannerAdapter;
import com.cw.jerrbase.base.fragment.BaseFragment;
import com.cw.jerrbase.base.glide.GlideUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @version V1.0
 * @ClassName: ${CLASS_NAME}
 * @Description: (首页)
 * @create by: chenwei
 * @date 2016/9/30 15:38
 */
public class MainFragment extends BaseFragment {

    @BindView(R.id.main_banner)
    ConvenientBanner mConvenientBanner;
    @BindView(R.id.main_lv)
    ListView mListView;

    private static List<String> imgList = new ArrayList<>();
    private static List<String> transformList = new ArrayList<>();

    static {
        imgList.add("http://img02.tooopen.com/images/20141231/sy_78292194946.jpg");
        imgList.add("http://img02.tooopen.com/images/20141225/sy_77939178247.jpg");
        imgList.add("http://img02.tooopen.com/images/20141222/sy_77676184738.jpg");
        imgList.add("http://img05.tooopen.com/images/20141203/sy_76337816975.jpg");
        imgList.add("http://img05.tooopen.com/images/20141127/sy_75921322624.jpg");

        transformList.add("DefaultTransformer");
        transformList.add("AccordionTransformer");
        transformList.add("BackgroundToForegroundTransformer");
        transformList.add("CubeInTransformer");
        transformList.add("CubeOutTransformer");
        transformList.add("DepthPageTransformer");
        transformList.add("FlipHorizontalTransformer");
        transformList.add("FlipVerticalTransformer");
        transformList.add("ForegroundToBackgroundTransformer");
        transformList.add("RotateDownTransformer");
        transformList.add("RotateUpTransformer");
        transformList.add("StackTransformer");
        transformList.add("ZoomInTransformer");
        transformList.add("ZoomOutTranformer");
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.fragment_main;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = super.onCreateView(inflater, container, savedInstanceState);
        initBanner();
        initTransform();
        return mView;
    }

    private void initBanner() {
        mConvenientBanner.setPages(new CBViewHolderCreator() {
            @Override
            public Object createHolder() {
                return new LocalImageHolderView();
            }
        }, imgList)
                .setPageIndicator(new int[]{R.drawable.ic_unselect_point, R.drawable.ic_select_point})
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.ALIGN_PARENT_RIGHT);
        mConvenientBanner.getViewPager().setPageTransformer(true, new AccordionTransformer());
    }

    private void initTransform() {
        BannerAdapter adapter = new BannerAdapter(mContext);
        adapter.addAll(transformList);
        mListView.setAdapter(adapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                try {
                    Class cls = Class.forName("com.ToxicBakery.viewpager.transforms." + transformList.get(i));
                    ABaseTransformer transformer = (ABaseTransformer) cls.newInstance();
                    mConvenientBanner.getViewPager().setPageTransformer(true, transformer);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (java.lang.InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    class LocalImageHolderView implements Holder<String> {

        private ImageView iv;

        @Override
        public View createView(Context context) {
            iv = new ImageView(context);
            iv.setScaleType(ImageView.ScaleType.FIT_XY);
            return iv;
        }

        @Override
        public void UpdateUI(Context context, int position, String data) {
            GlideUtil.displayImage(context, data, iv);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mConvenientBanner.startTurning(3000);
    }

    @Override
    public void onPause() {
        super.onPause();
        mConvenientBanner.stopTurning();
    }
}
