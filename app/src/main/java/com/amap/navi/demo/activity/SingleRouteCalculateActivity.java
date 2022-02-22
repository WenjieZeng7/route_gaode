package com.amap.navi.demo.activity;

import android.os.Bundle;

import com.amap.api.maps.model.LatLngBounds;
import com.amap.api.navi.AMapNaviView;
import com.amap.api.navi.AMapNaviViewOptions;
import com.amap.api.navi.enums.NaviType;
import com.amap.api.navi.model.AMapCalcRouteResult;
import com.amap.api.navi.model.AMapNaviPath;
import com.amap.api.navi.model.NaviLatLng;
import com.amap.navi.demo.R;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

public class SingleRouteCalculateActivity extends BaseActivity {
    protected AMapNaviPath path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_basic_navi);
        mAMapNaviView = (AMapNaviView) findViewById(R.id.navi_view);
        mAMapNaviView.onCreate(savedInstanceState);
        mAMapNaviView.setAMapNaviViewListener(this);

        AMapNaviViewOptions options = new AMapNaviViewOptions();
        options.setTilt(0);
        mAMapNaviView.setViewOptions(options);
    }

    @Override
    public void onInitNaviSuccess() {
        super.onInitNaviSuccess();
        /**
         * 方法: int strategy=mAMapNavi.strategyConvert(congestion, avoidhightspeed, cost, hightspeed, multipleroute); 参数:
         *
         * @congestion 躲避拥堵
         * @avoidhightspeed 不走高速
         * @cost 避免收费
         * @hightspeed 高速优先
         * @multipleroute 多路径
         *
         *  说明: 以上参数都是boolean类型，其中multipleroute参数表示是否多条路线，如果为true则此策略会算出多条路线。
         *  注意: 不走高速与高速优先不能同时为true 高速优先与避免收费不能同时为true
         */
        int strategy = 0;
        try {
            //再次强调，最后一个参数为true时代表多路径，否则代表单路径
            strategy = mAMapNavi.strategyConvert(true, false, false, false, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        mAMapNavi.calculateDriveRoute(sList, eList, mWayPointList, strategy);

    }

    @Override
    public void onCalculateRouteSuccess(AMapCalcRouteResult aMapCalcRouteResult) {
        super.onCalculateRouteSuccess(aMapCalcRouteResult);

        /**
         * *********************返回当前导航路线的所有坐标点
         */
        path = mAMapNavi.getNaviPath();
        List<NaviLatLng> coordList = path.getCoordList();

//        File f = new File("C:\\Users\\420\\Desktop\\地图相关\\高德.txt");
//        try {
//            BufferedWriter bw = new BufferedWriter(new FileWriter(f));
//            Iterator<NaviLatLng> it = coordList.iterator();
//            while(it.hasNext()){
//                String str = it.next().toString();
//                bw.write(str);
//                bw.flush();
//            }
//            bw.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

//        Iterator<NaviLatLng> it = coordList.iterator();
//        while (it.hasNext()){
//            System.out.println(it.next().toString());
//        }

        //测试通过
//        for (int i = 0; i < coordList.size()-100; i++) {
//            System.out.println(coordList.get(i+100).getLatitude());
//            System.out.println(coordList.get(i+100).getLongitude());
//        }
        /*以上********************返回当前导航路线的所有坐标点*/


        /**
         * *********用于获取该导航路线最小坐标点和最大坐标点围成的矩形区域。
         * 返回矩形左下和右上点的坐标.
         */
        LatLngBounds boundsForPath = path.getBoundsForPath();
        System.out.println(boundsForPath.toString());
        /*以上**************************用于获取该导航路线最小坐标点和最大坐标点围成的矩形区域。*/


        mAMapNavi.startNavi(NaviType.GPS); //模拟导航NaviType.EMULATOR
    }
}
