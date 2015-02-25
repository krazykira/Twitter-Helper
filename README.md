Twitter-Helper
==============
A Twitter integration library for Android that helps making twitter integration very easy. Using Twitter Helper you can:
  - Login and logout from Twitter
  - Post Simple Tweets
  - Post Tweets with Image
  

Before integrating Twitter into your Android app you need to create it on [Twitter Developer site]

### Configuring App on Twitter

Skip this step if you have already got twitter app configured.

You will need a **consumer key** and a **consumer secret** (collectively, a “Twitter app”) to use Twitter helper. You can create or review an application’s settings at Twitter’s developer site
  - Go to your Twitter application’s settings at Twitter’s developer site.
  - Find your application and go to the permissions tab.
  - Select the appropriate permissions for your needs (e.g. “Read and write”)
  - If you are using login, add a placeholder URL in the Callback URL ﬁeld (eg. “http://example.com”).
  - Click update settings.
  
**Allow Sign in with Twitter:**
> If you wish to use Login with Twitter, be sure that Allow this application to be used to Sign in to Twitter is checked:

<br>

-----

**Follow the guidelines below in order to integrate Twitter into your Android app.**

### Adding the Permission

Add internet permission to your project

```sh
 <uses-permission android:name="android.permission.INTERNET" />
   
```

### Initializing Twitter Helper Singleton

You can initialize the Twitter Helper singleton in either your Application class or in the start of your launch Activity. 
```sh
TwitterHelper.initialize(TWITTER_CONSUMER_KEY, TWITTER_CONSUMER_SECRET);

```

>If you forget to initialize Twitter Helper then it will cause exceptions. so **DONT FORGET TO INITIALIZE Twitter Helper Singleton**

### Logging In to Twitter

Once you have initialized the Twitter helper singleton then you can call the login method of Twitter Helper class like this.
```sh
TwitterHelper.logIntoTwitter(mContext, new TwitterLoginCallback() {
			
			@Override
			public void onLoginSuccess() {
				// Login Successfull so move Forward
				
			}
			
			@Override
			public void onLoginFailed(Exception e) {
				// Login Failed
				
			}
		});
```
In case of successfull login the auth key is automatically saved in Shared Preferences and would be used for posting tweets.

### Posting Simple Tweets and Tweets with Images on Twitter

If your not logged in then this method will automatically start the login process.
Once logged in to Twitter, you can post tweets on Twitter using the *postStatusInBackground* method. 

>The 3rd argument in the  method below is for **bitmapImage**. 
* You can pass in null for posting **simple tweets**.
* You can pass in bitmapImage for **posting tweets with images**.

```sh
TwitterHelper.postStatusInBackground(context, tweet, bitmapImage, showProgressDialog, new TwitterStatusCallback() {
			
			@Override
			public void onPostSuccessfull(Status status) {
				// Post Successfull
				
			}
			
			@Override
			public void onPostFailed(Exception e) {
				// Post Failed
				
			}
		});
```


### Logout From Twitter
You can call the method below once you want to logout from Twitter. This method will delete the auth token from shared preferences and the next time you would have to login again.

```sh
 TwitterHelper.logoutFromTwitter();
   
```



License
=======

    Copyright 2015 Sheraz Ahmad Khilji

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.


**For those who use this library don't forget to tell me how was it**

**HAPPY CODING ^_^**

[Twitter Developer site]:https://apps.twitter.com/
