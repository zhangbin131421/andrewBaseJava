package com.andrew.java.library.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

/**
 * author: zhangbin
 * created on: 2021/6/29 14:25
 * description:
 */
public abstract class AndrewFragmentDataBinding<BV extends ViewDataBinding> extends AndrewFragment {

    protected BV bindingView;
    abstract int layoutId();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (bindingView != null) {
            bindingView = DataBindingUtil.inflate(inflater, layoutId(), container, false);
            bindingView.setLifecycleOwner(this);
        }
        return bindingView.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (bindingView != null) {
            bindingView.unbind();
        }
    }
}
