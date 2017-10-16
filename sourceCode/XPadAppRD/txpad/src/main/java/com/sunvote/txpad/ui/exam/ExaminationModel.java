package com.sunvote.txpad.ui.exam;

import android.content.Context;

import com.sunvote.sunvotesdk.BaseStationManager;
import com.sunvote.txpad.Constants;
import com.sunvote.txpad.base.BaseModel;
import com.sunvote.txpad.bean.PaperScore;
import com.sunvote.txpad.bean.Question;
import com.sunvote.txpad.bean.ResponseDataBean;
import com.sunvote.util.SPUtils;

import java.util.List;

import cn.sunars.sdk.SunARS;
import rx.Observable;

/**
 * Created by Elvis on 2017/9/13.
 * Email:Eluis@psunsky.com
 * 版权所有：长沙中天电子设计开发有限公司
 * Description: 易测（ETest）
 */
public class ExaminationModel extends BaseModel {

    public Observable<ResponseDataBean<List<Question>>> getPaperQuestion(String paperId,String paperType){
        return apiService.getPaperQuestion(paperId,paperType).compose(BaseModel.<ResponseDataBean<List<Question>>>io_main());
    }


    public int getExamTime(Context context){
        return SPUtils.getInt(context, Constants.SAVE_EXAM_TIME_KEY,0);
    }

    public void startExam(int num){
        BaseStationManager.getInstance().voteExamination("2,0," + num + ",test");
    }

    public void stopExam(){
        BaseStationManager.getInstance().voteStop();
    }

    public boolean isExaming(){
        return BaseStationManager.getInstance().getBaseStationInfo().getMode() == SunARS.VoteType_Examination;
    }

    public Observable<ResponseDataBean<String>> addReport(PaperScore paperScore){
        return apiService.addReport(paperScore.toJson()).compose(BaseModel.<ResponseDataBean<String>>io_main());
    }
}
