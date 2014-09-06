package com.twitterhelper;

import twitter4j.Twitter;
import twitter4j.auth.RequestToken;
import android.content.Context;
import android.content.SharedPreferences;

public class TwitterHelper {

	/**
	 * Register your here app https://dev.twitter.com/apps/new and get your
	 * consumer key and secret
	 * */
	static String CONSUMER_KEY = "";
	static String CONSUMER_SECRET = "";
	static final String CALLBACK_SCHEME = "x-oauthflow-twitter";
	static final String CALLBACK_URL = CALLBACK_SCHEME + "://callback";

	// Shared Preferences
	static SharedPreferences mSharedPreferences;
	static Context mContext;
	// Twitter
	static Twitter twitter;
	static RequestToken requestToken;
	static TwitterLoginCallback twitterLoginListener;

	public static void initialize(String TWITTER_CONSUMER_KEY,
			String TWITTER_CONSUMER_SECRET) {
		CONSUMER_KEY = TWITTER_CONSUMER_KEY;
		CONSUMER_SECRET = TWITTER_CONSUMER_SECRET;
	}

	public static void logIntoTwitter(Context context,
			TwitterLoginCallback mListener) {
		mContext = context;
		twitterLoginListener = mListener;
		// Shared Preferences
		mSharedPreferences = mContext.getSharedPreferences(
				Constants.PREFERENCE_NAME, Context.MODE_PRIVATE);
		// Check if Internet present
		if (!InternetDetector.isConnectingToInternet(mContext)) {
			String error = "Please connect to working Internet connection";
			Exception e = new Exception(error);
			// Internet Connection is not present
			CommonMethods.showAlertDialog(mContext,
					"Internet Connection Error", error);
			// stop executing code by return
			if (twitterLoginListener != null)
				twitterLoginListener.onLoginFailed(e);
		}

		// Check if twitter keys are set
		if (CONSUMER_KEY.trim().length() == 0
				|| CONSUMER_SECRET.trim().length() == 0) {
			String error = "Please set your twitter oauth tokens first!";
			Exception e = new Exception(error);
			CommonMethods.showAlertDialog(mContext, "Twitter oAuth tokens",
					error);
			// stop executing code by return
			if (twitterLoginListener != null)
				twitterLoginListener.onLoginFailed(e);

		}
		if (isTwitterLoggedInAlready()) {
			twitterLoginListener.onLoginSuccess();

		} else {
			LoginAsyncTask lbt = new LoginAsyncTask();
			lbt.execute();
		}
	}

	/**
	 * Check user already logged in your application using twitter Login flag is
	 * fetched from Shared Preferences
	 * */
	static boolean isTwitterLoggedInAlready() {
		// return twitter login status from Shared Preferences
		return mSharedPreferences.getBoolean(Constants.PREF_KEY_TWITTER_LOGIN,
				false);
	}

}
