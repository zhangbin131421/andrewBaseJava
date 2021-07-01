package com.example.demojava;

import androidx.lifecycle.Observer;

import com.andrew.java.library.base.AndrewActivityDataBindingLoading;
import com.andrew.java.library.model.AndrewResponse;
import com.example.demojava.databinding.MainActivityBinding;
import com.example.demojava.model.AppUpdate;

public class MainActivity extends AndrewActivityDataBindingLoading<MainActivityBinding, MainVM> {

    @Override
    protected int layoutId() {
        return R.layout.main_activity;
    }

    @Override
    protected void initAndBindingVm() {
        mLoadingVm = getActivityScopeViewModel(MainVM.class);
        bindingView.setVm(mLoadingVm);
        TestVM testVM = getActivityScopeViewModel(TestVM.class);
        bindingView.setTest(testVM);
    }

    @Override
    protected void initView() {
        mLoadingVm.refreshTrigger.postValue(true);
//        mLoadingVm.loading.postValue(true);
    }

    @Override
    protected void initData() {
        mLoadingVm.app.observe(this, new Observer<AndrewResponse<AppUpdate>>() {
            @Override
            public void onChanged(AndrewResponse<AppUpdate> appUpdateAndrewResponse) {
                if (appUpdateAndrewResponse != null) {
                    mLoadingVm.name.postValue(appUpdateAndrewResponse.getMsg());
                }
            }
        });

    }
}
