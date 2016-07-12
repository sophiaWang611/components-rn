package com.rncomponents.MSPhoto;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import com.facebook.react.bridge.ActivityEventListener;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.WritableMap;
import com.rncomponents.Constants;
import com.rncomponents.MSPhoto.utils.PhotoPickUitl;
import com.rncomponents.utils.MediaUtils;

/**
 * Created by sophia on 16/2/17.
 */
public class MSPhotoPickerManager extends ReactContextBaseJavaModule implements ActivityEventListener {

    private final static String CALL_BACK_PARAMETER = "uri";

    private Activity mCurrentActivity;
    private Callback mCallback;

    public MSPhotoPickerManager(ReactApplicationContext reactContext) {
        super(reactContext);
        reactContext.addActivityEventListener(this);
    }

    @Override
    public String getName() {
        return "MSPhotoPicker";
    }

    @ReactMethod
    public void showPhotoPick(final boolean editable, final Callback onChose) {
        mCurrentActivity = getCurrentActivity();
        mCallback = onChose;
        PhotoPickUitl.pickPhoto(mCurrentActivity, 1, new PhotoPickUitl.OnPhotoCaptureListener() {
                    @Override
                    public void onPhotoCaptureUrl(Activity activity, Uri uri) {
                        onChoseImg(editable, uri.getPath());
                    }
                }, new PhotoPickUitl.OnPhotoGallerySelectListener() {
                    @Override
                    public void onPhotoUrls(Activity activity, Uri[] uris) {
                        if (uris.length > 0) {
                            String imagePath = MediaUtils.getRealPathFromURI(getReactApplicationContext(), uris[0]);
                            onChoseImg(editable, imagePath);
                        } else {
                            onChose.invoke(Arguments.createMap());
                        }
                    }
                }
        );
    }

    private void onChoseImg(boolean editable, String path) {
        if (editable) {
            showImageCrop(path);
        } else {
            WritableMap returnVal = Arguments.createMap();
            returnVal.putString(CALL_BACK_PARAMETER, "file://" + path);
            mCallback.invoke(returnVal);
        }
    }

    private String getRealPathFromURI(Uri contentUri) {
        String res = null;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = super.getReactApplicationContext().getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);
        }
        cursor.close();
        return res;
    }

    private void showImageCrop(String path) {
        Intent intent = new Intent(mCurrentActivity, ImageCroppingActivity.class);
        intent.putExtra("data", path);
        mCurrentActivity.startActivityForResult(intent, Constants.REQUEST_CODE_PHOTO_CROPPING, null);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constants.REQUEST_CODE_PHOTO_CROPPING) {
            if (resultCode == Activity.RESULT_OK) {
                if (data == null) {
                    return;
                }
                WritableMap returnVal = Arguments.createMap();
                String path = data.getStringExtra("data");
                returnVal.putString(CALL_BACK_PARAMETER, path);
                mCallback.invoke(returnVal);
            } else {
                if (mCallback != null) {
                    mCallback.invoke(Arguments.createMap());
                }
            }
        }
    }
}
