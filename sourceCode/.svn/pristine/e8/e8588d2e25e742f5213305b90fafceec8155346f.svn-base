package com.sunvote.txpad.ui.sign;

import com.sunvote.sunvotesdk.BaseStationManager;
import com.sunvote.sunvotesdk.basestation.IKeyEventCallBack;
import com.sunvote.txpad.base.BaseModel;
import com.sunvote.txpad.bean.ResponseDataBean;
import com.sunvote.txpad.bean.Student;
import com.sunvote.util.SPUtils;

import java.util.List;

import cn.sunars.sdk.SunARS;
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

    public void startSign(int mode){
        if(mode == 1){
            BaseStationManager.getInstance().voteStartSign("" + mode);
        }else if(mode == 2){
            BaseStationManager.getInstance().voteStartBackgroundSignIn();
        }
    }

    public void stopSign(int mode){
        if(mode == 1){
            BaseStationManager.getInstance().voteStop();
        }else{
            BaseStationManager.getInstance().voteStopBackgroundSignIn();
        }
    }

    public void startExam(){

    }

    public void stopExam(){

    }

    public boolean isSignMode(){
        if(BaseStationManager.getInstance().getBaseStationInfo().getMode() == SunARS.VoteType_Signin){
            return true;
        }
        return false;
    }

    public boolean isSignBackground(){
        if(BaseStationManager.getInstance().getBaseStationInfo().isBackgroundSign()){
            return true;
        }
        return false;
    }

}
