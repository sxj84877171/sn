package com.sunvote.txpad.ui.fragment.exam;

import android.content.Intent;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.sunvote.txpad.ApplicationData;
import com.sunvote.txpad.R;
import com.sunvote.txpad.base.BaseFragment;
import com.sunvote.txpad.bean.ClassStudent;
import com.sunvote.txpad.bean.Paper;
import com.sunvote.txpad.ui.sign.SignActivity;
import com.sunvote.txpad.ui.vexam.ExaminationViewActivity;

import java.util.List;

/**
 * Created by Elvis on 2017/9/18.
 * Email:Eluis@psunsky.com
 * 版权所有：长沙中天电子设计开发有限公司
 * Description: 易测（ETest）
 */
public class PaperActivityFragment extends BaseFragment implements IPaperFragmentView{

    private ListView courseList ;
    private PaperListAdapter adapter;
    private ClassStudent classStudent;
    private PaperFragmentPresent present;
    private TextView className ;
    private TextView classStudentNum ;
    private View filler;
    private View newPaper;

    private View examinationViewCoursesChinese ;
    private View examinationViewCoursesMatch ;
    private View examinationViewCoursesEnglish;
    private View examinationViewCoursesPhysics;
    private View examinationViewCoursesChemistry;
    private View examinationViewCoursesBiology;
    private View examinationViewCoursesHistory;
    private View examinationViewCoursesGeography;
    private View examinationViewCoursesPolitics;

    public PaperActivityFragment() {
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    public void initView() {
        courseList = findViewById(R.id.course_list);
        className = findViewById(R.id.class_name);
        classStudentNum = findViewById(R.id.class_students_num);
        filler = findViewById(R.id.filler);
        newPaper = findViewById(R.id.new_paper);
        examinationViewCoursesChinese = findViewById(R.id.examination_view_courses_chinese);
        examinationViewCoursesMatch = findViewById(R.id.examination_view_courses_match);
        examinationViewCoursesEnglish = findViewById(R.id.examination_view_courses_english);
        examinationViewCoursesPhysics = findViewById(R.id.examination_view_courses_physics);
        examinationViewCoursesChemistry = findViewById(R.id.examination_view_courses_chemistry);
        examinationViewCoursesBiology = findViewById(R.id.examination_view_courses_biology);
        examinationViewCoursesHistory = findViewById(R.id.examination_view_courses_history);
        examinationViewCoursesGeography = findViewById(R.id.examination_view_courses_geography);
        examinationViewCoursesPolitics = findViewById(R.id.examination_view_courses_politics);
    }

    @Override
    public void initMVP() {
        present = new PaperFragmentPresent();
        present.setView(this);
        present.setModel(new PaperFragmentModel());
    }

    @Override
    public void initListener() {
        adapter.setOnItemTypeClickListener(new PaperListAdapter.OnItemTypeClickListener() {
            @Override
            public void onClick(Paper paper, int index, int type) {
                switch (type){
                    case PaperListAdapter.PREVIEW:
                        gotoPreviewPaper(classStudent,paper);
                        break;
                    case PaperListAdapter.FOLLOWUPTEST:
                        gotoFollowTest(classStudent,paper);
                        break;
                    case PaperListAdapter.PHASETEST:
                        gotoPhaseTest(classStudent,paper);
                        break;
                    default:
                        gotoPreviewPaper(classStudent,paper);
                        break;
                }
            }
        });

        filler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                present.getPaperList(true);
            }
        });
        newPaper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showToast("请期待");
            }
        });
    }

    public void setClassStudent(ClassStudent classStudent) {
        this.classStudent = classStudent;
    }

    @Override
    public void onResume() {
        super.onResume();
        classStudent = ApplicationData.getInstance().getClassStudent();
        showClassInfo(classStudent);
        present.getPaperList(false);
    }

    public void showClassInfo(ClassStudent classStudent) {
        if(classStudent != null){
            this.classStudent = classStudent;
            className.setText(classStudent.getClassName());
            classStudentNum.setText(getString(R.string.home_fragment_class_student_num)
                    + classStudent.getStudentCount() + getString(R.string.home_fragment_class_student_flag));
        }
    }

    @Override
    public void addPaperList(List<Paper> papers) {
        if(papers != null){
            adapter.addPaperList(papers);
        }
    }

    @Override
    public void initData() {
        adapter = new PaperListAdapter();
        courseList.setAdapter(adapter);
        present.init();
        int subjectId = Integer.parseInt(ApplicationData.getInstance().getLoginInfo().getSubjectId());
        switch (subjectId){
            case 25:
                examinationViewCoursesGeography.setBackgroundResource(R.drawable.rancnge_background_choose);
                break;
            case 26:
                examinationViewCoursesEnglish.setBackgroundResource(R.drawable.rancnge_background_choose);
                break;
        }
    }

    @Override
    public void gotoPreviewPaper(ClassStudent classStudent,Paper paper) {
        Intent intent = new Intent(getBaseActivity(), ExaminationViewActivity.class);
        ApplicationData.getInstance().setClassStudent(classStudent);
        ApplicationData.getInstance().setClassPaper(paper);
        startActivity(intent);
    }

    @Override
    public void gotoFollowTest(ClassStudent classStudent,Paper paper) {
//        Intent intent = new Intent(getActivity(), SignActivity.class);
//        intent.putExtra("paper",paper);
//        intent.putExtra("classStudent",classStudent);
//        startActivity(intent);
        ApplicationData.getInstance().setNoClassPaper(paper);
        showToast("请期待");
    }

    @Override
    public void gotoPhaseTest(ClassStudent classStudent,Paper paper) {
        Intent intent = new Intent(getActivity(), SignActivity.class);
        ApplicationData.getInstance().setClassStudent(classStudent);
        ApplicationData.getInstance().setClassPaper(paper);
        startActivity(intent);
    }

    @Override
    public void showPaperList(List<Paper> papers) {
        if(papers != null){
            adapter.setPaperList(papers);
        }
    }


}
