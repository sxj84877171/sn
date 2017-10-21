package com.sunvote.txpad.ui.fragment.scorebook;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.sunvote.txpad.R;
import com.sunvote.txpad.base.BaseActivity;
import com.sunvote.txpad.bean.PaperScore;
import com.sunvote.txpad.ui.report.PaperReportActivity;

import java.util.List;

/**
 * Created by Elvis on 2017/9/30.
 * Email:Eluis@psunsky.com
 * 版权所有：长沙中天电子设计开发有限公司
 * Description: 易测（ETest）
 */
public class ItemViewPagePaper implements IItemViewPagePaperView{

    private BaseActivity mBaseActivity ;
    private ItemViewPagePaperPresent present;
    private PaperScoreAdapter paperScoreAdapter;

    private View paperAnalog;
    private ImageView paperAnalogChoose;
    private View paperZhenti;
    private ImageView paperZhentiChoose;
    private View paperPersonal;
    private ImageView paperPersonalChoose;
    private TextView paperPersonalNum;
    private TextView paperAnalogNum;
    private TextView paperZhentiNum;
    private TextView paperAllNum;

    private ListView paperScoreList;

    public ItemViewPagePaper(BaseActivity context){
        this.mBaseActivity = context;
        init();
    }

    private View rootView ;

    public View getRootView() {
        return rootView;
    }

    private void init(){
        rootView = LayoutInflater.from(mBaseActivity).inflate(R.layout.item_view_page_paper,null);
        initView();
        initData();
        initListener();
        initMVP();
    }

    private void initMVP() {
        present = new ItemViewPagePaperPresent();
        present.setView(this);
        present.setModel(new ItemViewPagePaperModel());
        present.init();
    }

    public void initView(){
        paperAnalog = rootView.findViewById(R.id.paper_analog);
        paperAnalogChoose = rootView.findViewById(R.id.paper_analog_choose);
        paperZhenti = rootView.findViewById(R.id.paper_zhenti);
        paperZhentiChoose = rootView.findViewById(R.id.paper_zhenti_choose);
        paperPersonal = rootView.findViewById(R.id.paper_personal);
        paperPersonalChoose = rootView.findViewById(R.id.paper_personal_choose);
        paperAnalogNum = rootView.findViewById(R.id.paper_analog_num);
        paperPersonalNum = rootView.findViewById(R.id.paper_personal_num);
        paperZhentiNum = rootView.findViewById(R.id.paper_zhenti_num);
        paperAllNum = rootView.findViewById(R.id.paper_all_num);
        paperScoreList = rootView.findViewById(R.id.paper_score_list);
    }

    public void initData(){
        paperScoreAdapter = new PaperScoreAdapter();
        paperScoreList.setAdapter(paperScoreAdapter);
        paperScoreList.setDivider(null);
        paperScoreList.setDividerHeight(0);
    }

    public void initListener(){
        paperAnalog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                present.fill(1,0,0);
            }
        });
        paperZhenti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                present.fill(0,1,0);
            }
        });
        paperPersonal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                present.fill(0,0,1);
            }
        });
        paperScoreAdapter.setOnItemPaperListener(new PaperScoreAdapter.OnItemPaperListener() {
            @Override
            public void onItemOnClick(View view, int i, PaperScore paperScore) {
                gotoPaperReport(paperScore);
            }
        });
    }

    @Override
    public void showNetError() {
        if(mBaseActivity != null) {
            mBaseActivity.showNetError();
        }
    }

    @Override
    public void showProgress() {
        if(mBaseActivity != null) {
            mBaseActivity.showProgress();
        }
    }

    @Override
    public void dismissProgress() {
        if(mBaseActivity != null){
            mBaseActivity.dismissProgress();
        }
    }

    @Override
    public void showChooseType(boolean mn, boolean zt, boolean personal) {
        if(mn){
            paperAnalogChoose.setImageResource(R.drawable.choose);
        }else{
            paperAnalogChoose.setImageResource(0);
        }
        if(zt){
            paperZhentiChoose.setImageResource(R.drawable.choose);
        }else{
            paperZhentiChoose.setImageResource(0);
        }
        if(personal){
            paperPersonalChoose.setImageResource(R.drawable.choose);
        }else{
            paperPersonalChoose.setImageResource(0);
        }
    }

    @Override
    public void showTestPaperInfo(int all, int mn, int zt, int personal) {
        paperAllNum.setText(getString(R.string.score_book_type_all) + ":" + all);
        paperAnalogNum.setText(getString(R.string.score_book_type_analog) + ":" + mn);
        paperZhentiNum.setText(getString(R.string.score_book_type_zhenti) + ":" + zt);
        paperPersonalNum.setText(getString(R.string.score_book_type_personal) + ":" + personal);
    }

    @Override
    public void showData(List<PaperScore> paperScoreList) {
        paperScoreAdapter.setPaperScoreList(paperScoreList);
    }

    private String getString(int resId){
        return mBaseActivity.getString(resId);
    }

    public void setStartTime(String startTime){
        present.setStartTime(startTime);
    }

    public void setEndTime(String endTime){
        present.setEndTime(endTime);
    }

    public void gotoPaperReport(PaperScore paperScore){
        Intent intent = new Intent(mBaseActivity,PaperReportActivity.class);
        intent.putExtra("PaperScore",paperScore);
        mBaseActivity.startActivity(intent);
    }
}
