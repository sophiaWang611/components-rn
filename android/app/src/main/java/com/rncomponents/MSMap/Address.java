/*
 * @Author: Mars Tsang
 * @Mail: zmars@me.com
 */

package com.rncomponents.MSMap;

import android.text.TextUtils;

import com.amap.api.services.core.PoiItem;

/**
 * Created by zzmars on 14-10-21.
 */
public class Address {

//    public String city;
//    public String cityCode;
//    public String district;
//    public String districtCode;
//    public String poiName;

    public Long relative;// 关联id（买家或者卖家）,
    public Integer type;//  地址类型（0 买家的收货地址 1 卖家的家厨地址 2 卖家的自提地址）,
    public Boolean isDefault;//  是否是默认地址,

    public String province;
    public String provinceCode;
    public String city;
    public String cityCode;
    public String district;
    public String districtCode;
    public String poiName;
    public String otherPoiInfo;
    public String addressDesc;
    public String street;
    public Long addressId;
    public double latitude;
    public double lng;
    public double longitude;
    public String keyWords;// 买家收货地址关键词（不超过30个字，v1.7以上版本必传）
    public String addressDisplay;
    public String poiDisplay;//  poi定位信息  高德中对应 poiName

    public Address() {

    }


    public Address(PoiItem item, String addrComment) {
        this.poiName = item.getTitle();
        this.province = item.getProvinceName();
        this.provinceCode = item.getProvinceCode();
        this.city = item.getCityName();
        this.cityCode = item.getCityCode();
        this.district = item.getAdName();
        this.districtCode = item.getAdCode();
//        this.addressDesc = item.getSnippet();
//        this.otherPoiInfo = addrComment;
        this.addressDesc = addrComment;
        this.otherPoiInfo = item.getSnippet();
        this.latitude = item.getLatLonPoint().getLatitude();
        this.longitude = item.getLatLonPoint().getLongitude();
        this.lng = item.getLatLonPoint().getLongitude();
    }

    public Address(String province, String city, String district,
                   String poiName, double lat, double lng) {
        this.province = province;
        this.city = city;
        this.district = district;
        this.poiName = poiName;
        this.latitude = lat;
        this.lng = lng;
        this.longitude = lng;
    }


    public void setLatitude(double lat) {
        this.latitude = lat;
    }

    public double getLatitude() {
        return this.latitude;
    }

    public void setLongitude(double lon) {
        this.longitude = lon;
        this.lng = lon;
    }

    public double getLongitude() {
        if (longitude == 0)
            return this.lng;
        return this.longitude;
    }

    public String getDetails() {
        if (getProvince().equals(getCity()))
            return getCity() + getDistrict() + getOtherPoiInfo() + getPoiName() + getAddressDesc();
        else
        return getProvince() + getCity() + getDistrict() + getOtherPoiInfo() + getPoiName() + getAddressDesc();
    }

    @Override
    public String toString() {
        return getPoiName() + " " + getAddressDesc();
    }

    public String getCityDistrictOtherPoiInfo() {
        return getCity() + " " + getDistrict() + " " + getOtherPoiInfo();
    }

    public String getProviceCityDistrict() {
        if (getProvince().equals(getCity()))
            return getCity() + " " + getDistrict();
        else
            return getProvince() + getCity() + " " + getDistrict();
    }

    public String getTotalAddress() {
        if (getProvince().equals(getCity()))
            return getCity() + getDistrict() + " " + getOtherPoiInfo() + " " + getPoiName() + getAddressDesc();
        else
            return getProvince() + getCity() + getDistrict() + " " + getOtherPoiInfo() + " " + getPoiName() + getAddressDesc();
    }

    public String getCityDistrictOtherPoiInfoPoiNameInfo() {
        return getCity() + getDistrict() + getOtherPoiInfo() + getPoiName();
    }

    public String getDistrictOtherPoiInfoPoiNameInfo() {
        return getDistrict() + getOtherPoiInfo() + getPoiName();
    }

    public String getProvince() {
        if (TextUtils.isEmpty(this.province)) {
            return "";
        } else {
            return this.province;
        }
    }

    public String getCity() {
        if (TextUtils.isEmpty(this.city)) {
            return "";
        } else {
            return this.city;
        }
    }

    public String getStreet() {
        if (TextUtils.isEmpty(this.street)) {
            return "";
        } else {
            return this.street;
        }
    }

    public String getDistrict() {
        if (TextUtils.isEmpty(this.district)) {
            return "";
        } else {
            return this.district;
        }
    }

    public String getOtherPoiInfo() {
        //otherPoiInfo一般是路名加门牌号，定位的时候没有这个数据
        //所以当没有这个用路名替换
        if (TextUtils.isEmpty(this.otherPoiInfo)) {
            return getStreet();
        } else {
            return this.otherPoiInfo;
        }
    }

    public String getPoiName() {
        if (TextUtils.isEmpty(this.poiName)) {
            return "";
        } else {
            return this.poiName;
        }
    }

    public String getAddressDesc() {
        if (TextUtils.isEmpty(this.addressDesc)) {
            return "";
        } else {
            return this.addressDesc;
        }
    }

    public String getSelectedId() {
        if (TextUtils.isEmpty(this.cityCode)) {
            return this.provinceCode;
        } else {
            return this.cityCode;
        }
    }

    public String getSelectedCity() {
        if (TextUtils.isEmpty(this.city)) {
            return this.province;
        } else {
            return this.city;
        }
    }


    public String getProvinceCityValue(){
        String str="";
        if(TextUtils.isEmpty(getProvince()))
            str=str+"";
        else
            str=str+getProvince();
        if(TextUtils.isEmpty(getCity()))
            str=str+"";
        else{
            if(!getProvince().equals(getCity()))
            str=str+" "+getCity();
        }

        return str;
    }

    public String getKeyWords() {
        if (TextUtils.isEmpty(this.keyWords)) {
            return getPoiName() + getAddressDesc();
        } else {
            return this.keyWords;
        }
    }

    // 买家地址详情  v1.7 开始 添加、修改买家收货地址 使用
    public String getBuyerAddressDetails() {
        if (getProvince().equals(getCity()))
            return getCity() + getDistrict() + getKeyWords();
        else
            return getProvince() + getCity() + getDistrict() + getKeyWords();
    }

    public String getPoiDisplay() {
        if (TextUtils.isEmpty(this.poiDisplay)) {
            return "";
        } else {
            return this.poiDisplay;
        }
    }

    public String getAddressDisplay() {
        if (TextUtils.isEmpty(this.addressDisplay)) {
            return "";
        } else {
            return this.addressDisplay;
        }
    }

    public void setAddressDesc(String addressDesc) {
        this.addressDesc = addressDesc;
    }
}
