package com.bw.com.eventbusstudy.model;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.bw.com.eventbusstudy.okhttputils.OkHttpUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * @author liuruiqi
 * @fileName MainModel
 * @package com.bw.com.eventbusstudy.model
 * @date 2019/3/13 20:32
 **/
public class MainModel {
    public interface onShowLisenter{
        void show(String headPic,String nickName);
    }
    private onShowLisenter showLisenter;

    public void setonShowLisenter (onShowLisenter showLisenter){
        this.showLisenter=showLisenter;
    }


    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
             if (msg.what==0){
                 String json= (String) msg.obj;
                 try {
                     JSONObject jsonObject = new JSONObject(json);
                     JSONObject result = jsonObject.getJSONObject("result");
                     String headPic = result.getString("headPic");
                     String nickName = result.getString("nickName");
                     if (showLisenter!=null){
                         showLisenter.show(headPic,nickName);
                     }
                 } catch (JSONException e) {
                     e.printStackTrace();
                 }
             }
        }
    };



    public void send(HashMap<String,String> parmars) {
        String url="http://172.17.8.100/small/user/verify/v1/getUserById";
      OkHttpUtils.getInstance().doGet(url, new Callback() {
          @Override
          public void onFailure(Call call, IOException e) {

          }

          @Override
          public void onResponse(Call call, Response response) throws IOException {
              String json = response.body().string();
              Log.i("oio",json);
              Message message = new Message();
              message.what=0;
              message.obj=json;

              handler.sendMessage(message);

          }
      },parmars);
    }
}
