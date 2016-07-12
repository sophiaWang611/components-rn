package com.rncomponents.MSPhoto;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.mishi.utils.BitmapUtils;
import com.rncomponents.MSPhoto.widget.ImageCroppingView;
import com.rncomponents.R;


/**
 * Created by sophia on 16/2/19.
 */
public class ImageEditView extends RelativeLayout {
    ImageCroppingView croppingView;

    private final int kImageSize = 640;
    private Double mAspectRatio = 0.75; // 宽高比 4:3
    private int width;
    private boolean shouldUpdate = false;
    private String imageSourceUri;

    public ImageEditView(Context context) {
        super(context);

        this.width = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getWidth();

        View container = LayoutInflater.from(context).inflate(R.layout.activity_image_edit, this, true);
        croppingView = (ImageCroppingView)container.findViewById(R.id.ui_image_edit_cropping_view);
    }

    public void setImageSourceUri(String imageSourceUri) {
        this.shouldUpdate = true;
        this.imageSourceUri = imageSourceUri;
    }

    public void setWidth(int width) {
        if (width > 0) {
            this.width = width;
        }
    }

    public void setmAspectRatio(double ratio) {
        if (ratio > 0) {
            this.mAspectRatio = ratio;
        }
    }

    public void maybeUpdateView() {
        if (!this.shouldUpdate) {
            return;
        }
        croppingView.setViewId(getId());

        LayoutParams params = (LayoutParams) croppingView.getLayoutParams();
        params.width = width;
        params.height = (int) (width * mAspectRatio);
        croppingView.setLayoutParams(params);

        if (imageSourceUri.startsWith("file://")) {
            imageSourceUri = imageSourceUri.replace("file://", "");
        }
        Bitmap bitmap = BitmapUtils.getSmallBitmap(imageSourceUri, kImageSize, (int) (kImageSize * mAspectRatio), false);
        croppingView.setImageBitmap(bitmap);
        croppingView.shouldCallBackAfterInit(true);


    }

}
