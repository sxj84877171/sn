package com.sunvote.txpad.ui.report;

import android.provider.ContactsContract;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.sunvote.txpad.R;
import com.sunvote.txpad.base.BaseActivity;
import com.sunvote.txpad.base.BasePresent;
import com.sunvote.txpad.bean.PaperScore;

/**
 * Created by Elvis on 2017/10/9.
 * Email:Eluis@psunsky.com
 * 版权所有：长沙中天电子设计开发有限公司
 * Description: 易测（ETest）
 */
public class PaperReportActivity extends BaseActivity implements IPaperReportView{

    private WebView paperReport;

    private PaperReportPresent present;
    private PaperScore paperScore;
    private TextView className;
    private TextView classStudentsNum;
    private TextView paperTitle;
    private TextView testStartTime;
    private TextView paperScoreAll;
    private TextView paperScoreAvl;
    private TextView paperHighStudent;
    private TextView paperLowStudent;

    private View sortNo;
    private View sortRang;
    private ImageView sortNoImg ;
    private ImageView sortRangImg ;
    /**
     * 获取资源文件
     * @return
     */
    @Override
    public int getLayoutID() {
        return R.layout.activity_paper_report;
    }

    /**
     * 初始化框架
     */
    @Override
    public void initMVP() {
        present = new PaperReportPresent();
        present.setView(this);
        present.setModel(new PaperReportModel());
        present.init();
    }

    @Override
    public void initData() {
        paperScore = (PaperScore) getIntent().getSerializableExtra("PaperScore");
        present.getReport(paperScore);
        if(paperScore != null){
            showReportLittleInfo(paperScore);
        }
    }

    public void showReportLittleInfo(PaperScore paperScore) {
        className.setText(paperScore.getClassName());
        classStudentsNum.setText(paperScore.getRightRate().size()+getString(R.string.home_fragment_class_student_flag));
        paperTitle.setText(paperScore.getPaperName());
        testStartTime.setText(paperScore.getReportTime());
        paperScoreAll.setText(getString(R.string.score_book_paper_all) + " : " + paperScore.getPaperScore());
        paperScoreAvl.setText(getString(R.string.score_book_paper_avl) + " : " + paperScore.getClassAverage());
    }

    public void showReportStudent(int high,int low){
        paperHighStudent.setText(getString(R.string.score_book_paper_high_student) + " : " + high);
        paperLowStudent.setText(getString(R.string.score_book_paper_low_student) + " : " + low);
    }

    @Override
    public void initView() {
        paperReport = getViewById(R.id.paper_report);
        className = getViewById(R.id.class_name);
        classStudentsNum = getViewById(R.id.class_students_num);
        paperTitle = getViewById(R.id.paper_title);
        testStartTime = getViewById(R.id.test_start_time);
        paperScoreAll = getViewById(R.id.paper_score_all);
        paperScoreAvl = getViewById(R.id.paper_score_avl);
        paperHighStudent = getViewById(R.id.paper_high_student);
        paperLowStudent = getViewById(R.id.paper_low_student);
        sortNo = getViewById(R.id.sort_no);
        sortRang = getViewById(R.id.sort_rang);
        sortNoImg = getViewById(R.id.sort_no_img);
        sortRangImg = getViewById(R.id.sort_rang_img);
        sortNo.setBackgroundResource(R.drawable.rancnge_background_choose);
        sortRang.setBackgroundResource(R.drawable.rancnge_background);
        sortNoImg.setImageResource(R.drawable.rang_unchoose);
        sortRangImg.setImageResource(R.drawable.rang_choose);
        WebSettings settings = paperReport.getSettings();
        settings.setJavaScriptEnabled(true);
    }

    @Override
    public BasePresent getBasePresent() {
        return present;
    }

    @Override
    public void showStudentInfo(int high, int low) {
        paperHighStudent.setText(getString(R.string.score_book_paper_high_student) + " : " + high);
        paperLowStudent.setText(getString(R.string.score_book_paper_low_student) + " : " + low);
    }

    @Override
    public void showReport(String html){
        paperReport.loadDataWithBaseURL(null,html,"text/html","utf-8",null);
    }

    @Override
    public void initListener() {
        sortNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                present.setSortByNo(true);
                sortNo.setBackgroundResource(R.drawable.rancnge_background_choose);
                sortRang.setBackgroundResource(R.drawable.rancnge_background);
                sortNoImg.setImageResource(R.drawable.rang_unchoose);
                sortRangImg.setImageResource(R.drawable.rang_choose);
            }
        });

        sortRang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                present.setSortByNo(false);
                sortNo.setBackgroundResource(R.drawable.rancnge_background);
                sortRang.setBackgroundResource(R.drawable.rancnge_background_choose);

                sortNoImg.setImageResource(R.drawable.rang_choose);
                sortRangImg.setImageResource(R.drawable.rang_unchoose);
            }
        });
    }

}
