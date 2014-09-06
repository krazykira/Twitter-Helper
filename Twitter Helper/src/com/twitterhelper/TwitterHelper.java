package com.twitterhelper;

import twitter4j.*;
import twitter4j.conf.*;
import twitter4j.auth.*;
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
		// Shared Preferences
		mSharedPreferences = mContext.getSharedPreferences(
				Constants.PREFERENCE_NAME, Context.MODE_PRIVATE);
	}

	public static void logIntoTwitter(Context context,
			TwitterLoginCallback mListener) {
		mContext = context;
		twitterLoginListener = mListener;
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
		if (isTwitterLoggedIn()) {
			twitterLoginListener.onLoginSuccess();

		} else {
			LoginAsyncTask lbt = new LoginAsyncTask();
			lbt.execute();
		}
	}

	/**
	 * Post status on Twitter in background thread
	 * 
	 * @param mContext
	 * @param status
	 * @param mCallback
	 *            this will be called when background process gets completed
	 * @param showProgress
	 */
	public static void postStatusInBackground(final Context mContext,
			final String status, final boolean showProgress,
			final TwitterStatusCallback mCallback) {
		if (isTwitterLoggedIn()) {
			PostTwitterStatusTask pst = new PostTwitterStatusTask(mContext,
					status, mCallback, showProgress);
			pst.execute();
		} else
			logIntoTwitter(mContext, new TwitterLoginCallback() {

				@Override
				public void onLoginSuccess() {
					PostTwitterStatusTask pst = new PostTwitterStatusTask(
							mContext, status, mCallback, showProgress);
					pst.execute();

				}

				@Override
				public void onLoginFailed(Exception e) {
					CommonMethods.showAlertDialog(mContext,
							"Twitter Login Failed", e.getMessage());
				}
			});

	}

	/**
	 * Post status to Twitter
	 * 
	 * @param status
	 *            which the user wants to post on Twitter
	 * @return Twitter status Response
	 * @throws TwitterException
	 */
	public static Status postStatus(String status) throws TwitterException {
		// Update status
		twitter4j.Status response = null;
		if (isTwitterLoggedIn()) {
			ConfigurationBuilder builder = new ConfigurationBuilder();
			builder.setOAuthConsumerKey(CONSUMER_KEY);
			builder.setOAuthConsumerSecret(CONSUMER_SECRET);

			// Access Token
			String access_token = mSharedPreferences.getString(
					Constants.PREF_KEY_OAUTH_TOKEN, "");
			// Access Token Secret
			String access_token_secret = mSharedPreferences.getString(
					Constants.PREF_KEY_OAUTH_SECRET, "");

			AccessToken accessToken = new AccessToken(access_token,
					access_token_secret);
			Twitter twitter = new TwitterFactory(builder.build())
					.getInstance(accessToken);

			// Update status
			response = twitter.updateStatus(status);
		} else {
			CommonMethods.showAlertDialog(mContext, "Twitter Not Logged in",
					"Log into twitter to continue");
		}
		return response;
	}

	/**
	 * Check user already logged in your application using twitter Login flag is
	 * fetched from Shared Preferences
	 * */
	static boolean isTwitterLoggedIn() {
		// return twitter login status from Shared Preferences
		return mSharedPreferences.getBoolean(Constants.PREF_KEY_TWITTER_LOGIN,
				false);
	}

}
