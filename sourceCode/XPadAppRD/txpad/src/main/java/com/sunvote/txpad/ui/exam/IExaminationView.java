package com.sunvote.txpad.ui.exam;

import com.sunvote.txpad.base.BaseView;
import com.sunvote.txpad.bean.Question;

import java.util.List;

/**
 * Created by Elvis on 2017/9/13.
 * Email:Eluis@psunsky.com
 * 版权所有：长沙中天电子设计开发有限公司
 * Description: 易测（ETest）
 */
public interface IExaminationView extends BaseView {

    public void showPaper(List<Question> questionList);

    public void showTime(String time);

    public void showProgress(int reply,int all);
}
