package com.myrecyclerview;

import commonadapter.ViewLayout;

/**
 * Created by yasar on 12/4/18.
 */

public class ModelTest implements ViewLayout {

    private String name;


    @Override
    public int getLayoutRes() {
        return R.layout.row;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
