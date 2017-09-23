package com.sunvote.txpad.ui.sign;

import com.sunvote.sunvotesdk.BaseStationManager;
import com.sunvote.sunvotesdk.basestation.IKeyEventCallBack;
import com.sunvote.txpad.base.BaseModel;
import com.sunvote.txpad.bean.ResponseDataBean;
import com.sunvote.txpad.bean.Student;
import com.sunvote.util.SPUtils;

import java.util.List;

import rx.Observable;

/**
 * Created by Elvis on 2017/9/13.
 * Email:Eluis@psunsky.com
 * 版权所有：长沙中天电子设计开发有限公司
 * Description:易测（ETest）
 */
public class SignModel extends BaseModel {


    public Observable<ResponseDataBean<List<Student>>> getStudentList(String classId){
        return apiService.getStudentList(classId).compose(BaseModel.<ResponseDataBean<List<Student>>>io_main());
    }

    public void startSign(IKeyEventCallBack keyEventCallBack,int mode){
        BaseStationManager.getInstance().registerKeyEventCallBack(keyEventCallBack);
        if(mode == 1){
            BaseStationManager.getInstance().voteStartSign("" + mode);
        }else if(mode == 2){
            BaseStationManager.getInstance().voteStartBackgroundSignIn();
        }
    }

    public void stopSign(IKeyEventCallBack keyEventCallBack){
        BaseStationManager.getInstance().unRegisterKeyEventCallBack(keyEventCallBack);
        BaseStationManager.getInstance().voteStop();
        BaseStationManager.getInstance().voteStopBackgroundSignIn();
    }

    public void startExam(){

    }

    public void stopExam(){

    }

}
