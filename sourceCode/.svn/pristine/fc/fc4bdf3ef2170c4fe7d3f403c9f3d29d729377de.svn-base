package com.sunvote.txpad.ui.stuprogress;

import android.view.View;
import android.widget.GridView;
import android.widget.TextView;

import com.sunvote.txpad.ApplicationData;
import com.sunvote.txpad.R;
import com.sunvote.txpad.base.BaseActivity;
import com.sunvote.txpad.base.BasePresent;
import com.sunvote.txpad.bean.Paper;
import com.sunvote.txpad.bean.Student;
import com.sunvote.txpad.ui.sign.StudentAdapter;

import java.util.List;

/**
 * Created by Elvis on 2017/9/27.
 * Email:Eluis@psunsky.com
 * 版权所有：长沙中天电子设计开发有限公司
 * Description: 易测（ETest）
 */
public class StudentProgressActivity extends BaseActivity implements IStudentProgressView{

    private StudentProgressPresent present;

    private TextView answerAll;
    private TextView answerPart ;
    private TextView noAnswer;
    private TextView unsigned ;
    private TextView paperTitle;
    private TextView testNum;
    private TextView testStartTime;
    private GridView students;
    private StudentAdapter studentAdapter;
    private Paper paper;
    /**
     * 获取资源文件
     *
     * @return
     */
    @Override
    public int getLayoutID() {
        return R.layout.activity_student_progress;
    }

    /**
     * 初始化框架
     */
    @Override
    public void initMVP() {
        present = new StudentProgressPresent();
        present.setView(this);
        present.setModel(new StudentProgressModel());
        paper = ApplicationData.getInstance().getClassPaper();
    }

    @Override
    public BasePresent getBasePresent() {
        return present;
    }

    @Override
    public void initView() {
        students = getViewById(R.id.students);
        answerAll = getViewById(R.id.answer_all);
        answerPart = getViewById(R.id.answer_part);
        noAnswer = getViewById(R.id.answer_no_start);
        unsigned = getViewById(R.id.answer_no_sign);
        paperTitle = getViewById(R.id.paper_title);
        testStartTime = getViewById(R.id.test_start_time);
        testNum = getViewById(R.id.test_num);
    }

    @Override
    public void initData() {
        studentAdapter = new StudentAdapter();
        studentAdapter.setMode(StudentAdapter.MODE_ANSWER);
        students.setAdapter(studentAdapter);
        paperTitle.setText(paper.getPaperName());
        testStartTime.setText(paper.getCreateTime());
        testNum.setText(paper.getQuesCount() + getString(R.string.examination_view_question_flag));
        present.init();
    }

    @Override
    public void showAnswerInfo(int answered, int answering, int unAnswered, int unsignedNum) {
        answerAll.setText(getString(R.string.examitation_answer_all) + answered);
        answerPart.setText(getString(R.string.examitation_answer_part) + answering);
        noAnswer.setText(getString(R.string.examitation_answer_no_start) + unAnswered);
        unsigned.setText(getString(R.string.examitation_answer_no_sign) + unsignedNum);
    }

    @Override
    public void showStudent(List<Student> list) {
        studentAdapter.showStudents(list);
    }

    @Override
    protected void onResume() {
        super.onResume();
        present.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        present.onPause();
    }
}
