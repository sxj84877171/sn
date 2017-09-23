package com.sunvote.txpad.ui.fragment.dce_check;

import com.sunvote.sunvotesdk.basestation.DefaultKeyEventCallBack;
import com.sunvote.txpad.ApplicationData;
import com.sunvote.txpad.ApplicationDataManager;
import com.sunvote.txpad.base.BaseFragmentPresent;
import com.sunvote.txpad.base.BaseModel;
import com.sunvote.txpad.base.BaseSubscriber;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by Elvis on 2017/9/19.
 * Email:Eluis@psunsky.com
 * 版权所有：长沙中天电子设计开发有限公司
 * Description: 易测（ETest）
 */
public class DeviceCheckFragmentPresent extends BaseFragmentPresent <DeviceCheckFragmentMode,IDeviceCheckFragmentView> {


    private boolean isStartTest ;

    public boolean isStartTest() {
        return isStartTest;
    }

    public void test(){
        if(isStartTest){
            isStartTest = false;
            ApplicationDataManager.getInstance().setOfflineAllKeyboard();
            view.showStartTest();
            model.stopTest(keyEventCallBack);
        }else{
            isStartTest = true;
            view.showStopTest();
            model.startTest(keyEventCallBack);
        }
    }

    private DefaultKeyEventCallBack keyEventCallBack = new DefaultKeyEventCallBack(){

        @Override
        public void keyEventKeyResultStats(final String keyId,final String info,final float time) {
            super.keyEventKeyResultStats(keyId, info, time);
            Observable.create(new Observable.OnSubscribe<Void>() {
                @Override
                public void call(Subscriber<? super Void> subscriber) {
                    subscriber.onNext(null);
                    subscriber.onCompleted();
                }
            }).compose(BaseModel.<Void>io_main()).subscribe(new BaseSubscriber<Void>(){
                @Override
                public void onNext(Void aVoid) {
                    view.showStudents(ApplicationData.getInstance().getStudentList());
                    int online = ApplicationDataManager.getInstance().getOnlineStudentCount();
                    int all = ApplicationDataManager.getInstance().getStudentCount();
                    int weak = ApplicationDataManager.getInstance().getWeakStudentCount();
                    view.showBottomInfo(online,all-online,weak);
                }
            });
        }
    };



}
