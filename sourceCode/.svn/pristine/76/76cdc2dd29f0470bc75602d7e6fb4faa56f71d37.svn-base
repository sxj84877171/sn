package com.sunvote.txpad.ui.sign;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.sunvote.txpad.R;
import com.sunvote.txpad.view.PickerScrollView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Elvis on 2017/9/20.
 * Email:Eluis@psunsky.com
 * 版权所有：长沙中天电子设计开发有限公司
 * Description: 易测（ETest）
 */
public class SettingPagerAdapter extends PagerAdapter {

    private int currentPostion = 0;
    private int minuteTime = 0;
    private int secondTime = 0;
    /**
      这里对应协议中
      “1” 按键签到模式，用户按“OK” 签到
      “2” User-ID 或签到码签到（输入数字，用户号或密码）
      “4” 定制签到
     */
    private int signType = 1;
    private int oldKeyBoardID = 0;
    private int newKayBoardID = 0;
    private Context context;
    private List<View> list = new ArrayList<>();

    public SettingPagerAdapter(Context context){
        this.context = context;
    }

    public void initData(){
        list.clear();
        View examinationView = getExaminationView(context);
        list.add(examinationView);
        View signMode = getSignModeView(context);
        list.add(signMode);
        View repaceView = getRepaceView(context);
        list.add(repaceView);
        notifyDataSetChanged();
    }

    private View getRepaceView(Context context) {
        View rootView = LayoutInflater.from(context).inflate(R.layout.keyboard_replace,null);
        final PickerScrollView oldKeyboard = rootView.findViewById(R.id.old_keyboard);
        List<String> data = new ArrayList<>();
        for(int i= 0 ; i <= 120; i++){
            if(i< 10){
                data.add("0" + i);
            }else{
                data.add("" + i) ;
            }
        }
        oldKeyboard.setData(data);
        oldKeyboard.setSelected(0);
        oldKeyboard.setOnSelectListener(new PickerScrollView.onSelectListener() {
            @Override
            public void onSelect(String pickers) {
                oldKeyBoardID = Integer.parseInt(pickers);
            }
        });
        PickerScrollView newKeyboard = rootView.findViewById(R.id.new_keyboard);
        data = new ArrayList<>();
        for(int i= 0 ; i <= 120; i++){
            if(i< 10){
                data.add("0" + i);
            }else{
                data.add("" + i) ;
            }
        }
        newKeyboard.setOnSelectListener(new PickerScrollView.onSelectListener() {
            @Override
            public void onSelect(String pickers) {
                newKayBoardID = Integer.parseInt(pickers);
            }
        });
        newKeyboard.setData(data);
        newKeyboard.setSelected(0);
        return rootView;
    }

    private View getExaminationView(Context context) {
        View rootView = LayoutInflater.from(context).inflate(R.layout.examination_time,null);
        PickerScrollView minute = rootView.findViewById(R.id.minute);
        List<String> data = new ArrayList<>();
        for(int i= 0 ; i <= 120; i++){
            if(i< 10){
                data.add("0" + i);
            }else{
                data.add("" + i) ;
            }
        }
        minute.setFixed(" 分钟");
        minute.setData(data);
        minute.setSelected(0);
        minute.setOnSelectListener(new PickerScrollView.onSelectListener() {
            @Override
            public void onSelect(String pickers) {
                minuteTime = Integer.parseInt(pickers);
            }
        });
        PickerScrollView second = rootView.findViewById(R.id.second);
        data = new ArrayList<>();
        for(int i= 0 ; i < 60; i++){
            if(i< 10){
                data.add("0" + i);
            }else{
                data.add("" + i) ;
            }
        }
        second.setFixed(" 秒");
        second.setOnSelectListener(new PickerScrollView.onSelectListener() {
            @Override
            public void onSelect(String pickers) {
                secondTime = Integer.parseInt(pickers);
            }
        });
        second.setData(data);
        second.setSelected(0);
        return rootView;
    }

    private View getSignModeView(Context context) {
        View rootView = LayoutInflater.from(context).inflate(R.layout.sign_mode,null);
        View signModePressKey = rootView.findViewById(R.id.sign_mode_presskey);
        View signModeBackground = rootView.findViewById(R.id.sign_mode_background);
        View signModeRandom = rootView.findViewById(R.id.sign_mode_random);
        final  ImageView signModePressKeyImg = rootView.findViewById(R.id.sign_mode_presskey_img);
        final ImageView signModeBackgroundImg = rootView.findViewById(R.id.sign_mode_background_img);
        final ImageView signModeRandomImg = rootView.findViewById(R.id.sign_mode_random_img);
        signModePressKeyImg.setImageResource(0);
        signModeBackgroundImg.setImageResource(0);
        signModeRandomImg.setImageResource(0);
        signModePressKey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signModePressKeyImg.setImageResource(R.drawable.choose_selected);
                signModeBackgroundImg.setImageResource(0);
                signModeRandomImg.setImageResource(0);
                signType = 1 ;//查看变量定义

            }
        });
        signModeBackground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signModePressKeyImg.setImageResource(0);
                signModeBackgroundImg.setImageResource(R.drawable.choose_selected);
                signModeRandomImg.setImageResource(0);
                signType = 2 ;
            }
        });
//        signModeRandom.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                signModePressKeyImg.setImageResource(0);
//                signModeBackgroundImg.setImageResource(0);
//                signModeRandomImg.setImageResource(R.mipmap.ic_launcher);
//                signType = 4 ;
//            }
//        });
        switch (signType){
            case 1:signModePressKeyImg.setImageResource(R.drawable.choose_selected);break;
            case 2:signModeBackgroundImg.setImageResource(R.drawable.choose_selected);break;
            case 4:signModeRandomImg.setImageResource(R.drawable.choose_selected);break;
        }
        return rootView;
    }

    @Override
    public int getCount() {
        return list.size();
    }


    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        currentPostion = position;
        container.addView(list.get(position));
        return list.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(list.get(position));
    }


    public int getCurrentPostion() {
        return currentPostion;
    }

    public int getMinuteTime() {
        return minuteTime;
    }

    public int getNewKayBoardID() {
        return newKayBoardID;
    }

    public int getOldKeyBoardID() {
        return oldKeyBoardID;
    }

    public int getSecondTime() {
        return secondTime;
    }

    public int getSignType() {
        return signType;
    }

    /**
     * 必须在initData前面使用
     * @param signType
     */
    public void setSignType(int signType) {
        this.signType = signType;
    }
}
