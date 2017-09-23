package com.sunvote.xpadapp.fragments;

import com.sunvote.xpadapp.R;

import android.app.Activity;
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
import android.widget.TextView;

public class NoFileFragment extends Fragment {
	private TextView tv;
	public int meetingId;

	long lastTapTime;
	int tapCount;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		Log.d("MeetingWelcome", "onCreateView");
		View view = inflater.inflate(R.layout.fragment_no_file, container, false);

		tv = (TextView) view.findViewById(R.id.fragment_no_file_title);

		// String sname = mMainActivity.meetingInfo.meetingTitle;
		tv.setText(String.format( getString(R.string.no_file_tips),meetingId));

		TextView tv2 =  (TextView) view.findViewById(R.id.fragment_no_file_title_2);
		tv2.setText(getString(R.string.no_file_tips_download));

		View adminView = (View)view.findViewById(R.id.fragment_online_btn_admin);
		adminView.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (System.currentTimeMillis() - lastTapTime > 500) {
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
		return view;
	}

	private boolean isAttach = false ;

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

	public void setMeetingId(int meetingId) {
		this.meetingId = meetingId;
		if(isAttach){
			if(tv != null){
				tv.setText(String.format( getString(R.string.no_file_tips),meetingId));
			}
		}
	}

	private void showAdmin(){
		FragmentManager fm = getFragmentManager();
		FragmentTransaction tx = fm.beginTransaction();
		AdminFragment fAdmin = new AdminFragment();
		tx.add(R.id.frame_content, fAdmin, "admin");
		tx.addToBackStack(null);
		tx.commitAllowingStateLoss();
	}
}
