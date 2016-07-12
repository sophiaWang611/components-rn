package com.rncomponents.utils;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.provider.MediaStore;

import com.mishi.utils.BitmapUtils;

import java.io.FileOutputStream;
import java.io.IOException;

public class MediaUtils {

    private static final String Tag = "MediaUtil";
    public static String CAPTURE_IMAGE_PATH = "";

    private MediaUtils() {

    }

    public static String getRealPathFromURI(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = {MediaStore.Images.Media.DATA};
            cursor = context.getContentResolver().query(contentUri, proj, null,
                    null, null);

            if (null == cursor)
                return null;

            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    public static boolean saveBitmapToSdcard(Bitmap bitmap, String fileName) {
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(fileName);
            return bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
        } catch (Exception e) {
            e.printStackTrace(System.err);
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (Exception e) {
                }
            }
        }
        return false;
    }

    @Deprecated
    private static int calculateInSampleSize(BitmapFactory.Options options,
                                             int reqWidth, int reqHeight, boolean reverse) {
        // Raw height and width of image
        int height = options.outHeight;
        int width = options.outWidth;
        if (reverse) {
            height = options.outWidth;
            width = options.outHeight;
            // Log.e("lfa", "reverse");
        } else {
            height = options.outHeight;
            width = options.outWidth;
            // Log.e("lfa", "raw");
        }
        // Log.e("lfa", "raw width：" + options.outWidth + "，raw height："
        // + options.outHeight);
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and
            // keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

    @Deprecated
    public static Bitmap decodeSampledBitmapFromFile(String path, int reqWidth,
                                                     int reqHeight, boolean reverse) {
        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth,
                reqHeight, reverse);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(path, options);
    }

    private static int getImageRotate(String path) {
        int rotate = 0;
        ExifInterface exif = null;
        try {
            exif = new ExifInterface(path);
        } catch (IOException e) {

        }
        if (exif == null) {
            return rotate;
        }

        int exifOrientation = exif
                .getAttributeInt(ExifInterface.TAG_ORIENTATION,
                        ExifInterface.ORIENTATION_NORMAL);
        switch (exifOrientation) {
            case ExifInterface.ORIENTATION_ROTATE_90:
                rotate = 90;
                break;
            case ExifInterface.ORIENTATION_ROTATE_180:
                rotate = 180;
                break;
            case ExifInterface.ORIENTATION_ROTATE_270:
                rotate = 270;
                break;
            default:
                rotate = 0;
                break;
        }
        // Log.e("lfa", "rotate：" + rotate);
        return rotate;
    }

    @Deprecated
    public static Bitmap adjustImageOrientation(Bitmap source, int rotate) {
        if (rotate == 0) {
            return source;
        }

        int w = source.getWidth();
        int h = source.getHeight();

        // Setting pre rotate
        Matrix mtx = new Matrix();
        mtx.preRotate(rotate);

        // Rotating Bitmap & convert to ARGB_8888, required by tess
        Bitmap newBmp = Bitmap.createBitmap(source, 0, 0, w, h, mtx, true);
        source.recycle();
        return newBmp;
    }

    public static void rotateImage(String originalPath, String rotatePath) {
        int rotote = getImageRotate(originalPath);
        Bitmap rotateBmp = adjustImageOrientation(BitmapUtils.decodeFileToJpg(originalPath), rotote);
        saveBitmapToSdcard(rotateBmp, rotatePath);
    }

}
