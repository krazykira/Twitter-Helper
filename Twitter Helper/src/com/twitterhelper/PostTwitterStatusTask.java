package com.twitterhelper;

import android.content.Context;
import android.os.AsyncTask;

class PostTwitterStatusTask extends AsyncTask<Void, Void, Void> {
	private Context mContext;
	private boolean showDialog;
	private TwitterStatusCallback mCallback;
	private Exception exception;
	private String statusMessage;
	private twitter4j.Status responseStatus;

	public PostTwitterStatusTask(Context mContext, String statusToPost,
			TwitterStatusCallback mCallback, boolean showProgressDialog) {
		statusMessage = statusToPost;
		showDialog = showProgressDialog;
		this.mCallback = mCallback;
	}

	@Override
	protected void onPreExecute() {
		if (showDialog) {
			CommonMethods.showProgressDialog(mContext,
					Constants.TWITTER_POST_STATUS, false);
		}
		super.onPreExecute();
	}

	@Override
	protected Void doInBackground(Void... params) {
		try {
			responseStatus = TwitterHelper.postStatus(mContext, statusMessage);
		} catch (Exception e) {
			exception = e;
		}
		return null;
	}

	@Override
	protected void onPostExecute(Void result) {
		if (exception == null) {
			mCallback.onPostSuccessfull(responseStatus);
		} else {
			if (mCallback != null)
				mCallback.onPostFailed(exception);
		}
		super.onPostExecute(result);
	}

}
