package com.rncomponents.MSPicker;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.rncomponents.R;

/**
 * Created by sophia on 16/2/16.
 */
public class MSSelectPickerAdapter extends BaseAdapter {
    private String[] arr;
    private Context mContext;
    private String selectedText = "";

    public MSSelectPickerAdapter(Context mContext, String[] arr, String selectedText) {
        this.mContext = mContext;
        if (arr != null) {
            this.arr = arr;
        }
        this.selectedText = selectedText;
    }

    /**
     * 设置选中的position,并通知列表刷新
     */
    public void setSelectedPosition(int pos) {
        if (arr != null && pos < arr.length) {
            selectedText = arr[pos];
            notifyDataSetChanged();
        }

    }


    public int getCount() {
        return this.arr.length;
    }

    public Object getItem(int position) {
        return arr[position];
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View view, ViewGroup arg2) {
        ViewHolder viewHolder = null;
        if (view == null) {
            viewHolder = new ViewHolder();
            view = LayoutInflater.from(mContext).inflate(
                    R.layout.adapter_select_picker_item, null);
            viewHolder.textView3 = (TextView) view
                    .findViewById(R.id.tv_title3);
            viewHolder.view1 = view
                    .findViewById(R.id.ui_line_top);
            viewHolder.view2 = view
                    .findViewById(R.id.ui_line_bottom);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder.textView3.setText(arr[position]);

        if (!TextUtils.isEmpty(selectedText) && selectedText.equals(arr[position])) {
            viewHolder.textView3.setTextColor(mContext.getResources().getColor(R.color.ms_black));
            viewHolder.textView3.setTextSize(21);

            viewHolder.view1.setVisibility(View.VISIBLE);
            viewHolder.view2.setVisibility(View.VISIBLE);
        } else {
            viewHolder.textView3.setTextColor(mContext.getResources().getColor(R.color.ms_gray));
            viewHolder.textView3.setTextSize(16);
            viewHolder.view1.setVisibility(View.INVISIBLE);
            viewHolder.view2.setVisibility(View.INVISIBLE);
        }

        return view;

    }

    final static class ViewHolder {
        TextView textView1; //
        TextView textView2; //
        TextView textView3; //
        View view1; //
        View view2; //
    }

}
