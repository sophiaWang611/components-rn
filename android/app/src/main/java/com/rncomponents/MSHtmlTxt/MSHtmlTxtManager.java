package com.rncomponents.MSHtmlTxt;

import android.text.Html;
import android.text.Spannable;

import com.facebook.react.views.text.ReactTextUpdate;
import com.facebook.react.views.text.ReactTextView;
import com.facebook.react.views.text.ReactTextViewManager;
import com.facebook.react.views.text.TextInlineImageSpan;

/**
 * Created by sophia on 16/3/11.
 */
public class MSHtmlTxtManager extends ReactTextViewManager {

    public String getName() {
        return "MSTextView";
    }

    @Override
    public void updateExtraData(ReactTextView view, Object extraData) {
        view.setPadding(0, 0, 0, 0);
        ReactTextUpdate update = (ReactTextUpdate)extraData;
        if(update.containsImages()) {
            Spannable spannable = update.getText();
            TextInlineImageSpan.possiblyUpdateInlineImageSpans(spannable, view);
            view.setText(update);
        } else {
            Spannable spannable = update.getText();
            Spannable formated = (Spannable) Html.fromHtml(spannable.toString());
            ReactTextUpdate htmlUp = new ReactTextUpdate(formated, update.getJsEventCounter(), update.containsImages());
            view.setText(htmlUp);
        }
    }
}
