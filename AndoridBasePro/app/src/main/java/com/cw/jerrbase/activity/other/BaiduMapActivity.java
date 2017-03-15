package com.cw.jerrbase.activity.other;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.route.BikingRouteLine;
import com.baidu.mapapi.search.route.DrivingRouteLine;
import com.baidu.mapapi.search.route.TransitRouteLine;
import com.baidu.mapapi.search.route.WalkingRouteLine;
import com.cw.jerrbase.R;
import com.cw.jerrbase.base.activity.BaseTbActivity;
import com.cw.jerrbase.ttpapi.map.BaiduMapManage;
import com.cw.jerrbase.ttpapi.map.callback.LoctionResultListener;
import com.cw.jerrbase.ttpapi.map.callback.RouteSearchResultListener;
import com.cw.jerrbase.util.ToastUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @version V1.0
 * @ClassName: ${CLASS_NAME}
 * @Description: (BaiDuMap)
 * @create by: chenwei
 * @date 2017/3/2 15:56
 */
public class BaiduMapActivity extends BaseTbActivity {

    @BindView(R.id.bm_view)
    MapView mBmView;
    @BindView(R.id.bm_car)
    TextView mBmCar;
    @BindView(R.id.bm_bus)
    TextView mBmBus;
    @BindView(R.id.bm_cycling)
    TextView mBmCycling;
    @BindView(R.id.bm_walk)
    TextView mBmWalk;

    private BaiduMap mBaiduMap = null;
    private BaiduMapManage mBaiduMapManage = null;
    private LatLng locLng = null;
    private String cityName = "";
    private LatLng toll = new LatLng(31.116581, 121.391623);

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_baidumap;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleName("BaiDuMap");
        init();
    }

    private void init() {
        mBmCar.setSelected(true);
        mBaiduMap = mBmView.getMap();

        mBaiduMapManage = BaiduMapManage.getInstance();
        mBaiduMapManage.startLoc(new LoctionResultListener() {
            @Override
            public void onSuccess(BDLocation location) {
                locLng = new LatLng(location.getLatitude(), location.getLongitude());
                cityName = location.getCity();
                mBaiduMapManage.updateLocationMapStatus(mBaiduMap, location);
                mBaiduMapManage.drivingSearch(cityName, locLng, toll, null, new MyRouteSearchResultListener());
            }

            @Override
            public void onFailure(BDLocation location) {
                ToastUtil.showToast(mContext, "定位失败");
            }
        });
    }

    private class MyRouteSearchResultListener implements RouteSearchResultListener {

        @Override
        public void onDrivingSuccess(List<DrivingRouteLine> rLines) {
            mBaiduMapManage.updateDrivingSearchMapStatus(mBaiduMap, rLines);
        }

        @Override
        public void onTransitSuccess(List<TransitRouteLine> rLines) {
            mBaiduMapManage.updateTransitSearchMapStatus(mBaiduMap, rLines);
        }

        @Override
        public void onBikingSuccess(List<BikingRouteLine> rLines) {
            mBaiduMapManage.updateBikingSearchMapStatus(mBaiduMap, rLines);
        }

        @Override
        public void onWalkingSuccess(List<WalkingRouteLine> rLines) {
            mBaiduMapManage.updateWalkingSearchMapStatus(mBaiduMap, rLines);
        }

        @Override
        public void onFailure(Type type) {
            switch (type) {
                case DRIVING:
                    showToast("驾车路线获取失败");
                    break;
                case TRANSIT:
                    showToast("公交路线获取失败");
                    break;
                case BIKING:
                    showToast("骑行路线获取失败");
                    break;
                case WALKING:
                    showToast("步行路线获取失败");
                    break;
                default:
                    break;
            }
        }
    }


    @OnClick({R.id.bm_car, R.id.bm_bus, R.id.bm_cycling, R.id.bm_walk})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bm_car:
                mBaiduMapManage.drivingSearch(cityName, locLng, toll, null, new MyRouteSearchResultListener());
                mBmCar.setSelected(true);
                mBmBus.setSelected(false);
                mBmCycling.setSelected(false);
                mBmWalk.setSelected(false);
                break;
            case R.id.bm_bus:
                mBaiduMapManage.transitSearch(cityName, locLng, toll, null, new MyRouteSearchResultListener());
                mBmCar.setSelected(false);
                mBmBus.setSelected(true);
                mBmCycling.setSelected(false);
                mBmWalk.setSelected(false);
                break;
            case R.id.bm_cycling:
                mBaiduMapManage.bikingSearch(locLng, toll, new MyRouteSearchResultListener());
                mBmCar.setSelected(false);
                mBmBus.setSelected(false);
                mBmCycling.setSelected(true);
                mBmWalk.setSelected(false);
                break;
            case R.id.bm_walk:
                mBaiduMapManage.walkingSearch(locLng, toll, new MyRouteSearchResultListener());
                mBmCar.setSelected(false);
                mBmBus.setSelected(false);
                mBmCycling.setSelected(false);
                mBmWalk.setSelected(true);
                break;
        }
    }

    @Override
    protected void onResume() {
        mBaiduMapManage.onResume(mBmView);
        super.onResume();
    }

    @Override
    protected void onPause() {
        mBaiduMapManage.onPause(mBmView);
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        mBaiduMapManage.onDestroy(mBmView);
        super.onDestroy();
    }
}
