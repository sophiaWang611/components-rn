package com.rncomponents.MSBase;

import android.app.Activity;

import com.mishi.analytics.MsAnalyticsHelper;
import com.rncomponents.utils.Utils;

public class MSBaseActivity extends Activity {

    @Override
    protected void onStop() {
        super.onStop();
        if (!Utils.isAppOnForeground(this)) {
            MsAnalyticsHelper.INSTANCE.appDidEnterBackground();
        }
    }
}
