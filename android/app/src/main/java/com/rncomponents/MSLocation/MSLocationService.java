package com.rncomponents.MSLocation;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.LocationManagerProxy;
import com.amap.api.location.LocationProviderProxy;

import com.mishi.common.util.log.MsSdkLog;
import com.mishi.xstate.XState;
import com.mishi.xstate.util.XStateConstants;
import com.rncomponents.Constants;

/**
 * Created by dipingye on 15-1-4.
 */
public class MSLocationService implements AMapLocationListener {


    public interface LocalManagerListener {
        public void onLocationChanged(MSAddress address);
    }

    private static final String TAG = "MSLocationService";

    private static final int LOCAL_TIME_INTERVAL = 5000;
    private static final int LOCATE_TIME_PERIOD = 2000;
    private static final int LOCATE_ACC = 10;
    private boolean isInit = false;
    private Context mContext = null;
    private LocalManagerListener mUIListener = null;
    private LocationManagerProxy aMapLocManager = null;
    private MSAddress mAddress = null;//地址
    private Long mLastLacationTime = 0L;//每次修改Address都没更新

    private static MSLocationService sInstance = null;

    private MSLocationService() {
    }

    public static MSLocationService getInstance(Context context) {
        if (null == sInstance) {
            sInstance = new MSLocationService();
            sInstance.mContext = context;
        }
        if (null == sInstance.mContext)
            sInstance.mContext = context;
        return sInstance;
    }

    public void init() {
        if (false == isInit) {
            MsSdkLog.d(TAG, "======================MSLocationService init mContext = " + mContext);
            aMapLocManager = LocationManagerProxy.getInstance(mContext.getApplicationContext());
            isInit = true;
        }
    }

    public void release() {
        if (isInit) {
            MsSdkLog.d(TAG, "======================MSLocationService release ");
            stopLocation();
            mUIListener = null;
            mAddress = null;
            isInit = false;
        }
    }

    public void register(LocalManagerListener listener) {
        mUIListener = listener;
    }

    public void unregister() {
        mUIListener = null;
    }

    public void getCurrentLocal(LocalManagerListener listener) {
        if (null != listener)
            register(listener);

        //加入设置地址1小时超时逻辑
        if (null != mAddress && System.currentTimeMillis() - mLastLacationTime <= Constants.DEFAULT_SET_ADDRESS_OUT_TIME) {
            if (null != mUIListener)
                mUIListener.onLocationChanged(mAddress);

            return;
        }

        if (null == aMapLocManager) {
            aMapLocManager = LocationManagerProxy.getInstance(mContext);
        }

        aMapLocManager.requestLocationData(LocationProviderProxy.AMapNetwork, LOCATE_TIME_PERIOD, LOCATE_ACC, this);

        startLocationTimeoutCount();
    }

    public MSAddress getAddress() {
        return mAddress;
    }

    public void setmAddress(MSAddress address) {
        this.mAddress = address;
    }

    public String getCity() {
        if (null != mAddress) {
            if (TextUtils.isEmpty(mAddress.getCity())) {
                return mAddress.getProvince();
            } else {
                return mAddress.getCity();
            }
        } else
            return null;
    }

    private void startLocationTimeoutCount() {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (null != mUIListener) {
                    aMapLocManager.removeUpdates(MSLocationService.this);
                }
            }
        }, LOCAL_TIME_INTERVAL);

    }

    /**
     * 销毁定位
     */
    private void stopLocation() {
        if (aMapLocManager != null) {
            aMapLocManager.removeUpdates(this);
            aMapLocManager.destory();
        }
        aMapLocManager = null;
    }

    //AMapLocationListener
    @Override
    public void onLocationChanged(AMapLocation location) {
        if (location != null) {

            //for http request header
            XState.setValue(XStateConstants.KEY_LNG, String.valueOf(location.getLongitude()));
            XState.setValue(XStateConstants.KEY_LAT, String.valueOf(location.getLatitude()));
            mAddress = new MSAddress(location);

            if (null != mUIListener)
                mUIListener.onLocationChanged(mAddress);

            aMapLocManager.removeUpdates(MSLocationService.this);
        }
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        System.out.print(status);
    }

    @Override
    public void onProviderEnabled(String provider) {
        System.out.print(provider);
    }

    @Override
    public void onProviderDisabled(String provider) {
        System.out.print(provider);
    }

}
