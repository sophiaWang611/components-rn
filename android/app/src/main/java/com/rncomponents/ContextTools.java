package com.rncomponents;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;

import com.rncomponents.MSBase.ToastTools;

import java.util.List;

/**
 * Created by sophia on 16/7/11.
 */
public class ContextTools {
    public static final String INTENT_PICKER_TITLE1 = "intent_picker_title1";
    public static final String INTENT_PICKER_TITLE2 = "intent_picker_title2";
    public static final String INTENT_PICKER_LIST1 = "intent_picker_list1";
    public static final String INTENT_PICKER_LIST2 = "intent_picker_list2";
    public static final String INTENT_PICKER_SELECTED1 = "intent_picker_selected1";
    public static final String INTENT_PICKER_SELECTED2 = "intent_picker_selected2";

    public static final String KEY_INTENT_ADDRESS_ID = "key_intent_address_id";
    public static final String KEY_INTENT_ADDRESS_RESULT = "key_custome_address";


    public static boolean intentCanOpen(Context context, Intent intent) {
        PackageManager packageManager = context.getPackageManager();
        List<ResolveInfo> activities = packageManager.queryIntentActivities(intent, 0);
        return activities.size() > 0;
    }

    public static void showToastMessage(Context context, int type, String message) {
        if (ContextTools.isActivityFinish(context))
            return;

        ToastTools.showToast(context, type, message);
    }

    public static boolean isActivityFinish(Context context) {
        return (null != context && context instanceof Activity && ((Activity) context).isFinishing());
    }

}
