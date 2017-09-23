package com.sunvote.xpadapp.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.sunvote.xpadapp.MainActivity;
import com.sunvote.xpadapp.R;
import com.sunvote.xpadapp.base.BaseFragment;
import com.sunvote.xpadapp.utils.SharedPreferencesUtil;
import com.sunvote.xpadcomm.XPadApi;
import com.sunvote.xpadcomm.XPadApiInterface.VoteInfo;

public class SigninFragment extends BaseFragment {
	private TextView tv;
	private Button btnSignin;
	private VoteInfo voteInfo;
	View bgView;
	
	public void setInfo(VoteInfo info) {
		voteInfo = info;
	}
	 
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		Log.d("SigninFragment", "onCreateView");
		View view = inflater.inflate(R.layout.fragment_signin, container, false);
		view.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				;
			}
		});
		bgView = view.findViewById(R.id.signin_bg);
		
		
		tv = (TextView)view.findViewById(R.id.signin_title);

		btnSignin = (Button)view.findViewById(R.id.signin_btnSign);
		btnSignin.setEnabled(false);
		btnSignin.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				tv.setText(getString(R.string.signining));
				 MainActivity mact = (MainActivity)getActivity();
				 mact.presenter.submitVote( XPadApi.AnsType_Single,"1");
				 btnSignin.setEnabled(false);
				 
			}
		});
		 
		if(voteInfo!=null){
			onVoteEvent(voteInfo);
		}
		return view;
	}
	
	@Override 
	public void onVoteEvent( VoteInfo info) {
		 if(info.mode ==XPadApi.VoteType_Signin){
			 btnSignin.setEnabled(true);
			 
		 }else if(info.mode == XPadApi.VoteType_Stop){
			 btnSignin.setEnabled(false);
		 }
		 if(SharedPreferencesUtil.getData(getActivity(),"signin","").equals(voteInfo.dataPos + "&true")){
			 onVoteSubmitSuccess();
		 }
		
	}
	
	
	
	
	
	@Override
	public void onVoteSubmitSuccess() {
		tv.setText(getString(R.string.signined));
		btnSignin.setEnabled(false);
	//	bgView.setBackgroundColor(0xFF009966);
		SharedPreferencesUtil.saveData(getActivity(),"signin",voteInfo.dataPos + "&true");

 
	}
	@Override
	public void onVoteSubmitError() {
		super.onVoteSubmitError();
		tv.setText(getString(R.string.please_signin));
		 btnSignin.setEnabled(true);
	}
	
}
