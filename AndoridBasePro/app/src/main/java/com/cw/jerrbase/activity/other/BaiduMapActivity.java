package com.cw.jerrbase.activity.other;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.Poi;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.route.BikingRouteLine;
import com.baidu.mapapi.search.route.BikingRoutePlanOption;
import com.baidu.mapapi.search.route.BikingRouteResult;
import com.baidu.mapapi.search.route.DrivingRouteLine;
import com.baidu.mapapi.search.route.DrivingRoutePlanOption;
import com.baidu.mapapi.search.route.DrivingRouteResult;
import com.baidu.mapapi.search.route.IndoorRouteResult;
import com.baidu.mapapi.search.route.MassTransitRouteResult;
import com.baidu.mapapi.search.route.OnGetRoutePlanResultListener;
import com.baidu.mapapi.search.route.PlanNode;
import com.baidu.mapapi.search.route.RoutePlanSearch;
import com.baidu.mapapi.search.route.TransitRouteLine;
import com.baidu.mapapi.search.route.TransitRoutePlanOption;
import com.baidu.mapapi.search.route.TransitRouteResult;
import com.baidu.mapapi.search.route.WalkingRouteLine;
import com.baidu.mapapi.search.route.WalkingRoutePlanOption;
import com.baidu.mapapi.search.route.WalkingRouteResult;
import com.baidu.mapapi.utils.DistanceUtil;
import com.cw.jerrbase.BuildConfig;
import com.cw.jerrbase.R;
import com.cw.jerrbase.base.activity.BaseTbActivity;
import com.cw.jerrbase.common.Config;
import com.cw.jerrbase.ttpapi.map.overlayutil.BikingRouteOverlay;
import com.cw.jerrbase.ttpapi.map.overlayutil.DrivingRouteOverlay;
import com.cw.jerrbase.ttpapi.map.overlayutil.TransitRouteOverlay;
import com.cw.jerrbase.ttpapi.map.overlayutil.WalkingRouteOverlay;
import com.cw.jerrbase.util.gson.JsonUtils;

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
    private LocationClient mLocationClient = null;
    private BDLocationListener myListener = null;//定位监听
    private LocationClientOption mOption = null;//定位参数
    private RoutePlanSearch pSearch = null;
    private LatLng locLng = null;
    private String cityName = "";

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_baidumap;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleName("BaiDuMap");
        initBm();
    }

    private void initBm() {
        mBmCar.setSelected(true);

        mBaiduMap = mBmView.getMap();
        mBaiduMap.setMyLocationEnabled(true);

        mLocationClient = new LocationClient(getApplicationContext());
        myListener = new MyLocationListener();
        mLocationClient.registerLocationListener(myListener);

        mOption = new LocationClientOption();
        mOption.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        //可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        mOption.setCoorType("bd09ll");
        //可选，默认gcj02，设置返回的定位结果坐标系
        mOption.setScanSpan(0);
        //可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        mOption.setIsNeedAddress(true);
        //可选，设置是否需要地址信息，默认不需要
        mOption.setOpenGps(true);
        //可选，默认false,设置是否使用gps
        mOption.setLocationNotify(true);
        //可选，默认false，设置是否当GPS有效时按照1S/1次频率输出GPS结果
        mOption.setIsNeedLocationDescribe(true);
        //可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        mOption.setIsNeedLocationPoiList(true);
        //可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        mOption.setIgnoreKillProcess(false);
        //可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        mOption.SetIgnoreCacheException(false);
        //可选，默认false，设置是否收集CRASH信息，默认收集
        mOption.setEnableSimulateGps(false);
        //可选，默认false，设置是否需要过滤GPS仿真结果，默认需要
        mLocationClient.setLocOption(mOption);

        mLocationClient.start();
    }

    @OnClick({R.id.bm_car, R.id.bm_bus, R.id.bm_cycling, R.id.bm_walk})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bm_car:
                searchRoute(0);
                mBmCar.setSelected(true);
                mBmBus.setSelected(false);
                mBmCycling.setSelected(false);
                mBmWalk.setSelected(false);
                break;
            case R.id.bm_bus:
                searchRoute(1);
                mBmCar.setSelected(false);
                mBmBus.setSelected(true);
                mBmCycling.setSelected(false);
                mBmWalk.setSelected(false);
                break;
            case R.id.bm_cycling:
                searchRoute(2);
                mBmCar.setSelected(false);
                mBmBus.setSelected(false);
                mBmCycling.setSelected(true);
                mBmWalk.setSelected(false);
                break;
            case R.id.bm_walk:
                searchRoute(3);
                mBmCar.setSelected(false);
                mBmBus.setSelected(false);
                mBmCycling.setSelected(false);
                mBmWalk.setSelected(true);
                break;
        }
    }

    public class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            //获取定位结果
            StringBuffer sb = new StringBuffer(256);

            sb.append("time : ");
            sb.append(location.getTime());    //获取定位时间

            sb.append("\nerror code : ");
            sb.append(location.getLocType());    //获取类型类型

            sb.append("\nlatitude : ");
            sb.append(location.getLatitude());    //获取纬度信息

            sb.append("\nlontitude : ");
            sb.append(location.getLongitude());    //获取经度信息

            sb.append("\nradius : ");
            sb.append(location.getRadius());    //获取定位精准度

            if (location.getLocType() == BDLocation.TypeGpsLocation) {

                // GPS定位结果
                sb.append("\nspeed : ");
                sb.append(location.getSpeed());    // 单位：公里每小时

                sb.append("\nsatellite : ");
                sb.append(location.getSatelliteNumber());    //获取卫星数

                sb.append("\nheight : ");
                sb.append(location.getAltitude());    //获取海拔高度信息，单位米

                sb.append("\ndirection : ");
                sb.append(location.getDirection());    //获取方向信息，单位度

                sb.append("\naddr : ");
                sb.append(location.getAddrStr());    //获取地址信息

                sb.append("\ndescribe : ");
                sb.append("gps定位成功");

                setLocMarker(location);

            } else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {

                // 网络定位结果
                sb.append("\naddr : ");
                sb.append(location.getAddrStr());    //获取地址信息

                sb.append("\noperationers : ");
                sb.append(location.getOperators());    //获取运营商信息

                sb.append("\ndescribe : ");
                sb.append("网络定位成功");

                setLocMarker(location);

            } else if (location.getLocType() == BDLocation.TypeOffLineLocation) {

                // 离线定位结果
                sb.append("\ndescribe : ");
                sb.append("离线定位成功，离线定位结果也是有效的");

                setLocMarker(location);

            } else if (location.getLocType() == BDLocation.TypeServerError) {

                sb.append("\ndescribe : ");
                sb.append("服务端网络定位失败，可以反馈IMEI号和大体定位时间到loc-bugs@baidu.com，会有人追查原因");

            } else if (location.getLocType() == BDLocation.TypeNetWorkException) {

                sb.append("\ndescribe : ");
                sb.append("网络不同导致定位失败，请检查网络是否通畅");

            } else if (location.getLocType() == BDLocation.TypeCriteriaException) {

                sb.append("\ndescribe : ");
                sb.append("无法获取有效定位依据导致定位失败，一般是由于手机的原因，处于飞行模式下一般会造成这种结果，可以试着重启手机");

            }

            sb.append("\nlocationdescribe : ");
            sb.append(location.getLocationDescribe());    //位置语义化信息

            List<Poi> list = location.getPoiList();    // POI数据
            if (list != null) {
                sb.append("\npoilist size = : ");
                sb.append(list.size());
                for (Poi p : list) {
                    sb.append("\npoi= : ");
                    sb.append(p.getId() + " " + p.getName() + " " + p.getRank());
                }
            }
            if (BuildConfig.LOG_DEBUG)
                Log.i(Config.BAIDUMAP, sb.toString());
        }

        @Override
        public void onConnectHotSpotMessage(String s, int i) {

        }
    }

    private void setLocMarker(BDLocation location) {
        locLng = new LatLng(location.getLatitude(), location.getLongitude());
        cityName = location.getCity();
        MyLocationData myLocationData = new MyLocationData.Builder()
                .accuracy(location.getRadius())
                .latitude(location.getLatitude())
                .longitude(location.getLongitude())
                .build();
        mBaiduMap.setMyLocationData(myLocationData);
        mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newLatLng(locLng));
        searchRoute(0);
    }

    private void searchRoute(int type) {
        LatLng ll = new LatLng(31.116581, 121.391623);
        if (BuildConfig.LOG_DEBUG)
            Log.i(Config.TAG, "searchRoute: " + DistanceUtil.getDistance(locLng, ll) + "m");
        pSearch = RoutePlanSearch.newInstance();
        pSearch.setOnGetRoutePlanResultListener(new MyOnGetRoutePlanResultListener());
        PlanNode fromNode = PlanNode.withLocation(locLng);
        PlanNode toNode = PlanNode.withLocation(ll);
        mBaiduMap.clear();
        switch (type) {
            case 0:
                pSearch.drivingSearch(new DrivingRoutePlanOption().currentCity(cityName).from(fromNode).to(toNode));
                break;
            case 1:
                pSearch.transitSearch(new TransitRoutePlanOption().city(cityName).from(fromNode).to(toNode));
                break;
            case 2:
                pSearch.bikingSearch(new BikingRoutePlanOption().from(fromNode).to(toNode));
                break;
            case 3:
                pSearch.walkingSearch(new WalkingRoutePlanOption().from(fromNode).to(toNode));
                break;
            default:
                break;
        }
    }

    private class MyOnGetRoutePlanResultListener implements OnGetRoutePlanResultListener {

        @Override
        public void onGetWalkingRouteResult(WalkingRouteResult walkingRouteResult) {
            if (walkingRouteResult == null || walkingRouteResult.error != SearchResult.ERRORNO.NO_ERROR) {
                //未找到结果
                return;
            }
            if (walkingRouteResult.error == SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR) {
                //起终点或途经点地址有岐义，通过以下接口获取建议查询信息
                //walkingRouteResult.getSuggestAddrInfo()
                return;
            }
            if (walkingRouteResult.error == SearchResult.ERRORNO.NO_ERROR) {
                //获取步行线路规划结果
                List<WalkingRouteLine> wrLines = walkingRouteResult.getRouteLines();
                if (null == wrLines) return;
                List<WalkingRouteLine.WalkingStep> wSteps = wrLines.get(0).getAllStep();
                if (wSteps == null) return;
                List<LatLng> lls = wSteps.get(0).getWayPoints();
                if (null == lls) return;
                if (BuildConfig.LOG_DEBUG)
                    Log.i(Config.TAG, "onGetWalkingRouteResult: " + JsonUtils.toJson(lls));
                //创建步行路线规划线路覆盖物
                WalkingRouteOverlay overlay = new WalkingRouteOverlay(mBaiduMap);
                //设置步行路线规划数据
                overlay.setData(wrLines.get(0));
                //将步行路线规划覆盖物添加到地图中
                overlay.addToMap();
                overlay.zoomToSpan();
            }
        }

        @Override
        public void onGetTransitRouteResult(TransitRouteResult transitRouteResult) {
            if (transitRouteResult == null || transitRouteResult.error != SearchResult.ERRORNO.NO_ERROR) {
                //未找到结果
                return;
            }
            if (transitRouteResult.error == SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR) {
                //起终点或途经点地址有岐义，通过以下接口获取建议查询信息
                //transitRouteResult.getSuggestAddrInfo()
                return;
            }
            if (transitRouteResult.error == SearchResult.ERRORNO.NO_ERROR) {
                //获取公交换乘路径规划结果
                List<TransitRouteLine> wrLines = transitRouteResult.getRouteLines();
                if (null == wrLines) return;
                List<TransitRouteLine.TransitStep> wSteps = wrLines.get(0).getAllStep();
                if (wSteps == null) return;
                List<LatLng> lls = wSteps.get(0).getWayPoints();
                if (null == lls) return;
                if (BuildConfig.LOG_DEBUG)
                    Log.i(Config.TAG, "onGetTransitRouteResult: " + JsonUtils.toJson(lls));
                //创建公交路线规划线路覆盖物
                TransitRouteOverlay overlay = new TransitRouteOverlay(mBaiduMap);
                //设置公交路线规划数据
                overlay.setData(wrLines.get(0));
                //将公交路线规划覆盖物添加到地图中
                overlay.addToMap();
                overlay.zoomToSpan();
            }
        }

        @Override
        public void onGetMassTransitRouteResult(MassTransitRouteResult massTransitRouteResult) {

        }

        @Override
        public void onGetDrivingRouteResult(DrivingRouteResult drivingRouteResult) {
            if (drivingRouteResult == null || drivingRouteResult.error != SearchResult.ERRORNO.NO_ERROR) {
                //未找到结果
                return;
            }
            if (drivingRouteResult.error == SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR) {
                //起终点或途经点地址有岐义，通过以下接口获取建议查询信息
                //drivingRouteResult.getSuggestAddrInfo()
                return;
            }
            if (drivingRouteResult.error == SearchResult.ERRORNO.NO_ERROR) {
                //获取驾车线路规划结果
                List<DrivingRouteLine> wrLines = drivingRouteResult.getRouteLines();
                if (null == wrLines) return;
                List<DrivingRouteLine.DrivingStep> wSteps = wrLines.get(0).getAllStep();
                if (null == wSteps) return;
                List<LatLng> lls = wSteps.get(0).getWayPoints();
                if (null == lls) return;
                if (BuildConfig.LOG_DEBUG)
                    Log.i(Config.TAG, "onGetDrivingRouteResult: " + JsonUtils.toJson(lls));
                //创建驾车路线规划线路覆盖物
                DrivingRouteOverlay overlay = new DrivingRouteOverlay(mBaiduMap);
                //设置驾车路线规划数据
                overlay.setData(wrLines.get(0));
                //将驾车路线规划覆盖物添加到地图中
                overlay.addToMap();
                overlay.zoomToSpan();
            }
        }

        @Override
        public void onGetIndoorRouteResult(IndoorRouteResult indoorRouteResult) {

        }

        @Override
        public void onGetBikingRouteResult(BikingRouteResult bikingRouteResult) {
            if (bikingRouteResult == null || bikingRouteResult.error != SearchResult.ERRORNO.NO_ERROR) {
                //未找到结果
                return;
            }
            if (bikingRouteResult.error == SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR) {
                //起终点或途经点地址有岐义，通过以下接口获取建议查询信息
                //bikingRouteResult.getSuggestAddrInfo()
                return;
            }
            if (bikingRouteResult.error == SearchResult.ERRORNO.NO_ERROR) {
                List<BikingRouteLine> wrLines = bikingRouteResult.getRouteLines();
                if (null == wrLines) return;
                List<BikingRouteLine.BikingStep> wSteps = wrLines.get(0).getAllStep();
                if (wSteps == null) return;
                List<LatLng> lls = wSteps.get(0).getWayPoints();
                if (null == lls) return;
                if (BuildConfig.LOG_DEBUG)
                    Log.i(Config.TAG, "onGetBikingRouteResult: " + JsonUtils.toJson(lls));
                //创建骑行路线规划线路覆盖物
                BikingRouteOverlay overlay = new BikingRouteOverlay(mBaiduMap);
                //设置骑行路线规划数据
                overlay.setData(wrLines.get(0));
                //将骑行路线规划覆盖物添加到地图中
                overlay.addToMap();
                overlay.zoomToSpan();
            }
        }
    }

    @Override
    protected void onResume() {
        if (mBmView != null)
            mBmView.onResume();
        super.onResume();
    }

    @Override
    protected void onPause() {
        if (mBmView != null)
            mBmView.onPause();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        if (mBmView != null)
            mBmView.onDestroy();
        if (pSearch != null)
            pSearch.destroy();
        super.onDestroy();
    }
}
