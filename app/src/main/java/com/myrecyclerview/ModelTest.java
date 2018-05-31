package com.myrecyclerview;

import commonadapter.ViewLayout;

/**
 * Created by yasar on 12/4/18.
 */

public class ModelTest implements ViewLayout {

    private String name;
    private boolean isChecked;
    private boolean isMaleSelected = true;


    @Override
    public int getLayoutRes() {
        return R.layout.row;
    }

    @Override
    public Object getObject() {
        return this;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public boolean isMaleSelected() {
        return isMaleSelected;
    }

    public void setMaleSelected(boolean maleSelected) {
        isMaleSelected = maleSelected;
    }
}
