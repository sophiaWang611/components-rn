package com.rncomponents.MSWebView;

import com.facebook.react.bridge.WritableMap;
import com.facebook.react.uimanager.events.Event;
import com.facebook.react.uimanager.events.RCTEventEmitter;

/**
 * Created by sophia on 16/4/1.
 */
class TopShouldStartLoadWithRequest extends Event<TopShouldStartLoadWithRequest> {
    public static final String EVENT_NAME = "topChange";
    private WritableMap mEventData;

    public TopShouldStartLoadWithRequest(int viewId, long timestampMs, WritableMap eventData) {
        super(viewId, timestampMs);
        this.mEventData = eventData;
    }

    public String getEventName() {
        return EVENT_NAME;
    }

    public boolean canCoalesce() {
        return false;
    }

    public short getCoalescingKey() {
        return (short)0;
    }

    public void dispatch(RCTEventEmitter rctEventEmitter) {
        rctEventEmitter.receiveEvent(this.getViewTag(), this.getEventName(), this.mEventData);
    }
}
