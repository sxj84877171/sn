package com.sunvote.txpad.ui.exam;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.sunvote.txpad.ApplicationData;
import com.sunvote.txpad.ApplicationDataManager;
import com.sunvote.txpad.R;
import com.sunvote.txpad.base.BaseActivity;
import com.sunvote.txpad.base.BasePresent;
import com.sunvote.txpad.bean.Paper;
import com.sunvote.txpad.bean.Question;
import com.sunvote.txpad.bean.Student;
import com.sunvote.txpad.ui.sign.StudentAdapter;
import com.sunvote.txpad.ui.stuprogress.StudentProgressActivity;

import java.util.List;

/***
 * Created by Elvis on 2017/9/7.
 * Email:Eluis@psunsky.com
 * 版权所有：长沙中天电子设计开发有限公司
 * Description:ETest 开始考试页面
 */
public class ExaminationActivity extends BaseActivity implements IExaminationView {

    private ExaminationPresent present;
    private ListView questions;
    private ExamQuestionAdapter adapter;
    private Paper paper;
    private TextView paperTitle;
    private TextView testStartTime;
    private TextView testNum;
    private TextView signNumDesc;
    private ProgressBar signProcessBar;
    private TextView restTime;
    private View examControl;
    private TextView startExamText;

    private View listTitlePanel;
    private View girdTitlePanel;
    private View back;
    private GridView students;
    private StudentAdapter studentAdapter;

    private TextView answerAll;
    private TextView answerPart ;
    private TextView noAnswer;
    private TextView unsigned ;
    private ImageView startExamImg;
    private View replaceKeyboardControl;

    private ProgressDialog progressDialog;
    private AlertDialog replaceDialog ;

    private int mode = ApplicationDataManager.MODE_CLASS_STUDENT;

    @Override
    public int getLayoutID() {
        return R.layout.activity_examination;
    }

    @Override
    public void initMVP() {
        present = new ExaminationPresent();
        present.setView(this);
        present.setModel(new ExaminationModel());
        mode = getIntent().getIntExtra("mode",ApplicationDataManager.MODE_CLASS_STUDENT);

        adapter = new ExamQuestionAdapter();
        questions.setAdapter(adapter);
        studentAdapter = new StudentAdapter();
        studentAdapter.setMode(StudentAdapter.MODE_ANSWER);
        students.setAdapter(studentAdapter);

        paper = ApplicationData.getInstance().getClassPaper();
        if(paper != null) {
            present.setPaper(paper);
            paperTitle.setText(paper.getPaperName());
            testStartTime.setText(paper.getCreateTime());
            testNum.setText(paper.getQuesCount() + getString(R.string.examination_view_question_flag));
        }
        present.initPaperInfo(this);
        present.queryState();
    }

    @Override
    public BasePresent getBasePresent() {
        return present;
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
        examControl = getViewById(R.id.exam_control);
        listTitlePanel = getViewById(R.id.list_title_panel);
        girdTitlePanel = getViewById(R.id.gird_title_panel);
        students = getViewById(R.id.students);
        answerAll = getViewById(R.id.answer_all);
        answerPart = getViewById(R.id.answer_part);
        noAnswer = getViewById(R.id.answer_no_start);
        unsigned = getViewById(R.id.answer_no_sign);
        back = getViewById(R.id.back);
        startExamText = getViewById(R.id.start_exam_text);
        startExamImg = getViewById(R.id.start_exam_img);
        replaceKeyboardControl = getViewById(R.id.replace_keyboard_control);
    }

    @Override
    public void initListener() {
        examControl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                present.exam();
            }
        });
        listTitlePanel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                changeView();
                showStudentProgress();
            }
        });
        girdTitlePanel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeView();
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        replaceKeyboardControl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showReplaceDialog();
            }
        });
    }

    @Override
    public void initData() {
        present.showInfo();
    }

    @Override
    public void showPaper(List<Question> questionList) {
        if(mode == ApplicationDataManager.MODE_CLASS_STUDENT){
            ApplicationData.getInstance().getClassPaper().setQuestionList(questionList);
        }else{
            ApplicationData.getInstance().getNoClassPaper().setQuestionList(questionList);
        }
        adapter.setQuestionList(questionList);
    }

    @Override
    public void showTime(String time) {
        restTime.setText(time);
    }

    @Override
    public void showProgress(int reply, int all) {
        signNumDesc.setText(getString(R.string.examitation_submit_num) + reply + "/" + all);
        signProcessBar.setMax(all);
        signProcessBar.setProgress(reply);
    }

    @Override
    public void changeView() {
        if(listTitlePanel.getVisibility() == View.VISIBLE){
            listTitlePanel.setVisibility(View.GONE);
            girdTitlePanel.setVisibility(View.VISIBLE);
            questions.setVisibility(View.GONE);
            students.setVisibility(View.VISIBLE);
        }else{
            listTitlePanel.setVisibility(View.VISIBLE);
            girdTitlePanel.setVisibility(View.GONE);
            questions.setVisibility(View.VISIBLE);
            students.setVisibility(View.GONE);
        }
    }

    public void startExam() {
        startExamText.setText(R.string.examitation_end_exam);
        startExamImg.setImageResource(R.drawable.stop_examination);
    }

    public void stopExam() {
        startExamText.setText(R.string.examitation_start_exam);
        startExamImg.setImageResource(R.drawable.exam_start);
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
    public void showStudentProgress() {
        Intent intent = new Intent(this, StudentProgressActivity.class);
        startActivity(intent);
    }

    @Override
    public void showStoping() {
        if(progressDialog == null) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setIndeterminate(false);
            progressDialog.setMessage(getString(R.string.examination_submit_stoping));
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.setCancelable(false);
        }
        progressDialog.show();
    }

    @Override
    public void showStoped() {
        if(progressDialog != null){
            progressDialog.dismiss();
            progressDialog = null;
        }
    }

    @Override
    public void showSuccess() {
//        showToast("提交成功");
        finish();
    }


    @Override
    public boolean onBack() {
        if(present.isExam()){
            showToast("请先停止考试");
            return true;
        }
        return super.onBack();
    }

    @Override
    public void onBackPressed() {
        if(!onBack()){
            super.onBackPressed();
        }
    }

    public void showReplaceDialog(){
        if(replaceDialog == null){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(R.string.examitation_replace_keyboard);
            View rootView = LayoutInflater.from(this).inflate(R.layout.dialog_keyboard_replace,null);
            final View cancel = rootView.findViewById(R.id.cancel);
            final View sure = rootView.findViewById(R.id.submit);
            final EditText oldKeyBoardView = rootView.findViewById(R.id.old_keyboard_msg);
            final EditText newKeyBoardView = rootView.findViewById(R.id.new_keyboard_msg);
            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(replaceDialog != null){
                        replaceDialog.dismiss();
                    }
                }
            });
            sure.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String oldKeyboard = oldKeyBoardView.getText().toString();
                    String newKeyboard = newKeyBoardView.getText().toString();
                    if(TextUtils.isEmpty(oldKeyboard)){
                        showToast(R.string.examination_replace_old_msg_empty);
                        return;
                    }
                    if(TextUtils.isEmpty(newKeyboard)){
                        showToast(R.string.examination_replace_new_msg_empty);
                        return;
                    }
                    Student student = ApplicationDataManager.getInstance().findStudentKeyId(oldKeyboard);
                    if(student == null){
                        showToast(R.string.examination_replace_old_msg_error);
                        return;
                    }
                    if(ApplicationDataManager.getInstance().findStudentKeyId(newKeyboard) != null){
                        showToast(R.string.examination_replace_new_msg_error);
                        return;
                    }
                    student.getKeyBoard().setKeyId(newKeyboard);
                    if(replaceDialog != null){
                        replaceDialog.dismiss();
                    }
                }
            });
            builder.setView(rootView);
            replaceDialog = builder.create();
        }
        replaceDialog.show();
    }
}
