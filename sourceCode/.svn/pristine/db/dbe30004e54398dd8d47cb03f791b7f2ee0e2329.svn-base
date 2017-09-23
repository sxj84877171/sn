package com.sunvote.txpad.ui.main;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.sunvote.sunvotesdk.BaseStationManager;
import com.sunvote.txpad.ApplicationData;
import com.sunvote.txpad.R;
import com.sunvote.txpad.base.BaseActivity;
import com.sunvote.txpad.ui.fragment.dce_check.DeviceCheckFragment;
import com.sunvote.txpad.ui.fragment.exam.PaperActivityFragment;
import com.sunvote.txpad.ui.fragment.manager.BaseStationManagerFragment;
import com.sunvote.txpad.ui.namelist.NameListActivity;
import com.sunvote.txpad.ui.vexam.ExaminationViewActivity;

/**
 * Created by Elvis on 2017/9/18.
 * Email:Eluis@psunsky.com
 * 版权所有：长沙中天电子设计开发有限公司
 * Description: 易测（ETest）
 */
public class HomeActivity extends BaseActivity implements IHomeActivityView{

    private View managerView ;
    private View nameLinear;
    private View pageLinear;
    private View messageLinear;
    private TextView username;

    private PaperActivityFragment paperActivityFragment;
    private BaseStationManagerFragment managerFragment;
    private DeviceCheckFragment deviceCheckFragment;
    private Fragment currentFragment ;
    private HomePresent present;

    @Override
    public int getLayoutID() {
        return R.layout.activity_home;
    }

    @Override
    public void initMVP() {
        present = new HomePresent();
        present.setView(this);
        present.setModel(new HomeModel());
        present.init();
    }

    @Override
    protected void onDestroy() {
        present.onDestroy();
        super.onDestroy();
    }

    @Override
    public void initView() {
        managerView = getViewById(R.id.manager_linear);
        nameLinear = getViewById(R.id.name_linear);
        pageLinear = getViewById(R.id.page_linear);
        messageLinear = getViewById(R.id.message_linear);
        username = getViewById(R.id.username);
    }

    @Override
    public void showPaperFragment(){
        if(currentFragment == null || currentFragment != paperActivityFragment) {
            FragmentManager fm = getFragmentManager();
            FragmentTransaction transaction = fm.beginTransaction();
            if (paperActivityFragment == null) {
                paperActivityFragment = new PaperActivityFragment();
            }
            paperActivityFragment.setClassStudent(ApplicationData.getInstance().getClassStudent());
            transaction.replace(R.id.content, paperActivityFragment);
            transaction.commit();
            currentFragment = paperActivityFragment;
        }
    }

    @Override
    public void showManagerFrament() {
        if(currentFragment == null || currentFragment != managerFragment) {
            FragmentManager fm = getFragmentManager();
            FragmentTransaction transaction = fm.beginTransaction();
            if (managerFragment == null) {
                managerFragment = new BaseStationManagerFragment();
            }
            transaction.replace(R.id.content, managerFragment);
            transaction.commit();
            currentFragment = managerFragment;
        }
    }


    @Override
    public void initListener() {
        managerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                present.showManagerFrament();
            }
        });

        nameLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), NameListActivity.class);
                getActivity().startActivity(intent);
            }
        });

        pageLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                present.showPaperFragment();
            }
        });

        messageLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
    }

    @Override
    public void initData() {
        username.setText(ApplicationData.getInstance().getLoginInfo().getName());
    }

    private long lastBackPressed ;
    @Override
    public void onBackPressed() {
        if(System.currentTimeMillis() - lastBackPressed > 1000){
            lastBackPressed = System.currentTimeMillis();
        }else{
            finish();
        }
    }

    public void showDevideCheckFragment(){
        if(currentFragment == null || currentFragment != deviceCheckFragment) {
            FragmentManager fm = getFragmentManager();
            FragmentTransaction transaction = fm.beginTransaction();
            if(deviceCheckFragment == null){
                deviceCheckFragment = new DeviceCheckFragment();
            }
            transaction.replace(R.id.content, deviceCheckFragment);
            transaction.commit();
            currentFragment = deviceCheckFragment;
        }
    }
}
