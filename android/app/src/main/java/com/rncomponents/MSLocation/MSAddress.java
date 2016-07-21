package com.rncomponents.MSLocation;

import com.amap.api.location.AMapLocation;
import com.amap.api.services.geocoder.RegeocodeAddress;
import com.mishi.common.util.StringUtils;

/**
 * Created by sophia on 16/3/4.
 */
public class MSAddress {
    public String name;
    public String address;
    public String province;
    public String provinceCode;
    public String city;
    public String cityCode;
    public String district;
    public String districtCode;
    public double lat;
    public double lon;

    public MSAddress() {
    }

    public MSAddress(AMapLocation location) {
        this.setName(location.getPoiName());
        this.setAddress(location.getAddress());
        this.setProvince(location.getProvince());
        this.setCity(location.getCity());
        this.setCityCode(location.getCityCode());
        this.setDistrict(location.getDistrict());
        this.setLat(location.getLatitude());
        this.setLon(location.getLongitude());
    }

    public MSAddress(RegeocodeAddress address) {
        this.setCity(address.getCity());
        this.setCityCode(address.getCityCode());
        this.setDistrict(address.getDistrict());
        this.setDistrictCode(address.getAdCode());
        this.setProvince(address.getProvince());
    }

    public void setValues(RegeocodeAddress address) {
        if (!StringUtils.isEmpty(address.getCity())) this.setCity(address.getCity());
        if (!StringUtils.isEmpty(address.getCityCode())) this.setCityCode(address.getCityCode());
        if (!StringUtils.isEmpty(address.getDistrict())) this.setDistrict(address.getDistrict());
        if (!StringUtils.isEmpty(address.getAdCode())) this.setDistrictCode(address.getAdCode());
        if (!StringUtils.isEmpty(address.getProvince())) this.setProvince(address.getProvince());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getDistrictCode() {
        return districtCode;
    }

    public void setDistrictCode(String districtCode) {
        this.districtCode = districtCode;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }
}
