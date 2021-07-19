package com.example.demojava.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.demojava.R;

import java.lang.reflect.Method;

import static android.content.Context.INPUT_METHOD_SERVICE;

public class MKeyboardNumber extends FrameLayout implements View.OnClickListener {

    private EditText editText;

    public MKeyboardNumber(@NonNull Context context) {
        super(context);
        init();
    }

    public MKeyboardNumber(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MKeyboardNumber(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public MKeyboardNumber(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        View.inflate(getContext(), R.layout.view_keyboard_number, this);
        findViewById(R.id.tvNumberDot).setOnClickListener(this);
        findViewById(R.id.tvNumberDel).setOnClickListener(this);
        findViewById(R.id.tvNumber0).setOnClickListener(this);
        findViewById(R.id.tvNumber1).setOnClickListener(this);
        findViewById(R.id.tvNumber2).setOnClickListener(this);
        findViewById(R.id.tvNumber3).setOnClickListener(this);
        findViewById(R.id.tvNumber4).setOnClickListener(this);
        findViewById(R.id.tvNumber5).setOnClickListener(this);
        findViewById(R.id.tvNumber6).setOnClickListener(this);
        findViewById(R.id.tvNumber7).setOnClickListener(this);
        findViewById(R.id.tvNumber8).setOnClickListener(this);
        findViewById(R.id.tvNumber9).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvNumberDot:
                insert(".");
                break;
            case R.id.tvNumberDel:
                delete();
                break;
            case R.id.tvNumber0:
                insert("0");
                break;
            case R.id.tvNumber1:
                insert("1");
                break;
            case R.id.tvNumber2:
                insert("2");
                break;
            case R.id.tvNumber3:
                insert("3");
                break;
            case R.id.tvNumber4:
                insert("4");
                break;
            case R.id.tvNumber5:
                insert("5");
                break;
            case R.id.tvNumber6:
                insert("6");
                break;
            case R.id.tvNumber7:
                insert("7");
                break;
            case R.id.tvNumber8:
                insert("8");
                break;
            case R.id.tvNumber9:
                insert("9");
                break;
        }
    }

    public void setEditText(EditText editText) {
        this.editText = editText;
        hideSoftInput(editText);
    }

    private void insert(String text) {
        if (editText != null) {
            editText.getEditableText().insert(editText.getSelectionStart(), text);
        }
    }

    private void delete() {
        if (editText != null) {
            int start = editText.getSelectionStart();
            if (start > 0) {
                editText.getEditableText().delete(start - 1, start);
            }
        }
    }

    private void hideSoftInput(final EditText editText) {
        if (android.os.Build.VERSION.SDK_INT <= 10) {
//       editText.setInputType(InputType.TYPE_NULL);
            InputMethodManager inputManager = (InputMethodManager) getContext().getSystemService(INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(editText.getWindowToken(), 0);
        } else {
            Class<EditText> cls = EditText.class;
            Method method;
            try {
                method = cls.getMethod("setShowSoftInputOnFocus", boolean.class);
                method.setAccessible(true);
                method.invoke(editText, false);
            } catch (Exception e) {
            }

            try {
                method = cls.getMethod("setSoftInputShownOnFocus", boolean.class);
                method.setAccessible(true);
                method.invoke(editText, false);
            } catch (Exception e) {
            }
        }

    }
}

