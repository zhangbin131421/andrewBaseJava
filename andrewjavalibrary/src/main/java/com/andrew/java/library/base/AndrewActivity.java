package com.andrew.java.library.base;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.MutableLiveData;

import com.andrew.java.library.R;
import com.andrew.java.library.observer.LoadingObserver;
import com.andrew.java.library.utils.statusbar.AppUtils;
import com.andrew.java.library.utils.statusbar.StatusBarUtil;

/**
 * author: zhangbin
 * created on: 2021/6/29 11:31
 * description:
 */
public abstract class AndrewActivity extends AppCompatActivity {
    protected abstract int layoutId();

    boolean immersiveStatusBar;
    int statusBarColor = R.color.theme_color;
    boolean fontIconDark;
    MutableLiveData<Boolean> loadingState = new MutableLiveData<>();

    String[] permissions;
    int requestCode = -100;
    String description = "";
    boolean activityRequestPermission;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (immersiveStatusBar) {
            StatusBarUtil.setTranslucentStatus(this);
        } else {
            StatusBarUtil.setStatusBarColor(this, ContextCompat.getColor(this, statusBarColor));
            StatusBarUtil.setImmersiveStatusBar(this, fontIconDark);
        }
        setContentView(layoutId());
        loadingState.observe(this, new LoadingObserver(this));
    }

    private boolean checkDangerousPermissions(String[] permissions) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED
                    || ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
                return false;
            }
        }
        return true;
    }

    public void handlePermissionResult(int requestCode, boolean success) {
    }

    public void requestDangerousPermissions(String[] permissions, int requestCode, String permissionsDescription) {
        this.permissions = permissions;
        this.requestCode = requestCode;
        this.description = permissionsDescription;
        if (checkDangerousPermissions(permissions)) {
            handlePermissionResult(requestCode, true);
            return;
        }
//        ActivityCompat.requestPermissions(this, permissions, requestCode)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            activityRequestPermission = true;
            this.requestPermissions(permissions, requestCode);
        }
    }

    @Override
    public void onRequestPermissionsResult(final int requestCode, @NonNull final String[] permissions, @NonNull int[] grantResults) {
        if (activityRequestPermission) {
            boolean granted = true;
            boolean deniedNoHit = false;//权限被禁用且不再提示
//        应用第一次安装，并且权限被禁用时，返回true
//        权限被禁用时，返回true
//        权限被禁用且不再提示时，返回false
//        已授权时返回false
            for (int i = 0; i < grantResults.length; i++) {
                if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                    granted = false;
                    if (!ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[i])) {// 权限被禁用且不再提示时，返回false
                        deniedNoHit = true;
                    }
                }
            }
            if (granted) {
                handlePermissionResult(requestCode, true);
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(this)
                        .setTitle("权限")
                        .setMessage("应用需要" + description + "权限，不授权将无法正常工作！")
                        .setNegativeButton("拒绝", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                handlePermissionResult(requestCode, false);
                            }
                        });

                if (deniedNoHit) { //勾选不再提醒/禁止后不再提示
                    builder.setPositiveButton("去授权", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                            Uri uri = Uri.fromParts("package", getPackageName(), null);//注意就是"package",不用改成自己的包名
                            intent.setData(uri);
                            startActivityForResult(intent, requestCode);
                        }
                    });

                } else {   //选择禁止
                    builder.setPositiveButton("去授权", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            requestDangerousPermissions(permissions, requestCode, description);
                        }
                    }).create();

                }
                AlertDialog alertDialog = builder.create();
                alertDialog.setCancelable(false);
                alertDialog.setCanceledOnTouchOutside(false);
                alertDialog.show();
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
        activityRequestPermission = false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == this.requestCode) {
            requestDangerousPermissions(this.permissions, this.requestCode, description);
            this.permissions = null;
        }
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        if (AppUtils.checkIntentActivity(intent)) {
            super.startActivityForResult(intent, requestCode);
        }
    }

    protected void toggleSoftInput() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    protected void openUrlInBrowser(String url) {
        Uri uri = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

    protected void showLongToast(String text) {
        Toast.makeText(getApplicationContext(), text, Toast.LENGTH_LONG).show();
    }

    protected void showShortToast(String text) {
        Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
    }

    protected void showLongToast(int stringRes) {
        showLongToast(getApplicationContext().getString(stringRes));
    }

    protected void showShortToast(int stringRes) {
        showShortToast(getApplicationContext().getString(stringRes));
    }
}
