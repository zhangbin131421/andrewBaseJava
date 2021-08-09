package com.andrew.java.library.base;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.MutableLiveData;

import com.andrew.java.library.observer.LoadingObserver;
import com.andrew.java.library.vmodel.AndrewLoadingViewModel;

import org.jetbrains.annotations.NotNull;

/**
 * author: zhangbin
 * created on: 2021/6/29 14:25
 * description:
 */
public abstract class AndrewDialogFragmentDataBindingLoading<BV extends ViewDataBinding, VM extends AndrewLoadingViewModel> extends AndrewDialogFragmentDataBinding<BV> {

    protected VM mLoadingVm;
    MutableLiveData<Boolean> loadingState = new MutableLiveData<>();

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadingState.observe(getViewLifecycleOwner(), new LoadingObserver(getContext()));
        if (mLoadingVm != null) {
            mLoadingVm.attachLoading(loadingState);
        }
    }

}
