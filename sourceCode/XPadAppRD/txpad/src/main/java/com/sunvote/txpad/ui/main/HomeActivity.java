package com.sunvote.txpad.ui.main;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.sunvote.sunvotesdk.BaseStationManager;
import com.sunvote.txpad.ApplicationData;
import com.sunvote.txpad.ApplicationDataManager;
import com.sunvote.txpad.R;
import com.sunvote.txpad.base.BaseActivity;
import com.sunvote.txpad.base.BasePresent;
import com.sunvote.txpad.cache.FileCache;
import com.sunvote.txpad.ui.exam.ExaminationActivity;
import com.sunvote.txpad.ui.fragment.dce_check.DeviceCheckFragment;
import com.sunvote.txpad.ui.fragment.exam.PaperActivityFragment;
import com.sunvote.txpad.ui.fragment.manager.BaseStationManagerFragment;
import com.sunvote.txpad.ui.fragment.scorebook.ScoreBookFragment;
import com.sunvote.txpad.ui.login.LoginActivity;
import com.sunvote.txpad.ui.namelist.NameListActivity;
import com.sunvote.txpad.ui.vexam.ExaminationViewActivity;
import com.sunvote.util.LogUtil;

/**
 * Created by Elvis on 2017/9/18.
 * Email:Eluis@psunsky.com
 * 版权所有：长沙中天电子设计开发有限公司
 * Description: 易测（ETest）
 */
public class HomeActivity extends BaseActivity implements IHomeActivityView{

    public static final String TAG = "HomeActivity" ;

    private View managerView ;
    private View nameLinear;
    private View pageLinear;
    private View messageLinear;
    private View scoreBooklinear;
    private View header;
    private TextView username;

    private ImageView managerViewImg ;
    private ImageView nameLinearImg;
    private ImageView pageLinearImg;
    private ImageView messageLinearImg;
    private ImageView scoreBooklinearImg;

    private TextView managerViewText ;
    private TextView nameLinearText;
    private TextView pageLinearText;
    private TextView messageLinearText;
    private TextView scoreBooklinearText;

    private PaperActivityFragment paperActivityFragment;
    private BaseStationManagerFragment managerFragment;
    private DeviceCheckFragment deviceCheckFragment;
    private ScoreBookFragment scoreBookFragment;
    private Fragment currentFragment ;
    private HomePresent present;
    private AlertDialog examDialog ;

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
    public BasePresent getBasePresent() {
        return present;
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
        scoreBooklinear = getViewById(R.id.score_book_linear);

        managerViewImg = getViewById(R.id.manager_linear_img);
        nameLinearImg = getViewById(R.id.name_linear_img);
        pageLinearImg = getViewById(R.id.page_linear_img);
        messageLinearImg = getViewById(R.id.message_linear_img);
        scoreBooklinearImg = getViewById(R.id.score_book_linear_img);

        managerViewText = getViewById(R.id.manager_linear_text);
        nameLinearText = getViewById(R.id.name_linear_text);
        pageLinearText = getViewById(R.id.page_linear_text);
        messageLinearText = getViewById(R.id.message_linear_text);
        scoreBooklinearText = getViewById(R.id.score_book_linear_text);

        username = getViewById(R.id.username);
        header = getViewById(R.id.header);
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
    public void gotoLoginPage() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        exit();
    }

    @Override
    public void showExamDialog() {
        if(examDialog == null){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            examDialog = builder.setTitle(R.string.sign_no_time_remind_title).setMessage(R.string.exam_is_running).setPositiveButton(R.string.sign_no_time_remind_positive, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(HomeActivity.this,ExaminationActivity.class);
                    intent.putExtra("mode", ApplicationDataManager.MODE_CLASS_STUDENT);
                    startActivity(intent);
                }
            }).create();
        }
        examDialog.show();
    }

    @Override
    public void showChooseButton(int type) {
        managerViewImg.setImageResource(R.drawable.manager_unselected);
        nameLinearImg.setImageResource(R.drawable.roster_unselected);
        pageLinearImg.setImageResource(R.drawable.test_paper_bank_unselected);
        messageLinearImg.setImageResource(R.drawable.message_box_unselected);
        scoreBooklinearImg.setImageResource(R.drawable.score_book_unselected);


        nameLinearText.setTextColor(getResources().getColor(R.color.text_gray));
        pageLinearText.setTextColor(getResources().getColor(R.color.text_gray));
        messageLinearText.setTextColor(getResources().getColor(R.color.text_gray));
        scoreBooklinearText.setTextColor(getResources().getColor(R.color.text_gray));
        managerViewText.setTextColor(getResources().getColor(R.color.text_gray));

        switch (type){
            case 1:
                pageLinearImg.setImageResource(R.drawable.test_paper_bank_selected);
                pageLinearText.setTextColor(getResources().getColor(R.color.textview_item_student_title));
                break;
            case 2:
                nameLinearImg.setImageResource(R.drawable.roster_selected);
                nameLinearText.setTextColor(getResources().getColor(R.color.textview_item_student_title));
                break;
            case 3:
                messageLinearImg.setImageResource(R.drawable.message_box_selected);
                messageLinearText.setTextColor(getResources().getColor(R.color.textview_item_student_title));
                break;
            case 4:
                scoreBooklinearImg.setImageResource(R.drawable.score_book_selected);
                scoreBooklinearText.setTextColor(getResources().getColor(R.color.textview_item_student_title));
                break;
            case 5:
                managerViewImg.setImageResource(R.drawable.manager_selected);
                managerViewText.setTextColor(getResources().getColor(R.color.textview_item_student_title));
                break;
        }
    }


    @Override
    public void initListener() {
        managerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                present.showManagerFrament();
                showChooseButton(5);
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
                showChooseButton(1);
            }
        });

        messageLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showToast("请期待");
            }
        });

        scoreBooklinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               showScoreBookFragment();
                showChooseButton(4);
            }
        });

        header.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FileCache.getFileCache().deleteEncryptCatch("LoginInfo");
                gotoLoginPage();
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
            exit();
        }
    }

    @Override
    public void showScoreBookFragment(){
        if(currentFragment == null || currentFragment != scoreBookFragment){
            FragmentManager fm = getFragmentManager();
            FragmentTransaction transaction = fm.beginTransaction();
            if(scoreBookFragment == null){
                scoreBookFragment = new ScoreBookFragment();
            }
            transaction.replace(R.id.content, scoreBookFragment);
            transaction.commit();
            currentFragment = scoreBookFragment;
        }
    }

    @Override
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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getScreenDensity_ByWindowManager();
    }

    public void getScreenDensity_ByWindowManager(){
        DisplayMetrics mDisplayMetrics = new DisplayMetrics();//屏幕分辨率容器
        getWindowManager().getDefaultDisplay().getMetrics(mDisplayMetrics);
        int width = mDisplayMetrics.widthPixels;
        int height = mDisplayMetrics.heightPixels;
        float density = mDisplayMetrics.density;
        int densityDpi = mDisplayMetrics.densityDpi;
//        showToast("Screen Ratio: ["+width+"x"+height+"],density="+density+",densityDpi="+densityDpi);
//        showToast("Screen mDisplayMetrics: "+mDisplayMetrics);
        LogUtil.i(TAG,"Screen Ratio: ["+width+"x"+height+"],density="+density+",densityDpi="+densityDpi);
        LogUtil.i(TAG,"Screen mDisplayMetrics: "+mDisplayMetrics);
    }

    @Override
    protected void onResume() {
        super.onResume();
        present.onResume();
    }
}
