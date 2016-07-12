package com.rncomponents.MSSeekBar;

import android.support.annotation.Nullable;

import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.annotations.ReactProp;

public class MSSeekBarManager extends SimpleViewManager<ReactSeekBar> {

    @Override
    public String getName() {
        return "RCTSeekBar";
    }

    @Override
    protected ReactSeekBar createViewInstance(ThemedReactContext reactContext) {
        return new ReactSeekBar(reactContext);
    }

    @ReactProp(name = "value")
    public void setProgress(ReactSeekBar view, @Nullable float progress) {
        float minValue = view.getmMinValue();
        float maxValue = view.getmMaxValue();
        float officialMin = view.getOfficialMinVal();
        if (progress < officialMin) {
            progress = officialMin;
        }
        float percentage = (progress - minValue) / (maxValue - minValue);
        view.setProgress((int) (percentage * view.getMax()));
    }

    @ReactProp(name = "minimumValue")
    public void setMinimumValue(ReactSeekBar view, @Nullable float minValue) {
        view.setmMinValue(minValue);
    }

    @ReactProp(name = "maximumValue")
    public void setMaxValue(ReactSeekBar view, @Nullable float maxValue) {
        view.setmMaxValue(maxValue);
    }

    @ReactProp(name = "minValue")
    public void setMinValue(ReactSeekBar view, @Nullable float minValue) {
        view.setOfficialMinVal(minValue);
    }
}