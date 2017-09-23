package com.sunvote.txpad.ui.exam;

import android.content.Context;

import com.sunvote.txpad.Constants;
import com.sunvote.txpad.base.BaseModel;
import com.sunvote.txpad.bean.Question;
import com.sunvote.txpad.bean.ResponseDataBean;
import com.sunvote.util.SPUtils;

import java.util.List;

import rx.Observable;

/**
 * Created by Elvis on 2017/9/13.
 * Email:Eluis@psunsky.com
 * 版权所有：长沙中天电子设计开发有限公司
 * Description: 易测（ETest）
 */
public class ExaminationModel extends BaseModel {

    public Observable<ResponseDataBean<List<Question>>> getPaperQuestion(String paperId){
        return apiService.getPaperQuestion(paperId).compose(BaseModel.<ResponseDataBean<List<Question>>>io_main());
    }


    public int getExamTime(Context context){
        return SPUtils.getInt(context, Constants.SAVE_EXAM_TIME_KEY,0);
    }
}
