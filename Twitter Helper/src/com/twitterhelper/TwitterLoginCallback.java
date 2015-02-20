package com.twitterhelper;

public interface TwitterLoginCallback {

	public void onLoginFailed(Exception e);

	public void onLoginSuccess();
}
