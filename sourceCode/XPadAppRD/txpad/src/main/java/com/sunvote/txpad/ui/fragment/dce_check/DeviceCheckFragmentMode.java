package com.sunvote.txpad.ui.fragment.dce_check;

import com.sunvote.sunvotesdk.BaseStationManager;
import com.sunvote.sunvotesdk.basestation.IKeyEventCallBack;
import com.sunvote.txpad.base.BaseFragementModel;

/**
 * Created by Elvis on 2017/9/19.
 * Email:Eluis@psunsky.com
 * 版权所有：长沙中天电子设计开发有限公司
 * Description: 易测（ETest）
 */
public class DeviceCheckFragmentMode extends BaseFragementModel {

    public void startTest(IKeyEventCallBack keyEventCallBack){
        BaseStationManager.getInstance().registerKeyEventCallBack(keyEventCallBack);
        BaseStationManager.getInstance().voteStartKeyPadTest();
    }

    public void stopTest(IKeyEventCallBack keyEventCallBack){
        BaseStationManager.getInstance().unRegisterKeyEventCallBack(keyEventCallBack);
        BaseStationManager.getInstance().voteStop();
    }
}
