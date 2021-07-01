package com.andrew.java.library.widget.loading.views;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;


import com.andrew.java.library.R;
import com.andrew.java.library.widget.loading.Determinate;
import com.andrew.java.library.widget.loading.Indeterminate;

import java.text.DecimalFormat;

public class SpinProgressView extends FrameLayout implements Determinate, Indeterminate {
    private SpinView spinView;
    private TextView progressTv;
    private long max = 0;
    private DecimalFormat df = new DecimalFormat("0.00%");//格式化小数


    public SpinProgressView(@NonNull Context context) {
        super(context);
        View inflate = LayoutInflater.from(context).inflate(R.layout.dialog_loading_progress, this);
        spinView = inflate.findViewById(R.id.spin_view);
        progressTv = inflate.findViewById(R.id.tv_progress);
    }


    @Override
    public void setMax(long max) {
        this.max = max;
    }

    @Override
    public void setProgress(long progress) {
        if (max == 0) {
            return;
        }
        if (progressTv != null) {
            progressTv.setText(df.format(progress / max));
        }
    }

    @Override
    public void setAnimationSpeed(float scale) {
        if (spinView != null) {
            spinView.setAnimationSpeed(scale);
        }
    }
}
