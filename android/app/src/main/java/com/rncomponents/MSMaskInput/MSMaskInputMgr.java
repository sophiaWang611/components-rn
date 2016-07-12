package com.rncomponents.MSMaskInput;

import android.os.SystemClock;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.Gravity;

import com.facebook.infer.annotation.Assertions;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.uimanager.PixelUtil;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.UIManagerModule;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.facebook.react.uimanager.events.EventDispatcher;
import com.facebook.react.views.textinput.ReactEditText;
import com.facebook.react.views.textinput.ReactTextChangedEvent;
import com.facebook.react.views.textinput.ReactTextInputEvent;
import com.facebook.react.views.textinput.ReactTextInputManager;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Nullable;

/**
 * Created by sophia on 16/2/3.
 */
public class MSMaskInputMgr extends ReactTextInputManager {

    static final String REACT_CLASS = "MSMaskInput";

    private String mask = "";
    private String regular = "";
    private TextWatcher textWatcher;
    private ThemedReactContext reactContext;

    @Override
    public String getName() {
        return REACT_CLASS;
    }

    @ReactProp(name = "mask")
    public void setMask(ReactEditText view, @Nullable String mask) {
        this.mask = mask;
        if (this.textWatcher != null) {
            view.removeTextChangedListener(this.textWatcher);
        }
        this.textWatcher = new MSMaskInputMgr.ReactTextInputTextWatcher(reactContext, view, mask, regular);
        view.addTextChangedListener(this.textWatcher);
    }

    @ReactProp(name = "inputType")
    public void setInputType(ReactEditText view, @Nullable String type) {
        if ("numberDecimal".equalsIgnoreCase(type)) {
            view.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        }
    }

    @ReactProp(
            name = "multiline",
            defaultBoolean = false
    )
    public void setMultiline(ReactEditText view, boolean multiline) {
        super.setMultiline(view, multiline);
        view.setGravity(Gravity.TOP);
    }

    protected void addEventEmitters(final ThemedReactContext reactContext, final ReactEditText editText) {
        //super.addEventEmitters(reactContext, editText);
        this.reactContext = reactContext;
        this.textWatcher = new MSMaskInputMgr.ReactTextInputTextWatcher(reactContext, editText, mask, regular);
        editText.addTextChangedListener(this.textWatcher);
    }

    private class ReactTextInputTextWatcher implements TextWatcher {
        private EventDispatcher mEventDispatcher;
        private ReactEditText mEditText;
        private String mPreviousText;
        private String mask;
        private String regular;

        public ReactTextInputTextWatcher(ReactContext reactContext, ReactEditText editText, String mask, String regular) {
            this.mEventDispatcher = ((UIManagerModule)reactContext.getNativeModule(UIManagerModule.class)).getEventDispatcher();
            this.mEditText = editText;
            this.mPreviousText = null;
            this.mask = mask;
            this.regular = regular;
        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            this.mPreviousText = s.toString();
        }

        private void setText(String val) {
            mEditText.setText(val);
            mEditText.setSelection(val.length());
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if(count != 0 || before != 0) {
                Assertions.assertNotNull(this.mPreviousText);
                String newText = s.toString();
                String oldText = this.mPreviousText;

                if (this.regular != null && this.regular.length() != 0) {
                    if (this.regular.length() < newText.length()) {
                        setText(oldText);
                        return;
                    }

                    Pattern pattern = Pattern.compile(this.regular, Pattern.CASE_INSENSITIVE);
                    Matcher matcher = pattern.matcher(newText);
                    if (!matcher.matches()) {
                        setText(oldText);
                        return;
                    }
                }

                String returnTxt = "";
                if (this.mask != null && this.mask.length() != 0) {
                    if (this.mask.length() < newText.length()) {
                        setText(oldText);
                        return;
                    }

                    int last = 0;
                    boolean needAppend = false;
                    for (int i = 0; i < newText.length(); i++) {
                        char currentCharMask = this.mask.charAt(i);
                        char currentChar = newText.charAt(i);
                        if (Character.isDigit(currentChar) && currentCharMask == '#') {
                            returnTxt += currentChar;
                        } else {
                            if (currentCharMask == '#') {
                                break;
                            }
                            if (Character.isDigit(currentChar) && currentCharMask!= currentChar) {
                                needAppend = true;
                            }
                            returnTxt += String.valueOf(currentCharMask);
                        }
                        last = i;
                    }

                    for (int i = last+1; i < this.mask.length(); i++) {
                        char currentCharMask = this.mask.charAt(i);
                        if (currentCharMask != '#') {
                            returnTxt += currentCharMask;
                        }
                        if (currentCharMask == '#') {
                            break;
                        }
                    }
                    if (needAppend) {
                        returnTxt += newText.substring(start, start + count);
                    }
                }
                if (returnTxt.length() != 0) {
                    newText = returnTxt;
                }

                if(count != before || !newText.equals(oldText)) {
                    int contentWidth = this.mEditText.getWidth();
                    int contentHeight = this.mEditText.getHeight();
                    if(this.mEditText.getLayout() != null) {
                        contentWidth = this.mEditText.getCompoundPaddingLeft() + this.mEditText.getLayout().getWidth() + this.mEditText.getCompoundPaddingRight();
                        contentHeight = this.mEditText.getCompoundPaddingTop() + this.mEditText.getLayout().getHeight() + this.mEditText.getCompoundPaddingTop();
                    }

                    this.mEventDispatcher.dispatchEvent(new ReactTextChangedEvent(this.mEditText.getId(), SystemClock.uptimeMillis(), newText, (int) PixelUtil.toDIPFromPixel((float) contentWidth), (int)PixelUtil.toDIPFromPixel((float)contentHeight), this.mEditText.incrementAndGetEventCounter()));
                    this.mEventDispatcher.dispatchEvent(new ReactTextInputEvent(this.mEditText.getId(), SystemClock.uptimeMillis(), newText, oldText, start, start + before));
                }
            }
        }

        public void afterTextChanged(Editable s) {
        }
    }

}
