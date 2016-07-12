package com.rncomponents.MSPhoto;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.widget.Toast;

import com.rncomponents.Constants;
import com.rncomponents.MSBase.MSBaseActivity;
import com.rncomponents.MSPhoto.utils.IntentAction;
import com.rncomponents.MSPhoto.utils.PhotoPickUitl;

/**
 * Created by sophia on 16/2/17.
 */
public class MSPhotoPickerActivity extends MSBaseActivity {
    private final static int REQUEST_CODE_ASK_PERMISSIONS = 123;
    private Intent intent = new Intent(IntentAction.ACTION_MULTIPLE_PICK);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        intent.putExtra("maxSelectedNum", PhotoPickUitl.maxSelectedNum);

        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                this.requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE_ASK_PERMISSIONS);
            } else if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED) {
                this.requestPermissions(new String[]{Manifest.permission.CAMERA}, REQUEST_CODE_ASK_PERMISSIONS);
            } else {
                startActivityForResult(intent, Constants.REQUEST_FOR_PHOTO_PICKER);
            }
        } else {
            startActivityForResult(intent, Constants.REQUEST_FOR_PHOTO_PICKER);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_PERMISSIONS:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission Granted
                    startActivityForResult(intent, Constants.REQUEST_FOR_PHOTO_PICKER);
                } else {
                    // Permission Denied
                    Toast.makeText(this, "没有访问相册的权限，您可以在设置->Apps中打开权限", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                this.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constants.REQUEST_FOR_PHOTO_PICKER) {
            if (resultCode == IntentAction.ACTION_TAKE_PHOTO_RESULT_OK && PhotoPickUitl.onPhotoCaptureListener != null) {
                Uri uri = data.getParcelableExtra(IntentAction.EXTRA_DATA);
                PhotoPickUitl.onPhotoCaptureListener.onPhotoCaptureUrl(this, uri);
            } else if (resultCode == IntentAction.ACTION_MULTIPLE_PICK_RESULT_OK && PhotoPickUitl.onPhotoGallerySelectListener != null) {
                Parcelable[] extras = data.getParcelableArrayExtra(IntentAction.EXTRA_DATA);
                Uri[] uris = new Uri[extras.length];
                for (int i = 0; i < extras.length; i++) {
                    uris[i] = (Uri) extras[i];
                }
                PhotoPickUitl.onPhotoGallerySelectListener.onPhotoUrls(this, uris);
            }
            finish();
            PhotoPickUitl.onPhotoGallerySelectListener = null;
            PhotoPickUitl.onPhotoCaptureListener = null;
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

}
