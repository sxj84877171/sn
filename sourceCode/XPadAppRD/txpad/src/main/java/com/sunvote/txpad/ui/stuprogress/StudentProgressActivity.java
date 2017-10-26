package com.sunvote.txpad.ui.stuprogress;

import android.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
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
public class StudentProgressActivity extends BaseActivity implements IStudentProgressView {

    private StudentProgressPresent present;

    private TextView answerAll;
    private TextView answerPart;
    private TextView noAnswer;
    private TextView unsigned;
    private TextView paperTitle;
    private TextView testNum;
    private TextView testStartTime;
    private GridView students;
    private TextView replaceKeyboardControl;
    private StudentAdapter studentAdapter;
    private Paper paper;
    private AlertDialog replaceDialog;

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
        replaceKeyboardControl = getViewById(R.id.replace_keyboard_control);
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

    @Override
    public void initListener() {
        replaceKeyboardControl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                present.changeReplaceFunction();
            }
        });

        studentAdapter.setOnItemClickListener(new StudentAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, Student student, int index) {
                showReplaceDialog(student, present.getMode());
            }
        });
    }

    @Override
    public void showReplaceFunction(int mode, boolean replace) {
        if (replace) {
            studentAdapter.setCanClick(true);
            replaceKeyboardControl.setBackgroundResource(R.drawable.rancnge_background_55);
        } else {
            studentAdapter.setCanClick(false);
            replaceKeyboardControl.setBackgroundResource(R.drawable.rancnge_background);
        }
    }

    @Override
    public void showReplaceDialog(final Student student, final int mode) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View rootView = LayoutInflater.from(this).inflate(R.layout.dialog_replace_keyboard, null);
        TextView name = rootView.findViewById(R.id.name);
        name.setText(student.getStudentName());
        TextView oldKeyboard = rootView.findViewById(R.id.old_keyboard);
        switch (mode) {
            case 1:
                if (TextUtils.isEmpty(student.getKeyBoard().getKeyId())) {
                    showNoKeyboardMsg();
                    return;
                }
                oldKeyboard.setText(student.getKeyBoard().getKeyId());
                break;
            case 2:
                if (TextUtils.isEmpty(student.getKeyBoard().getKeySn())) {
                    showNoKeyboardMsg();
                    return;
                }
                oldKeyboard.setText(student.getKeyBoard().getKeySn());
                break;
            case 3:
                if (TextUtils.isEmpty(student.getKeypadId())) {
                    showNoKeyboardMsg();
                    return;
                }
                oldKeyboard.setText(student.getKeypadId());
                break;
        }
        final TextView newKeyboard = rootView.findViewById(R.id.new_keyboard);
        View cancel = rootView.findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (replaceDialog != null) {
                    replaceDialog.dismiss();
                    replaceDialog = null;
                }
            }
        });
        View save = rootView.findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (replaceDialog != null) {
                    replaceDialog.dismiss();
                    replaceDialog = null;
                }
                String newKeyboardText = newKeyboard.getText().toString();
                switch (mode) {
                    case 1:
                        student.getKeyBoard().setKeyId(newKeyboardText);
                        break;
                    case 2:
                        student.getKeyBoard().setKeySn(newKeyboardText);
                        break;
                    case 3:
                        student.setKeypadId(newKeyboardText);
                        present.updateStudent(student);
                        break;
                }
                student.getKeyBoard().setSign(true);
            }
        });
        builder.setView(rootView);
        replaceDialog = builder.create();
        WindowManager.LayoutParams layoutParams = replaceDialog.getWindow().getAttributes();
        layoutParams.width = dip2px(340);
        replaceDialog.getWindow().setAttributes(layoutParams);
        replaceDialog.show();
    }

    public void showNoKeyboardMsg() {
        showToast(R.string.examination_replace_no_blind_msg_error);
    }

    @Override
    public void showNetError() {
        showToast(R.string.examination_replace_no_upload_msg_error);
    }
}
