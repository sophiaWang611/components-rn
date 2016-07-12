package com.rncomponents.MSPhoto.widget;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.imagechooser.model.ImageGroup;
import com.rncomponents.R;
import com.squareup.picasso.Picasso;

import java.io.File;

public class AlbumItem extends RelativeLayout {

    private View mViewRoot;
    private ImageView mIv;
    private TextView mTv;

    public AlbumItem(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        mViewRoot = LayoutInflater.from(context).inflate(R.layout.view_album_item, this, true);
        mIv = (ImageView) mViewRoot.findViewById(R.id.iv);
        mTv = (TextView) mViewRoot.findViewById(R.id.tv);
    }

    public void init(ImageGroup item, boolean select) {
        if (item == null) {
            return;
        }

        String firstImgPath = item.getFirstImgPath();
        if (!TextUtils.isEmpty(firstImgPath)) {
            Picasso.with(getContext()).load(new File(firstImgPath)).placeholder(R.drawable.picker_photo_holder).resize(80, 80).centerCrop().into(mIv);
        }

        String dir = item.getDirName();
        if (!TextUtils.isEmpty(dir)) {
            mTv.setText(dir);
        }

        mViewRoot.setBackgroundResource(select ? R.color.ms_text_hint : android.R.color.transparent);
    }

}
