package com.example.demojava.ui;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.util.Log;
import android.view.View;

import com.andrew.java.library.base.AndrewActivityDataBindingLoading;
import com.bumptech.glide.Glide;
import com.example.demojava.MainVM;
import com.example.demojava.R;
import com.example.demojava.TestVM;
import com.example.demojava.databinding.Demo1ActivityBinding;
import com.example.demojava.model.Person;
import com.example.demojava.model.Zuzhang;
import com.example.demojava.ui.adapter.PageAdapter;
import com.example.demojava.ui.adapter.SpacesItemDecoration;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
        bindingView.image.post(new Runnable() {
            @Override
            public void run() {


            }
        });
    }

    @Override
    protected void initView() {
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
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {

            }
        });
        bindingView.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HandlerThread handlerThread;
                Looper.prepare();
                Looper.myLooper().quit();
                Handler handler = new Handler();
                v.post(new Runnable() {
                    @Override
                    public void run() {

                    }
                });
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
//                DemoFragmentDialog.show(Demo1Activity.this);


            }
        });
    }

    private String getTime(Date date) {//可根据需要自行截取数据显示
        Log.d("getTime()", "choice date millis: " + date.getTime());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(date);
    }

}
