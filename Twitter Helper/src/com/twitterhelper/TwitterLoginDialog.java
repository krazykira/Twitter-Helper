package com.twitterhelper;

import android.R.style;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.net.Uri;
import android.util.Log;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

class TwitterLoginDialog {
	private static Dialog mDialog;

	public static void showLoginDialog(Context mContext, Uri uri) {
		mDialog = new Dialog(mContext,
				style.Theme_DeviceDefault_Dialog_NoActionBar_MinWidth);
		mDialog.setContentView(getWebView(mContext, uri.toString()));
		mDialog.setCancelable(true);
		mDialog.setCanceledOnTouchOutside(true);
		mDialog.setOnCancelListener(new OnCancelListener() {

			@Override
			public void onCancel(DialogInterface dialog) {
				dialog.dismiss();
				TwitterHelper.twitterLoginListener.onLoginFailed(null);

			}
		});
		mDialog.show();

	}

	private static WebView getWebView(Context mContext, String url) {
		WebView mWebView = new WebView(mContext);
		// Let's display the progress in the activity title bar, like the
		// browser app does.
		// mActivity.getWindow().requestFeature(Window.FEATURE_PROGRESS);
		mWebView.getSettings().setJavaScriptEnabled(true);

		mWebView.setWebChromeClient(new WebChromeClient() {
			@Override
			public void onProgressChanged(WebView view, int progress) {
				// Activities and WebViews measure progress with different
				// scales.
				// The progress meter will automatically disappear when we reach
				// 100%
				// mActivity.setProgress(progress * 1000);
				if (progress > 90)
					CommonMethods.dismissProgressDialog();

			}
		});
		mWebView.setWebViewClient(new WebViewClient() {

			@Override
			public void onReceivedError(WebView view, int errorCode,
					String description, String failingUrl) {
				mDialog.dismiss();
				Uri uri = Uri.parse(failingUrl);
				if (uri.getScheme().toString()
						.equals(TwitterHelper.CALLBACK_SCHEME)) {
					fetchAccessToken(uri);
				} else {
					TwitterHelper.twitterLoginListener
							.onLoginFailed(new Exception(description));
				}
			}

		});

		mWebView.loadUrl(url);
		return mWebView;

	}

	/**
	 * Start fetching Access token
	 * 
	 * @param uri
	 *            to use for fetching access token
	 */
	private static void fetchAccessToken(Uri uri) {
		if (uri != null
				&& uri.toString().startsWith(TwitterHelper.CALLBACK_URL)) {
			Log.i("retrieveAndSaveOauth", "Callback received : " + uri);
			Log.i("retrieveAndSaveOauth", "Retrieving Access Token");
			RetrieveAccessTokenTask task = new RetrieveAccessTokenTask(uri);
			task.execute();
		}
	}

}
