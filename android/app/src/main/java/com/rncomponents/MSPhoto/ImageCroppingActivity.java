/*
 * @Author: Mars Tsang
 * @Mail: zmars@me.com
 */

package com.rncomponents.MSPhoto;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.Window;

import com.mishi.utils.BitmapUtils;
import com.rncomponents.MSBase.MSBaseActivity;
import com.rncomponents.MSPhoto.widget.ClipImageLayout;
import com.rncomponents.R;
import com.rncomponents.utils.FileUtils;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class ImageCroppingActivity extends MSBaseActivity implements View.OnClickListener {

    private final static String TAG  = "ImageCroppingActivity";

    @InjectView(R.id.ui_image_cropping_view)
    ClipImageLayout imageCroppingView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_cropp);
        ButterKnife.inject(this);

        findViewById(R.id.img_crop_bar_close).setOnClickListener(this);
        findViewById(R.id.img_crop_bar_done).setOnClickListener(this);

        String filePath = getIntent().getStringExtra("data");
        if(filePath != null && filePath.length() >= 0){
            // TODO 需要处理下 完全展示原图 可能 outOfMemory

            DisplayMetrics dm = BitmapUtils.getDevicePixels(this);
            Rect rectangle= new Rect();
            Window window= getWindow();
            window.getDecorView().getWindowVisibleDisplayFrame(rectangle);
            int statusBarHeight= rectangle.top;
            int actionBarHeight = 0;
            TypedValue tv = new TypedValue();
            if (getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true))
            {
                actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data,getResources().getDisplayMetrics());
            }
            dm.heightPixels -= statusBarHeight;
            dm.heightPixels -= actionBarHeight;
            if(dm.heightPixels > 1000 || dm.widthPixels >1000){
                dm.heightPixels = dm.heightPixels / 10;
                dm.widthPixels = dm.widthPixels / 10;
            }
            Bitmap bitmap = BitmapUtils.decodeBitmapFromFile(filePath,dm.widthPixels,dm.heightPixels);
            imageCroppingView.setImageBitmap(bitmap);
        }else{
            finish();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_crop_bar_close:
                setResult(RESULT_CANCELED, new Intent());
                finish();
                break;
            case R.id.img_crop_bar_done:
                done();
                break;
            default:
                break;
        }
    }

    private void done() {
        try {
            Bitmap bitmap = imageCroppingView.clip();
            String fileName = FileUtils.fileNameTmpImage();
            String filePath = FileUtils.imageStorePath() + fileName;

            BitmapUtils.savaBitmap(bitmap, filePath);
            BitmapUtils.recyleBitmap(bitmap);
            //
            Intent data = new Intent();
            data.putExtra("data", "file://" + filePath);

            setResult(RESULT_OK, data);
            finish();
            return;

        }catch (Exception e){
        }
        setResult(RESULT_CANCELED);
        finish();
    }

}
