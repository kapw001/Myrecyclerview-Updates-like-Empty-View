package com.myrecyclerview;

import android.content.Context;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import commonadapter.RecyclerViewRow;
import commonadapter.ViewLayout;

/**
 * Created by yasar on 12/4/18.
 */

public class RowItem extends LinearLayout implements RecyclerViewRow<ModelTest> {

    private TextView textView;
    private View foreGroundView;

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

        foreGroundView = (View) findViewById(R.id.view_foreground);

    }

    @Override
    public View getForeGroundView() {
        return foreGroundView;
    }

    @Override
    public void showData(ModelTest item) {

        textView.setText(item.getName());

    }

    @Override
    public void setOnItemClickListener(View.OnClickListener listener) {

    }

    @Override
    public void setOnItemLongClickListener(View.OnLongClickListener listener) {

    }
}
