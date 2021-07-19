package com.example.demojava.ui.fragment;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import androidx.fragment.app.DialogFragment;

import com.example.demojava.R;

/**
 * author: zhangbin
 * created on: 2021/7/19 19:59
 * description:
 */
public class DemoFragmentDialog extends DialogFragment {


    /**
     * The system calls this to get the DialogFragment's layout, regardless
     * of whether it's being displayed as a dialog or an embedded fragment.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout to use as dialog or embedded fragment
        return inflater.inflate(R.layout.dialog_demo, container, false);
    }

    /**
     * The system calls this only when creating the layout in a dialog.
     */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // The only reason you might override this method when using onCreateView() is
        // to modify any dialog characteristics. For example, the dialog includes a
        // title by default, but your custom layout might not need it. So here you can
        // remove the dialog title, but you must call the superclass to get the Dialog.
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

//    @Override
//    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
//        EditText editPassword = view.findViewById(R.id.edit_password);
//        editPassword.requestFocus();//设置焦点
//        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);//设置输入盘可见
//        super.onViewCreated(view, savedInstanceState);
//    }

    @Override
    public void onStart() {
        /*
            因为View在添加后,对话框最外层的ViewGroup并不知道我们导入的View所需要的的宽度。 所以我们需要在onStart生命周期里修改对话框尺寸参数
         */
        WindowManager.LayoutParams params = getDialog().getWindow().getAttributes();
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
//        params.gravity = Gravity.BOTTOM;
//        params.dimAmount = 0f;//调整透明度
        getDialog().getWindow().setAttributes(params);
//        getDialog().getWindow().getDecorView().setBackground(new ColorDrawable(Color.TRANSPARENT));
        super.onStart();
    }
}
