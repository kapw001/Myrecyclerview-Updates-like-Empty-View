package com.myrecyclerview;

import android.content.Context;
import android.os.Build;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import commonadapter.RecyclerViewRow;
import commonadapter.ViewLayout;

/**
 * Created by yasar on 12/4/18.
 */

public class RowItem extends LinearLayout implements RecyclerViewRow<ModelTest> {

    private TextView textView;
    private CheckBox checkBox;
    private View foreGroundView;
    private RadioGroup radioGroup;
    private RadioButton radioMale;
    private RadioButton radioFemale;

    public RowItem(Context context) {
        super(context);
    }

    public RowItem(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public RowItem(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public RowItem(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        textView = (TextView) findViewById(R.id.name);

        checkBox = (CheckBox) findViewById(R.id.checkBox);
        radioGroup = (RadioGroup) findViewById(R.id.radio);
        radioMale = (RadioButton) findViewById(R.id.radioMale);
        radioFemale = (RadioButton) findViewById(R.id.radioFemale);
        foreGroundView = (View) findViewById(R.id.view_foreground);

    }

    @Override
    public View getForeGroundView() {
        return foreGroundView;
    }

    @Override
    public void showData(final ModelTest item) {

        textView.setText(item.getName());

        checkBox.setChecked(item.isChecked());


        if (item.isMaleSelected()) {

            radioMale.setChecked(true);

        } else {

            radioFemale.setChecked(true);
        }

//        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
//
////                RadioButton radioButton=(RadioButton)findViewById(checkedId);
//
//                switch (checkedId) {
//                    case R.id.radioMale:
//
////                        radioMale.setChecked(true);
//
//                        item.setMaleSelected(true);
//
//                        break;
//                    case R.id.radioFemale:
//
////                        radioFemale.setChecked(true);
//                        item.setMaleSelected(false);
//                        break;
//                }
//            }
//        });

    }


    @Override
    public void setOnItemClickListener(View.OnClickListener listener) {

        setOnClickListener(listener);

    }

    @Override
    public void setOnItemLongClickListener(View.OnLongClickListener listener) {

    }

    @Override
    public void setOnChangeListener(CompoundButton.OnCheckedChangeListener onChangeListener) {

        checkBox.setOnCheckedChangeListener(onChangeListener);
    }

    @Override
    public void setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener onCheckedChangeListener) {

        radioGroup.setOnCheckedChangeListener(onCheckedChangeListener);
    }
}
