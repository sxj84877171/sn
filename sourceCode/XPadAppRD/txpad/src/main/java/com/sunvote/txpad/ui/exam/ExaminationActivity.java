package com.sunvote.txpad.ui.exam;

import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.sunvote.txpad.R;
import com.sunvote.txpad.base.BaseActivity;
import com.sunvote.txpad.bean.Paper;
import com.sunvote.txpad.bean.Question;

import java.util.List;

/***
 * Created by Elvis on 2017/9/7.
 * Email:Eluis@psunsky.com
 * 版权所有：长沙中天电子设计开发有限公司
 * Description:ETest 开始考试页面
 */
public class ExaminationActivity extends BaseActivity implements IExaminationView{

    private ExaminationPresent present;
    private ListView questions;
    private ExamQuestionAdapter adapter;
    private Paper paper;
    private TextView paperTitle;
    private TextView testStartTime;
    private TextView testNum ;
    private TextView signNumDesc;
    private ProgressBar signProcessBar;
    private TextView restTime;

    @Override
    public int getLayoutID() {
        return R.layout.activity_examination;
    }

    @Override
    public void initMVP() {
        present = new ExaminationPresent();
        present.setView(this);
        present.setModel(new ExaminationModel());
        paper = (Paper) getIntent().getSerializableExtra("paper");
        present.setPaper(paper);
    }

    @Override
    public void initView() {
        questions = getViewById(R.id.questions);
        paperTitle = getViewById(R.id.paper_title);
        testStartTime = getViewById(R.id.test_start_time);
        testNum = getViewById(R.id.test_num);
        signNumDesc = getViewById(R.id.sign_num_desc);
        signProcessBar = getViewById(R.id.sign_process_bar);
        restTime = getViewById(R.id.rest_time);
    }

    @Override
    public void initData() {
        adapter = new ExamQuestionAdapter();
        questions.setAdapter(adapter);
        paperTitle.setText(paper.getPaperName());
        testStartTime.setText(paper.getCreateTime());
        testNum.setText(paper.getQuesCount() + getString(R.string.examination_view_question_flag));
        present.initPaperInfo(this);
    }

    @Override
    public void showPaper(List<Question> questionList) {
        adapter.setQuestionList(questionList);
    }

    @Override
    public void showTime(String time) {
        restTime.setText(time);
    }

    @Override
    public void showProgress(int reply, int all) {
        signNumDesc.setText(getString(R.string.examination_submit) + reply + "/" + all);
        signProcessBar.setMax(all);
        signProcessBar.setProgress(reply);
    }
}
