package com.sunvote.txpad.ui.sign;

import android.content.Context;

import com.sunvote.sunvotesdk.BaseStationManager;
import com.sunvote.sunvotesdk.basestation.IKeyEventCallBack;
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
import com.sunvote.txpad.cache.SpCache;
import com.sunvote.util.LogUtil;
import com.sunvote.util.SPUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.http.Body;
import rx.Observable;
import rx.Subscriber;

/**
 * Created by Elvis on 2017/9/13.
 * Email:Eluis@psunsky.com
 * 版权所有：长沙中天电子设计开发有限公司
 * Description:易测（ETest）
 */
public class SignPresent extends BasePresent<SignModel, ISignView> {

    private Paper paper;

    private boolean isSign = false;

    private boolean isExam = false;

    private boolean isReplace = false;


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
        if (students != null && students.size() > 0) {
            view.displayStudents(students);
        } else {
            if (classStudent != null) {
                ResponseDataBean<List<Student>> cache = (ResponseDataBean<List<Student>>) FileCache.getFileCache().readEncryptObject("getStudentList&" + classStudent.getClassId());
                if (cache != null) {
                    List<Student> studentList = cache.getData();
                    if (studentList != null) {
                        ApplicationData.getInstance().setStudentList(studentList);
                        view.displayStudents(studentList);
                    }else{
                        getClassStudents(classStudent);
                    }
                } else {
                    getClassStudents(classStudent);
                }
            }
        }
    }

    private void getClassStudents(final ClassStudent classStudent) {
        view.showProgress();
        mRxManager.add(model.getStudentList(classStudent.getClassId()).subscribe(new BaseSubscriber<ResponseDataBean<List<Student>>>() {
            @Override
            public void onNext(ResponseDataBean<List<Student>> listResponseDataBean) {
                List<Student> studentList = listResponseDataBean.getData();
                if (studentList != null) {
                    ResponseDataBean<List<Student>> cache = (ResponseDataBean<List<Student>>) FileCache.getFileCache().readEncryptObject("getStudentList&" + classStudent.getClassId());
                    listResponseDataBean = model.mergeData(cache,listResponseDataBean);
                    ApplicationData.getInstance().setStudentList(listResponseDataBean.getData());
                    view.displayStudents(studentList);
                }
                view.dismissProgress();
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                view.showNetError();
                view.dismissProgress();
            }
        }));
    }

    /**
     * 查询基站工作模式，初始化签到按钮
     * 首先查询当前签到模式
     * 如果1-1绑定模式，查询是否开启，开启则需要关闭后台签到模式，并初始化界面
     * 如果是后台签到模式，则需要关闭1-1签到模式，并初始化界面
     *
     * @param context
     */
    public void queryState(Context context) {
        int mode = SPUtils.getInt(context, Constants.SAVE_SIGN_MODE_KEY);
        if (mode == 1) {
            isSign = model.isSignMode();
            if (isSign) {
                view.startSign();
            } else {
                view.stopSign();
            }
            if (model.isSignBackground()) {
                model.stopSign(2);
            }
        } else {
            isSign = model.isSignMode();
            if (isSign) {
                model.stopSign(1);
            }
            isSign = model.isSignBackground();
            if (isSign) {
                view.startSign();
            } else {
                view.stopSign();
            }
        }

    }

    public void sign(Context context) {
        int mode = SpCache.getInstance().getInt(Constants.SAVE_SIGN_MODE_KEY, 1);
        if (!isSign) {
            isSign = true;
            ApplicationDataManager.getInstance().clearSign();
            refresh();
            view.startSign();
            showSignMode();
            model.startSign(mode);
        } else {
            isSign = false;
            view.stopSign();
            model.stopSign(mode);
        }
    }

    public boolean isSign() {
        return isSign;
    }

    public void exam() {
        if (!isTimeOK()) {
            view.showNoTimeDialog();
            return;
        }

        if (!isExam) {
            if(ApplicationDataManager.getInstance().getBlindKeyboardCount(getMode()) == 0){
                view.showNoSignDialog();
                return;
            }
            isExam = true;
            isSign = false;
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
        int conflict = ApplicationDataManager.getInstance().getKeyboardConflict();
        view.showSignedStudent(signed, all);
        view.showSignNum(signed, all - signed, conflict);
    }

    @Override
    public void onDestroy() {
        BaseStationManager.getInstance().unRegisterKeyEventCallBack(keyEventCallBack);
    }

    @Override
    public void onCreate(Context context) {
        BaseStationManager.getInstance().registerKeyEventCallBack(keyEventCallBack);
    }

    public boolean isTimeOK() {
        int times = SpCache.getInstance().getInt(Constants.SAVE_EXAM_TIME_KEY, 45);
        return times > 0;
    }

    public void showSignMode(){
        int mode = SpCache.getInstance().getInt(Constants.SAVE_SIGN_MODE_KEY,1);
//        view.showSignMode(mode);
    }

    public int  getMode(){
        return SpCache.getInstance().getInt(Constants.SAVE_SIGN_MODE_KEY,1);
    }

    public void changeReplaceFunction(){
        isReplace = !isReplace;
        view.showReplaceFunction(getMode(),isReplace);
    }

    public void updateStudent(Student student){
        ClassStudent classStudent = new ClassStudent();
        classStudent.setClassId(ApplicationData.getInstance().getClassStudent().getClassId());
        List<Student> studentList = new ArrayList<>();
        studentList.add(student);
        classStudent.setStudentList(studentList);
        model.updateStudent(classStudent).subscribe(new BaseSubscriber<ResponseDataBean<Void>>(){
            @Override
            public void onNext(ResponseDataBean<Void> voidResponseDataBean) {
                super.onNext(voidResponseDataBean);
                if("0".equals(voidResponseDataBean.getCode())){

                }else{
                    onError(new Exception());
                }
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                view.showNetError();
            }
        });
    }
}
