package com.sunvote.txpad.ui.fragment.exam;

import com.sunvote.txpad.base.BaseFragementModel;
import com.sunvote.txpad.base.BaseModel;
import com.sunvote.txpad.bean.Paper;
import com.sunvote.txpad.bean.Question;
import com.sunvote.txpad.bean.ResponseDataBean;

import java.util.List;

import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Elvis on 2017/9/12.
 * Email:Eluis@psunsky.com
 * 版权所有：长沙中天电子设计开发有限公司
 * Description:平板模块键盘投票功能模块
 */
public class PaperFragmentModel extends BaseFragementModel {

    public Observable<ResponseDataBean<List<Paper>>> getPaperList(String userId,String subjectId){
        return apiService.getPaperList(userId,subjectId).compose(BaseModel.<ResponseDataBean<List<Paper>>>io_main());
    }

    public Observable<ResponseDataBean<List<Question>>> getPaperQuestion(String paperId){
        return apiService.getPaperQuestion(paperId).compose(BaseModel.<ResponseDataBean<List<Question>>>io_main());
    }
}
