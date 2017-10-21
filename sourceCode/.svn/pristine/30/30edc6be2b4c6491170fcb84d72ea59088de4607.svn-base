package com.sunvote.txpad.ui.login;

import android.text.TextUtils;

import com.sunvote.txpad.ApplicationData;
import com.sunvote.txpad.base.BasePresent;
import com.sunvote.txpad.base.BaseSubscriber;
import com.sunvote.txpad.bean.LoginInfo;
import com.sunvote.txpad.bean.ResponseDataBean;
import com.sunvote.txpad.cache.FileCache;

import java.io.File;

/**
 * Created by Elvis on 2017/9/12.
 * Email:Eluis@psunsky.com
 * 版权所有：长沙中天电子设计开发有限公司
 * Description:平板模块键盘投票功能模块
 */
public class LoginPresent extends BasePresent<LoginModel, ILoginView> {

    /**
     * 登录
     * @param username 用户名
     * @param password 密码
     */
    public void login(final String username, String password) {
        view.showProgress();
        if (checkUsernameAndPassword(username, password)) {
            mRxManager.add(model.login(username, password).subscribe(new BaseSubscriber<ResponseDataBean<LoginInfo>>() {
                @Override
                public void onNext(ResponseDataBean<LoginInfo> loginInfoResponseDataBean) {
                    if (SUCCESS.equals(loginInfoResponseDataBean.getCode())) {
                        ApplicationData.getInstance().setLoginInfo(loginInfoResponseDataBean.getData());
                        FileCache.getFileCache().saveObject("LoginInfo",loginInfoResponseDataBean.getData());
                        model.saveUsernameToLocal(username);
                        view.gotoHomePage();
                    } else {
                        view.showError();
                    }
                    view.dismissProgress();
                }

                @Override
                public void onError(Throwable e) {
                    super.onError(e);
                    view.showNetError();
                    view.dismissProgress();
                }
            }));
        }else{
            view.dismissProgress();
        }
    }

    /**
     * 是否为空
     * @param msg 校验字符串
     * @return 是否为空
     */
    public boolean isEmpty(String msg) {
        return TextUtils.isEmpty(msg);
    }

    /**
     * 校验用户名和密码是否符合要求
     * @param username 用户名
     * @param password 密码
     * @return 是否通过
     */
    public boolean checkUsernameAndPassword(String username, String password) {
        if (isEmpty(username)) {
            view.showUsernameEmpty();
            return false;
        }

        if (isEmpty(password)) {
            view.showPassowrdEmpty();
            return false;
        }

        return true;
    }

    @Override
    public void init() {
        String name = model.getUsernameFromLocal();
        if(!TextUtils.isEmpty(name)){
            view.initUsername(name);
        }
        LoginInfo loginInfo = (LoginInfo) FileCache.getFileCache().readObject("LoginInfo");
        if(loginInfo != null){
            ApplicationData.getInstance().setLoginInfo(loginInfo);
            view.gotoHomePage();
        }
    }
}
