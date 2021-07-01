package com.andrew.java.library.observer;

import android.content.Context;

import androidx.lifecycle.Observer;

import com.andrew.java.library.widget.loading.KProgressHUD;

/**
 * author: zhangbin
 * created on: 2021/6/29 11:48
 * description:
 */
public class LoadingObserver implements Observer<Boolean> {
    private KProgressHUD dialog;

    public LoadingObserver(Context context) {
        dialog = new KProgressHUD(context).setStyle(KProgressHUD.Style.SPIN_INDETERMINATE).setCancellable(false).setAnimationSpeed(2).setDimAmount(0.5f);
    }

    @Override
    public void onChanged(Boolean show) {
        if (show == null)
            return;
        if (show) {
            dialog.show();
        } else {
            dialog.dismiss();
        }
    }
}
