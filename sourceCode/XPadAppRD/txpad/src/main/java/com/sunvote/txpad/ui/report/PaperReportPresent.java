package com.sunvote.txpad.ui.report;

import com.sunvote.txpad.ApplicationDataHelper;
import com.sunvote.txpad.ApplicationDataManager;
import com.sunvote.txpad.R;
import com.sunvote.txpad.base.BaseActivity;
import com.sunvote.txpad.base.BasePresent;
import com.sunvote.txpad.base.BaseSubscriber;
import com.sunvote.txpad.bean.PaperScore;
import com.sunvote.txpad.bean.ResponseDataBean;
import com.sunvote.txpad.bean.StudentScore;
import com.sunvote.txpad.cache.FileCache;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Elvis on 2017/10/9.
 * Email:Eluis@psunsky.com
 * 版权所有：长沙中天电子设计开发有限公司
 * Description: 易测（ETest）
 */
public class PaperReportPresent extends BasePresent<PaperReportModel,IPaperReportView> {
    private PaperScore mPaperScore;
    private boolean sortByNo = true;

    @Override
    public void init() {
    }

    public void setSortByNo(boolean sortByNo) {
        this.sortByNo = sortByNo;
        sort();
        String content = getHtmlContent(mPaperScore);
        view.showReport(content);
    }

    public void getReport(PaperScore paperScore){
        if(paperScore != null) {
            if (paperScore.getDetail() == null) {
                view.showProgress();
                PaperScore temp = (PaperScore) FileCache.getFileCache().readEncryptObject("PaperScore&" + paperScore.getReportId());
                if (temp != null) {
                    mPaperScore = temp;
                    showReportInfo();
                } else {
                    model.getReport(paperScore.getReportId()).subscribe(new BaseSubscriber<ResponseDataBean<PaperScore>>() {
                        @Override
                        public void onNext(ResponseDataBean<PaperScore> paperScoreResponseDataBean) {
                            mPaperScore = paperScoreResponseDataBean.getData();
                            FileCache.getFileCache().saveEncryptObject("PaperScore&" + mPaperScore.getReportId(), mPaperScore);
                            showReportInfo();
                        }

                        @Override
                        public void onError(Throwable e) {
                            super.onError(e);
                            view.dismissProgress();
                            view.showNetError();
                        }
                    });

                }
            }else{
                mPaperScore = paperScore;
                showReportInfo();
            }
        }
    }

    public void showReportInfo() {
        Collections.sort(mPaperScore.getDetail(),scoreComparator);
        fillRangStudent(mPaperScore.getDetail());
        sort();
        view.showReportLittleInfo(mPaperScore);
        view.showStudentInfo(getBestStudent(), getWorstStudent());
        String content = getHtmlContent(mPaperScore);
        view.showReport(content);
        view.dismissProgress();
    }

    public void sort() {
        if(sortByNo){
            Collections.sort(mPaperScore.getDetail(),noComparator);
        }else{
            Collections.sort(mPaperScore.getDetail(),scoreComparator);
        }

    }

    public int getBestStudent(){
        int max = 0 ;
        List<StudentScore> studentScores = mPaperScore.getDetail();
        if(studentScores != null){
            for(StudentScore studentScore:studentScores){
                if(studentScore.getTotalScore() > max){
                    max = studentScore.getTotalScore();
                }
            }
        }
        return max;
    }

    public int getWorstStudent(){
        int min = Integer.MAX_VALUE ;
        List<StudentScore> studentScores = mPaperScore.getDetail();
        if(studentScores != null){
            for(StudentScore studentScore:studentScores){
                if(ApplicationDataHelper.getInstance().isEffectiveStduent(studentScore)) {
                    int socre = studentScore.getTotalScore();
                    if (socre < min) {
                        min = socre;
                    }
                }
            }
        }
        if(min == Integer.MAX_VALUE ){
            min = 0;
        }
        return min;
    }


    public String getHtmlContent(PaperScore mPaperScore){
        StringBuilder stringBuilder = new StringBuilder();
        /*添加header标签及js，css*/
        stringBuilder.append(model.getHtmlHeaderContent(mPaperScore.getClassName() + "(" + mPaperScore.getPaperName() + ")"));
        stringBuilder.append(model.getBodyFrist());
        /*<div>  最外面DIV*/
        stringBuilder.append(model.getDivStyle100());
        /*<table> 添加左边表格*/
        stringBuilder.append(model.getDivTLeft());
        stringBuilder.append(model.getFristTableTag());
        stringBuilder.append(model.LEFT_FIX_CONTENT_ITEM_COL);
        stringBuilder.append(model.getDivTLeftTableTBodyTag(getString(R.string.score_book_report_no),
                getString(R.string.score_book_report_username),
                getString(R.string.score_book_report_correct_rate_answer),
                getString(R.string.score_book_report_score),
                getString(R.string.score_book_report_rang)));
        /*</table*/
        stringBuilder.append(model.getLastTableTag());

        /*<div> 添加左边 上面标题栏*/
        stringBuilder.append(model.getDivFreeze());
        /*<table> 添加左边下面内容*/
        stringBuilder.append(model.getFristTableTag());
        stringBuilder.append(model.LEFT_FIX_CONTENT_ITEM_COL);
        List<StudentScore> studentScoreList = mPaperScore.getDetail();
        for(StudentScore studentScore : studentScoreList) {
            String studentName = studentScore.getStudentName();
            if(!ApplicationDataHelper.getInstance().isEffectiveStduent(studentScore)){
                studentName += getString(R.string.student_off);
            }
            stringBuilder.append(model.getDivTLeftTableTBodyContentTag(
                    studentScore.getStudentId(),studentName,
                    ApplicationDataManager.getInstance().progressText(studentScore.getTotalScore(),mPaperScore.getPaperScore()) + "%","" + studentScore.getTotalScore()
            ,"" + studentScore.getRang()));
        }
        /*</table> 结束左边表格内容*/
        stringBuilder.append(model.getLastTableTag());
        stringBuilder.append(model.getLastDiv());
        stringBuilder.append(model.getLastDiv());

        stringBuilder.append(model.getDivT_R());
        /*<div> 添加右边表格*/
        stringBuilder.append(model.getDivT_R_T());
        // 添加右边表格标题栏
        stringBuilder.append(model.getDivT_R_Title());
        stringBuilder.append(model.getFristTableTag());
        List<String> rightRateList = mPaperScore.getRightRate();
        if(rightRateList != null) {
            stringBuilder.append(model.getTrFristTag());
            // 添加题1 题2 …… 数据
            for (int i = 1; i < rightRateList.size() + 1; i++) {
                stringBuilder.append(model.getQuestionCell(getString(R.string.score_book_report_question_flag) + i));
            }
            stringBuilder.append(model.getTrLastTag());
            // 添加70% 60% 每个题目正确率的数据
            stringBuilder.append(model.getTrFristTag());
            for (int i = 1; i < rightRateList.size() + 1; i++) {
                try{
                    float precent = Float.parseFloat(rightRateList.get(i-1));
                    if(precent > 59.9f){
                        stringBuilder.append(model.getQuestionCell(rightRateList.get(i-1) + "%"));
                    }else{
                        stringBuilder.append(model.getQuestionCellLow(rightRateList.get(i-1) + "%"));
                    }
                }catch (Exception ex){stringBuilder.append(model.getQuestionCell(""));}
            }
            stringBuilder.append(model.getTrLastTag());
        }
        stringBuilder.append(model.getLastTableTag());
        stringBuilder.append(model.getLastDiv());
        stringBuilder.append(model.getLastDiv());

        // 添加右边表格下面学生具体的答题情况
        stringBuilder.append(model.getDivT_R_content());
        stringBuilder.append(model.getFristTableTag());
        for(StudentScore studentScore:studentScoreList){
            stringBuilder.append(model.getTrFristTag());
            List<String> answerList = studentScore.getAnswer();
            for(String answer : answerList){
                String[] answers = answer.split(",");
                if(answers.length >= 2){
                    if("1".equals(answers[1])){// 正确答案 单元格
                        stringBuilder.append(model.getQuestionCellRight(answers[0]));
                    }else{// 错误答案单元格
                        stringBuilder.append(model.getQuestionCellWrong(answers[0]));
                    }
                }

            }
            stringBuilder.append(model.getTrLastTag());
        }
        stringBuilder.append(model.getLastTableTag());
        stringBuilder.append(model.getLastDiv());

        stringBuilder.append(model.getLastDiv());
        stringBuilder.append(model.getLastDiv());

        /*</div> 结束整个大的div*/
        stringBuilder.append(model.getLastDiv());

        /*</div>*/
        stringBuilder.append(model.getLastDiv());
        stringBuilder.append(model.getBodyLast());
        return stringBuilder.toString();
    }

    public String getString(int resID){
        if(view instanceof BaseActivity){
            return ((BaseActivity)view).getString(resID);
        }
        return null;
    }

    private Comparator<StudentScore> noComparator = new Comparator<StudentScore>() {
        @Override
        public int compare(StudentScore studentScore, StudentScore t1) {
            return studentScore.getStudentId().compareTo(t1.getStudentId());
        }
    };

    private Comparator<StudentScore> scoreComparator = new Comparator<StudentScore>() {
        @Override
        public int compare(StudentScore studentScore, StudentScore t1) {
            return t1.getTotalScore() - studentScore.getTotalScore();
        }
    };

    public void fillRangStudent(List<StudentScore> list){
        if(list != null){
            for(int i = 1 ; i <= list.size(); i++){
                StudentScore studentScore = list.get(i-1);
                studentScore.setRang(i);
            }
        }
    }

}
