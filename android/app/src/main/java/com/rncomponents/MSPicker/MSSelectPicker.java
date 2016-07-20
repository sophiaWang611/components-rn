package com.rncomponents.MSPicker;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.rncomponents.ContextTools;
import com.rncomponents.MSBase.MSBaseActivity;
import com.rncomponents.R;

import java.util.ArrayList;

/**
 * Created by sophia on 16/2/16.
 */
public class MSSelectPicker extends MSBaseActivity implements View.OnClickListener {

    private ListView firstColumn;
    private ListView secondColumn;
    private MSSelectPickerAdapter firstColumnAdapter;
    private MSSelectPickerAdapter secondColumnAdapter;
    private int firstSelected;
    private int secondSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);// 去掉标题栏
        setContentView(R.layout.activity_select_picker);

        findViewById(R.id.tv_close).setOnClickListener(this);
        findViewById(R.id.tv_done).setOnClickListener(this);

        firstColumn = (ListView) findViewById(R.id.ui_lv_acsd_listview_start);
        secondColumn = (ListView) findViewById(R.id.ui_lv_acsd_listview_end);

        Intent intent = getIntent();

        if (intent != null) {
            //设置每一列的标题
            this.resetPickerTitle(intent);
            //初始化
            this.setListViewContent(intent);
        }

    }

    private void resetPickerTitle(Intent intent) {
        String firstTitle = intent.getStringExtra(ContextTools.INTENT_PICKER_TITLE1);
        if (firstTitle != null && firstTitle.length() > 0) {
            TextView firstColumnTitle = (TextView) findViewById(R.id.ui_lv_acsd_textView_start);
            firstColumnTitle.setText(firstTitle);
        }

        String secondTitle = intent.getStringExtra(ContextTools.INTENT_PICKER_TITLE2);
        if (secondTitle != null && secondTitle.length() > 0) {
            TextView secondColumnTitle = (TextView) findViewById(R.id.ui_lv_acsd_textView_end);
            secondColumnTitle.setText(secondTitle);
        }
    }

    private void setListViewContent(Intent intent) {
        ArrayList<String> firstItems = intent.getStringArrayListExtra(ContextTools.INTENT_PICKER_LIST1);
        firstSelected = intent.getIntExtra(ContextTools.INTENT_PICKER_SELECTED1, 0);
        firstColumnAdapter = new MSSelectPickerAdapter(this, firstItems.toArray(new String[0]), firstItems.get(firstSelected));
        firstColumn.setAdapter(firstColumnAdapter);
        firstColumn.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                firstSelected = i;
                firstColumnAdapter.setSelectedPosition(firstSelected);
            }
        });
        firstColumn.setSelection(firstSelected);

        ArrayList<String> secondItems = intent.getStringArrayListExtra(ContextTools.INTENT_PICKER_LIST2);
        secondSelected = intent.getIntExtra(ContextTools.INTENT_PICKER_SELECTED2, 0);
        secondColumnAdapter = new MSSelectPickerAdapter(this, secondItems.toArray(new String[0]), secondItems.get(secondSelected));
        secondColumn.setAdapter(secondColumnAdapter);
        secondColumn.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                secondSelected = i;
                secondColumnAdapter.setSelectedPosition(secondSelected);
            }
        });
        secondColumn.setSelection(secondSelected);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.tv_close) {
            setResult(RESULT_CANCELED, new Intent());
            finish();
        } else if (id == R.id.tv_done) {
            done();
        }
    }

    private void done() {
        Intent data = new Intent();
        data.putExtra(ContextTools.INTENT_PICKER_SELECTED1, this.firstSelected);
        data.putExtra(ContextTools.INTENT_PICKER_SELECTED2, this.secondSelected);
        setResult(RESULT_OK, data);
        finish();
    }

}
