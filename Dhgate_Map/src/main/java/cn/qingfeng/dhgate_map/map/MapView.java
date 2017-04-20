package cn.qingfeng.dhgate_map.map;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;

import cn.qingfeng.dhgate_map.R;

/**
 * @AUTHER: 李青峰
 * @EMAIL: 1021690791@qq.com
 * @PHONE: 18045142956
 * @DATE: 2017/4/18 22:33
 * @DESC: 自定义Map
 * @VERSION: V1.0
 */
public class MapView extends FrameLayout {
    private Activity mActivity;

    //百度地图核心类
    private com.baidu.mapapi.map.MapView mBaiduMapView;
    private BaiduMap mBaiduMap;
    private LocationClient mLocationClient;

    public MapView(@NonNull Context context) {
        this(context, null);
    }

    public MapView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MapView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mActivity = (Activity) context;
        View view = View.inflate(context, R.layout.layout_my_map, this);
        mBaiduMapView = (com.baidu.mapapi.map.MapView) view.findViewById(R.id.baidu_map);
        mBaiduMapView.showZoomControls(false);

        mBaiduMap = mBaiduMapView.getMap();


    }

    private void initLocation() {
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);// 可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("bd09ll");// 可选，默认gcj02，设置返回的定位结果坐标系
//int span = 1000;
        option.setScanSpan(0);// 可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(true);// 可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);// 可选，默认false,设置是否使用gps
        option.setLocationNotify(true);// 可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
        option.setIsNeedLocationDescribe(true);// 可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(true);// 可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIgnoreKillProcess(false);// 可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        option.SetIgnoreCacheException(false);// 可选，默认false，设置是否收集CRASH信息，默认收集
        option.setEnableSimulateGps(false);// 可选，默认false，设置是否需要过滤gps仿真结果，默认需要
        mLocationClient.setLocOption(option);
    }

    public static void initialize(Context context) {
        SDKInitializer.initialize(context.getApplicationContext());
    }

    public void showCurLocation() {
        // 声明LocationClient类
        mLocationClient = new LocationClient(getContext());
        mLocationClient.registerLocationListener(new MyLocationListener()); // 注册监听函数
        initLocation();
        mLocationClient.start();
    }

    public class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {

            //定义Maker坐标点
            LatLng point = new LatLng(location.getLatitude(), location.getLongitude());
            //构建Marker图标  ，这里可以自己替换
            BitmapDescriptor bitmap = BitmapDescriptorFactory
                    .fromResource(R.mipmap.ic_launcher);
            //构建MarkerOption，用于在地图上添加Marker
            OverlayOptions option = new MarkerOptions()
                    .position(point)
                    .icon(bitmap)
                    .zIndex(12)
                    .draggable(true);
            //在地图上添加Marker，并显示
            mBaiduMap.addOverlay(option);

            MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(point);//使输入的点位于地图中心
            mBaiduMap.setMapStatus(u);
            mBaiduMap.animateMapStatus(MapStatusUpdateFactory.zoomTo(16));
        }

        @Override
        public void onConnectHotSpotMessage(String s, int i) {

        }
    }


}
