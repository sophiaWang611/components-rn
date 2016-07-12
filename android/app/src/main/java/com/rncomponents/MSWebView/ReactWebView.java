package com.rncomponents.MSWebView;

import android.text.TextUtils;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.facebook.react.bridge.LifecycleEventListener;
import com.facebook.react.uimanager.ThemedReactContext;

import javax.annotation.Nullable;

/**
 * Subclass of {@link WebView} that implements {@link LifecycleEventListener} interface in order
 * to call {@link WebView#destroy} on activty destroy event and also to clear the client
 */
public class ReactWebView extends WebView implements LifecycleEventListener {
    private @Nullable
    String injectedJS;

    /**
     * WebView must be created with an context of the current activity
     *
     * Activity Context is required for creation of dialogs internally by WebView
     * Reactive Native needed for access to ReactNative internal system functionality
     *
     */
    public ReactWebView(ThemedReactContext reactContext) {
        super(reactContext);
    }

    @Override
    public void onHostResume() {
        // do nothing
    }

    @Override
    public void onHostPause() {
        // do nothing
    }

    @Override
    public void onHostDestroy() {
        cleanupCallbacksAndDestroy();
    }

    @Override
    public void setWebViewClient(WebViewClient client) {
        super.setWebViewClient(client);
    }

    public void setInjectedJavaScript(@Nullable String js) {
        injectedJS = js;
    }

    public void callInjectedJavaScript() {
        if (getSettings().getJavaScriptEnabled() &&
                injectedJS != null &&
                !TextUtils.isEmpty(injectedJS)) {
            loadUrl("javascript:(function() {\n" + injectedJS + ";\n})();");
        }
    }

    public void cleanupCallbacksAndDestroy() {
        setWebViewClient(null);
        destroy();
    }
}