package com.sunvote.txpad.ui.stuprogress;

import com.sunvote.txpad.base.BaseView;
import com.sunvote.txpad.bean.Student;

import java.util.List;

/**
 * Created by Elvis on 2017/9/27.
 * Email:Eluis@psunsky.com
 * 版权所有：长沙中天电子设计开发有限公司
 * Description: 易测（ETest）
 */
public interface IStudentProgressView extends BaseView {

    public void showAnswerInfo(int answered, int answering, int unAnswered, int unsignedNum);

    public void showStudent(List<Student> list);
}
