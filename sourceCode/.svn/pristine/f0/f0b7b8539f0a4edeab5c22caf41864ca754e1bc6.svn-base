package com.sunvote.txpad.ui.manager;

import com.sunvote.sunvotesdk.basestation.BaseStationInfo;
import com.sunvote.txpad.base.BasePresent;
import com.sunvote.txpad.base.BaseSubscriber;

/**
 * Created by Elvis on 2017/9/14.
 * Email:Eluis@psunsky.com
 * 版权所有：长沙中天电子设计开发有限公司
 * Description: 易测（ETest）
 */
public class BaseStationManagerPresent extends BasePresent<BaseStationManagerModel,IBaseStationManagerView> {

    public void initBaseStation(){
        model.initBaseStation().subscribe(new BaseSubscriber<BaseStationInfo>(){
            @Override
            public void onNext(BaseStationInfo baseStationInfo) {
                super.onNext(baseStationInfo);
            }
        });
    }
}
