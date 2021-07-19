package com.example.demojava.ui;

import android.text.InputType;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.andrew.java.library.base.AndrewActivityDataBindingLoading;
import com.andrew.java.library.model.AndrewResponse;
import com.example.demojava.MainVM;
import com.example.demojava.R;
import com.example.demojava.TestVM;
import com.example.demojava.databinding.TestActivityBinding;
import com.example.demojava.model.AppUpdate;
import com.example.demojava.ui.fragment.DemoFragmentDialog;
import com.orhanobut.logger.Logger;

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

        bindingView.etv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Logger.e("  onClick(MotionEvent event) ----------------1111111111111---------------");
                bindingView.keyboard.setEditText(bindingView.etv1);
            }
        });
        bindingView.etv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Logger.e("  onClick(MotionEvent event) ----------------22222222222222---------------");

                bindingView.keyboard.setEditText(bindingView.etv2);
            }
        });

        bindingView.etv1.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                Logger.e("  onClick(MotionEvent event) ----------------333333333333333---------------");

                int inType = bindingView.etv1.getInputType(); // backup the input type
                bindingView.etv1.setInputType(InputType.TYPE_NULL); // disable soft input
                bindingView.etv1.onTouchEvent(event); // call native handler
                bindingView.etv1.setInputType(inType); // restore input type
                return true;
            }
        });
//        bindingView.etv2.setOnTouchListener(new View.OnTouchListener() {
//            public boolean onTouch(View v, MotionEvent event) {
//                Logger.e("  onClick(MotionEvent event) ----------------44444444444444444---------------");
//                int inType = bindingView.etv2.getInputType(); // backup the input type
//                bindingView.etv2.setInputType(InputType.TYPE_NULL); // disable soft input
//                bindingView.etv2.onTouchEvent(event); // call native handler
//                bindingView.etv2.setInputType(inType); // restore input type
//                return false;
//            }
//        });

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
//        List<String> list1 = new ArrayList<>();
//        for (int i = 10; i < 15; i++) {
//            list1.add("" + i);
//        }
//        adapter1.addAllNotify(list1, true);
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
