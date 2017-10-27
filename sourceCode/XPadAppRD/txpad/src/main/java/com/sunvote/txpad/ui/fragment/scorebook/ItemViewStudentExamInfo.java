package com.sunvote.txpad.ui.fragment.scorebook;

import android.view.LayoutInflater;
import android.view.View;

import com.sunvote.txpad.R;
import com.sunvote.txpad.base.BaseActivity;

/**
 * Created by XXW on 2017/10/27.
 */

public class ItemViewStudentExamInfo implements IItemViewStudentExamInfoView {

    private BaseActivity baseActivity;
    private View rootView;

    public ItemViewStudentExamInfo(BaseActivity baseActivity){
        this.baseActivity = baseActivity;
        init();
    }
    @Override
    public void showNetError() {
        baseActivity.showNetError();
    }

    @Override
    public void showProgress() {
        baseActivity.showProgress();
    }

    @Override
    public void dismissProgress() {
        baseActivity.dismissProgress();
    }

    private void init(){
        rootView = LayoutInflater.from(baseActivity).inflate(R.layout.item_view_page_paper,null);
        initView();
        initData();
        initListener();
        initMVP();
    }

    public void initView() {
    }

    public void initData(){

    }

    public void initListener(){

    }

    public void initMVP(){

    }

    public View getRootView() {
        return rootView;
    }
}
