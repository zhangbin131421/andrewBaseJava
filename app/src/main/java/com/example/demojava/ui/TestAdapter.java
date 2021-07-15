package com.example.demojava.ui;

import android.content.Context;

import com.andrew.java.library.adapter.AndrewRecyclerViewAdapterBinding;
import com.example.demojava.R;

/**
 * author: zhangbin
 * created on: 2021/7/15 15:10
 * description:
 */
public class TestAdapter extends AndrewRecyclerViewAdapterBinding<String> {
    public TestAdapter(Context context) {
        super(context);
    }

    @Override
    protected int layoutId() {
        return R.layout.item_test_adpter;
    }
}
