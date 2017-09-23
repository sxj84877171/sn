package com.sunvote.txpad.ui.exam;

import android.content.Context;

import com.sunvote.txpad.ApplicationData;
import com.sunvote.txpad.base.BasePresent;
import com.sunvote.txpad.base.BaseSubscriber;
import com.sunvote.txpad.bean.Paper;
import com.sunvote.txpad.bean.Question;
import com.sunvote.txpad.bean.ResponseDataBean;
import com.sunvote.txpad.cache.FileCache;

import java.util.List;

/**
 * Created by Elvis on 2017/9/13.
 * Email:Eluis@psunsky.com
 * 版权所有：长沙中天电子设计开发有限公司
 * Description: 易测（ETest）
 */
public class ExaminationPresent extends BasePresent<ExaminationModel,IExaminationView> {

    private Paper paper;
    private int time ;

    public void setPaper(Paper paper) {
        this.paper = paper;
    }

    public void initPaperInfo(Context context){
        if(paper != null){
            getPaperQuestion(paper.getPaperId());
        }
        time = model.getExamTime(context);
        view.showTime(intToTimeString(time));
        view.showProgress(0, paper.getQuesCount());
    }

    public void getPaperQuestion(final String paperId){
        if(paperId != null) {
            ResponseDataBean<List<Question>> cache = (ResponseDataBean<List<Question>>) FileCache.getFileCache().readObject("getPaperQuestion&" + paperId);
            if (cache != null) {
                List<Question> questions = cache.getData();
                if(questions!= null){
                    view.showPaper(questions);
                }
            }else {
                model.getPaperQuestion(paperId).subscribe(new BaseSubscriber<ResponseDataBean<List<Question>>>() {
                    @Override
                    public void onNext(ResponseDataBean<List<Question>> listResponseDataBean) {
                        super.onNext(listResponseDataBean);
                        if (listResponseDataBean != null) {
                            FileCache.getFileCache().saveObject("getPaperQuestion&" + paperId, listResponseDataBean);
                            List<Question> questions = listResponseDataBean.getData();
                            if (questions != null) {
                                view.showPaper(questions);
                            }
                        }
                    }
                });
            }
        }
    }

    public String intToTimeString(int time){
        int modeSec = time % 60 ;
        String modeSecStr = null ;
        if(modeSec == 0){
            modeSecStr = "00" ;
        }else if(modeSec < 10){
            modeSecStr = "0" + modeSec ;
        }else{
            modeSecStr = "" + modeSec ;
        }
        int present = time / 60 ;
        String pStr = null ;
        if(present == 0){
            pStr = "00"  ;
        }else if(present < 10) {
            pStr = "0" + present ;
        }else{
            pStr = "" + present ;
        }
        return pStr + ":" + modeSecStr ;
    }
}
