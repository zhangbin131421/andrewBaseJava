package com.example.demojava.ui;

import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.andrew.java.library.base.AndrewActivityDataBindingLoading;
import com.example.demojava.MainVM;
import com.example.demojava.R;
import com.example.demojava.TestVM;
import com.example.demojava.databinding.Demo1ActivityBinding;
import com.example.demojava.ui.adapter.PageAdapter;
import com.example.demojava.ui.adapter.SpacesItemDecoration;
import com.example.demojava.ui.fragment.DemoFragmentDialog;

import java.util.ArrayList;
import java.util.List;

public class Demo1Activity extends AndrewActivityDataBindingLoading<Demo1ActivityBinding, MainVM> {
    PageAdapter pageAdapter = new PageAdapter(this);

    @Override
    protected int layoutId() {
        return R.layout.demo1_activity;
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
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        bindingView.recyclerviewPage.setLayoutManager(linearLayoutManager);
        bindingView.recyclerviewPage.addItemDecoration(new SpacesItemDecoration(10));
        bindingView.recyclerviewPage.setAdapter(pageAdapter);
        List<Integer> list = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            list.add(i);
        }
        pageAdapter.addAllNotify(list, true);
    }

    @Override
    protected void initData() {
        bindingView.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pageAdapter.lastPage();
            }
        });

        bindingView.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pageAdapter.nextPage();
            }
        });
        bindingView.btnDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DemoFragmentDialog.show(Demo1Activity.this);
            }
        });
    }


}
