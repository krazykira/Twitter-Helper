package com.twitterhelper;

import twitter4j.Status;

public interface TwitterStatusCallback {
	public void onPostSuccessfull(Status status);

	public void onPostFailed(Exception e);

}
