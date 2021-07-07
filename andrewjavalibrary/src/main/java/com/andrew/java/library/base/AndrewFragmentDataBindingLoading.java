package com.andrew.java.library.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.MutableLiveData;

import com.andrew.java.library.observer.LoadingObserver;
import com.andrew.java.library.vmodel.AndrewLoadingViewModel;

/**
 * author: zhangbin
 * created on: 2021/6/29 14:25
 * description:
 */
public abstract class AndrewFragmentDataBindingLoading<BV extends ViewDataBinding, VM extends AndrewLoadingViewModel> extends AndrewFragmentDataBinding<BV> {

    protected VM mLoadingVm;
    MutableLiveData<Boolean> loadingState = new MutableLiveData<>();

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        loadingState.observe(getViewLifecycleOwner(), new LoadingObserver(getContext()));
        if (mLoadingVm != null) {
            mLoadingVm.attachLoading(loadingState);
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (bindingView != null) {
            bindingView.unbind();
        }
    }
}
