package com.rncomponents.MSPhoto.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.rncomponents.R;

/**
 * Created by sophia on 16/2/18.
 */
public class PhotoGroupAdapter extends BaseAdapter {
    private Activity mActivity;

    public PhotoGroupAdapter(Activity context) {
        mActivity = context;
    }

    @Override
    public int getCount() {
        return 1;
    }

    @Override
    public Object getItem(int position) {
        return new Object();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return convertView == null ? LayoutInflater.from(parent.getContext()).inflate(R.layout.photo_group_list,parent,false) : convertView;
    }

}
