package com.sunvote.txpad.base;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.sunvote.sunvotesdk.BaseStationManager;
import com.sunvote.sunvotesdk.basestation.BaseStationInfo;
import com.sunvote.txpad.R;
import com.sunvote.txpad.ui.manager.BaseStationManagerModel;

/**
 * Created by Elvis on 2017/9/6.
 * Email:Eluis@psunsky.com
 * 版权所有：长沙中天电子设计开发有限公司
 * Description:平板模块键盘投票功能模块
 *
 * 登录界面
 */
public abstract class BaseActivity extends AppCompatActivity {

    private TextView freePairing;
    private TextView keyboard;
    private TextView frequency;
    private TextView connectState ;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏
        setContentView(getLayoutID());
        initMVP();
        initView();
        initData();
        initListener();
        initBootview();
    }

    /**
     * 初始化控件按钮事件
     */
    public void initListener() {

    }

    /**
     * 初始化数据
     */
    public void initData() {

    }

    /**
     * 初始化控件ID
     */
    public void initView() {
    }

    /**
     * 获取资源文件
     * @return
     */
    public abstract int getLayoutID();

    /**
     * 转换对应控件
     * @param resId 资源ID
     * @param <T> 对应所需要的控件
     * @return 对应控件
     */
    public <T extends View> T getViewById(int resId){
        return (T)findViewById(resId);
    }

    /**
     * 获取该Activity对象
     * @return
     */
    public BaseActivity getActivity(){
        return this;
    }

    /**
     * 抛出toast 信息
     * @param msg
     */
    public void showToast(String msg){
        Toast.makeText(getActivity(),msg,Toast.LENGTH_SHORT).show();
    }

    /**
     * 抛出toast 信息
     * @param resId
     */
    public void showToast(int resId){
        showToast(getString(resId));
    }

    /**
     * 显示网络错误
     */
    public void showNetError() {
        showToast(R.string.network_error);
    }

    /**
     * 初始化框架
     */
    public abstract void initMVP();

    /***
     * 底层view的初始化
     */
    public void initBootview(){
        freePairing = getViewById(R.id.home_fragment_free_pairing);
        keyboard = getViewById(R.id.home_fragment_s52plus);
        frequency = getViewById(R.id.home_fragment_frequency);
        connectState = getViewById(R.id.connect_state);
    }

    /**
     * 初始化底层控件数据
     */
    public void initBootviewData(){
        if(freePairing != null){
            String workingMode = BaseStationManager.getInstance().getBaseStationInfo().getKeyPadWorkingMode();
            if("1".equals(workingMode)){
                freePairing.setText(R.string.home_fragment_fixed_pairing);
            }else{
                freePairing.setText(R.string.home_fragment_free_pairing);
            }
        }
        if(keyboard != null){
            String keyPadId = BaseStationManager.getInstance().getBaseStationInfo().getKeyPadID();
            if(keyPadId != null){
                freePairing.setText(keyPadId);
            }else{//TODO
                keyboard.setText(R.string.home_fragment_s52plus);
            }

        }

        if(frequency != null){
            String channel = BaseStationManager.getInstance().getBaseStationInfo().getBaseStationChannel();
            if(channel != null){
                frequency.setText("CH:" + channel);
            }else{
                frequency.setText(R.string.home_fragment_frequency);
            }
        }

        if(BaseStationManager.getInstance().getBaseStationInfo().isConnected()){
            if(connectState != null){
                connectState.setText("已连接");
            }
        }else{
            if(connectState != null){
                connectState.setText("未连接");
            }
        }
    }

    private ProgressDialog progressDialog;

    /**
     * 加载进度条
     */
    public void showProgress(){
        if(progressDialog == null) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setIndeterminate(false);
            progressDialog.setMessage(getString(R.string.dialog_loading));
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.setCancelable(false);
        }
        progressDialog.show();
    }

    /**
     * 隐藏进度条
     */
    public void dismissProgress(){
        if(progressDialog != null){
            progressDialog.dismiss();
        }
    }

    /**
     * 重新刷新数据
     */
    @Override
    protected void onResume() {
        super.onResume();
        initBootviewData();
    }
}
