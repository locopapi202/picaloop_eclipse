package com.picaloopand.picaloop;

import java.io.InputStream;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender.SendIntentException;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;

public class GoogleLoginActivity extends Activity implements ConnectionCallbacks,
OnConnectionFailedListener {
	
    ImageView imgProfilePic;
    TextView googleName, googleEmail;
    private LinearLayout googlellProfileLayout;
	//boolean googleLoginSignInClicked = true;
	boolean googleLoginIntentInProgress;
	// Google client to interact with Google API
	public static GoogleApiClient mGoogleApiClient;
	public static final int RC_SIGN_IN = 0;
	private static final String TAG = "WelcomeActivity";
	/* Track whether the sign-in button has been clicked so that we know to resolve
	 * all issues preventing sign-in without waiting.
	 */
	private boolean googleLoginButtonClicked = true;

	/* Store the connection result from onConnectionFailed callbacks so that we can
	 * resolve them when the user clicks sign-in.
	 */
	private ConnectionResult googleLoginConnectionResult;
	
    // Profile pic image size in pixels
    private static final int PROFILE_PIC_SIZE = 400;
	
    SharedPreferences userProfile;
    public static Editor editProfile;
	
    protected MyApplication app;
    
    public static final String SIGN_IN_METHOD = "google";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_google_login);
        
        imgProfilePic = (ImageView) findViewById(R.id.googleProfilePic);
        googleName = (TextView) findViewById(R.id.googleName);
        googleEmail = (TextView) findViewById(R.id.googleEmail);
        googlellProfileLayout = (LinearLayout) findViewById(R.id.googlellProfile);
        userProfile = getSharedPreferences("userProfile", MODE_PRIVATE);
        editProfile = userProfile.edit();
        
        
        // Get the application instance
       app = (MyApplication)getApplication();
        
        // Initializing google plus api client
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this).addApi(Plus.API)
                .addScope(Plus.SCOPE_PLUS_LOGIN).build();
  
    }
	
    public void onStart() {
        super.onStart();
       mGoogleApiClient.connect();
    }
 
    
    public void onStop() {
        super.onStop();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }


	private void openSettings() {
		// TODO Auto-generated method stub
		Toast.makeText(this, "settings menu clicked!", Toast.LENGTH_LONG).show();
	}


	/* A helper method to resolve the current ConnectionResult error. */
	private void resolveSignInError() {
	if (googleLoginConnectionResult == null){
			Toast.makeText(this, "googleLoginConnectionResult null!", Toast.LENGTH_LONG).show();
		}
	 if (googleLoginConnectionResult.hasResolution()) {
	    try {
	      googleLoginIntentInProgress = true;
	      googleLoginConnectionResult.startResolutionForResult(this, RC_SIGN_IN);
	    } catch (SendIntentException e) {
	      // The intent was canceled before it was sent.  Return to the default
	      // state and attempt to connect to get an updated ConnectionResult.
	     googleLoginIntentInProgress = false;
	      mGoogleApiClient.connect();
	    }
	  }
	}

	public void onActivityResult(int requestCode, int responseCode, Intent intent) {
		  if (requestCode == RC_SIGN_IN) {
		    if (responseCode != Activity.RESULT_OK) {
		    	googleLoginButtonClicked = false;
		    }

		    googleLoginIntentInProgress = false;

		    if (!mGoogleApiClient.isConnecting()) {
		      mGoogleApiClient.connect();
		    }
		  }
		}
	@Override
	public void onConnectionFailed(ConnectionResult result) {
		   if (!result.hasResolution()) {
		        GooglePlayServicesUtil.getErrorDialog(result.getErrorCode(), this,
		                0).show();
				editProfile.putString("userSignIn", null);
				editProfile.commit();
			    Intent intent = new Intent(this, WelcomeActivity.class);
				startActivity(intent);

		       // return;
		    }
		  if (!googleLoginIntentInProgress) {
		    // Store the ConnectionResult so that we can use it later when the user clicks
		    // 'sign-in'.
		    googleLoginConnectionResult = result;

		    if (googleLoginButtonClicked) {
		      // The user has already clicked 'sign-in' so we attempt to resolve all
		      // errors until the user is signed in, or they cancel.
		      resolveSignInError();
		    }
		  }
			/*editProfile.putString("userSignIn", null);
			editProfile.commit();
		    Intent intent = new Intent(this, WelcomeActivity.class);
			startActivity(intent);*/
		}
		
	@Override
	public void onConnected(Bundle connectionHint) {
		googleLoginButtonClicked = false;
		Toast.makeText(this, "User is connected!", Toast.LENGTH_LONG).show();
	    // Get user's information
	    getProfileInformation();
	    
	    Intent intent = new Intent(this, CreateALoopActivity.class);
		startActivity(intent);
	 
	    // Update the UI after signin
	    //updateUI(true);
	}
	
	@Override
	public void onConnectionSuspended(int arg0) {
	    mGoogleApiClient.connect();
	   // updateUI(false);
	}
	


	/**
	 * Fetching user's information name, email, profile pic
	 * */
	private void getProfileInformation() {
	    try {
	        if (Plus.PeopleApi.getCurrentPerson(mGoogleApiClient) != null) {
	            Person currentPerson = Plus.PeopleApi
	                    .getCurrentPerson(mGoogleApiClient);
	            String personName = currentPerson.getDisplayName();
	            String personPhotoUrl = currentPerson.getImage().getUrl();
	            String personGooglePlusProfile = currentPerson.getUrl();
	            String email = Plus.AccountApi.getAccountName(mGoogleApiClient);
	 
	            Log.e(TAG, "Name: " + personName + ", plusProfile: "
	                    + personGooglePlusProfile + ", email: " + email
	                    + ", Image: " + personPhotoUrl);
	 

	            editProfile.clear();
	            editProfile.putString("userSignIn", SIGN_IN_METHOD);
	            editProfile.putString("userName", personName);
	            editProfile.putString("userEmail", email);
	            editProfile.putString("userProfilePic", personPhotoUrl);
	            editProfile.putString("firstTime", "no");
	            editProfile.commit();  
	            
	            googleName.setText(personName);
	            googleEmail.setText(email);
	 
	            // by default the profile url gives 50x50 px image only
	            // we can replace the value with whatever dimension we want by
	            // replacing sz=X
	            personPhotoUrl = personPhotoUrl.substring(0,
	                    personPhotoUrl.length() - 2)
	                    + PROFILE_PIC_SIZE;
	            
	            new LoadProfileImage(imgProfilePic).execute(personPhotoUrl);
	 
	        } else {
	            Toast.makeText(this,
	                    "Person information is null", Toast.LENGTH_LONG).show();
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
   
	    
	}
	 
	/**
	 * Background Async task to load user profile picture from url
	 * */
	private class LoadProfileImage extends AsyncTask<String, Void, Bitmap> {
	    ImageView bmImage;
	 
	    public LoadProfileImage(ImageView bmImage) {
	        this.bmImage = bmImage;
	    }
	 
	    protected Bitmap doInBackground(String... urls) {
	        String urldisplay = urls[0];
	        Bitmap mIcon11 = null;
	        try {
	            InputStream in = new java.net.URL(urldisplay).openStream();
	            mIcon11 = BitmapFactory.decodeStream(in);
	        } catch (Exception e) {
	            Log.e("Error", e.getMessage());
	            e.printStackTrace();
	        }
	        return mIcon11;
	    }
	 
	    protected void onPostExecute(Bitmap result) {
	        bmImage.setImageBitmap(result);
	    }
	}
	
	/**
	 * Sign-out from google
	 * */
	/*public static void signOutFromGplus(MyApplication app, GoogleApiClient googleApi) {
	    if (googleApi.isConnected()) {
	        Plus.AccountApi.clearDefaultAccount(googleApi);
	        googleApi.disconnect();
	        googleApi.connect();
	      //  updateUI(false);
	        editProfile.putString("userSignIn", null);
			app.getApplicationContext().startActivity(new Intent(app.getApplicationContext(), GoogleLoginActivity.class));
			
	    }
	} */
	
	
}
