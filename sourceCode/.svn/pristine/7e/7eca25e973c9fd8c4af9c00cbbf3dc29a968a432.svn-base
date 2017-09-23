package com.sunvote.txpad.ui.sign;

import android.content.Intent;
import android.view.View;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.sunvote.txpad.R;
import com.sunvote.txpad.base.BaseActivity;
import com.sunvote.txpad.bean.ClassStudent;
import com.sunvote.txpad.bean.Paper;
import com.sunvote.txpad.bean.Student;
import com.sunvote.txpad.ui.exam.ExaminationActivity;

import java.util.List;

/**
 * Created by Elvis on 2017/9/13.
 * Email:Eluis@psunsky.com
 * 版权所有：长沙中天电子设计开发有限公司
 * 易测（ETest）
 */
public class SignActivity extends BaseActivity implements ISignView {

    private SingPresent present;
    private SettingPanel panel;

    private GridView students;
    private View back;
    private View settingPanel;
    private View setting;
    private StudentAdapter adapter;

    private TextView testNum;
    private TextView createTime;
    private TextView paperTitle;
    private TextView signRollCall;
    private ProgressBar signProcessBar;

    private TextView signednum;
    private TextView unSignNum ;
    private TextView weakCurrentNum ;

    private View startSign;
    private TextView startSignText ;
    private View startExam;
    private TextView startExamText ;
    private Paper paper;

    @Override
    public int getLayoutID() {
        return R.layout.activity_sign;
    }

    @Override
    public void initMVP() {
        present = new SingPresent();
        present.setView(this);
        present.setModel(new SignModel());
    }

    @Override
    public void initView() {
        students = getViewById(R.id.students);
        back = getViewById(R.id.back);
        settingPanel = getViewById(R.id.setting_panel);
        setting = getViewById(R.id.setting);
        testNum = getViewById(R.id.test_num);
        createTime = getViewById(R.id.test_start_time);
        paperTitle = getViewById(R.id.paper_title);
        signRollCall = getViewById(R.id.sign_num_desc);
        signProcessBar = getViewById(R.id.sign_process_bar);
        signednum = getViewById(R.id.signed_num);
        unSignNum = getViewById(R.id.unsigned_num);
        weakCurrentNum = getViewById(R.id.weak_current_num);
        startSign = getViewById(R.id.start_sign);
        startSignText = getViewById(R.id.start_sign_text);
        startExam = getViewById(R.id.start_exam);
        startExamText = getViewById(R.id.start_exam_text);

        panel = new SettingPanel(settingPanel);
        panel.initView();
        panel.dismiss();
    }

    @Override
    public void initListener() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        panel.initListener();
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             panel.show();
            }
        });
        startSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                present.sign(getApplicationContext());
            }
        });
        startExam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                present.exam();
            }
        });
    }

    @Override
    public void initData() {
        Intent intent = getIntent();
        paper = (Paper) intent.getSerializableExtra("paper");
        present.setPaper(paper);
        adapter = new StudentAdapter();
        students.setAdapter(adapter);
        present.init();
    }

    @Override
    public void initViewData(ClassStudent classStudent, Paper paper) {
        if (paper != null) {
            testNum.setText(paper.getQuesCount() + getString(R.string.examination_view_question_flag));
            createTime.setText(paper.getCreateTime());
            paperTitle.setText(paper.getPaperName());
        }
        if (classStudent != null) {
            showSignedStudent(0, classStudent.getStudentCount());
            showSignNum(0,classStudent.getStudentCount(),0);
        }
    }

    /**
     * 显示学生详细情况
     * @param studentList
     */
    @Override
    public void displayStudents(List<Student> studentList) {
        adapter.showStudents(studentList);
    }


    @Override
    public void showSignedStudent(int signed, int all) {
        signRollCall.setText(getString(R.string.sign_roll_call) + signed + "/" + all);
        signProcessBar.setMax(all);
        signProcessBar.setProgress(signed);
    }

    public void showSignNum(int signed, int unsigned,int weak){
        signednum.setText(getString(R.string.sign_had_sign) + signed);
        unSignNum.setText(getString(R.string.sign_wait_sign) + unsigned);
        weakCurrentNum.setText(getString(R.string.basestation_manager_check_weak_current) + weak);
    }

    public void startSign(){
        startSignText.setText(R.string.sign_stop_sign);
    }

    public void stopSign(){
        startSignText.setText(R.string.sign_start_sign);
    }

    public void startExam(){
//        startExamText.setText(R.string.examitation_end_exam);

        Intent intent = new Intent(this,ExaminationActivity.class);
        intent.putExtra("paper",paper);
        startActivity(intent);
    }

    public void stopExam(){
//        startExamText.setText(R.string.examitation_start_exam);
    }
}
