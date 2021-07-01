package com.example.demojava.base;

import com.andrew.java.library.vmodel.AndrewLoadingViewModel;
import com.example.demojava.net.API;

/**
 * author: zhangbin
 * created on: 2021/7/1 15:52
 * description:
 */
public class BaseViewModel extends AndrewLoadingViewModel {
    public  API api = API.Builder.getDefaultService();
}
