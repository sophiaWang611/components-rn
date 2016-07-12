package com.rncomponents.MSPhoto;

import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.annotations.ReactProp;

import javax.annotation.Nullable;

/**
 * Created by sophia on 16/2/18.
 */
public class ImageEditManager extends SimpleViewManager<ImageEditView> {
    ImageEditView imageEditView;

    @Override
    public String getName() {
        return "RNImageEditor";
    }

    @Override
    protected ImageEditView createViewInstance(ThemedReactContext themedReactContext) {
        imageEditView = new ImageEditView(themedReactContext);
        return imageEditView;
    }

    @ReactProp(name = "imageSourceUri")
    public void setImageSourceUri(ImageEditView view, @Nullable String imageSourceUri) {
        view.setImageSourceUri(imageSourceUri);
    }

    @ReactProp(name = "width")
    public void setWidth(ImageEditView view, @Nullable int width) {
        view.setWidth(width);
    }

    @ReactProp(name = "ratio")
    public void setAspectRatio(ImageEditView view, @Nullable double ratio) {
        view.setmAspectRatio(ratio);
    }

    @Override
    protected void onAfterUpdateTransaction(ImageEditView view) {
        super.onAfterUpdateTransaction(view);
        view.maybeUpdateView();
    }

}
