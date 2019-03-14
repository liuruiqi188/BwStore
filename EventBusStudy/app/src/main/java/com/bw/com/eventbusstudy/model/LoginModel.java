package com.bw.com.eventbusstudy.model;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.bw.com.eventbusstudy.okhttputils.OkHttpUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * @author liuruiqi
 * @fileName LoginModel
 * @package com.bw.com.eventbusstudy.model
 * @date 2019/3/13 18:55
 **/
public class LoginModel {

    public interface onLoginLisenter{
        void login(String status);
    }
    private onLoginLisenter loginLisenter;

    public void setonLoginLisenter(onLoginLisenter loginLisenter){
        this.loginLisenter=loginLisenter;
    }
    private HashMap<String,String> parmars=new HashMap<>();


private Handler handler=new Handler(){
    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        if (msg.what==0){
            String json= (String) msg.obj;
                    loginLisenter.login(json);

        }
    }
};



    public void send(String phone, String pwd) {
        String url="http://172.17.8.100/small/user/v1/login";
        parmars.put("phone",phone);
        parmars.put("pwd",pwd);
        OkHttpUtils.getInstance().doPost(url, parmars, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String json = response.body().string();
                Log.i("zzz",json);
                Message message = new Message();
                message.what=0;
                message.obj=json;
                handler.sendMessage(message);

            }
        });
    }
}
