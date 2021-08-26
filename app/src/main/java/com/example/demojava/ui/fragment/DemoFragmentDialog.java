package com.example.demojava.ui.fragment;

import android.os.Bundle;
import android.view.Gravity;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;

import com.andrew.java.library.base.AndrewDialogFragmentDataBinding;
import com.example.demojava.R;
import com.example.demojava.databinding.DialogDemoBinding;

/**
 * author: zhangbin
 * created on: 2021/7/19 19:59
 * description:
 */
public class DemoFragmentDialog extends AndrewDialogFragmentDataBinding<DialogDemoBinding> {

    public static void show(FragmentActivity activity) {
        DemoFragmentDialog demoFragmentDialog = new DemoFragmentDialog();
        demoFragmentDialog.show(activity.getSupportFragmentManager(), "DemoFragmentDialog");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.dialogFullScreen2);

    }

    @Override
    public int getDialogGravity() {
        return Gravity.RIGHT;
    }

    @Override
    public float getWidthPercent() {
        return 0.5f;
    }

    @Override
    public float getHeightPercent() {
        return 1f;
    }

    @Override
    protected int layoutId() {
        return R.layout.dialog_demo;
    }

    @Override
    protected void initAndBindingVm() {

    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

//    @Override
//    public float getWidthPercent() {
//        return 0.6f;
//    }


}
