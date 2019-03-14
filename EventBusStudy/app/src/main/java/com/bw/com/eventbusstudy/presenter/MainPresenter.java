package com.bw.com.eventbusstudy.presenter;

import com.bw.com.eventbusstudy.activity.MainActivity;
import com.bw.com.eventbusstudy.model.MainModel;
import com.bw.com.eventbusstudy.view.ShowView;

import java.util.HashMap;

/**
 * @author liuruiqi
 * @fileName MainPresenter
 * @package com.bw.com.eventbusstudy.presenter
 * @date 2019/3/13 20:31
 **/
public class MainPresenter {

    private final MainModel mainModel;
    private final ShowView showView;

    public MainPresenter(ShowView view) {
        //实例化
        mainModel = new MainModel();
        showView = view;
    }

    public void send(HashMap<String,String> parmars) {
        mainModel.send(parmars);
        mainModel.setonShowLisenter(new MainModel.onShowLisenter() {
            @Override
            public void show(String headPic, String nickName) {
                showView.show(headPic,nickName);
            }
        });
    }
}
