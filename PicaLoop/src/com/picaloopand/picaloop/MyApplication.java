package com.picaloopand.picaloop;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;

public class MyApplication extends android.app.Application {

    private static MyApplication instance;
	private static final String GOOGLESIGNIN = "google";
	private static final String FACEBOOKSIGNIN = "facebook";
	private static final String PCLSIGNIN = "pcl";

    public MyApplication() {
    	instance = this;
    }

    public static Context getContext() {
    	return instance;
    }
    
    public static void signOut(MyApplication app, SharedPreferences userProfile){
    	String userSignInMethod;
        Editor editProfile;
        Intent welcomeIntent = new Intent(app.getApplicationContext(), WelcomeActivity.class);
	    welcomeIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            	
        userSignInMethod = userProfile.getString("userSignIn", null);
		
        if(userSignInMethod != null){
			editProfile = userProfile.edit();
	        editProfile.putString("userSignIn", null);
	        editProfile.commit();
			if(userSignInMethod.contains(GOOGLESIGNIN)){
				//MyApplication.signOutFromGplus(app, mGoogleApiClient);
				//app.getApplicationContext().startActivity(new Intent(app.getApplicationContext(), WelcomeActivity.class));
				app.getApplicationContext().startActivity(welcomeIntent);
			}
			if(userSignInMethod.contains(FACEBOOKSIGNIN)){
				Toast.makeText(app.getApplicationContext(), "signOut Clicked! facebooklogin", Toast.LENGTH_LONG).show();
				//app.getApplicationContext().startActivity(new Intent(app.getApplicationContext(), WelcomeActivity.class));
				app.getApplicationContext().startActivity(welcomeIntent);
			}
			if(userSignInMethod.contains(PCLSIGNIN)){
			//	Toast.makeText(app.getApplicationContext(), "signOut Clicked! pcl", Toast.LENGTH_LONG).show();
				//app.getApplicationContext().startActivity(new Intent(app.getApplicationContext(), WelcomeActivity.class));
				app.getApplicationContext().startActivity(welcomeIntent);
			}
         }
    	
    }
    
    
	public static void signOutFromGplus(MyApplication app, GoogleApiClient googleApi) {
	  //  if (googleApi.isConnected()) {
	    //    Plus.AccountApi.clearDefaultAccount(googleApi);
	      //  googleApi.disconnect();
	     //   googleApi.connect();
	      //  updateUI(false);
		    Intent welcomeIntent = new Intent(app.getApplicationContext(), WelcomeActivity.class);
		    welcomeIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			app.getApplicationContext().startActivity(welcomeIntent);
			
	   // }
	} 

}