package com.example.demojava.ui;

import android.view.View;

import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.andrew.java.library.base.AndrewActivityDataBindingLoading;
import com.andrew.java.library.model.AndrewResponse;
import com.example.demojava.MainVM;
import com.example.demojava.R;
import com.example.demojava.TestVM;
import com.example.demojava.databinding.TestActivityBinding;
import com.example.demojava.model.AppUpdate;

import java.util.ArrayList;
import java.util.List;

public class TestActivity extends AndrewActivityDataBindingLoading<TestActivityBinding, MainVM> {
    TestAdapter adapter1 = new TestAdapter(this);
    @Override
    protected int layoutId() {
        return R.layout.test_activity;
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
        mLoadingVm.loading.postValue(true);
//        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        bindingView.recyclerView1.setLayoutManager(new LinearLayoutManager(this));

        bindingView.recyclerView1.setAdapter(adapter1);

        bindingView.recyclerView2.setLayoutManager(new LinearLayoutManager(this));
        TestAdapter adapter2 = new TestAdapter(this);
        bindingView.recyclerView2.setAdapter(adapter2);


        List<String> list1 = new ArrayList<>();
        List<String> list2 = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            list1.add("" + i);
            list2.add("" + (i + 20));
        }
        adapter1.addAllNotify(list1, true);
        adapter2.addAllNotify(list2, true);

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
        List<String> list1 = new ArrayList<>();
        for (int i = 10; i < 15; i++) {
            list1.add("" + i);
        }
        adapter1.addAllNotify(list1, true);

    }
}
