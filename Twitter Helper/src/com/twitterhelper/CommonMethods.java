package com.twitterhelper;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;

class CommonMethods {
	private static ProgressDialog mProgressDialog;

	/**
	 * Show progress dialog with title
	 * 
	 * @param mContext
	 * @param title
	 * @param message
	 * @param cancelable
	 */
	public final static void showProgressDialog(Context mContext, String title,
			String message, boolean cancelable) {
		mProgressDialog = new ProgressDialog(mContext);
		mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		mProgressDialog.setTitle(title);
		mProgressDialog.setMessage(message);
		mProgressDialog.setCancelable(cancelable);
		mProgressDialog.show();
	}

	/**
	 * Show progress dialog without title
	 * 
	 * @param mContext
	 * @param message
	 * @param cancelable
	 */
	public final static void showProgressDialog(Context mContext,
			String message, boolean cancelable) {
		mProgressDialog = new ProgressDialog(mContext);
		mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		mProgressDialog.setMessage(message);
		mProgressDialog.setCancelable(cancelable);
		mProgressDialog.show();
	}

	/**
	 * Dismiss Progress dialog
	 */
	public final static void dismissProgressDialog() {
		if (mProgressDialog != null && mProgressDialog.isShowing())
			mProgressDialog.dismiss();
	}

	public static void showAlertDialog(Context context, String message) {
		AlertDialog.Builder bld = new AlertDialog.Builder(context);
		bld.setMessage(message);
		bld.setNeutralButton("OK", null);
		bld.create().show();
	}

	public static void showAlertDialog(Context context, String title,
			String message) {
		AlertDialog.Builder bld = new AlertDialog.Builder(context);
		bld.setTitle(title);
		bld.setMessage(message);
		bld.setNeutralButton("OK", null);
		bld.create().show();
	}

}
