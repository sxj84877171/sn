package com.sunvote.txpad.ui.fragment.manager;

import com.sunvote.txpad.base.BaseFragmentPresent;
import com.sunvote.txpad.base.BaseSubscriber;

/**
 * Created by Elvis on 2017/9/18.
 * Email:Eluis@psunsky.com
 * 版权所有：长沙中天电子设计开发有限公司
 * Description: 易测（ETest）
 */
public class BaseStationFragmentPresent extends BaseFragmentPresent<BaseStationFragmentModel,IBaseStationFragmentView> {

    public void init(){
        view.showKeyPadWorkingMode(model.getBaseStationMode());
        view.showKeyPadModel(model.getKeyPadMode());
        view.showCh(model.getChMsg());
    }

    public void setFreeMode(){
        view.showProgress();
        model.writeMode(2).subscribe(new BaseSubscriber<String>(){
            @Override
            public void onNext(String s) {
                view.showKeyPadWorkingMode(s);
                view.dismissProgress();
            }
        });
    }

    public void setFixedMode(){
        view.showProgress();
        model.writeMode(1).subscribe(new BaseSubscriber<String>(){
            @Override
            public void onNext(String s) {
                view.showKeyPadWorkingMode(s);
                view.dismissProgress();
            }
        });
    }

    public void deviceCheck(){
        view.showCheckFragment();
    }
}
