package com.picaloopand.picaloop;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.widget.Toast;

import com.activeandroid.ActiveAndroid;
import com.google.android.gms.common.api.GoogleApiClient;

public class MyApplication extends android.app.Application {

    private static MyApplication instance;
	private static final String GOOGLESIGNIN = "google";
	private static final String FACEBOOKSIGNIN = "facebook";
	private static final String PCLSIGNIN = "pcl";

    public MyApplication() {
    	instance = this;
    }
    
    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();
        
        //Notice this initialization code here
        ActiveAndroid.initialize(this);
    }

    public static Context getContext() {
    	return instance;
    }
    
    public static void openProfile(MyApplication app){
    	
        Intent userProfileIntent = new Intent(app.getApplicationContext(), UserProfileActivity.class);
        userProfileIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    	app.getApplicationContext().startActivity(userProfileIntent);
    	//Toast.makeText(app.getApplicationContext(), "open profile Clicked!", Toast.LENGTH_LONG).show();
    }
    
    public static void openSettings(MyApplication app){
    	
        Intent settingsIntent = new Intent(app.getApplicationContext(), SettingsActivity.class);
        settingsIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    	app.getApplicationContext().startActivity(settingsIntent);
        Toast.makeText(app.getApplicationContext(), "open settings Clicked!", Toast.LENGTH_LONG).show();
    }
    
    public static void createALoop(MyApplication app){
    	
        Intent createALoopIntent = new Intent(app.getApplicationContext(), CreateALoopActivity.class);
        createALoopIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        app.getApplicationContext().startActivity(createALoopIntent);
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