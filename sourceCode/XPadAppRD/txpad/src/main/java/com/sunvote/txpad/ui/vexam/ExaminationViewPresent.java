package com.sunvote.txpad.ui.vexam;

import com.sunvote.txpad.base.BasePresent;
import com.sunvote.txpad.base.BaseSubscriber;
import com.sunvote.txpad.bean.Paper;
import com.sunvote.txpad.bean.Question;
import com.sunvote.txpad.bean.ResponseDataBean;
import com.sunvote.txpad.cache.FileCache;

import java.util.Date;
import java.util.List;

/**
 * Created by Elvis on 2017/9/15.
 * Email:Eluis@psunsky.com
 * 版权所有：长沙中天电子设计开发有限公司
 * Description: 易测（ETest）
 */
public class ExaminationViewPresent extends BasePresent<ExaminationViewModel,IViewExaminationView> {

    public void getPaperList(String userId,String subjectId,String paperType){
        mRxManager.add(model.getPaperList(userId,subjectId,paperType).subscribe(new BaseSubscriber<ResponseDataBean<List<Paper>>>(){
            @Override
            public void onNext(ResponseDataBean<List<Paper>> listResponseDataBean) {
                super.onNext(listResponseDataBean);
            }
        }));


    }

    public void getPaperQuestion(final String paperId,final String paperType){
        if(paperId != null) {
            ResponseDataBean<List<Question>> cache = (ResponseDataBean<List<Question>>) FileCache.getFileCache().readEncryptObject("getPaperQuestion&" + paperId + "&" + paperType);
            if (cache != null) {
                List<Question> questions = cache.getData();
                if(questions!= null){
                    view.showPaper(questions);
                }
            }
            mRxManager.add(model.getPaperQuestion(paperId,paperType).subscribe(new BaseSubscriber<ResponseDataBean<List<Question>>>() {
                @Override
                public void onNext(ResponseDataBean<List<Question>> listResponseDataBean) {
                    super.onNext(listResponseDataBean);
                    if(listResponseDataBean != null) {
                        FileCache.getFileCache().saveEncryptObject("getPaperQuestion&" + paperId + "&" + paperType, listResponseDataBean);
                        List<Question> questions = listResponseDataBean.getData();
                        if(questions!= null){
                            view.showPaper(questions);
                        }
                    }
                }
            }));
        }else{
//            view.noPaper();
        }
    }

    public String getCurrentTime(){
        return simpleDateFormat.format(new Date());
    }
}
