package com.xplmc.selfpics.component;

import android.app.Application;

/**
 * Created by xiaoping on 2015/8/23.
 */
public class MyApplication extends Application{

    @Override
    public void onCreate() {
        super.onCreate();

        //初始化图片列表
        PictureHolder.getInstance().initPictureList(this);
    }
}
