package com.rncomponents.RNImageCrop;

import android.graphics.Bitmap;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.WritableMap;
import com.mishi.utils.BitmapUtils;
import com.rncomponents.utils.FileUtils;

/**
 * Created by sophia on 16/2/29.
 */
public class RNImageCropManager extends ReactContextBaseJavaModule {
    public RNImageCropManager(ReactApplicationContext reactContext) {
        super(reactContext);
    }

    @Override
    public String getName() {
        return "RNImageCrop";
    }

    @ReactMethod
    public void cropImage(double rate, String path, Callback cb) {
        String fileName = FileUtils.fileNameTmpImage();
        final String filePath = FileUtils.imageStorePath() + fileName;

        Bitmap bitmap = BitmapUtils.prepareBitmapForUpload(path);
        if (bitmap == null && path.startsWith("file://")) {
            String src = path.replace("file://", "");
            bitmap = BitmapUtils.prepareBitmapForUpload(src);
        }
        BitmapUtils.savaBitmap(bitmap, filePath);
        BitmapUtils.recyleBitmap(bitmap);

        WritableMap returnVal = Arguments.createMap();
        returnVal.putString("filePath", "file://" + filePath);
        cb.invoke(returnVal);
    }
}
