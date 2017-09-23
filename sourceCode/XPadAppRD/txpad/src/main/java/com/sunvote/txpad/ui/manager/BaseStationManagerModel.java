package com.sunvote.txpad.ui.manager;

import com.sunvote.sunvotesdk.BaseStationManager;
import com.sunvote.sunvotesdk.basestation.BaseStationInfo;
import com.sunvote.txpad.base.BaseModel;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by Elvis on 2017/9/14.
 * Email:Eluis@psunsky.com
 * 版权所有：长沙中天电子设计开发有限公司
 * Description: 易测（ETest）
 */
public class BaseStationManagerModel extends BaseModel {

    public Observable<BaseStationInfo> initBaseStation(){
        return  Observable.create(new Observable.OnSubscribe<BaseStationInfo>() {
            @Override
            public void call(Subscriber<? super BaseStationInfo> subscriber) {
                BaseStationManager.getInstance().init();
                subscriber.onNext(BaseStationManager.getInstance().getBaseStationInfo());
                subscriber.onCompleted();
            }
        }).compose(BaseModel.<BaseStationInfo>io_main());

    }
}
