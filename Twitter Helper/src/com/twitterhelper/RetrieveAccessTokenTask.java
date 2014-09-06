package com.twitterhelper;

import twitter4j.TwitterException;
import twitter4j.User;
import twitter4j.auth.AccessToken;
import android.content.Context;
import android.content.SharedPreferences.Editor;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

class RetrieveAccessTokenTask extends AsyncTask<Void, Void, Void> {
	private Context context;
	private Uri uri;
	private Exception exception;
	private TwitterLoginCallback mListener;

	public RetrieveAccessTokenTask(Uri uri) {
		context = TwitterHelper.mContext;
		mListener = TwitterHelper.twitterLoginListener;
		this.uri = uri;
		exception = null;
	}

	@Override
	protected void onPreExecute() {
		CommonMethods.showProgressDialog(context,
				R.string.dialog_default_message, false);
		super.onPreExecute();
	}

	@Override
	protected Void doInBackground(Void... params) {
		try {
			saveOAuthToken();
		} catch (Exception e) {
			exception = e;
		}

		return null;
	}

	@Override
	protected void onPostExecute(Void result) {
		CommonMethods.dismissProgressDialog();
		if (exception == null)
			mListener.onLoginSuccess();
		else
			mListener.onLoginFailed(exception);
		super.onPostExecute(result);
	}

	private void saveOAuthToken() throws TwitterException {
		// oAuth verifier
		String verifier = uri
				.getQueryParameter(Constants.URL_TWITTER_OAUTH_VERIFIER);

		// Get the access token
		AccessToken accessToken = TwitterHelper.twitter.getOAuthAccessToken(
				TwitterHelper.requestToken, verifier);

		// Shared Preferences
		Editor e = TwitterHelper.mSharedPreferences.edit();

		// After getting access token, access token secret
		// store them in application preferences
		e.putString(Constants.PREF_KEY_OAUTH_TOKEN, accessToken.getToken());
		e.putString(Constants.PREF_KEY_OAUTH_SECRET,
				accessToken.getTokenSecret());
		// Store login status - true
		e.putBoolean(Constants.PREF_KEY_TWITTER_LOGIN, true);
		e.commit(); // save changes

		Log.e("Twitter OAuth Token", "> " + accessToken.getToken());

		// Getting user details from twitter
		// For now i am getting his name only
		long userID = accessToken.getUserId();
		User user = TwitterHelper.twitter.showUser(userID);
		String username = user.getName();

	}

}
