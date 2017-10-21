package com.sunvote.txpad.ui.fragment.scorebook;

import android.app.Dialog;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import com.sunvote.txpad.R;
import com.sunvote.txpad.base.BaseFragment;
import com.sunvote.txpad.base.BasePresent;
import com.sunvote.txpad.bean.ClassStudent;
import com.sunvote.txpad.view.DataPickerDialog;

/**
 * Created by Elvis on 2017/9/29.
 * Email:Eluis@psunsky.com
 * 版权所有：长沙中天电子设计开发有限公司
 * Description: 易测（ETest）
 */
public class ScoreBookFragment extends BaseFragment implements IScoreBookFragmentView{

    private ScoreBookPresent present;
    private ViewPager content;
    private ScoreBookContentAdapter adapter;

    private TextView className ;
    private TextView classStudentNum ;

    private View chooseStartTime;
    private View chooseEndTime ;
    private DataPickerDialog dataPickerDialog;
    private TextView chooseStartTimeText;
    private TextView chooseEndTimeText ;

    private TextView paperStatistics;
    private TextView personnelStatistics;
    private View paperStatisticsImg;
    private View personnelStatisticsImg;

    @Override
    public void initMVP() {
        present = new ScoreBookPresent();
        present.setView(this);
        present.setModel(new ScoreBookModel());
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_score_book;
    }

    @Override
    public void initView() {
        content = findViewById(R.id.score_book_content);
        className = findViewById(R.id.class_name);
        classStudentNum = findViewById(R.id.class_students_num);
        chooseStartTime = findViewById(R.id.choose_start_time);
        chooseEndTime = findViewById(R.id.choose_end_time);
        paperStatistics = findViewById(R.id.paper_statistics);
        personnelStatistics = findViewById(R.id.personnel_statistics);
        paperStatisticsImg = findViewById(R.id.paper_statistics_img);
        personnelStatisticsImg = findViewById(R.id.personnel_statistics_img);
        chooseStartTimeText = findViewById(R.id.choose_start_time_text);
        chooseEndTimeText = findViewById(R.id.choose_end_time_text);
    }

    @Override
    public void initData() {
        adapter = new ScoreBookContentAdapter(getBaseActivity());
        content.setAdapter(adapter);
        dataPickerDialog = new DataPickerDialog(getBaseActivity());
        paperStatisticsImg.setVisibility(View.VISIBLE);
        personnelStatisticsImg.setVisibility(View.GONE);
        present.init();
    }

    @Override
    public BasePresent getBasePresent() {
        return present;
    }

    public void showClassInfo(ClassStudent classStudent) {
        if(classStudent != null){
            className.setText(classStudent.getClassName());
            classStudentNum.setText(getString(R.string.home_fragment_class_student_num)
                    + classStudent.getStudentCount() + getString(R.string.home_fragment_class_student_flag));
        }
    }

    @Override
    public void initListener() {
        chooseStartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dataPickerDialog.what = 1;
                dataPickerDialog.show();
                initDatePicketTime(present.getStartTime());
            }
        });

        chooseEndTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dataPickerDialog.what = 2;
                dataPickerDialog.show();
                initDatePicketTime(present.getEndTime());
            }
        });

        paperStatistics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(adapter.getCount() > 0) {
                    paperStatisticsImg.setVisibility(View.VISIBLE);
                    personnelStatisticsImg.setVisibility(View.GONE);
                    content.setCurrentItem(0);
                }
            }
        });

        personnelStatistics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(adapter.getCount() > 1){
                    paperStatisticsImg.setVisibility(View.GONE);
                    personnelStatisticsImg.setVisibility(View.VISIBLE);
                    content.setCurrentItem(1);
                }else{
                    showToast("请期待");
                }
            }
        });

        dataPickerDialog.setOnDatePickerListener(new DataPickerDialog.OnDatePickerListener() {
            @Override
            public void onDatePicker(String data, int year, int month, int dayOfMonth, Object object, int what) {
                String time = year + "-";
                if(month < 10){
                    time += "0" + month;
                }else{
                    time += month;
                }
                time += "-" ;
                if(dayOfMonth < 10){
                    time += "0" + dayOfMonth;
                }else{
                    time += dayOfMonth;
                }

                if(what == 1){
                    showStartTime(time);
                }
                if(what == 2){
                    showEndTime(time);
                }
            }
        });
    }


    public void showStartTime(String time){
        present.setStartTime(time);
        chooseStartTimeText.setText(time);
        adapter.setStartTime(time);
    }

    public void showEndTime(String time){
        chooseEndTimeText.setText(time);
        present.setEndTime(time);
        adapter.setEndTime(time + "A");
    }

    public void initDatePicketTime(String date){
        String[] ds = date.split("-");
        if(ds.length >= 3) {
            int year = Integer.parseInt(ds[0]);
            int month = Integer.parseInt(ds[1]);
            int dayOfMonth = Integer.parseInt(ds[2]);
            dataPickerDialog.initDate(year, month, dayOfMonth);
        }
    }

}
