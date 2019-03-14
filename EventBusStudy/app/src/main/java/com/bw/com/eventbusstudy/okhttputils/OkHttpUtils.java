package com.bw.com.eventbusstudy.okhttputils;

import android.text.TextUtils;
import android.util.Log;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * @author liuruiqi
 * @fileName OkHttpUtils
 * @package com.bw.com.eventbusstudy.okhttputils
 * @date 2019/3/13 18:56
 **/
public class OkHttpUtils {
    //单例化
    public static OkHttpUtils okHttpUtils;

    private OkHttpUtils() {
    }
    public static OkHttpUtils getInstance(){
        if (okHttpUtils==null){
            synchronized (OkHttpUtils.class){
                if (okHttpUtils==null){
                    okHttpUtils=new OkHttpUtils();
                }
            }
        }
        return okHttpUtils;
    }

    //声明
private static OkHttpClient okHttpClient;

    public synchronized static OkHttpClient getOkHttpClient(final String sessionId, final String userId){

            //拦截器
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                @Override
                public void log(String message) {
                    Log.i("aaa", message);
                }
            });
            //设置拦截器莫事
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            okHttpClient=new OkHttpClient.Builder().addInterceptor(interceptor).addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {

                    if (!TextUtils.isEmpty(sessionId)&&!TextUtils.isEmpty(userId)){
                        Request request=chain.request().newBuilder()
                                .addHeader("sessionId",sessionId)
                                .addHeader("userId",userId)
                                .build();
                        return chain.proceed(request);
                    }else {
                        Request request = chain.request();
                        return  chain.proceed(request);
                    }
                }
            }).build();

        return okHttpClient;
    }

    //doget
    public static void doGet(String url, Callback callback,Map<String,String> parmars){
        String sessionId = parmars.get("sessionId");
        String userId = parmars.get("userId");
        OkHttpClient okHttpClient = getOkHttpClient(sessionId,userId);
        Request request = new Request.Builder().url(url).build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(callback);
    }

    //dopost
    public static void doPost(String url, Map<String,String> parmars,Callback callback){

        OkHttpClient okHttpClient = getOkHttpClient(null,null);
        FormBody.Builder builder = new FormBody.Builder();
        for (String key:parmars.keySet()){
            builder.add(key,parmars.get(key));
        }
        FormBody formBody = builder.build();
        Request request = new Request.Builder().url(url).post(formBody).build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(callback);
    }

}
