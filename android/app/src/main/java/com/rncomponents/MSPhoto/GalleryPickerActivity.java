package com.rncomponents.MSPhoto;

import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AbsListView.MultiChoiceModeListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.Checkable;
import android.widget.GridView;
import android.widget.TextView;

import com.imagechooser.listener.OnTaskResultListener;
import com.imagechooser.model.ImageGroup;
import com.imagechooser.task.ImageLoadTask;
import com.rncomponents.Constants;
import com.rncomponents.MSBase.MSBaseActivity;
import com.rncomponents.MSPhoto.adapter.Gallery2Adapter;
import com.rncomponents.MSPhoto.adapter.PhotoGroupAdapter;
import com.rncomponents.MSPhoto.adapter.TakePhotoAdapter;
import com.rncomponents.MSPhoto.utils.IntentAction;
import com.rncomponents.R;
import com.rncomponents.utils.MergeAdapter;

import java.io.File;
import java.util.ArrayList;

public class GalleryPickerActivity extends MSBaseActivity implements OnItemClickListener,
        MultiChoiceModeListener {
	final static String[] PROJECTION = { MediaStore.Images.Media.DATA,
			MediaStore.Images.Media._ID ,MediaStore.Images.Media.BUCKET_DISPLAY_NAME};
    final static String SELECOTR = MediaStore.Images.Media.BUCKET_DISPLAY_NAME + " NOT LIKE '%mishi%'";
	final static String SORTORDER = MediaStore.Images.Media._ID + " DESC";
	final static Uri URI = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
	public static final int DATA_INDEX = 0;
	public static final int ID_INDEX = 1;
	private static final int LOADER_ID = 0;

    private View mViewTitleBar;
	private GridView mGridView;
//	private GalleryAdapter galleryAdapter;
    private Gallery2Adapter gallery2Adapter;
    private TakePhotoAdapter takePhotoAdapter;
    private PhotoGroupAdapter photoGroupAdapter;
    private MergeAdapter mergeAdapter;
	private boolean mIsMultiple;
    private int maxSelectedNum;

    private ArrayList<ImageGroup> mList;
    private int mCurrentListIndex;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gallery_pick_layout);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        mViewTitleBar = findViewById(R.id.title_bar);
        TextView tvTitle = (TextView) mViewTitleBar.findViewById(R.id.title);
        tvTitle.setText("选择图片");
        Button btnTitleRight = (Button) mViewTitleBar.findViewById(R.id.title_right);
        btnTitleRight.setText("相册选择");
        btnTitleRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onOptionsItemSelected();
            }
        });

		mIsMultiple = IntentAction.ACTION_MULTIPLE_PICK.equals(getIntent().getAction());
        maxSelectedNum = getIntent().getIntExtra("maxSelectedNum", Integer.MAX_VALUE);
		final GridView view = (GridView) findViewById(R.id.grid);
        mergeAdapter = new MergeAdapter();

        takePhotoAdapter = new TakePhotoAdapter(this);
        takePhotoAdapter.setCount(1);

        mergeAdapter.addAdapter(takePhotoAdapter);

//        photoGroupAdapter = new PhotoGroupAdapter(this);
//        mergeAdapter.addAdapter(photoGroupAdapter);

//		galleryAdapter = new GalleryAdapter(this);
        gallery2Adapter = new Gallery2Adapter(this);

//        mergeAdapter.addAdapter(galleryAdapter);
        mergeAdapter.addAdapter(gallery2Adapter);

		view.setAdapter(mergeAdapter);
		view.setOnItemClickListener(this);
		view.setMultiChoiceModeListener(this);
		mGridView = view;

//		getSupportLoaderManager().initLoader(LOADER_ID, null, this);

        final Context context = this;
        new ImageLoadTask(this, new OnTaskResultListener() {
            @Override
            public void onResult(boolean success, String error, Object result) {
                mList = (ArrayList<ImageGroup>) result;
                refresh();
            }
        }).execute();
	}

    private void refresh() {
        if ((mList == null) || mList.isEmpty()) {
            return;
        }

        ImageGroup item = mList.get(mCurrentListIndex);
        if ((item != null) && (item.getImageCount() > 0)) {
            mGridView.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mGridView.setSelection(0);
                }
            }, 100);
        }
        gallery2Adapter.setData(item);
        gallery2Adapter.notifyDataSetChanged();
    }

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		if (mIsMultiple) {
			if (view instanceof Checkable) {
				Checkable c = (Checkable) view;
				boolean checked = !c.isChecked();
                mGridView.setItemChecked(position, checked);
			}
		} else {
			Uri uri = ContentUris.withAppendedId(URI, id);
			Intent data = new Intent();
			data.putExtra(IntentAction.EXTRA_DATA, uri);
			setResult(RESULT_OK, data);
			finish();
		}
	}

	@Override
	public boolean onCreateActionMode(ActionMode mode, Menu menu) {
		MenuInflater inflater = mode.getMenuInflater();
		inflater.inflate(R.menu.gallery, menu);
		int selectCount = mGridView.getCheckedItemCount();
		String title = getResources().getQuantityString(
				R.plurals.number_of_items_selected, selectCount, selectCount);
		mode.setTitle(title);
		return true;
	}

	@Override
	public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
		return false;
	}

	@Override
	public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
		if (item.getItemId() == R.id.menu_done) {
			System.out.println(item.getTitle() + " " + item.getItemId());
			long[] ids = mGridView.getCheckedItemIds();
			Uri[] uris = new Uri[ids.length];
			for (int i = 0; i < ids.length; i++) {
				Uri uri = ContentUris.withAppendedId(URI, ids[i]);
				uris[i] = uri;
			}

			Intent data = new Intent();
			data.putExtra(IntentAction.EXTRA_DATA, uris);
			setResult(IntentAction.ACTION_MULTIPLE_PICK_RESULT_OK, data);
			finish();
			return true;
		}
		return false;
	}

	@Override
	public void onDestroyActionMode(ActionMode mode) {
        mViewTitleBar.setVisibility(View.VISIBLE);
    }

	@Override
	public void onItemCheckedStateChanged(ActionMode mode, int position,
			long id, boolean checked) {
        int selectCount = mGridView.getCheckedItemCount();
        if (selectCount > maxSelectedNum && checked) {
            mGridView.setItemChecked(position, false);
        } else {
            mViewTitleBar.setVisibility(View.GONE);
            String title = getResources().getQuantityString(
                    R.plurals.number_of_items_selected, selectCount, selectCount);
            mode.setTitle(title);
        }

	}

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constants.REQUEST_FOR_SELECT_ALBUM) {
            if (resultCode == RESULT_OK) {
                int index = data.getIntExtra(SelectAlbumActivity.SELECT_INDEX, 0);
                if (index != mCurrentListIndex) {
                    mCurrentListIndex = index;
                    refresh();
                    return;
                }
            }
        }

        String capturePhotoUrl = takePhotoAdapter.onActivityResult(requestCode,resultCode,data);
        if(!TextUtils.isEmpty(capturePhotoUrl)) {
            Intent intent = new Intent();
            intent.putExtra(IntentAction.EXTRA_DATA, Uri.fromFile(new File(capturePhotoUrl)));
            setResult(IntentAction.ACTION_TAKE_PHOTO_RESULT_OK, intent);
            finish();
            return;
        }

        super.onActivityResult(requestCode,resultCode,data);
    }

    private void onOptionsItemSelected() {
        if ((mList == null) || mList.isEmpty()) {
            return;
        }
        Intent intent = new Intent(this, SelectAlbumActivity.class);
        intent.putExtra(SelectAlbumActivity.INIT_INDEX, mCurrentListIndex);
        intent.putParcelableArrayListExtra(SelectAlbumActivity.ALBUM_DATA, mList);
        startActivityForResult(intent, Constants.REQUEST_FOR_SELECT_ALBUM);
    }

}
