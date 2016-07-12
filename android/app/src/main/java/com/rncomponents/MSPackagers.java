package com.rncomponents;

import com.facebook.react.ReactPackage;
import com.facebook.react.bridge.JavaScriptModule;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.uimanager.ViewManager;
import com.rncomponents.MSHtmlTxt.MSHtmlTxtManager;
import com.rncomponents.MSMap.MSMapManager;
import com.rncomponents.MSMaskInput.MSMaskInputMgr;
import com.rncomponents.MSOverlay.MSOverlayManager;
import com.rncomponents.MSPhoto.MSPhotoPickerManager;
import com.rncomponents.MSPicker.MSPickerManager;
import com.rncomponents.MSSeekBar.MSSeekBarManager;
import com.rncomponents.MSSwitch.MSSwitchManager;
import com.rncomponents.MSWebView.MSWebViewManager;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by sophia on 16/7/11.
 */
public class MSPackagers implements ReactPackage {
    @Override
    public List<NativeModule> createNativeModules(ReactApplicationContext reactApplicationContext) {
        return  Arrays.asList(new NativeModule[]{
                new MSPickerManager(reactApplicationContext),
                new MSPhotoPickerManager(reactApplicationContext),
                new MSMapManager(reactApplicationContext)
        });
    }

        @Override
    public List<Class<? extends JavaScriptModule>> createJSModules() {
        return Collections.emptyList();
    }

    @Override
    public List<ViewManager> createViewManagers(ReactApplicationContext reactApplicationContext) {
        return  Arrays.asList(new ViewManager[]{
                new MSWebViewManager(),
                new MSSwitchManager(),
                new MSSeekBarManager(),
                new MSOverlayManager(),
                new MSMaskInputMgr(),
                new MSHtmlTxtManager()
        });
    }
}
