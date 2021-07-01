package com.andrew.java.library.utils.statusbar;

import android.content.Intent;

/**
 * author: zhangbin
 * created on: 2021/6/29 13:54
 * description:
 */
public class AppUtils {

    // 两次点击按钮之间的点击间隔不能少于1000毫秒
    private static final int MIN_CLICK_DELAY_TIME = 1000;
    private static long lastClickTime = 0;

    private static boolean isNormalClick() {
        long curClickTime = System.currentTimeMillis();
        boolean flag = (curClickTime - lastClickTime) > MIN_CLICK_DELAY_TIME;
        lastClickTime = curClickTime;
        return flag;

    }

    private static String mActivityJumpTag = "";

    /**
     * 校验页面多次跳转
     *
     * @param intent
     * @return
     */
    public static boolean checkIntentActivity(Intent intent) {
        boolean result = true;
        String tag = "";
        if (intent.getComponent() != null) {// 显式跳转
            tag = intent.getComponent().getClassName();
        } else if (intent.getAction() != null) {// 隐式跳转
            tag = intent.getAction();
        } else {
            return false;
        }
        if (!isNormalClick() && tag.equals(mActivityJumpTag)) {
            result = false;
        }
        // 记录启动标记和时间
        mActivityJumpTag = tag;
        return result;
    }
}
