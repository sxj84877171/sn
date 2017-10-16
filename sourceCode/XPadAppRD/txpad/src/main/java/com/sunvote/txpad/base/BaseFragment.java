package com.sunvote.txpad.base;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sunvote.util.LogUtil;

/**
 * Created by Elvis on 2017/9/8.
 * Email:Eluis@psunsky.com
 * 版权所有：长沙中天电子设计开发有限公司
 * Description:平板模块键盘投票功能模块
 */
public class BaseFragment extends Fragment implements BaseFragmentView{

    protected View rootView ;

    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {
        try{
            int resId = getLayoutId();
            if(resId != 0){
                rootView = inflater.inflate(resId, container, false);
                initView();
                initMVP();
                initData();
                initListener();
            }
        }catch (Exception ex){
            LogUtil.e("BaseFragment",ex);
        }
        return rootView;
    }

    /**
     * 初始化框架
     */
    public void initMVP() {
    }

    /**
     * 获取对应的资源文件
     * @return
     */
    public int getLayoutId(){
        return 0;
    }

    /**
     * 初始化控件
     */
    public void initView(){
        LogUtil.i("BaseFragment","initView");
    }

    /**
     * 获取对应的View
     */
    public <T extends View> T findViewById(int resId){
        return (T)rootView.findViewById(resId);
    }

    /**
     * 初始化控件事件
     */
    public void initListener(){
        LogUtil.i("BaseFragment","initListener");
    }

    /**
     * 初始化数据
     */
    public void initData(){
        LogUtil.i("BaseFragment","initData");
    }

    /**
     * 获取对应的activity
     * @return
     */
    public BaseActivity getBaseActivity() {
        return (BaseActivity)getActivity();
    }

    /**
     * 显示网络错误
     */
    @Override
    public void showNetError() {
        getBaseActivity().showNetError();
    }

    /**
     * 显示进度条
     */
    @Override
    public void showProgress() {
        getBaseActivity().showProgress();
    }

    /**
     * 隐藏进度条
     */
    @Override
    public void dismissProgress() {
        BaseActivity baseActivity = getBaseActivity();
        if(baseActivity != null){
            baseActivity.dismissProgress();
        }
    }

    public void showToast(String msg){
        getBaseActivity().showToast(msg);
    }

    public void showToast(int resId){
        getBaseActivity().showToast(resId);
    }
}
