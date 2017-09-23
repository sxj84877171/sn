package com.sunvote.txpad.ui.login;

import com.sunvote.txpad.base.BaseModel;
import com.sunvote.txpad.bean.LoginInfo;
import com.sunvote.txpad.bean.ResponseDataBean;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by Elvis on 2017/9/12.
 * Email:Eluis@psunsky.com
 * 版权所有：长沙中天电子设计开发有限公司
 * Description:平板模块键盘投票功能模块
 */
public class LoginModel extends BaseModel {

    /**
     * 登陆， 发起网络请求任务
     * @param username 用户名
     * @param password 密码
     * @return 任务
     */
    public Observable<ResponseDataBean<LoginInfo>> login(String username,String password){
        return apiService.login(username,password).compose(BaseModel.<ResponseDataBean<LoginInfo>>io_main());
    }

}
