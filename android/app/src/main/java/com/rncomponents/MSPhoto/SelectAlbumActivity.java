package com.rncomponents.MSPhoto;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;


import com.imagechooser.model.ImageGroup;
import com.rncomponents.MSBase.MSBaseActivity;
import com.rncomponents.MSPhoto.widget.AlbumItem;
import com.rncomponents.R;

import java.util.ArrayList;

public class SelectAlbumActivity extends MSBaseActivity {

    public static final String INIT_INDEX = "init_index";
    public static final String ALBUM_DATA = "album_data";
    public static final String SELECT_INDEX = "select_index";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_album);

        Intent intent = getIntent();
        if (intent != null) {
            final int initIndex = intent.getIntExtra(INIT_INDEX, 0);
            ArrayList<ImageGroup> albumData = intent.getParcelableArrayListExtra(ALBUM_DATA);
            final ListView lv = (ListView) findViewById(R.id.lv);
            lv.setAdapter(new AlbumAdapter(this, initIndex, albumData));
            lv.post(new Runnable() {
                @Override
                public void run() {
                    lv.setSelection(initIndex);
                }
            });
        }
    }

    private static class AlbumAdapter extends BaseAdapter {

        private Activity mActivity;
        private int mSelectIndex;
        private ArrayList<ImageGroup> mList;

        public AlbumAdapter(Activity activity, int selectIndex, ArrayList<ImageGroup> list) {
            mActivity = activity;
            mSelectIndex = selectIndex;
            mList = list;
        }

        @Override
        public int getCount() {
            return (mList == null) ? 0 : mList.size();
        }

        @Override
        public ImageGroup getItem(int position) {
            return mList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            AlbumItem view;
            if (convertView == null) {
                view = new AlbumItem(mActivity);
            } else {
                view = (AlbumItem) convertView;
            }
            view.init(getItem(position), (mSelectIndex == position) ? true : false);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.putExtra(SELECT_INDEX, position);
                    mActivity.setResult(Activity.RESULT_OK, intent);
                    mActivity.finish();
                }
            });

            return view;
        }
    }
}
