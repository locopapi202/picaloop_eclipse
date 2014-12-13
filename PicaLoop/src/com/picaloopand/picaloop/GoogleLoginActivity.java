package com.picaloopand.picaloop;

import java.io.InputStream;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender.SendIntentException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
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

public class GoogleLoginActivity extends ActionBarActivity implements ConnectionCallbacks,
OnConnectionFailedListener {
	
    ImageView imgProfilePic;
    TextView googleName, googleEmail;
    private LinearLayout googlellProfileLayout;
	//boolean googleLoginSignInClicked = true;
	boolean googleLoginIntentInProgress;
	// Google client to interact with Google API
	private GoogleApiClient mGoogleApiClient;
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
	
	

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_google_login);
        imgProfilePic = (ImageView) findViewById(R.id.googleProfilePic);
        googleName = (TextView) findViewById(R.id.googleName);
        googleEmail = (TextView) findViewById(R.id.googleEmail);
        googlellProfileLayout = (LinearLayout) findViewById(R.id.googlellProfile);
               
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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.actionbar_menu, menu);
	    return super.onCreateOptionsMenu(menu);

	}
	
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		
		// Handle presses on the action bar items
	    switch (item.getItemId()) {
	        case R.id.action_signoff:
	        	signOutFromGplus();
	        	//signInWithgoogle();
	            return true;
	        case R.id.action_settings:
	            openSettings();
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
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
		        return;
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
		}
		
	@Override
	public void onConnected(Bundle connectionHint) {
		googleLoginButtonClicked = false;
		Toast.makeText(this, "User is connected!", Toast.LENGTH_LONG).show();
	    // Get user's information
	    getProfileInformation();
	 
	    // Update the UI after signin
	    updateUI(true);
	}
	
	@Override
	public void onConnectionSuspended(int arg0) {
	    mGoogleApiClient.connect();
	    updateUI(false);
	}
	
	/**
	 * Updating the UI, showing/hiding buttons and profile layout
	 * */
	private void updateUI(boolean isSignedIn) {
	    if (isSignedIn) {

	        googlellProfileLayout.setVisibility(View.VISIBLE);

	    } else {
	        googlellProfileLayout.setVisibility(View.GONE);

	    }
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
	private void signOutFromGplus() {
	    if (mGoogleApiClient.isConnected()) {
	        Plus.AccountApi.clearDefaultAccount(mGoogleApiClient);
	        mGoogleApiClient.disconnect();
	        mGoogleApiClient.connect();
	        updateUI(false);
			Intent intent = new Intent(this, WelcomeActivity.class);
			startActivity(intent);
	    }
	}
	
	
}
