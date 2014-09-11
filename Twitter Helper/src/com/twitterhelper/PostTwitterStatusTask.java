package com.twitterhelper;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;

class PostTwitterStatusTask extends AsyncTask<Void, Void, Void> {
	private Context mContext;
	private boolean showDialog;
	private TwitterStatusCallback mCallback;
	private Exception exception;
	private String tweetMessage;
	private twitter4j.Status responseStatus;
	private Bitmap bitmapImage;

	public PostTwitterStatusTask(Context mContext, String tweet, Bitmap bitmap,
			TwitterStatusCallback mCallback, boolean showProgressDialog) {
		this.mContext = mContext;
		tweetMessage = tweet;
		showDialog = showProgressDialog;
		this.mCallback = mCallback;
		bitmapImage = bitmap;
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
			responseStatus = TwitterHelper.postStatus(mContext, tweetMessage,
					bitmapImage);
		} catch (Exception e) {
			exception = e;
		}
		return null;
	}

	@Override
	protected void onPostExecute(Void result) {
		CommonMethods.dismissProgressDialog();
		if (exception == null) {
			mCallback.onPostSuccessfull(responseStatus);
		} else {
			if (mCallback != null)
				mCallback.onPostFailed(exception);
		}
		super.onPostExecute(result);
	}

}
