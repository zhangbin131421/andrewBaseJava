package com.example.demojava.widget;

import android.content.Context;
import android.text.InputType;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.orhanobut.logger.Logger;

/**
 * author: zhangbin
 * created on: 2021/7/19 14:00
 * description:
 */
public class MEditText extends androidx.appcompat.widget.AppCompatEditText {


    public MEditText(@NonNull Context context) {
        super(context);
    }

    public MEditText(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MEditText(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        Logger.e("  onTouchEvent(MotionEvent event) -------------------------------");
//        int inType = this.getInputType(); // backup the input type
//        this.setInputType(InputType.TYPE_NULL); // disable soft input
//        this.onTouchEvent(event); // call native handler
//        this.setInputType(inType); // restore input type
//        return super.onTouchEvent(event);
//    }
}
