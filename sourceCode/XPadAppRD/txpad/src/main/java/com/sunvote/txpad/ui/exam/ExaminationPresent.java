package com.sunvote.txpad.ui.exam;

import android.content.Context;
import android.os.Handler;

import com.sunvote.sunvotesdk.BaseStationManager;
import com.sunvote.sunvotesdk.basestation.DefaultKeyEventCallBack;
import com.sunvote.txpad.ApplicationData;
import com.sunvote.txpad.ApplicationDataManager;
import com.sunvote.txpad.Constants;
import com.sunvote.txpad.base.BasePresent;
import com.sunvote.txpad.base.BaseSubscriber;
import com.sunvote.txpad.bean.Paper;
import com.sunvote.txpad.bean.PaperScore;
import com.sunvote.txpad.bean.Question;
import com.sunvote.txpad.bean.ResponseDataBean;
import com.sunvote.txpad.cache.FileCache;
import com.sunvote.txpad.cache.SpCache;

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
    private boolean isExam = false;
    private Handler handler;
    private Context context;

    public void setPaper(Paper paper) {
        this.paper = paper;
    }

    public void initPaperInfo(Context context){
        this.context = context;
        if(paper != null){
            getPaperQuestion(paper.getPaperId(),paper.getPaperType());
        }
        time = model.getExamTime(context);
        view.showTime(intToTimeString(time));

        ApplicationDataManager.getInstance().fillQuestionStudent();
        ApplicationDataManager.getInstance().fillStudentQuestion();
        if(handler == null){
            handler = new Handler();
        }
        showInfo();
    }

    public void getPaperQuestion(final String paperId,final String paperType){
        if(paperId != null) {
            ResponseDataBean<List<Question>> cache = (ResponseDataBean<List<Question>>) FileCache.getFileCache().readEncryptObject("getPaperQuestion&" + paperId + "&" + paperType);
            if (cache != null) {
                List<Question> questions = cache.getData();
                if(questions!= null){
                    view.showPaper(questions);
                }else{
                    getQuestions(paperId,paperType);
                }
            }else {
                getQuestions(paperId,paperType);
            }
        }
    }

    private void getQuestions(final String paperId,final String paperType){
        view.showProgress();
        mRxManager.add(model.getPaperQuestion(paperId,paperType).subscribe(new BaseSubscriber<ResponseDataBean<List<Question>>>() {
            @Override
            public void onNext(ResponseDataBean<List<Question>> listResponseDataBean) {
                super.onNext(listResponseDataBean);
                if (listResponseDataBean != null) {
                    FileCache.getFileCache().saveEncryptObject("getPaperQuestion&" + paperId + "&" + paperType, listResponseDataBean);
                    List<Question> questions = listResponseDataBean.getData();
                    if (questions != null) {
                        view.showPaper(questions);
                    }
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

    public void queryState(){
        isExam = model.isExaming();
        if(isExam){
            view.startExam();
            String oldTime =  SpCache.getInstance().getString(Constants.SAVE_START_EXAM_BEGIN,"0");
            long oldTimeLong = Long.parseLong(oldTime);
            long restTime = (System.currentTimeMillis() - oldTimeLong)/1000 ;
            if(restTime > 0){
                if(handler != null){
                    time = (int)restTime;
                    handler.postDelayed(runnable,1000);
                }
            }else{
                stop();
            }

        }else{
            view.stopExam();
        }
    }

    public void exam() {
        if (!isExam) {
            start();
        } else {
            stop();
        }
    }

    private void start() {
        isExam = true;
        if(handler != null){
            time =  model.getExamTime(context);
            handler.postDelayed(runnable,1000);
        }
        ApplicationDataManager.getInstance().fillQuestionStudent();
        ApplicationDataManager.getInstance().fillStudentQuestion();
        SpCache.getInstance().putInt(Constants.SAVE_START_EXAM_FLAG,1);
        SpCache.getInstance().putString(Constants.SAVE_START_EXAM_BEGIN,"" + System.currentTimeMillis());
        if(getMode() == 1){
            BaseStationManager.getInstance().setKeyboardUseID();
        }else if(getMode() == 2){
            BaseStationManager.getInstance().setKeyboardUseSN();
        }else{
            BaseStationManager.getInstance().setKeyboardUseSN();
        }
        view.startExam();
        BaseStationManager.getInstance().registerKeyEventCallBack(callBack);
        model.startExam(ApplicationData.getInstance().getClassPaper().getQuesCount());
    }

    private void stop() {
        if(handler != null) {
            handler.removeCallbacks(runnable);
        }
        BaseStationManager.getInstance().voteStop();
        BaseStationManager.getInstance().voteStopBackgroundSignIn();
        isExam = false;
        view.showStoping();
        model.stopExam();
        SpCache.getInstance().putInt(Constants.SAVE_START_EXAM_FLAG,0);
        BaseStationManager.getInstance().unRegisterKeyEventCallBack(callBack);
        final PaperScore paperScore = ApplicationDataManager.getInstance().transferPaperScore();
        FileCache.getFileCache().saveUnencryptObject("PaperScore" + paperScore.getReportTime(),paperScore);
        view.showSuccess();
        view.showStoped();
        view.stopExam();
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if(time > 0){
                time -- ;
                view.showTime(intToTimeString(time));
                if(handler != null){
                    handler.postDelayed(this,1000);
                }
            }else{
                stop();
            }
        }
    };

    @Override
    public void onDestroy() {
        if(handler != null){
            handler.removeCallbacks(runnable);
            handler = null;
        }
        super.onDestroy();
    }

    private DefaultKeyEventCallBack callBack = new DefaultKeyEventCallBack(){
        @Override
        public void keyEventKeyResultInfo(String keyId, String info, float time) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    showInfo();
                }
            });
            super.keyEventKeyResultInfo(keyId, info, time);

        }
    };

    public void showInfo() {
        view.showProgress(ApplicationDataManager.getInstance().getCompeleteExamStudent(),
                ApplicationDataManager.getInstance().getStudentCount());
        view.showPaper(ApplicationData.getInstance().getClassPaper().getQuestionList());
        view.showStudent(ApplicationData.getInstance().getStudentList());
        view.showAnswerInfo(ApplicationDataManager.getInstance().getCompeleteExamStudent(),
                ApplicationDataManager.getInstance().getAnsweringExamStudent(),
                ApplicationDataManager.getInstance().getNoanswerExamStudent(),
                ApplicationDataManager.getInstance().getStudentCount() - ApplicationDataManager.getInstance().getSignStudent()
                );
    }

    public boolean isExam() {
        return isExam;
    }

    public int  getMode(){
        return SpCache.getInstance().getInt(Constants.SAVE_SIGN_MODE_KEY,1);
    }
}
