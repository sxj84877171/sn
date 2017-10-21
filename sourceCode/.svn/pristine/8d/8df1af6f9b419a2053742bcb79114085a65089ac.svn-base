package com.sunvote.txpad.ui.stuprogress;

import android.os.Handler;

import com.sunvote.sunvotesdk.BaseStationManager;
import com.sunvote.sunvotesdk.basestation.DefaultKeyEventCallBack;
import com.sunvote.txpad.ApplicationData;
import com.sunvote.txpad.ApplicationDataManager;
import com.sunvote.txpad.base.BaseModel;
import com.sunvote.txpad.base.BasePresent;
import com.sunvote.txpad.base.BaseSubscriber;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by Elvis on 2017/9/27.
 * Email:Eluis@psunsky.com
 * 版权所有：长沙中天电子设计开发有限公司
 * Description: 易测（ETest）
 */
public class StudentProgressPresent extends BasePresent<StudentProgressModel,IStudentProgressView> {

    public void init(){
        showInfo();
    }

    public void onResume(){
        BaseStationManager.getInstance().registerKeyEventCallBack(callBack);
    }

    public void onPause(){
        BaseStationManager.getInstance().unRegisterKeyEventCallBack(callBack);
    }


    private DefaultKeyEventCallBack callBack = new DefaultKeyEventCallBack(){
        @Override
        public void keyEventKeyResultInfo(String keyId, String info, float time) {
            Observable.create(new Observable.OnSubscribe<Object>() {

                @Override
                public void call(Subscriber<? super Object> subscriber) {
                    subscriber.onNext(null);
                    subscriber.onCompleted();
                }
            }).compose(BaseModel.io_main()).subscribe(new BaseSubscriber<Object>(){
                @Override
                public void onNext(Object o) {
                    showInfo();
                }
            });
            super.keyEventKeyResultInfo(keyId, info, time);
        }
    };

    public void showInfo() {
        view.showStudent(ApplicationData.getInstance().getStudentList());
        view.showAnswerInfo(ApplicationDataManager.getInstance().getCompeleteExamStudent(),
                ApplicationDataManager.getInstance().getAnsweringExamStudent(),
                ApplicationDataManager.getInstance().getNoanswerExamStudent(),
                ApplicationDataManager.getInstance().getStudentCount() - ApplicationDataManager.getInstance().getSignStudent()
        );
    }
}
