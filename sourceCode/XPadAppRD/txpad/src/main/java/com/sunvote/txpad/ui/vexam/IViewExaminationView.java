package com.sunvote.txpad.ui.vexam;

import com.sunvote.txpad.base.BaseView;
import com.sunvote.txpad.bean.ClassStudent;
import com.sunvote.txpad.bean.Paper;
import com.sunvote.txpad.bean.Question;

import java.util.List;

/**
 * Created by Elvis on 2017/9/15.
 * Email:Eluis@psunsky.com
 * 版权所有：长沙中天电子设计开发有限公司
 * Description: 易测（ETest）
 */
public interface IViewExaminationView extends BaseView {

    public void showQuestionAnswer(Question question,int index);

    public void noPaper();

    public void showPaper(List<Question> questionList);

    public void gotoPhaseTest(ClassStudent classStudent, Paper paper);

}
