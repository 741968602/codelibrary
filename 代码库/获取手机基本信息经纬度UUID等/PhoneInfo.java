package com.miaotech.health.utils;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.LocationManagerProxy;
import com.amap.api.location.LocationProviderProxy;
import com.miaotech.health.model.PublicVo;

import java.util.UUID;

/**
 * Created by Administrator on 2015/10/23.
 * 获得手机基本信息，获得经纬度的方法放在回调函数中，自动定位成功便set到内存
 */
public class PhoneInfo implements AMapLocationListener{
    LocationManagerProxy mLocationManagerProxy;// 初始化定位
    String os_type = "Android";//设备类型
    String os_version = android.os.Build.VERSION.RELEASE;//操作系统版本
    String device_type = android.os.Build.MODEL;//手机型号
    String channel="baidustore";
    private  String device_id(Context mContext) {//device_id
        String device_id = ((TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE))
                .getDeviceId();
        return device_id;
    }
    /**
     * 将手机基本信息放到内存
     */
    public void savePhoneInfo(Context mContext){
        PublicVo.setOs_type(os_type);
        PublicVo.setOs_version(os_version);
        PublicVo.setDevice_type(device_type);
        PublicVo.setDevice_id(getMyUUID(mContext));
        PublicVo.setApp_version(getVersionName(mContext));
        PublicVo.setChannel(channel);
        location(mContext);
    }

    // 获取版本号
    private String getVersionName(Context mContext) {

        PackageManager pm = mContext.getPackageManager();
        try {
            PackageInfo info = pm.getPackageInfo(mContext.getPackageName(), 0);
            return info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "";
        }
    }
    private String getMyUUID(Context mContext){

        final TelephonyManager tm = (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);
        final String tmDevice, tmSerial, tmPhone, androidId;
        tmDevice = "" + tm.getDeviceId();
        tmSerial = "" + tm.getSimSerialNumber();
        androidId = "" + android.provider.Settings.Secure.getString(mContext.getContentResolver(),android.provider.Settings.Secure.ANDROID_ID);
        UUID deviceUuid = new UUID(androidId.hashCode(), ((long)tmDevice.hashCode() << 32) | tmSerial.hashCode());
        String uniqueId = deviceUuid.toString();
        Log.d("debug", "uuid=" + uniqueId);
        return uniqueId;
    }


    /**
     * 定位
     *
     * @param mContext
     */

    public void location(Context mContext) {
        mLocationManagerProxy = LocationManagerProxy.getInstance(mContext);
        mLocationManagerProxy.requestLocationData(LocationProviderProxy.AMapNetwork, -1, 15,
                this);
    }

    @Override
    public void onLocationChanged(Location location) {
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    @Override
    public void onProviderEnabled(String provider) {
    }

    @Override
    public void onProviderDisabled(String provider) {
    }

    @Override
    public void onLocationChanged(AMapLocation arg0) {
        // 定位回调
        if (arg0 != null && arg0.getAMapException().getErrorCode() == 0) {

            Log.i("la+++++++++++++", arg0.getLatitude() + "");
            Log.i("l0+++++++++++++", arg0.getLongitude() + "");

            if(arg0.getLatitude()==0.0||arg0.getLatitude()==0||arg0.getLatitude()==0.00){
                PublicVo.setLatitude(null);
                PublicVo.setLongitude(null);
            }
            PublicVo.setLatitude(arg0.getLatitude());
            PublicVo.setLongitude(arg0.getLongitude());

        }
    }

}
