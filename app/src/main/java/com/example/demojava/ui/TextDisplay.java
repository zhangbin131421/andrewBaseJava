package com.example.demojava.ui;

import android.content.Context;
import android.graphics.Outline;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.RelativeSizeSpan;
import android.view.Display;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.demojava.R;
import com.example.demojava.base.BasePresentation;
import com.example.demojava.util.net.ScreenManager;


/**
 * Created by highsixty on 2018/3/23.
 * mail  gaolulin@sunmi.com
 */

public class TextDisplay extends BasePresentation {

    private LinearLayout root;
    private TextView tvTitle;
    private TextView tv;
    private LinearLayout llPresentChoosePayMode;
    private LinearLayout llPresentInfo;
    private TextView tvPaySuccess;
    private TextView paymodeOne;
    private TextView paymodeTwo;
    private TextView paymodeThree;
    private ImageView ivTitle;
    private ProgressBar presentProgress;


    private LinearLayout llPresentPayFail;
    private TextView presentFailOne;
    private TextView presentFailTwo;
    private TextView presentFailThree;
    public int state;

    public TextDisplay(Context outerContext, Display display) {
        super(outerContext, display);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            setContentView(R.layout.vice_text_min_layout);
    }


    public void update(String tip) {

    }



    @Override
    public void show() {
        super.show();

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
    }

    @Override
    public void onSelect(boolean isShow) {
    }
}
