package com.andrew.java.library.utils;

import android.os.Environment;
import android.text.Html;
import android.text.Spanned;

public interface ConstantsUtil {
    String TOKEN = "token";
    String USER_ID = "userId";
    String ORGANIZED_OR_NOT = "organizedOrNot";
    String CURRENT_COMPANY_ID = "CurrentCompanyId";
    String AVATAR = "avatar";
    String MOBILE = "mobile";
    String EMAIL = "email";
    String ADDRESS = "address";
    String USER_INFO = "userInfo";
    String ALIAS = "alias";//推送别名
    String Font_SIZE = "fontSize";
    String Font_Multiple = "fontMultiple";
    int REQUEST_CODE_REFRESH = 99;
    int REQUEST_CODE_100 = 100;
    int REQUEST_CODE_101 = 101;
    int REQUEST_CODE_102 = 102;
    int REQUEST_CODE_103 = 103;
    int REQUEST_CODE_104 = 104;
    int REQUEST_CODE_105 = 105;
    int REQUEST_CODE_106 = 106;
    int REQUEST_CODE_SEX = 201;
    int REQUEST_CODE_EMAIL = 202;
    int REQUEST_CODE_ADDRESS = 203;
    int REQUEST_CODE_VIDEO = 400;
    int REQUEST_CODE_IMAGE = 401;
    int REQUEST_CODE_FILE = 402;
    int REQUEST_CODE_CAMERA = 500;
    int REQUEST_CODE_ALBUM = 501;
    int REQUEST_CODE_PICTURE_CROP = 502;

    int IMAGE_TYPE_DEFAULT = 600;//普通图片
    int IMAGE_TYPE_IDENTITY = 601;//身份证
    int IMAGE_TYPE_BUSINESS_CERTIFICATE = 602;//营业执照


    int PAGE_SIZE = 20;

    String INTENT_KEY1 = "ik1";
    String INTENT_KEY2 = "ik2";
    String INTENT_KEY3 = "ik3";
    String INTENT_KEY4 = "ik4";
    String INTENT_KEY_TYPE = "type";
    String INTENT_KEY_STATUS = "authStatus";
    String INTENT_PARENT_ID = "parentId";
    String INTENT_PHONE = "phone";

    String INTENT_TITLE = "title";//标题
    String INTENT_TITLE_RIGHT_NAME = "titleRightName";//标题右侧文本内容
    String INTENT_TITLE_RIGHT_URL = "titleRightUrl";//标题右侧文本内容
    String INTENT_URL = "url";//

    String SDCARD_ROOT = Environment.getExternalStorageDirectory().getAbsolutePath();
    String SDCARD_TS = SDCARD_ROOT + "/task/";
    String SDCARD_APP_ALBUM = SDCARD_TS + "album/";
    String SDCARD_CROP = SDCARD_TS + "crop/";
    String SDCARD_AUDIO = SDCARD_TS + "Audio";



}
