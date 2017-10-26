package com.sunvote.txpad.ui.sign;

import com.sunvote.sunvotesdk.BaseStationManager;
import com.sunvote.sunvotesdk.basestation.IKeyEventCallBack;
import com.sunvote.txpad.base.BaseModel;
import com.sunvote.txpad.bean.ClassStudent;
import com.sunvote.txpad.bean.ResponseDataBean;
import com.sunvote.txpad.bean.Student;
import com.sunvote.util.SPUtils;

import java.util.List;

import cn.sunars.sdk.SunARS;
import retrofit2.http.Body;
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
            BaseStationManager.getInstance().voteStartBackgroundSignIn();
            BaseStationManager.getInstance().setKeyboardUseID();
        }else if(mode == 2){
            BaseStationManager.getInstance().voteStartBackgroundSignIn();
            BaseStationManager.getInstance().setKeyboardUseSN();
        }else{
            BaseStationManager.getInstance().voteStartSign("1");
            BaseStationManager.getInstance().setKeyboardUseSN();
        }
    }

    public void stopSign(int mode){
        BaseStationManager.getInstance().voteStop();
        BaseStationManager.getInstance().voteStopBackgroundSignIn();
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

    public ResponseDataBean<List<Student>> mergeData(ResponseDataBean<List<Student>> cache,ResponseDataBean<List<Student>> newData){
        if(newData == null){
            return cache;
        }

        if(cache == null){
            return newData;
        }

        List<Student> students = newData.getData();
        List<Student> cacheStudents = cache.getData();
        if(students != null && cacheStudents != null){
            for(Student student : students){
                for(Student cstudent:cacheStudents){
                    if(student.equals(cstudent)){
                        student.setKeyBoard(cstudent.getKeyBoard());
                    }
                }
            }
        }
        return newData;
    }

    public Observable<ResponseDataBean<Void>> updateStudent( ClassStudent classStudent){
        return apiService.updateStudent(classStudent.toJson()).compose(BaseModel.<ResponseDataBean<Void>>io_main());
    }
}
