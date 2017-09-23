package com.sunvote.txpad.ui.vexam;

import android.content.Intent;
import android.os.Build;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.sunvote.txpad.R;
import com.sunvote.txpad.base.BaseActivity;
import com.sunvote.txpad.bean.ClassStudent;
import com.sunvote.txpad.bean.Paper;
import com.sunvote.txpad.bean.Question;
import com.sunvote.txpad.ui.sign.SignActivity;

import java.util.List;

/**
 * Created by Elvis on 2017/9/15.
 * Email:Eluis@psunsky.com
 * 版权所有：长沙中天电子设计开发有限公司
 * Description: 易测（ETest）
 */
public class ExaminationViewActivity extends BaseActivity implements IViewExaminationView {

    private ExaminationViewPresent present;
    private ListView questions;
    private QuestionAdapter adapter;

    private View close;
    private View resolutionPanel;

    private TextView questionAnswerNo;
    private TextView questionAnswerChoose;
    private TextView questionAnswerText;

    private TextView paperName;

    private TextView testNum;
    private TextView testStartTime;

    private View phaseTest;
    private ClassStudent classStudent;
    private Paper paper;


    @Override
    public int getLayoutID() {
        return R.layout.activity_examination_view;
    }

    @Override
    public void initMVP() {
        present = new ExaminationViewPresent();
        present.setView(this);
        present.setModel(new ExaminationViewModel());
    }

    @Override
    public void initView() {
        questions = getViewById(R.id.questions);
        close = getViewById(R.id.close);
        resolutionPanel = getViewById(R.id.resolution_panel);
        questionAnswerNo = getViewById(R.id.question_answer_no);
        questionAnswerChoose = getViewById(R.id.question_answer_choose);
        questionAnswerText = getViewById(R.id.question_answer_text);
        testNum = getViewById(R.id.test_num);
        testStartTime = getViewById(R.id.test_start_time);
        phaseTest = getViewById(R.id.phase_test);
        paperName = getViewById(R.id.paper_name);
    }

    @Override
    public void initListener() {
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resolutionPanel.setVisibility(View.GONE);
            }
        });
        adapter.setOnViewResolution(new QuestionAdapter.OnViewResolution() {
            @Override
            public void onViewResolution(Question question, int index) {
                resolutionPanel.setVisibility(View.VISIBLE);
                if(question != null){
                    showQuestionAnswer(question, index);
                }
            }
        });

        phaseTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoPhaseTest(classStudent,paper);
            }
        });
    }

    @Override
    public void initData() {
        Intent intent = getIntent();
        classStudent = (ClassStudent) intent.getSerializableExtra("classStudent");
        paper = (Paper)intent.getSerializableExtra("paper");
        adapter = new QuestionAdapter();
        questions.setAdapter(adapter);
        questions.setDivider(null);
        testNum.setText(adapter.getCount() + getString(R.string.examination_view_question_flag));
        paperName.setText(paper.getPaperName());
        testStartTime.setText(present.getCurrentTime());
        present.getPaperQuestion(paper.getPaperId());
    }

    @Override
    public void showQuestionAnswer(Question question, int index) {
        questionAnswerNo.setText(getString(R.string.examination_view_question_no) + ":" + index + "  " +
                getString(R.string.name_list_id_sn) + ":" + question.getId());
        questionAnswerChoose.setText(question.getAnswers());
        Spanned spanned = null;
        if(Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N ){
            spanned = Html.fromHtml(question.getDiscuss() + "</br>" +
                    question.getAnalyse() + "</br>" +
                    question.getMethod() , 0,new HtmlImageGetter(this),null);
        }else{
            spanned = Html.fromHtml(question.getDiscuss() + "</br>" +
                    question.getAnalyse() + "</br>" +
                    question.getMethod());
        }
        questionAnswerText.setText(spanned);
    }

    @Override
    public void noPaper() {
        showToast("请选择试卷");
        finish();
    }

    @Override
    public void showPaper(List<Question> questionList) {
        adapter.setQuestionList(questionList);
        testNum.setText(adapter.getCount() + getString(R.string.examination_view_question_flag));
    }

    @Override
    public void gotoPhaseTest(ClassStudent classStudent, Paper paper) {
        Intent intent = new Intent(getActivity(), SignActivity.class);
        intent.putExtra("paper",paper);
        intent.putExtra("classStudent",classStudent);
        startActivity(intent);
    }
}
