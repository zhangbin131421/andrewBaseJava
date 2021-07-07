package com.andrew.java.library.base;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

/**
 * author: zhangbin
 * created on: 2021/6/29 14:25
 * description:
 */
public abstract class AndrewFragmentDataBinding<BV extends ViewDataBinding> extends AndrewFragment {
    protected AppCompatActivity mActivity;

    protected BV bindingView;

    private ViewModelProvider mFragmentProvider;
    private ViewModelProvider mActivityProvider;
    private ViewModelProvider mApplicationProvider;

    protected abstract int layoutId();

    protected abstract void initAndBindingVm();

    protected abstract void initView();

    protected abstract void initData();

    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.mActivity = (AppCompatActivity) context;
    }

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
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initAndBindingVm();
        initView();
        initData();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (bindingView != null) {
            bindingView.unbind();
        }
    }

    protected <T extends ViewModel> T getFragmentScopeViewModel(@NonNull Class<T> modelClass) {
        if (mFragmentProvider == null) {
            mFragmentProvider = new ViewModelProvider(this);
        }
        return mFragmentProvider.get(modelClass);
    }

    protected <T extends ViewModel> T getActivityScopeViewModel(@NonNull Class<T> modelClass) {
        if (mActivityProvider == null) {
            mActivityProvider = new ViewModelProvider(mActivity);
        }
        return mActivityProvider.get(modelClass);
    }

    protected <T extends ViewModel> T getApplicationScopeViewModel(@NonNull Class<T> modelClass) {
        if (mApplicationProvider == null) {
            mApplicationProvider = new ViewModelProvider((ViewModelStoreOwner) mActivity.getApplicationContext(), getApplicationFactory(mActivity));
        }
        return mApplicationProvider.get(modelClass);
    }

    private ViewModelProvider.Factory getApplicationFactory(Activity activity) {
        checkActivity(this);
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

    private void checkActivity(Fragment fragment) {
        Activity activity = fragment.getActivity();
        if (activity == null) {
            throw new IllegalStateException("Can't create ViewModelProvider for detached fragment");
        }
    }
}
