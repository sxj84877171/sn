package com.sunvote.txpad.ui.sign;

import com.sunvote.txpad.base.BaseView;
import com.sunvote.txpad.bean.ClassStudent;
import com.sunvote.txpad.bean.Paper;
import com.sunvote.txpad.bean.Student;

import java.util.List;

/**
 *  * Created by Elvis on 2017/9/13.
 * Email:Eluis@psunsky.com
 * 版权所有：长沙中天电子设计开发有限公司
 * Description: 易测（ETest）
 */
public interface ISignView extends BaseView {

    /**
     * 显示学生签到数和总数
     * @param signed
     * @param all
     */
    public void showSignedStudent(int signed,int all);

    /**
     * 初始化界面数据
     * @param classStudent
     * @param paper
     */
    public void initViewData(ClassStudent classStudent, Paper paper);

    /**
     * 显示学生详细情况
     * @param studentList
     */
    public void displayStudents(List<Student> studentList);

    /**
     *
     * @param signed 已经到
     * @param unsigned 未签到
     * @param weak 弱电
     */
    public void showSignNum(int signed, int unsigned,int weak);

    /**
     * 开启签到
     */
    public void startSign();

    /**
     * 停止签到
     */
    public void stopSign();

    /**
     * 开启考试
     */
    public void startExam();

    /**
     * 停止考试
     */
    public void stopExam();
}
