package com.sunvote.txpad.ui.fragment.dce_check;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.sunvote.txpad.ApplicationData;
import com.sunvote.txpad.ApplicationDataManager;
import com.sunvote.txpad.R;
import com.sunvote.txpad.base.BaseFragment;
import com.sunvote.txpad.bean.Student;
import com.sunvote.txpad.ui.main.HomeActivity;
import com.sunvote.txpad.ui.sign.StudentAdapter;
import com.sunvote.util.SPUtils;

import java.util.List;
import java.util.Locale;

/**
 * Created by Elvis on 2017/9/19.
 * Email:Eluis@psunsky.com
 * 版权所有：长沙中天电子设计开发有限公司
 * Description: 易测（ETest）  设备检测
 */
public class DeviceCheckFragment extends BaseFragment implements IDeviceCheckFragmentView{

    private GridView students;

    private DeviceCheckFragmentPresent present;
    private StudentAdapter adapter;
    private View startTest;
    private ImageView startTestImg;
    private TextView startTestText ;
    private TextView onlineText;
    private TextView offlineText;
    private TextView weakText;
    private View back ;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_device_check;
    }

    @Override
    public void initMVP() {
        present = new DeviceCheckFragmentPresent();
        present.setView(this);
        present.setModel(new DeviceCheckFragmentMode());
    }

    @Override
    public void initView() {
        students = findViewById(R.id.students);
        startTest = findViewById(R.id.start_test);
        startTestText = findViewById(R.id.start_test_text);
        startTestImg = findViewById(R.id.start_test_img);
        onlineText = findViewById(R.id.online_text);
        offlineText = findViewById(R.id.offline_text);
        weakText = findViewById(R.id.weak_text);
        back = findViewById(R.id.back);
    }

    @Override
    public void initData() {
        adapter = new StudentAdapter();
        adapter.setMode(StudentAdapter.MODE_CHECK_ONLINE);
        students.setAdapter(adapter);
        ApplicationDataManager.getInstance().setOfflineAllKeyboard();
        showStudents(ApplicationData.getInstance().getStudentList());
        int online = ApplicationDataManager.getInstance().getOnlineStudentCount();
        int all = ApplicationDataManager.getInstance().getStudentCount();
        int weak = ApplicationDataManager.getInstance().getWeakStudentCount();
        showBottomInfo(online,all-online,weak);
    }

    @Override
    public void initListener() {
        startTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                present.test();
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HomeActivity homeActivity = (HomeActivity)getBaseActivity();
                if(homeActivity != null){
                    homeActivity.showManagerFrament();
                }
            }
        });
    }

    @Override
    public void showStartTest() {
        startTestImg.setImageResource(R.drawable.sign_start);
        startTestText.setText(R.string.basestation_manager_begin_check);
    }

    @Override
    public void showStopTest() {
        startTestImg.setImageResource(R.drawable.sign_stop);
        startTestText.setText(R.string.basestation_manager_stop_check);
    }

    @Override
    public void showStudents(List<Student> students) {
        adapter.showStudents(students);
    }

    @Override
    public void showBottomInfo(int online, int offline, int weak) {
        onlineText.setText(getString(R.string.basestation_manager_check_online) + online);
        offlineText.setText(getString(R.string.basestation_manager_check_offline) + offline);
        weakText.setText(getString(R.string.basestation_manager_check_weak_current) + weak);
    }


    protected void selectLanguage(String language) {
        //设置语言类型
        Resources resources = getResources();
        Configuration configuration = resources.getConfiguration();
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        switch (language) {
            case "en":
                configuration.locale = Locale.ENGLISH;
                break;
            case "zh":
                configuration.locale = Locale.SIMPLIFIED_CHINESE;
                break;
            default:
                configuration.locale = Locale.SIMPLIFIED_CHINESE;
        }
        resources.updateConfiguration(configuration, displayMetrics);

        //保存设置语言的类型
        SPUtils.putString(getActivity(),"language", language);
    }

    @Override
    public void onPause() {
        super.onPause();
        present.stopTest();
    }
}
