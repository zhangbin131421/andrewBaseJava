package com.andrew.java.library.base;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

/**
 * author: zhangbin
 * created on: 2021/6/29 14:08
 * description:
 */
public abstract class AndrewActivityDataBinding<BV extends ViewDataBinding> extends AndrewActivity {

    protected BV bindingView;
    private ViewModelProvider mActivityProvider;
    private ViewModelProvider mApplicationProvider;

    protected abstract void initAndBindingVm();

    protected abstract void initView();

    protected abstract void initData();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bindingView = DataBindingUtil.setContentView(this, layoutId());
        bindingView.setLifecycleOwner(this);
        initAndBindingVm();
        initView();
        initData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (bindingView != null) {
            bindingView.unbind();
        }
    }

    protected <T extends ViewModel> T getActivityScopeViewModel(@NonNull Class<T> modelClass) {
        if (mActivityProvider == null) {
            mActivityProvider = new ViewModelProvider(this);
        }
        return mActivityProvider.get(modelClass);
    }

    protected <T extends ViewModel> T getApplicationScopeViewModel(@NonNull Class<T> modelClass) {
        if (mApplicationProvider == null) {
            mApplicationProvider = new ViewModelProvider((ViewModelStoreOwner) this.getApplicationContext(), getAppFactory(this));
        }
        return mApplicationProvider.get(modelClass);
    }

    private ViewModelProvider.Factory getAppFactory(Activity activity) {
        Application application = checkApplication(activity);
        return ViewModelProvider.AndroidViewModelFactory.getInstance(application);
    }

    private Application checkApplication(Activity activity) {
        Application application = activity.getApplication();
        if (application == null) {
            throw new IllegalStateException("Your activity/fragment is not yet attached to "
                    + "Application. You can't request ViewModel before onCreate call.");
        }
        return application;
    }
}
