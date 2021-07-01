package com.andrew.java.library.base;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.ViewDataBinding;

import com.andrew.java.library.vmodel.AndrewLoadingViewModel;

/**
 * 带loading加载基类
 * author: zhangbin
 * created on: 2021/6/29 14:08
 * description:
 */
public abstract class AndrewActivityDataBindingLoading<BV extends ViewDataBinding, VM extends AndrewLoadingViewModel> extends AndrewActivityDataBinding<BV> {

    protected VM mLoadingVm;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (mLoadingVm != null) {
            mLoadingVm.attachLoading(loadingState);
        }
    }


}
