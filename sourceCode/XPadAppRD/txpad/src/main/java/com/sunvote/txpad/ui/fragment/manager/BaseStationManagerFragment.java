package com.sunvote.txpad.ui.fragment.manager;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.sunvote.txpad.R;
import com.sunvote.txpad.base.BaseActivity;
import com.sunvote.txpad.base.BaseFragment;
import com.sunvote.txpad.ui.main.HomeActivity;

/**
 * Created by Elvis on 2017/9/18.
 * Email:Eluis@psunsky.com
 * 版权所有：长沙中天电子设计开发有限公司
 * Description: 易测（ETest）
 */
public class BaseStationManagerFragment extends BaseFragment implements IBaseStationFragmentView{

    private Button freeMode;
    private Button fixedMode;
    private TextView ch;
    private View deviceCheck;
    private BaseStationFragmentPresent present;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_base_station_manager;
    }

    @Override
    public void initMVP() {
        present = new BaseStationFragmentPresent();
        present.setView(this);
        present.setModel(new BaseStationFragmentModel());
        present.init();
    }

    @Override
    public void initView() {
        freeMode = findViewById(R.id.free_mode);
        fixedMode = findViewById(R.id.fixed_mode);
        deviceCheck = findViewById(R.id.device_check);
        ch = findViewById(R.id.ch);
    }

    @Override
    public void initListener() {
        freeMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                present.setFreeMode();
            }
        });

        fixedMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                present.setFixedMode();
            }
        });

        deviceCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                present.deviceCheck();
            }
        });
    }

    @Override
    public void showKeyPadWorkingMode(String mode) {
        if("1".equals(mode)){
            fixedMode.setBackgroundResource(R.drawable.rancnge_background_55);
            freeMode.setBackgroundResource(R.drawable.rancnge_background);
        }else{
            fixedMode.setBackgroundResource(R.drawable.rancnge_background);
            freeMode.setBackgroundResource(R.drawable.rancnge_background_55);
        }
        getBaseActivity().initBootviewData();
    }

    @Override
    public void showKeyPadModel(String mode) {

    }

    @Override
    public void showLanguage(String language) {

    }

    @Override
    public void showCh(String chMsg) {
        ch.setText(chMsg);
    }

    @Override
    public void showCheckFragment() {
        BaseActivity baseActivity = getBaseActivity();
        if(baseActivity instanceof HomeActivity) {
            HomeActivity homeActivity = (HomeActivity) baseActivity;
            homeActivity.showDevideCheckFragment();
        }
    }
}
