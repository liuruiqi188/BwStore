package com.bw.com.eventbusstudy.presenter;

import com.bw.com.eventbusstudy.activity.LoginActivity;
import com.bw.com.eventbusstudy.model.LoginModel;
import com.bw.com.eventbusstudy.view.LoginView;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author liuruiqi
 * @fileName LoginPresenter
 * @package com.bw.com.eventbusstudy.presenter
 * @date 2019/3/13 18:54
 **/
public class LoginPresenter {

    private final LoginModel loginModel;
    private final LoginView loginView;

    public LoginPresenter(LoginView view) {
        //实例化
        loginModel = new LoginModel();
        loginView = view;
    }

    public void send(String phone, String pwd) {

        //联系M层
        loginModel.send(phone,pwd);
        loginModel.setonLoginLisenter(new LoginModel.onLoginLisenter() {
            @Override
            public void login(String status) {
                loginView.Login(status);
            }
        });

    }
}
