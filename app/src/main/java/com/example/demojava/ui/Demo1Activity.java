package com.example.demojava.ui;

import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.andrew.java.library.base.AndrewActivityDataBindingLoading;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.example.demojava.MainVM;
import com.example.demojava.R;
import com.example.demojava.TestVM;
import com.example.demojava.databinding.Demo1ActivityBinding;
import com.example.demojava.ui.adapter.PageAdapter;
import com.example.demojava.ui.adapter.SpacesItemDecoration;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
//                DemoFragmentDialog.show(Demo1Activity.this);

                //时间选择器
                TimePickerView mStartDatePickerView = new TimePickerBuilder(Demo1Activity.this, new OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {//选中事件回调
                        // 这里回调过来的v,就是show()方法里面所添加的 View 参数，如果show的时候没有添加参数，v则为null
//                        bindingView.btnDialog.setText(DateTimeHelper.formatToString(date,"yyyy-MM-dd"));
                        Toast.makeText(Demo1Activity.this, getTime(date), Toast.LENGTH_SHORT).show();

                    }
                })
//                .setDecorView((RelativeLayout)findViewById(R.id.activity_rootview))//必须是RelativeLayout，不设置setDecorView的话，底部虚拟导航栏会显示在弹出的选择器区域
//                //年月日时分秒 的显示与否，不设置则默认全部显示
//                .setType(new boolean[]{true, true, true, true, true, true})
//                .setLabel("", "", "", "", "", "")
//                .isCenterLabel(false)//是否只显示中间选中项的label文字，false则每项item全部都带有label。
//                .setTitleText("开始日期")//标题文字
//                .setTitleSize(20)//标题文字大小
//                .setTitleColor(getResources().getColor(R.color.pickerview_title_text_color))//标题文字颜色
//                .setCancelText("取消")//取消按钮文字
//                .setCancelColor(getResources().getColor(R.color.pickerview_cancel_text_color))//取消按钮文字颜色
//                .setSubmitText("确定")//确认按钮文字
//                .setSubmitColor(getResources().getColor(R.color.pickerview_submit_text_color))//确定按钮文字颜色
//                .setContentTextSize(20)//滚轮文字大小
//                .setTextColorCenter(getResources().getColor(R.color.pickerview_center_text_color))//设置选中文本的颜色值
//                .setLineSpacingMultiplier(1.8f)//行间距
//                .setDividerColor(getResources().getColor(R.color.pickerview_divider_color))//设置分割线的颜色
//                .setRangDate(startDate, endDate)//设置最小和最大日期
//                .setDate(selectedDate)//设置选中的日期
                        .build();

                mStartDatePickerView.show();
            }
        });
    }

    private String getTime(Date date) {//可根据需要自行截取数据显示
        Log.d("getTime()", "choice date millis: " + date.getTime());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(date);
    }

}
