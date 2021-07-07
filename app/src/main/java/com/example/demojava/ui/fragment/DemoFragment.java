package com.example.demojava.ui.fragment;

import com.andrew.java.library.base.AndrewFragmentDataBindingLoading;
import com.example.demojava.R;
import com.example.demojava.TestVM;
import com.example.demojava.databinding.DemoFragmentBinding;

/**
 * author: zhangbin
 * created on: 2021/7/7 16:31
 * description:
 */
public class DemoFragment extends AndrewFragmentDataBindingLoading<DemoFragmentBinding, DemoVM> {
    @Override
    protected int layoutId() {
        return R.layout.main_fragment;
    }

    @Override
    protected void initAndBindingVm() {
        mLoadingVm = getFragmentScopeViewModel(DemoVM.class);
//        mLoadingVm = getActivityScopeViewModel(DemoVM.class);
//        mLoadingVm = getApplicationScopeViewModel(DemoVM.class);
        bindingView.setVm(mLoadingVm);
        TestVM testVM = getApplicationScopeViewModel(TestVM.class);
        bindingView.setTest(testVM);
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }
}
