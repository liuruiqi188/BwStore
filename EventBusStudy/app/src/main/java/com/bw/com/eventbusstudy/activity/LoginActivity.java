package com.bw.com.eventbusstudy.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bw.com.eventbusstudy.R;
import com.bw.com.eventbusstudy.presenter.LoginPresenter;
import com.bw.com.eventbusstudy.view.LoginView;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity implements LoginView {

    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_pwd)
    EditText etPwd;
    @BindView(R.id.login)
    Button login;
    private LoginPresenter loginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        //实例化
        loginPresenter = new LoginPresenter(this);


        //登录点击事件
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取账号密码
                String phone = etPhone.getText().toString();
                String pwd = etPwd.getText().toString();
                //非空判断
                if (phone.equals("")||pwd.equals("")){
                    Toast.makeText(LoginActivity.this, "账号或者密码不能为空！", Toast.LENGTH_SHORT).show();
                    return;
                }

                //P层
                loginPresenter.send(phone,pwd);
            }
        });


    }


    @Override
    public void Login(String s) {
        try {
            JSONObject jsonObject=new JSONObject(s);
            String status = jsonObject.getString("status");
            if (status.equals("0000")){

                EventBus.getDefault().post(s);
                finish();
            }else {
                Toast.makeText(this, "账号或密码错误，请重新登陆", Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
