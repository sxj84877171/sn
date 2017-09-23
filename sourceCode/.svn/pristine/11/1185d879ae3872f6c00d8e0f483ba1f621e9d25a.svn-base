package com.sunvote.txpad.ui.sign;

import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.sunvote.sunvotesdk.BaseStationManager;
import com.sunvote.txpad.Constants;
import com.sunvote.txpad.R;
import com.sunvote.util.SPUtils;

/**
 * Created by Elvis on 2017/9/20.
 * Email:Eluis@psunsky.com
 * 版权所有：长沙中天电子设计开发有限公司
 * Description: 易测（ETest）
 */
public class SettingPanel {

    private View settingPanel;

    private View examitationTime;

    private View examitationReplaceKeyboard;

    private View examitationSignMode;

    private View examitationTimeChoose;

    private View examitationReplaceKeyboardChoose;

    private View examitationSignModeChoose;

    private ViewPager contentPanel;

    private TextView close;

    private TextView save;

    private int current = 0;

    private SettingPagerAdapter pagerAdapter;

    public <T extends View> T findViewById(int resId) {
        return (T) settingPanel.findViewById(resId);
    }

    public SettingPanel(View settingPanel) {
        this.settingPanel = settingPanel;
        pagerAdapter = new SettingPagerAdapter(settingPanel.getContext());
        int type = SPUtils.getInt(settingPanel.getContext(), Constants.SAVE_SIGN_MODE_KEY,0);
        pagerAdapter.setSignType(type);
        pagerAdapter.initData();
    }

    public void initView() {
        examitationTime = findViewById(R.id.examitation_time);
        examitationReplaceKeyboard = findViewById(R.id.examitation_replace_keyboard);
        examitationSignMode = findViewById(R.id.examitation_sign_mode);
        examitationTimeChoose = findViewById(R.id.examitation_time_choose);
        examitationReplaceKeyboardChoose = findViewById(R.id.examitation_replace_keyboard_choose);
        examitationSignModeChoose = findViewById(R.id.examitation_sign_mode_choose);

        contentPanel = findViewById(R.id.content_panel);
        close = findViewById(R.id.close);
        save = findViewById(R.id.save);
        showSelected(1);
    }

    public void initListener(){
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveDataToLocal();
                dismiss();
            }
        });

        examitationTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSelected(1);
            }
        });

        examitationSignMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSelected(2);
            }
        });

        examitationReplaceKeyboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSelected(3);
            }
        });

        contentPanel.setAdapter(pagerAdapter);
        contentPanel.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                current = position;
                showSelected(position+1);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    public void show(){
        settingPanel.setVisibility(View.VISIBLE);
    }

    public void dismiss(){
        settingPanel.setVisibility(View.GONE);
    }

    public int getColor(int resId){
        return ContextCompat.getColor(settingPanel.getContext(), resId);
    }

    public void showSelected(int selected){
        examitationTimeChoose.setBackgroundColor(getColor(R.color.black));
        examitationReplaceKeyboardChoose.setBackgroundColor(getColor(R.color.black));
        examitationSignModeChoose.setBackgroundColor(getColor(R.color.black));
        switch (selected){
            case 1:
                examitationTimeChoose.setBackgroundColor(getColor(R.color.textview_choose_color));
                break;
            case 2:
                examitationSignModeChoose.setBackgroundColor(getColor(R.color.textview_choose_color));
                break;
            case 3:
                examitationReplaceKeyboardChoose.setBackgroundColor(getColor(R.color.textview_choose_color));
                break;
        }
        contentPanel.setCurrentItem(selected-1);
    }

    public void saveDataToLocal(){
        switch (current){
            case 0:
                int times = pagerAdapter.getMinuteTime() * 60 + pagerAdapter.getSecondTime();
                SPUtils.putInt(settingPanel.getContext(), Constants.SAVE_EXAM_TIME_KEY,times);
                break;
            case 1:
                SPUtils.putInt(settingPanel.getContext(), Constants.SAVE_SIGN_MODE_KEY,pagerAdapter.getSignType());
                break;
            case 2:
                break;
        }
    }
}
