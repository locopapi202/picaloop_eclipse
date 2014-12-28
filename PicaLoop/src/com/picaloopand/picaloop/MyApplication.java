package com.picaloopand.picaloop;

import android.content.Context;
import android.content.Intent;

import com.google.android.gms.common.api.GoogleApiClient;

public class MyApplication extends android.app.Application {

    private static MyApplication instance;

    public MyApplication() {
    	instance = this;
    }

    public static Context getContext() {
    	return instance;
    }
    
	public static void signOutFromGplus(MyApplication app, GoogleApiClient googleApi) {
	  //  if (googleApi.isConnected()) {
	    //    Plus.AccountApi.clearDefaultAccount(googleApi);
	      //  googleApi.disconnect();
	     //   googleApi.connect();
	      //  updateUI(false);
			app.getApplicationContext().startActivity(new Intent(app.getApplicationContext(), WelcomeActivity.class));
			
	   // }
	} 

}