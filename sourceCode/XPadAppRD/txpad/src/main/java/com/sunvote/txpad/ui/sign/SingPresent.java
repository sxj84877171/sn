package com.sunvote.txpad.ui.sign;

import android.content.Context;

import com.sunvote.sunvotesdk.basestation.IKeyEventCallBack;
import com.sunvote.sunvotesdk.basestation.KeyBoard;
import com.sunvote.txpad.ApplicationData;
import com.sunvote.txpad.ApplicationDataManager;
import com.sunvote.txpad.Constants;
import com.sunvote.txpad.base.BaseModel;
import com.sunvote.txpad.base.BasePresent;
import com.sunvote.txpad.base.BaseSubscriber;
import com.sunvote.txpad.bean.ClassStudent;
import com.sunvote.txpad.bean.Paper;
import com.sunvote.txpad.bean.ResponseDataBean;
import com.sunvote.txpad.bean.Student;
import com.sunvote.txpad.cache.FileCache;
import com.sunvote.util.LogUtil;
import com.sunvote.util.SPUtils;

import java.util.List;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by Elvis on 2017/9/13.
 * Email:Eluis@psunsky.com
 * 版权所有：长沙中天电子设计开发有限公司
 * Description:易测（ETest）
 */
public class SingPresent extends BasePresent<SignModel, ISignView> {

    private Paper paper;

    private boolean isSign = false;

    private boolean isExam = false;

    public void setPaper(Paper paper) {
        this.paper = paper;
    }

    @Override
    public void init() {
        ClassStudent classStudent = ApplicationData.getInstance().getClassStudent();
        if (classStudent != null && paper != null) {
            view.initViewData(classStudent, paper);
        }

        List<Student> students = ApplicationData.getInstance().getStudentList();
        if (students != null) {
            view.displayStudents(students);
        }
    }

    public void sign(Context context) {
        int mode = SPUtils.getInt(context, Constants.SAVE_SIGN_MODE_KEY);
        if (!isSign) {
            isSign = true;
            ApplicationDataManager.getInstance().clearSign();
            refresh();
            view.startSign();
            model.startSign(keyEventCallBack, mode);
        } else {
            isSign = false;
            view.stopSign();
            model.stopSign(keyEventCallBack);
        }
    }

    public boolean isSign() {
        return isSign;
    }

    public void exam() {
        if (!isExam) {
            isExam = true;
            view.startExam();
            model.startExam();
        } else {
            isExam = false;
            view.stopExam();
            model.stopExam();
        }
    }

    private IKeyEventCallBack keyEventCallBack = new IKeyEventCallBack() {
        @Override
        public void keyEventKeyResultInfo(final String keyId, final String info, final float time) {
            Observable.create(new Observable.OnSubscribe<Void>() {
                @Override
                public void call(Subscriber<? super Void> subscriber) {
                    subscriber.onNext(null);
                    subscriber.onCompleted();
                }
            }).compose(BaseModel.<Void>io_main()).subscribe(new BaseSubscriber<Void>() {
                @Override
                public void onNext(Void aVoid) {
                    refresh();
                }
            });

        }

        @Override
        public void keyEventKeyResultStats(String keyId, String info, float time) {
            LogUtil.i("SigePresent", "keyId:" + keyId + ",info:" + info + ",time:" + time);
        }

        @Override
        public void keyEventKeyResultLoginInfo(final String keyId, final String info, float time) {
            Observable.create(new Observable.OnSubscribe<Void>() {
                @Override
                public void call(Subscriber<? super Void> subscriber) {
                    subscriber.onNext(null);
                    subscriber.onCompleted();
                }
            }).compose(BaseModel.<Void>io_main()).subscribe(new BaseSubscriber<Void>() {
                @Override
                public void onNext(Void aVoid) {
                    refresh();
                }
            });
        }

        @Override
        public void keyEventKeyResultRemoteControlAnswer(String keyId, String info, float time) {
            LogUtil.i("SigePresent", "keyId:" + keyId + ",info:" + info + ",time:" + time);
        }

        @Override
        public void keyEventKeyResultMatch(String keyId, String info, float time) {
            LogUtil.i("SigePresent", "keyId:" + keyId + ",info:" + info + ",time:" + time);
        }
    };

    private void refresh() {
        view.displayStudents(ApplicationData.getInstance().getStudentList());
        int signed = ApplicationDataManager.getInstance().getSignStudent();
        int weak = ApplicationDataManager.getInstance().getKeyboarWeak();
        int all = ApplicationDataManager.getInstance().getStudentCount();
        view.showSignedStudent(signed, all);
        view.showSignNum(signed, all - signed, weak);
    }
}
