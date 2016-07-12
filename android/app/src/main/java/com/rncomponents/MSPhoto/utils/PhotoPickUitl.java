/*
 * @Author: Mars Tsang
 * @Mail: zmars@me.com
 */

package com.rncomponents.MSPhoto.utils;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

import com.rncomponents.MSPhoto.MSPhotoPickerActivity;

import java.io.Serializable;

/**
 * Created by zzmars on 14-9-26.
 */
public class PhotoPickUitl {

    public static OnPhotoCaptureListener onPhotoCaptureListener;
    public static OnPhotoGallerySelectListener onPhotoGallerySelectListener;
    public static int maxSelectedNum = Integer.MAX_VALUE;

    public static void pickPhoto(Activity activity,OnPhotoCaptureListener captureListener,
                                 OnPhotoGallerySelectListener gallerySelectListener) {
        pickPhoto(activity,Integer.MAX_VALUE,captureListener,gallerySelectListener);
    }

    public static void pickPhoto(Activity activity,int maxSelectedNum,OnPhotoCaptureListener captureListener,
                                 OnPhotoGallerySelectListener gallerySelectListener) {
        PhotoPickUitl.maxSelectedNum = maxSelectedNum;
        onPhotoCaptureListener = captureListener;
        onPhotoGallerySelectListener = gallerySelectListener;
        Intent intent = new Intent(activity, MSPhotoPickerActivity.class);
        activity.startActivity(intent);
    }

    public interface OnPhotoCaptureListener extends Serializable{
        void onPhotoCaptureUrl(Activity activity, Uri uri);
    }

    public interface OnPhotoGallerySelectListener extends Serializable{
        void onPhotoUrls(Activity activity, Uri[] uris);
    }


}
