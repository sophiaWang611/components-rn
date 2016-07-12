/**
 * ImageLoadTask.java
 * ImageSelector
 *
 * Created by likebamboo on 2014-4-22
 * Copyright (c) 1998-2014 http://likebamboo.github.io/ All rights reserved.
 */

package com.imagechooser.task;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.provider.MediaStore.Images.Media;

import com.imagechooser.listener.OnTaskResultListener;
import com.imagechooser.model.ImageGroup;

import java.io.File;
import java.util.ArrayList;

/**
 * 使用contentProvider扫描图片异步任务
 *
 * @author likebamboo
 */
public class ImageLoadTask extends BaseTask {

    /**
     * Matches code in MediaProvider.computeBucketValues. Should be a common
     * function.
     */
    public static String getBucketId(String path) {
        return String.valueOf(path.toLowerCase().hashCode());
    }
    /***/

    /**
     * 上下文对象
     */
    private Context mContext = null;

    /**
     * 存放图片<文件夹,该文件夹下的图片列表>键值对
     */
    private ArrayList<ImageGroup> mGruopList = new ArrayList<ImageGroup>();

    public ImageLoadTask(Context context) {
        super();
        mContext = context;
        result = mGruopList;
    }

    public ImageLoadTask(Context context, OnTaskResultListener listener) {
        super();
        mContext = context;
        result = mGruopList;
        setOnResultListener(listener);
    }

    /*
     * (non-Javadoc)
     * @see android.os.AsyncTask#doInBackground(Params[])
     */
    @Override
    protected Boolean doInBackground(Void... params) {
        Uri mImageUri = Media.EXTERNAL_CONTENT_URI;
        ContentResolver mContentResolver = mContext.getContentResolver();
        // 构建查询条件，且只查询jpeg和png的图片
        StringBuilder selection = new StringBuilder();
        selection.append(Media.MIME_TYPE).append("=?");
        selection.append(" or ");
        selection.append(Media.MIME_TYPE).append("=?");

        String[] projection = { MediaStore.MediaColumns.DATA,
                Media.BUCKET_DISPLAY_NAME, Media._ID, Media.BUCKET_ID,  };

        Cursor mCursor = null;
        try {
            // 初始化游标
            mCursor = mContentResolver.query(mImageUri, projection, null,
                    null, Media.DATE_TAKEN + " DESC");
            // 遍历结果
            while (mCursor.moveToNext()) {
                // 获取图片的路径
                String path = mCursor.getString(mCursor.getColumnIndex(Media.DATA));

                long _id = mCursor.getLong(mCursor.getColumnIndex(MediaStore.MediaColumns._ID));

                // 获取该图片的所在文件夹的路径
                File file = new File(path);
                if (file.length() > 0) {
                    String parentName = "";
                    if (file.getParentFile() != null) {
                        parentName = file.getParentFile().getName();
                    } else {
                        parentName = file.getName();
                    }
                    // 构建一个imageGroup对象
                    ImageGroup item = new ImageGroup();
                    // 设置imageGroup的文件夹名称
                    item.setDirName(parentName);
                    // 寻找该imageGroup是否是其所在的文件夹中的第一张图片
                    int searchIdx = mGruopList.indexOf(item);
                    if (searchIdx >= 0) {
                        // 如果是，该组的图片数量+1
                        ImageGroup imageGroup = mGruopList.get(searchIdx);
                        imageGroup.addImage(path);
                        imageGroup.addImageId(_id);
                    } else {
                        // 否则，将该对象加入到groupList中
                        item.addImage(path);
                        item.addImageId(_id);
                        mGruopList.add(item);
                    }
                }
            }
        } catch (Exception e) {
            return false;
        } finally {
            // 关闭游标
            if (mCursor != null && !mCursor.isClosed()) {
                mCursor.close();
            }
        }
        return true;
    }
}
