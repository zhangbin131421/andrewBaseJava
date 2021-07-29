package com.example.demojava;

import android.text.InputType;
import android.view.MotionEvent;
import android.view.View;

import androidx.lifecycle.Observer;

import com.andrew.java.library.base.AndrewActivityDataBindingLoading;
import com.andrew.java.library.model.AndrewResponse;
import com.example.demojava.databinding.MainActivityBinding;
import com.example.demojava.model.AppUpdate;
import com.orhanobut.logger.Logger;

public class MainActivity extends AndrewActivityDataBindingLoading<MainActivityBinding, MainVM> {

    @Override
    protected int layoutId() {
        return R.layout.main_activity;
    }

    @Override
    protected void initAndBindingVm() {
        Logger.e("test2222222");
        mLoadingVm = getActivityScopeViewModel(MainVM.class);
        bindingView.setVm(mLoadingVm);
        TestVM testVM = getActivityScopeViewModel(TestVM.class);
        bindingView.setTest(testVM);
    }

    @Override
    protected void initView() {
        mLoadingVm.refreshTrigger.postValue(true);
        mLoadingVm.loading.postValue(true);
//        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        bindingView.eTv.requestFocus();
        bindingView.eTv.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                int inType = bindingView.eTv.getInputType(); // backup the input type
                bindingView.eTv.setInputType(InputType.TYPE_NULL); // disable soft input
                bindingView.eTv.onTouchEvent(event); // call native handler
                bindingView.eTv.setInputType(inType); // restore input type
                return true;
            }
        });
        bindingView.tvTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bindingView.eTv.setText("aaaaaaaaaa");

            }
        });
    }

    @Override
    protected void initData() {
        mLoadingVm.app.observe(this, new Observer<AndrewResponse<AppUpdate>>() {
            @Override
            public void onChanged(AndrewResponse<AppUpdate> appUpdateAndrewResponse) {
                mLoadingVm.loading.postValue(false);
                if (appUpdateAndrewResponse != null) {
                    mLoadingVm.name.postValue(appUpdateAndrewResponse.getMsg());
                }
            }
        });

    }

    public void jump(View view) {
    }
}
