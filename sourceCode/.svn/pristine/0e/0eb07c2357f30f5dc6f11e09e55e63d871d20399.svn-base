package com.sunvote.xpadapp.base;

import com.sunvote.xpadapp.MainActivity;
import com.sunvote.xpadapp.R;
import com.sunvote.xpadcomm.XPadApi;
import com.sunvote.xpadcomm.XPadApiInterface.VoteInfo;

import android.app.Activity;
import android.app.Fragment;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

public class BaseFragment extends MyFragment {

	public static final String DATAPOS="DATAPOS";
	public static final String VOTEMODE="VOTEMODE";
	public static final String VOTEVALUE="VOTEVALUE";
	public static final String VOTEVALUES="VOTEVALUES";
	public BaseFragment() {

	}

	@Override
	public void onStart() {

		super.onStart();

		// new Handler().postDelayed(new Runnable(){
		// public void run() {
		// Log.d("BaseFragment", "getVoteStatus");
		// mMainActivity.presenter.getVoteStatus();
		// }
		// }, 500);

	}

	public void stopDownload() {

	}

	public void onVoteEvent(int baseId, int iMode, Object info) {
		// TODO Auto-generated method stub

	}

	public void onVoteEvent(VoteInfo info) {

	}

	public void onVoteSubmitSuccess(XPadApi.VoteResultItem item) {
		onVoteSubmitSuccess();
	}

	public void onVoteSubmitSuccess() {

	}

	public void onVoteSubmitError(XPadApi.VoteResultItem item) {
		onVoteSubmitError();
	}

	public void onVoteSubmitError() {
		Toast.makeText(getActivity(), getString(R.string.submit_error), Toast.LENGTH_LONG).show();
	}

	public void onVoteSubmitAllOkSuccess() {

	}


}
