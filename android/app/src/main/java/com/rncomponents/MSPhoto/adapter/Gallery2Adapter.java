package com.rncomponents.MSPhoto.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.imagechooser.model.ImageGroup;
import com.rncomponents.MSPhoto.PhotoPickImageView;
import com.rncomponents.R;
import com.squareup.picasso.Picasso;

import java.io.File;

public class Gallery2Adapter extends BaseAdapter {

    private Context mContext;
    private ImageGroup mDirInfo;

    public Gallery2Adapter(Context context) {
        mContext = context;
    }

    public void setData(ImageGroup info) {
        mDirInfo = info;
    }

    @Override
    public int getCount() {
        if (mDirInfo == null) {
            return 0;
        } else {
            return mDirInfo.getImageCount();
        }
    }

    @Override
    public String getItem(int position) {
        return mDirInfo.getImgPath(position);
    }

    @Override
    public long getItemId(int position) {
        return mDirInfo.getImgId(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        PhotoPickImageView item;
        if (convertView == null) {
            item = new PhotoPickImageView(mContext);
            item.setScaleType(ImageView.ScaleType.CENTER_CROP);
        } else {
            item = (PhotoPickImageView) convertView;
        }
        Picasso.with(mContext).load(new File(getItem(position))).placeholder(R.drawable.picker_photo_holder).resize(120, 120).centerCrop().into(item);
        return item;
    }
}
