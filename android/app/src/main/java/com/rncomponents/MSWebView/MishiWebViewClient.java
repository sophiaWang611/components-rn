package com.rncomponents.MSWebView;

import android.graphics.Bitmap;
import android.webkit.WebView;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.common.SystemClock;
import com.facebook.react.uimanager.UIManagerModule;
import com.facebook.react.uimanager.events.Event;
import com.facebook.react.uimanager.events.EventDispatcher;
import com.facebook.react.uimanager.events.RCTEventEmitter;
import com.mishi.js.WVJBWebViewClient;
import com.rncomponents.MSWebView.events.TopLoadingErrorEvent;
import com.rncomponents.MSWebView.events.TopLoadingFinishEvent;
import com.rncomponents.MSWebView.events.TopLoadingStartEvent;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MishiWebViewClient extends WVJBWebViewClient {

    private static final String LUNCH_DETAIL_PREFIX_REGEX = WVJBWebViewClient.getCustomSchemePrefix2() + "orderDetail\\?";
    private static final String LUNCH_EVA_PREFIX_REGEX = WVJBWebViewClient.getCustomSchemePrefix2() + "eva\\?";
    private static final String SHOP_SETTINGS = "lunch/shopSettings";
    private static final String DISH_SETTINGS = "lunch/dishSettings";

    private boolean mLastLoadFailed = false;

    public MishiWebViewClient(WebView webView, String toInjectJSFileName) {
        super(webView, toInjectJSFileName);
        registerJSHandlers();
    }

    @Override
    public void onPageFinished(WebView webView, String url) {
        super.onPageFinished(webView, url);

        if (!mLastLoadFailed) {
            ReactWebView reactWebView = (ReactWebView) webView;
            reactWebView.callInjectedJavaScript();
            emitFinishEvent(webView, url);
        }
    }

    @Override
    public void onPageStarted(WebView webView, String url, Bitmap favicon) {
        super.onPageStarted(webView, url, favicon);
        mLastLoadFailed = false;

        dispatchEvent(
                webView,
                new TopLoadingStartEvent(
                        webView.getId(),
                        SystemClock.nanoTime(),
                        createWebViewEvent(webView, url)));
    }

    @Override
    public void onReceivedError(
            WebView webView,
            int errorCode,
            String description,
            String failingUrl) {
        super.onReceivedError(webView, errorCode, description, failingUrl);
        mLastLoadFailed = true;

        // In case of an error JS side expect to get a finish event first, and then get an error event
        // Android WebView does it in the opposite way, so we need to simulate that behavior
        emitFinishEvent(webView, failingUrl);

        WritableMap eventData = createWebViewEvent(webView, failingUrl);
        eventData.putDouble("code", errorCode);
        eventData.putString("description", description);

        dispatchEvent(
                webView,
                new TopLoadingErrorEvent(webView.getId(), SystemClock.nanoTime(), eventData));
    }

    @Override
    public void doUpdateVisitedHistory(WebView webView, String url, boolean isReload) {
        super.doUpdateVisitedHistory(webView, url, isReload);

        dispatchEvent(
                webView,
                new TopLoadingStartEvent(
                        webView.getId(),
                        SystemClock.nanoTime(),
                        createWebViewEvent(webView, url)));
    }

    private void emitFinishEvent(WebView webView, String url) {
        dispatchEvent(
                webView,
                new TopLoadingFinishEvent(
                        webView.getId(),
                        SystemClock.nanoTime(),
                        createWebViewEvent(webView, url)));
    }

    private static void dispatchEvent(WebView webView, Event event) {
        ReactContext reactContext = (ReactContext) webView.getContext();
        EventDispatcher eventDispatcher =
                reactContext.getNativeModule(UIManagerModule.class).getEventDispatcher();
        eventDispatcher.dispatchEvent(event);
    }

    private WritableMap createWebViewEvent(WebView webView, String url) {
        WritableMap event = Arguments.createMap();
        event.putDouble("target", webView.getId());
        // Don't use webView.getUrl() here, the URL isn't updated to the new value yet in callbacks
        // like onPageFinished
        event.putString("url", url);
        event.putString("title", webView.getTitle());
        event.putBoolean("canGoBack", webView.canGoBack());
        event.putBoolean("canGoForward", webView.canGoForward());
        return event;
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView webView, String url) {
        if (resolveMishiScheme(url)) {
            dispatchEvent(
                    webView,
                    new TopShouldStartLoadWithRequest(
                            webView.getId(),
                            SystemClock.nanoTime(),
                            createWebViewEvent(webView, url)));
            return true;
        }

        return super.shouldOverrideUrlLoading(webView, url);
    }

    private void registerJSHandlers() {
        registerHandler("nativeRefreshControl", new WVJBWebViewClient.WVJBHandler() {
            @Override
            public void request(Object data, WVJBWebViewClient.WVJBResponseCallback callback) {
                if (data instanceof JSONObject) {
                    JSONObject json = (JSONObject) data;
                    try {
                        boolean enable = json.getBoolean("enableRefresh");
                        WritableMap event = Arguments.createMap();
                        event.putBoolean("enablePullRefresh", enable);
                        ((ReactContext) webView.getContext()).getJSModule(RCTEventEmitter.class)
                                .receiveEvent(webView.getId(), "onChangePullRefresh", event);
                    } catch (JSONException e) {

                    }
                }
            }
        });
    }

    private boolean resolveMishiScheme(String url) {
        if (resolveLunchDetailRegex(url) || resolveLunchEvaRegex(url)) {
            return true;
        }
        if (url.startsWith("http") || url.startsWith("https")) {
            if (url.contains(SHOP_SETTINGS)) {
                return true;
            } else if (url.contains(DISH_SETTINGS)) {
                return true;
            }
        }
        return false;
    }

    private boolean resolveLunchDetailRegex(String text) {
        Pattern p = Pattern.compile(LUNCH_DETAIL_PREFIX_REGEX);
        Matcher m = p.matcher(text);
        return m.find();
    }

    private boolean resolveLunchEvaRegex(String text) {
        Pattern p = Pattern.compile(LUNCH_EVA_PREFIX_REGEX);
        Matcher m = p.matcher(text);
        return m.find();
    }

}
