package com.sunvote.xpadapp.fragments;

import com.sunvote.xpadapp.MainActivity;
import com.sunvote.xpadapp.R;
import com.sunvote.xpadapp.base.BaseFragment;
import com.sunvote.xpadapp.base.MyFragment;
import com.sunvote.xpadapp.db.DBManager;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

public class MeetingWelcomeFragment extends MyFragment {

	private TextView tv;
	long lastTapTime;
	int tapCount;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		Log.d("MeetingWelcome", "onCreateView");
		View view = inflater.inflate(R.layout.fragment_welcome, container, false);

		
		
		tv = (TextView) view.findViewById(R.id.welcome_title);
		View adminView = (View)view.findViewById(R.id.welcome_btn_admin);
		adminView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (System.currentTimeMillis() - lastTapTime > 400) {
					tapCount = 0;
					Log.d("OfflineFragment", "tapCount:0");
				} else {
					tapCount++;
					Log.d("OfflineFragment", "tapCount:" + tapCount);
				}
				lastTapTime = System.currentTimeMillis();
				if (tapCount >= 9) {
					tapCount = 0;
					showAdmin();
				}
			}
		});
		// String sname = mMainActivity.meetingInfo.meetingTitle;
		//tv.setTextColor(Color.RED);
		
		if(mMainActivity.meetingInfo!=null && mMainActivity.meetingInfo.meetingTitle !=null){
			tv.setText(mMainActivity.meetingInfo.meetingTitle);

		}else{
			Toast.makeText(getActivity(), "会议" + mMainActivity.meetingId + "不存在,请下载会议资料", Toast.LENGTH_LONG).show();
		}
		return view;
	}
	
	private void showAdmin(){
		FragmentManager fm = getFragmentManager();
		FragmentTransaction tx = fm.beginTransaction();
		AdminFragment fAdmin = new AdminFragment();
		tx.add(R.id.frame_content, fAdmin, "admin");
		tx.addToBackStack(null);
		tx.commitAllowingStateLoss();
	}

	boolean isAttach = false;

	@Override
	public void onResume() {
		super.onResume();
		isAttach = true;
	}

	@Override
	public void onPause() {
		super.onPause();
		isAttach = false;
	}

	public void setMeeting(){
		if(isAttach) {
			tv.setText(mMainActivity.meetingInfo.meetingTitle);
		}
	}
}
