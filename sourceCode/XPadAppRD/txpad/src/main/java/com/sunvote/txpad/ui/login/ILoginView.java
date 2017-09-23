package com.sunvote.txpad.ui.login;

import com.sunvote.txpad.base.BaseView;

/**
 * Created by Elvis on 2017/9/12.
 * Email:Eluis@psunsky.com
 * 版权所有：长沙中天电子设计开发有限公司
 * Description:平板模块键盘投票功能模块
 */
public interface ILoginView extends BaseView{

    /**
     * 跳转到主页
     */
    public void gotoHomePage();

    /**
     * 显示错误
     */
    void showError();

    /**
     * 用户名为空
     */
    void showUsernameEmpty();

    /**
     * 密码为空
     */
    void showPassowrdEmpty();

    /**
     * 密码错误
     */
    void showPasswordError();
}
