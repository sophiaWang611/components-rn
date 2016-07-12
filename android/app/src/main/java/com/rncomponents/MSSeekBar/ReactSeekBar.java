package com.rncomponents.MSSeekBar;

import android.content.Context;
import android.widget.SeekBar;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.uimanager.events.RCTEventEmitter;

public class ReactSeekBar extends SeekBar implements SeekBar.OnSeekBarChangeListener {

    private float mMinValue = 0;
    private float mMaxValue = 1;
    private float officialMinVal = 0;
    private final double COMPARE = 3e-8;

    public ReactSeekBar(Context context) {
        super(context);
        setOnSeekBarChangeListener(this);
    }

    public float getOfficialMinVal() {
        return officialMinVal;
    }

    public void setOfficialMinVal(float officialMinVal) {
        this.officialMinVal = officialMinVal;
    }

    public float getmMinValue() {
        return mMinValue;
    }

    public void setmMinValue(float mMinValue) {
        this.mMinValue = mMinValue;
    }

    public float getmMaxValue() {
        return mMaxValue;
    }

    public void setmMaxValue(float mMaxValue) {
        this.mMaxValue = mMaxValue;
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        WritableMap event = Arguments.createMap();
        double proVal = convertProgress(progress);
        event.putDouble("progress", proVal);

        if (proVal - officialMinVal > -COMPARE && officialMinVal - proVal < COMPARE) {
            ((ReactContext) getContext()).getJSModule(RCTEventEmitter.class).receiveEvent(getId(), "topChange", event);
            return;
        }

        float percentage = (officialMinVal - mMinValue) / (mMaxValue - mMinValue);
        seekBar.setProgress((int) (percentage * seekBar.getMax()));
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        ((ReactContext) getContext()).getJSModule(RCTEventEmitter.class).receiveEvent(
                getId(), "topSlidingComplete", null);
    }

    private double convertProgress(int progress) {
        return (1.0 * progress / getMax() * (mMaxValue - mMinValue) + mMinValue);
    }
}