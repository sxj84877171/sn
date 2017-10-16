package com.sunvote.txpad.ui.main;

import com.sunvote.txpad.base.BaseView;

/**
 * Created by Elvis on 2017/9/18.
 * Email:Eluis@psunsky.com
 * 版权所有：长沙中天电子设计开发有限公司
 * Description: 易测（ETest）
 */
public interface IHomeActivityView extends BaseView {

    public void showPaperFragment();

    public void showManagerFrament();

    public void showDevideCheckFragment();

    public void showScoreBookFragment();

    public void gotoLoginPage();
}
