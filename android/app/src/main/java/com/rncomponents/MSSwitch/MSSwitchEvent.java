package com.rncomponents.MSSwitch;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.uimanager.events.Event;
import com.facebook.react.uimanager.events.RCTEventEmitter;

/**
 * Created by sophia on 16/3/16.
 */
public class MSSwitchEvent extends Event<MSSwitchEvent> {
    public static final String EVENT_NAME = "topChange";
    private final boolean mIsChecked;

    public MSSwitchEvent(int viewId, long timestampMs, boolean isChecked) {
        super(viewId, timestampMs);
        this.mIsChecked = isChecked;
    }

    public boolean getIsChecked() {
        return this.mIsChecked;
    }

    public String getEventName() {
        return EVENT_NAME;
    }

    public short getCoalescingKey() {
        return (short)0;
    }

    public void dispatch(RCTEventEmitter rctEventEmitter) {
        rctEventEmitter.receiveEvent(this.getViewTag(), this.getEventName(), this.serializeEventData());
    }

    private WritableMap serializeEventData() {
        WritableMap eventData = Arguments.createMap();
        eventData.putInt("target", this.getViewTag());
        eventData.putBoolean("value", this.getIsChecked());
        return eventData;
    }
}

