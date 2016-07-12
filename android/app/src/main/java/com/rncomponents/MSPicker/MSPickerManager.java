package com.rncomponents.MSPicker;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;

import com.facebook.react.bridge.ActivityEventListener;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableType;
import com.facebook.react.bridge.WritableMap;
import com.rncomponents.Constants;
import com.rncomponents.ContextTools;

import java.util.ArrayList;

/**
 * Created by sophia on 16/2/15.
 */
public class MSPickerManager extends ReactContextBaseJavaModule implements ActivityEventListener {
    private Callback mCallback;
    private Callback mCancel;

    public MSPickerManager(ReactApplicationContext reactContext) {
        super(reactContext);
        reactContext.addActivityEventListener(this);
    }

    @Override
    public String getName() {
        return "MSPicker";
    }

    @ReactMethod
    public void showPickerDialog(ReadableArray items, ReadableArray titles, final Callback onConfirm,
                                 final Callback onCancel) {
        if (items.size() < 1 || titles.size() < 1) {
            return;
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getCurrentActivity());
        builder.setCancelable(false);
        builder.setTitle(titles.size() >= 1 ? titles.getString(0) : "");
        builder.setItems(getItems(items, 0), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (onConfirm != null) {
                    WritableMap returnVal = Arguments.createMap();
                    returnVal.putInt("selectedIndex", which);
                    onConfirm.invoke(returnVal);
                }
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (onCancel != null) {
                    onCancel.invoke();
                }
            }
        });
        builder.show();
    }

    @ReactMethod
    public void showColumnPicker(ReadableArray items, ReadableArray titles, ReadableArray selectedIndex,
                                 final Callback onConfirm, final Callback onCancel) {
        Activity activity = getCurrentActivity();
        final Intent intent = new Intent(activity, MSSelectPicker.class);
        intent.putStringArrayListExtra(ContextTools.INTENT_PICKER_LIST1, this.getItemsArrayList(items, 0));
        intent.putStringArrayListExtra(ContextTools.INTENT_PICKER_LIST2, this.getItemsArrayList(items, 1));
        intent.putExtra(ContextTools.INTENT_PICKER_TITLE1, titles.getString(0));
        intent.putExtra(ContextTools.INTENT_PICKER_TITLE2, titles.getString(1));
        if (selectedIndex != null && selectedIndex.size() > 0) {
            intent.putExtra(ContextTools.INTENT_PICKER_SELECTED1, selectedIndex.getInt(0));
        }
        if (selectedIndex != null && selectedIndex.size() > 1) {
            intent.putExtra(ContextTools.INTENT_PICKER_SELECTED2, selectedIndex.getInt(1));
        }
        activity.startActivityForResult(intent, Constants.REQUEST_COLUMN_PICKER, null);

        mCallback = onConfirm;
        mCancel = onCancel;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null) return;
        int RESULT_CANCELED = Activity.RESULT_CANCELED;
        int RESULT_OK = Activity.RESULT_OK;

        if (resultCode == RESULT_CANCELED && mCancel != null) {
            mCancel.invoke(Arguments.createMap());
        } else if (resultCode == RESULT_OK && requestCode == Constants.REQUEST_COLUMN_PICKER && mCallback != null) {
            WritableMap returnVal = Arguments.createMap();
            returnVal.putInt("selectedIndex", data.getIntExtra(ContextTools.INTENT_PICKER_SELECTED1, 0));
            returnVal.putInt("childIndex", data.getIntExtra(ContextTools.INTENT_PICKER_SELECTED2, 0));
            mCallback.invoke(returnVal);
        }
    }

    private String[] getItems(ReadableArray items, int index) {
        if (items.size() <= index) {
            return new String[0];
        }
        ReadableArray first = items.getArray(index);
        String[] returnVal = new String[first.size()];
        for (int i = 0; i < first.size(); i++) {
            ReadableType type = first.getType(i);
            if (ReadableType.Boolean.equals(type)) {
                returnVal[i] = String.valueOf(first.getBoolean(i));
            } else if (ReadableType.Number.equals(type)) {
                returnVal[i] = String.valueOf(first.getDouble(i));
            } else {
                returnVal[i] = first.getString(i);
            }
        }
        return returnVal;
    }

    private ArrayList<String> getItemsArrayList(ReadableArray items, int index) {
        if (items.size() <= index) {
            return new ArrayList<String>();
        }
        ReadableArray first = items.getArray(index);
        ArrayList<String> returnVal = new ArrayList<String>();
        for (int i = 0; i < first.size(); i++) {
            returnVal.add(first.getString(i));
        }
        return returnVal;
    }

}
