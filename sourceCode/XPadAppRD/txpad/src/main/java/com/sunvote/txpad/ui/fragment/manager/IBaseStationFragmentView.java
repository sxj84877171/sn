package com.sunvote.txpad.ui.fragment.manager;

import com.sunvote.txpad.base.BaseFragmentView;

/**
 * Created by Elvis on 2017/9/18.
 * Email:Eluis@psunsky.com
 * 版权所有：长沙中天电子设计开发有限公司
 * Description: 易测（ETest）
 */
public interface IBaseStationFragmentView extends BaseFragmentView {

    public void showKeyPadWorkingMode(String mode);

    public void showKeyPadModel(String mode);

    public void showLanguage(String language);

    public void showCh(String ch);

    public void showCheckFragment();

    public void refeash();
}
