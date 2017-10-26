package com.sunvote.txpad.ui.fragment.manager;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.sunvote.txpad.R;
import com.sunvote.txpad.base.BaseActivity;
import com.sunvote.txpad.base.BaseFragment;
import com.sunvote.txpad.base.BasePresent;
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
    private View chLine ;
    private TextView ch;
    private View deviceCheck;
    private View connectAgain;
    private AlertDialog chooseChDialog ;
    private BaseStationFragmentPresent present;
    private View signModePressKey ;
    private View signModeBackground ;
    private View signModeRandom;

    private View s52plus;
    private View s50;
    private View s50plus;
    private View s56;
    private View m30;


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
        connectAgain = findViewById(R.id.connect_again);
        signModePressKey = findViewById(R.id.sign_mode_presskey);
        signModeBackground = findViewById(R.id.sign_mode_background);
        signModeRandom = findViewById(R.id.sign_mode_random);
        ch = findViewById(R.id.ch);
        chLine = findViewById(R.id.ch_line);
        s52plus = findViewById(R.id.s52plus);
        s50 = findViewById(R.id.s50);
        s50plus = findViewById(R.id.s50plus);
        s56 = findViewById(R.id.s56);
        m30 = findViewById(R.id.m30);

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

        connectAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                present.connectAgain();
            }
        });

        chLine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showChoose();
            }
        });

        signModePressKey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                present.saveSignMode();
            }
        });

        signModeBackground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                present.saveSignUID();
            }
        });

        signModeRandom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                present.saveSignBlindSn();
            }
        });

        s52plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                present.showChooseKeyboardType(1);
            }
        });

        s50.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                present.showChooseKeyboardType(2);
            }
        });

        s50plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                present.showChooseKeyboardType(3);
            }
        });

        s56.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                present.showChooseKeyboardType(4);
            }
        });

        m30.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                present.showChooseKeyboardType(5);
            }
        });
    }

    @Override
    public BasePresent getBasePresent() {
        return present;
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

    @Override
    public void refeash() {
        BaseActivity baseActivity = getBaseActivity();
        if(baseActivity != null){
            baseActivity.initBootviewData();
        }
    }

    @Override
    public void showChoose() {
        if(chooseChDialog == null){
            AlertDialog.Builder builder = new AlertDialog.Builder(getBaseActivity());
            builder.setTitle(R.string.examination_ch_choose_title);
            chooseChDialog = builder.create();
            View rootView = LayoutInflater.from(getBaseActivity()).inflate(R.layout.input_ch_edittext,null);
            final EditText input = rootView.findViewById(R.id.input);
            TextView positive = rootView.findViewById(R.id.positive);
            TextView cancel = rootView.findViewById(R.id.cancel);
            chooseChDialog.setView(rootView);
            chooseChDialog.setCanceledOnTouchOutside(true);
            positive.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String text = input.getText().toString();
                    present.writeChanel(text);
                    chooseChDialog.dismiss();
                }
            });
            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    chooseChDialog.dismiss();
                }
            });
        }
        chooseChDialog.show();
    }

    @Override
    public void showFail() {
        showToast(R.string.basestation_mananger_input_error);
    }

    @Override
    public void showSignMode(int mode) {
        if(mode == 1){
            signModePressKey.setBackgroundResource(R.drawable.rancnge_background_55);
            signModeBackground.setBackgroundResource(R.drawable.rancnge_background);
            signModeRandom.setBackgroundResource(R.drawable.rancnge_background);
        }else if(mode == 2){
            signModePressKey.setBackgroundResource(R.drawable.rancnge_background);
            signModeBackground.setBackgroundResource(R.drawable.rancnge_background_55);
            signModeRandom.setBackgroundResource(R.drawable.rancnge_background);
        }else{
            signModePressKey.setBackgroundResource(R.drawable.rancnge_background);
            signModeBackground.setBackgroundResource(R.drawable.rancnge_background);
            signModeRandom.setBackgroundResource(R.drawable.rancnge_background_55);
        }
        getBaseActivity().initBootviewData();
    }

    @Override
    public void showChooseKeyboardType(int type) {
        s52plus.setBackgroundResource(R.drawable.rancnge_background);
        s50.setBackgroundResource(R.drawable.rancnge_background);
        s50plus.setBackgroundResource(R.drawable.rancnge_background);
        s56.setBackgroundResource(R.drawable.rancnge_background);
        m30.setBackgroundResource(R.drawable.rancnge_background);
        switch (type){
            case 1:
                s52plus.setBackgroundResource(R.drawable.rancnge_background_55);
                break;
            case 2:
                s50.setBackgroundResource(R.drawable.rancnge_background_55);
                break;
            case 3:
                s50plus.setBackgroundResource(R.drawable.rancnge_background_55);
                break;
            case 4:
                s56.setBackgroundResource(R.drawable.rancnge_background_55);
                break;
            case 5:
                m30.setBackgroundResource(R.drawable.rancnge_background_55);
                break;
        }
        getBaseActivity().initBootviewData();
    }
}
