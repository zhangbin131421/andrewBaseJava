package com.example.demojava.ui;

import android.content.Context;
import android.hardware.display.DisplayManager;
import android.os.Environment;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
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
import com.example.demojava.base.BasePresentation;
import com.example.demojava.databinding.TestActivityBinding;
import com.example.demojava.model.AppUpdate;
import com.example.demojava.ui.fragment.DemoFragmentDialog;
import com.example.demojava.util.net.ScreenManager;
import com.orhanobut.logger.Logger;

import java.lang.reflect.Method;
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



        bindingView.etv1.requestFocus();
//        bindingView.etv1.setOnTouchListener(new View.OnTouchListener() {
//            public boolean onTouch(View v, MotionEvent event) {
//                Logger.e("  onClick(MotionEvent event) ----------------333333333333333---------------");
//
//                int inType = bindingView.etv1.getInputType(); // backup the input type
//                bindingView.etv1.setInputType(InputType.TYPE_NULL); // disable soft input
//                bindingView.etv1.onTouchEvent(event); // call native handler
//                bindingView.etv1.setInputType(inType); // restore input type
//                return true;
//            }
//        });
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
        initPresentation();
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

    /**
     * 禁止Edittext弹出软件盘，光标依然正常显示。
     */
    public void disableShowSoftInput() {
        if (android.os.Build.VERSION.SDK_INT <= 10) {
            bindingView.etv1.setInputType(InputType.TYPE_NULL);
        } else {
            Class<EditText> cls = EditText.class;
            Method method;
            try {
                method = cls.getMethod("setShowSoftInputOnFocus", boolean.class);
                method.setAccessible(true);
                method.invoke(bindingView.etv1, false);
            } catch (Exception e) {
            }
        }
        bindingView.etv1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                Logger.e("sss="+s.toString());
                Logger.e("sss ="+s.length());
                bindingView.etv1.setSelection(bindingView.etv1.length());
            }
        });


    }


    /**  屏幕管理器 **/
    private DisplayManager mDisplayManager;
    /**  屏幕数组 **/
    private Display[] displays;
    /** 初始化第二块屏幕 **/

    private ScreenManager screenManager = ScreenManager.getInstance();

    private void initPresentation(){
//        screenManager.init(this);
//        Display[] displays = screenManager.getDisplays();
//        Logger.e( "屏幕数量" + displays.length);
//        for (int i = 0; i < displays.length; i++) {
//            Logger.e( "屏幕" + displays[i]);
//        }
//        Display display = screenManager.getPresentationDisplays();
//        if (display != null) {
//            TextDisplay  textDisplay = new TextDisplay(this, displays[1]);
//            textDisplay.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
//            textDisplay.show();
//        }

        DisplayManager mDisplayManager; // 屏幕管理类
        mDisplayManager = (DisplayManager)  this
                .getSystemService(Context.DISPLAY_SERVICE);
        Display[] displays = mDisplayManager.getDisplays();

            TextDisplay  mPresentation =  new  TextDisplay( this , displays[displays.length - 1]); // displays[1]是副屏

            mPresentation.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
            mPresentation.show();

    }


}
