package com.bw.com.eventbusstudy.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.com.eventbusstudy.R;
import com.bw.com.eventbusstudy.presenter.MainPresenter;
import com.bw.com.eventbusstudy.view.ShowView;
import com.facebook.drawee.view.SimpleDraweeView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import org.json.JSONException;
import org.json.JSONObject;


import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity  implements ShowView {

    @BindView(R.id.img)
    SimpleDraweeView img;
    @BindView(R.id.title)
    TextView title;
    private MainPresenter mainPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

          //注册时间
        EventBus.getDefault().register(this);



        //点击事件
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);

            }
        });


        //P
        mainPresenter = new MainPresenter(this);


    }

    //接收数据
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getData(String s){
        Log.i("uuu",s);
        try {

            JSONObject jsonObject = new JSONObject(s);
            JSONObject result = jsonObject.getJSONObject("result");
            String sessionId = result.getString("sessionId");
            String userId = result.getString("userId");
            //新建集合
            HashMap<String, String> parmars = new HashMap<>();
            parmars.put("sessionId",sessionId);
            parmars.put("userId",userId);
            //联系
            mainPresenter.send(parmars);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void show(String headPic, String nickName) {
        Log.i("rrr",nickName);
        Toast.makeText(this,nickName , Toast.LENGTH_SHORT).show();
        title.setText(nickName);
        Uri parse = Uri.parse(headPic);
        img.setImageURI(parse);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
