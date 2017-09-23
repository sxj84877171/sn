package com.sunvote.xpadapp.fragments;

import java.util.Timer;
import java.util.TimerTask;

import com.sunvote.udptransfer.Config;
import com.sunvote.xpadapp.R;
import com.sunvote.xpadapp.base.BaseFragment;
import com.sunvote.xpadapp.utils.SharedPreferencesUtil;
import com.sunvote.xpadcomm.ScreenUtil;
import com.sunvote.xpadcomm.XPadApi;
import com.sunvote.xpadcomm.XPadSystem;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PowerManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.view.View.OnClickListener;
import android.widget.Toast;

public class AdminFragment extends BaseFragment {
    public TextView tvSn;
    TextView tvModalInfo;
    String TAG = "AdminFragment";
    private int comTestCnt;
    private int comTestCnt2;
    private int comTestOkCnt;
    private ProgressDialog dlg = null;
    private Integer comTestInterval=20;
    private static final String COMTESTINTERVAL = "comTestInterval";
    private boolean stopComTest ;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admin, container, false);
        tvModalInfo = (TextView) view.findViewById(R.id.admin_modal_info);
        tvSn = (TextView) view.findViewById(R.id.admin_modal_sn);
        setKeySn();

        mMainActivity.adminFragment = this;
        comTestInterval = (Integer) SharedPreferencesUtil.getData(getActivity(),COMTESTINTERVAL,Integer.valueOf(20));
        try {
            tvModalInfo.setText(getString(R.string.app_version) + getVersionName() + /*"  " + getString(R.string.hardware_ver) + mMainActivity.mModelInfo.hModel +*/ "  " + getString(R.string.firmware_ver)
                    + Config.getInstance().VERSION_CODE + " " + getString(R.string.server_ip) + Config.getInstance().serverIP);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        ImageButton btnBack = (ImageButton) view.findViewById(R.id.admin_btnback);
        btnBack.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                timer.cancel();
                goBack();
            }
        });

        Button btnMatch = (Button) view.findViewById(R.id.admin_match);
        btnMatch.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("AdminFragment", "btnMatch");
                mMainActivity.presenter.execKeypadMatch(0, 0);
            }
        });

        Button btnConfig = (Button) view.findViewById(R.id.admin_config_mode);
        btnConfig.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.d("AdminFragment", "btnConfig");
                Toast.makeText(getActivity(),R.string.modify_id_msg,Toast.LENGTH_SHORT).show();
                mMainActivity.presenter.configMode();

            }
        });

        Button btnReboot = (Button) view.findViewById(R.id.admin_btnReboot);
        btnReboot.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage("确认要重启吗？");
                builder.setTitle("重启");
                builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        XPadSystem.rebootXPad(getActivity());
                    }
                });

                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                builder.create().show();


            }
        });

        Button btnPowerOff = (Button) view.findViewById(R.id.admin_btnShutdown);
        btnPowerOff.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage("确认要退出应用吗？");
                builder.setTitle("退出应用");
                builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
//                        XPadSystem.powerOffXPad(getActivity());
                        getActivity().finish();
                    }
                });

                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                builder.create().show();

            }
        });

        Button btnInterval = (Button) view.findViewById(R.id.admin_btnInterval);
        btnInterval.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                final EditText inputEt= new EditText(getActivity());
                inputEt.setText(String.format("%d",comTestInterval));
                inputEt.setSelectAllOnFocus(true);
                inputEt.setInputType(EditorInfo.TYPE_CLASS_PHONE);
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle(getString(R.string.setcomtest_interval)).setIcon(android.R.drawable.ic_dialog_info).setView(inputEt)
                        .setNegativeButton(getString(R.string.cancel), null);
                builder.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        inputEt.getText().toString();
                        comTestInterval = Integer.parseInt(inputEt.getText().toString());
                        SharedPreferencesUtil.saveData(getActivity(),COMTESTINTERVAL,comTestInterval);

                    }
                });
                builder.show();


//                final Thread thread = new Thread("getInfo") {
//                    public void run() {
//                        try {
//                            mMainActivity.presenter.getWorkMode();
//                            Thread.sleep(100);
//                            mMainActivity.presenter.getKeypadParam();
//                        }catch (Exception e){
//                            e.printStackTrace();
//                        }
//
//                    }
//                };
//                thread.start();
            }
        });

        Button btnComTest = (Button) view.findViewById(R.id.admin_btnComTest);
        //btnComTest.setVisibility(View.INVISIBLE);
        btnComTest.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
               /* comTestCnt = 0;
                comTestOkCnt = 0;
                comTestCnt2 = 0;
                dlg = new ProgressDialog(mMainActivity);
                dlg.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);// 设置水平进度条
                dlg.setCancelable(false);// 设置是否可以通过点击Back键取消
                dlg.setCanceledOnTouchOutside(false);// 设置在点击Dialog外是否取消Dialog进度条
                dlg.setIcon(R.drawable.ic_launcher);// 设置提示的title的图标，默认是没有的
                dlg.setTitle("串口通讯测试");
                dlg.setMax(200);
                dlg.setProgress(0);
                dlg.setMessage("串口通讯测试 收到次数:" + comTestCnt + "  成功次数:" + comTestOkCnt);
                dlg.setButton(DialogInterface.BUTTON_POSITIVE, "确定",
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                stopComTest = true;
                                mMainActivity.hideBottomUIMenu();
                                mMainActivity.showBottomUIMenu();
                              //  Toast.makeText(getActivity(),"收到次数："+ comTestCnt2,Toast.LENGTH_LONG).show();
                            }
                        });
                dlg.show();

                stopComTest = false;
                Thread checkCom = new Thread("checkCom") {
                    public void run() {
                        for (int i = 1; i <= 200; i++) {
                            if(stopComTest){
                                break;
                            }
                            mMainActivity.presenter.comCommunicationTest(i, comTestOkCnt);
                            dlg.setProgress(i);
                            try {
                                Thread.sleep(comTestInterval);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                };
                checkCom.start();*/

                XPadApi.getInstance().getClient().searchServarIp();
                Toast.makeText(getActivity(),"如果服务器IP地址变化，则自动转化一次",Toast.LENGTH_SHORT).show();

            }
        });


        timer.schedule(task, 1000, 1000);


        return view;
    }



    private static final int Msg_ComResponse = 1;
    private Handler mHandle = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case Msg_ComResponse:
                    Log.i(TAG, "handleMessage: on ComResponse");
                    comTestCnt++;
                    if (msg.arg1 != 0) {
                        comTestOkCnt++;
                    }
                    dlg.setMessage("串口通讯测试 收到次数:" + comTestCnt + "  成功次数:" + comTestOkCnt);
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    public void onResume() {
        // TODO Auto-generated method stub

        mMainActivity.showBottomUIMenu();
        super.onResume();
    }

    @Override
    public void onStop() {
        Log.d("adminFr", "onstop");

        mMainActivity.hideBottomUIMenu();
        super.onStop();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mMainActivity.adminFragment = null;
    }

    private final Timer timer = new Timer();
    private final Timer timerCom = new Timer();

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            super.handleMessage(msg);
            if (msg.what == 111) {
                setKeySn();

            }
        }
    };

    private TimerTask task = new TimerTask() {
        @Override
        public void run() {
            // TODO Auto-generated method stub
            Message message = new Message();
            message.what = 111;
            handler.sendMessage(message);
        }
    };




    public void setKeySn() {

        try {
            tvSn.setText("SN:" + mMainActivity.mOnlineInfo.keySn);
            tvModalInfo.setText(getString(R.string.app_version) + getVersionName() + /*"  " + getString(R.string.hardware_ver) + mMainActivity.mModelInfo.hModel +*/ "  " + getString(R.string.firmware_ver)
                    + Config.getInstance().VERSION_CODE+ " " + getString(R.string.server_ip) + Config.getInstance().serverIP);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private String getVersionName() throws Exception {
        String version = "";

        PackageManager packageManager = getActivity().getPackageManager();

        PackageInfo packInfo = packageManager.getPackageInfo(getActivity().getPackageName(), 0);
        version = packInfo.versionName;// + "." + packInfo.versionCode;

        return version;
    }

    private void goBack() {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction tx = fm.beginTransaction();
        tx.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out);
        //tx.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        tx.remove(AdminFragment.this);
        tx.commit();

    }


    public void onComCommunicationTest(final int sendn, final boolean checkOk) {
        Message msg = new Message();
        msg.what = Msg_ComResponse;
        msg.arg1 = checkOk ? 1 : 0;
        mHandle.sendMessage(msg);
        comTestCnt2++;
        Log.i(TAG, "onComCommunicationTest: send Msg_ComResponse");
    }

}
