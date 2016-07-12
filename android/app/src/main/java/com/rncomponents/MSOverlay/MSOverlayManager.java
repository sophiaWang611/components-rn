package com.rncomponents.MSOverlay;

import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.ViewGroupManager;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.facebook.react.views.view.ReactViewGroup;
import com.rncomponents.R;

/**
 * Created by sophia on 16/4/7.
 */
public class MSOverlayManager extends ViewGroupManager<ViewGroup> {
    private View layout;

    @Override
    public String getName() {
        return "MSOverlay";
    }

    @Override
    protected ViewGroup createViewInstance(ThemedReactContext themedReactContext) {
        this.layout = new View(themedReactContext);
        this.layout.setBackgroundColor(themedReactContext.getResources().getColor(R.color.translucent_clr));
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT);
        this.layout.setLayoutParams(params);
        this.layout.setVisibility(View.GONE);

        ViewGroup group = new ReactViewGroup(themedReactContext);
        group.addView(this.layout);
        return group;
    }

    @ReactProp(name = "isVisible")
    public void setIsVisible(ViewGroup viewGroup, boolean isVisible) {
        if (isVisible) {
            viewGroup.setVisibility(View.VISIBLE);
        } else {
            viewGroup.setVisibility(View.GONE);
        }
    }

}
