package com.example.demojava.ui;

import android.view.View;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
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
    }

    public void jump(View view) {
        showDialog();
    }

    public void showDialog() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        DemoFragmentDialog newFragment = new DemoFragmentDialog();
//        newFragment.setStyle(DialogFragment.STYLE_NORMAL, R.style.dialogFullScreen2);//添加上面创建的style

        if (true) {
            // The device is using a large layout, so show the fragment as a dialog
            newFragment.show(fragmentManager, "dialog");
        } else {
            // The device is smaller, so show the fragment fullscreen
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            // For a little polish, specify a transition animation
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            // To make it fullscreen, use the 'content' root view as the container
            // for the fragment, which is always the root view for the activity
            transaction.add(android.R.id.content, newFragment)
                    .addToBackStack(null).commit();
        }
    }

}
