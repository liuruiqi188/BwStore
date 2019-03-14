package com.bw.com.eventbusstudy.app;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * @author liuruiqi
 * @fileName MyApplication
 * @package com.bw.com.eventbusstudy.app
 * @date 2019/3/13 16:53
 **/
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
    }
}
