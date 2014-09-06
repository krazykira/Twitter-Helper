package com.twitterhelper;

import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;

class LoginAsyncTask extends AsyncTask<Void, Void, Void> {

	private Context context;
	private Exception exception;
	private TwitterLoginCallback mListener;

	public LoginAsyncTask() {
		mListener = TwitterHelper.twitterLoginListener;
		context = TwitterHelper.mContext;
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
			logintoTwitter();
		} catch (Exception e) {
			exception = e;
		}

		return null;
	}

	@Override
	protected void onPostExecute(Void result) {
		CommonMethods.dismissProgressDialog();
		if (exception == null) {
			Uri uri = Uri.parse(TwitterHelper.requestToken
					.getAuthenticationURL());
			TwitterLoginDialog.showLoginDialog(context, uri);
		} else {
			mListener.onLoginFailed(exception);
		}
		super.onPostExecute(result);
	}

	public void logintoTwitter() throws TwitterException {
		ConfigurationBuilder builder = new ConfigurationBuilder();
		builder.setOAuthConsumerKey(TwitterHelper.CONSUMER_KEY);
		builder.setOAuthConsumerSecret(TwitterHelper.CONSUMER_SECRET);
		Configuration configuration = builder.build();
		TwitterFactory factory = new TwitterFactory(configuration);
		TwitterHelper.twitter = factory.getInstance();
		TwitterHelper.requestToken = TwitterHelper.twitter
				.getOAuthRequestToken(TwitterHelper.CALLBACK_URL);
	}
}
