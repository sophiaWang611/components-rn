package com.rncomponents.MSSwitch;

import android.os.SystemClock;
import android.widget.CompoundButton;

import com.facebook.react.bridge.ReactContext;
import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.UIManagerModule;
import com.facebook.react.uimanager.annotations.ReactProp;

/**
 * Created by sophia on 16/3/15.
 */
public class MSSwitchManager extends SimpleViewManager<MSSwitchView> {
    private static final CompoundButton.OnCheckedChangeListener ON_CHECKED_CHANGE_LISTENER = new CompoundButton.OnCheckedChangeListener() {
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            ReactContext reactContext = (ReactContext)buttonView.getContext();
            ((UIManagerModule)reactContext.getNativeModule(UIManagerModule.class)).getEventDispatcher().dispatchEvent(new MSSwitchEvent(buttonView.getId(), SystemClock.uptimeMillis(), isChecked));
        }
    };

    @Override
    public String getName() {
        return "MSSwitchView";
    }

    @ReactProp(name = "enable")
    public void setEnable(MSSwitchView view, @javax.annotation.Nullable boolean enable) {
        view.setEnabled(enable);
    }

    @ReactProp(name = "value")
    public void setValue(MSSwitchView view, @javax.annotation.Nullable boolean value) {
        view.setChecked(value);
        view.setOnCheckedChangeListener(ON_CHECKED_CHANGE_LISTENER);
    }

    @Override
    protected MSSwitchView createViewInstance(ThemedReactContext themedReactContext) {
        return new MSSwitchView(themedReactContext);
    }

    protected void addEventEmitters(ThemedReactContext reactContext, MSSwitchView view) {
        view.setOnCheckedChangeListener(ON_CHECKED_CHANGE_LISTENER);
    }
}
