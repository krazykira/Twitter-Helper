package com.twitterhelper;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface.OnClickListener;

class CommonMethods {
	private static ProgressDialog mProgressDialog;

	/**
	 * Show progress dialog with title
	 * 
	 * @param mContext
	 * @param titleId
	 *            set 0 for hiding title
	 * @param messageId
	 * @param cancelable
	 */
	public final static void showProgressDialog(Context mContext, int titleId,
			int messageId, boolean cancelable) {
		mProgressDialog = new ProgressDialog(mContext);
		mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		mProgressDialog.setTitle(mContext.getResources().getString(titleId));
		mProgressDialog
				.setMessage(mContext.getResources().getString(messageId));
		mProgressDialog.setCancelable(cancelable);
		mProgressDialog.show();
	}

	/**
	 * Show progress dialog without title
	 * 
	 * @param mContext
	 * @param messageId
	 * @param cancelable
	 */
	public final static void showProgressDialog(Context mContext,
			int messageId, boolean cancelable) {
		mProgressDialog = new ProgressDialog(mContext);
		mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		mProgressDialog
				.setMessage(mContext.getResources().getString(messageId));
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

	public static void showCustomAlertDialog(Context context, String title,
			String message, String positiveText,
			OnClickListener positiveListener, String negativeText,
			OnClickListener negativeListener) {
		AlertDialog.Builder bld = new AlertDialog.Builder(context);
		bld.setTitle(title);
		bld.setMessage(message);
		bld.setPositiveButton(positiveText, positiveListener);
		bld.setNegativeButton(negativeText, negativeListener);
		bld.create().show();
	}
}
