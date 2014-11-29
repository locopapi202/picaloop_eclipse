package com.picaloopand.picaloop;

import java.io.InputStream;

import android.content.Intent;
import android.content.IntentSender.SendIntentException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;


public class WelcomeActivity extends ActionBarActivity implements OnClickListener, ConnectionCallbacks,
OnConnectionFailedListener{
	
	Button gmailLoginButton;
	Button fbLoginButton;
	Button pclLoginbutton;
	Button btnSignOut, btnRevokeAccess;
    ImageView imgProfilePic;
    TextView gmailName, gmailEmail;
    private LinearLayout gmailllProfileLayout;
	boolean gmailLoginSignInClicked;
	boolean fbLoginSignInClicked;
	boolean pclLoginSignInClicked;
	boolean gmailLoginIntentInProgress;
	// Google client to interact with Google API
	private GoogleApiClient mGoogleApiClient;
	private static final int RC_SIGN_IN = 0;
	private static final String TAG = "WelcomeActivity";
	/* Track whether the sign-in button has been clicked so that we know to resolve
	 * all issues preventing sign-in without waiting.
	 */
	private boolean gmailLoginButtonClicked;

	/* Store the connection result from onConnectionFailed callbacks so that we can
	 * resolve them when the user clicks sign-in.
	 */
	private ConnectionResult gmailLoginConnectionResult;
	
    // Profile pic image size in pixels
    private static final int PROFILE_PIC_SIZE = 400;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        
        gmailLoginButton = (Button) findViewById(R.id.gmailLoginButton);
        gmailLoginButton.setOnClickListener(this);
        btnSignOut = (Button) findViewById(R.id.btn_sign_out);
        btnSignOut.setOnClickListener(this);
        btnRevokeAccess = (Button) findViewById(R.id.btn_revoke_access);
        btnRevokeAccess.setOnClickListener(this);
        fbLoginButton = (Button) findViewById(R.id.fbLoginButton);
        fbLoginButton.setOnClickListener(this);
        pclLoginbutton = (Button) findViewById(R.id.pclLoginbutton);
        pclLoginbutton.setOnClickListener(this);
        imgProfilePic = (ImageView) findViewById(R.id.gmailProfilePic);
        gmailName = (TextView) findViewById(R.id.gmailName);
        gmailEmail = (TextView) findViewById(R.id.gmailEmail);
        gmailllProfileLayout = (LinearLayout) findViewById(R.id.gmailllProfile);
        
        // Initializing google plus api client
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this).addApi(Plus.API)
                .addScope(Plus.SCOPE_PLUS_LOGIN).build();
             
    }
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }
 
    protected void onStop() {
        super.onStop();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
      //  getMenuInflater().inflate(R.menu.welcome, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
       /* int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item); */
    	return true;
    }


	@Override
	public void onClick(View view) {
		  switch (view.getId()) {
	        case R.id.gmailLoginButton:
	            // gmail Signin button clicked
	            signInWithGmail();
	            break;
	        case R.id.fbLoginButton:
	            // fb Signin button clicked
	        	signInWithFb();
	            break;
	        case R.id.pclLoginbutton:
	            // pcl Signin button clicked
	        	signInWithPcl();
	            break;
	        case R.id.btn_sign_out:
	            // Signout button clicked
	            signOutFromGplus();
	            break;
	        case R.id.btn_revoke_access:
	            // Revoke access button clicked
	            revokeGplusAccess();
	            break;
	        }
	}
	
	/* A helper method to resolve the current ConnectionResult error. */
	private void resolveSignInError() {
		if (gmailLoginConnectionResult == null){
			Toast.makeText(this, "gmailLoginConnectionResult null!", Toast.LENGTH_LONG).show();
		}
	 if (gmailLoginConnectionResult.hasResolution()) {
	    try {
	      gmailLoginIntentInProgress = true;
	      gmailLoginConnectionResult.startResolutionForResult(this, RC_SIGN_IN);
	    } catch (SendIntentException e) {
	      // The intent was canceled before it was sent.  Return to the default
	      // state and attempt to connect to get an updated ConnectionResult.
	     gmailLoginIntentInProgress = false;
	      mGoogleApiClient.connect();
	    }
	  }
	}

	protected void onActivityResult(int requestCode, int responseCode, Intent intent) {
		  if (requestCode == RC_SIGN_IN) {
		    if (responseCode != RESULT_OK) {
		    	gmailLoginButtonClicked = false;
		    }

		    gmailLoginIntentInProgress = false;

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
		  if (!gmailLoginIntentInProgress) {
		    // Store the ConnectionResult so that we can use it later when the user clicks
		    // 'sign-in'.
		    gmailLoginConnectionResult = result;

		    if (gmailLoginButtonClicked) {
		      // The user has already clicked 'sign-in' so we attempt to resolve all
		      // errors until the user is signed in, or they cancel.
		      resolveSignInError();
		    }
		  }
		}
		
	@Override
	public void onConnected(Bundle connectionHint) {
		gmailLoginButtonClicked = false;
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
	    	gmailLoginButton.setVisibility(View.GONE);
	    	pclLoginbutton.setVisibility(View.GONE);
	    	fbLoginButton.setVisibility(View.GONE);
	        gmailllProfileLayout.setVisibility(View.VISIBLE);
	        btnSignOut.setVisibility(View.VISIBLE);
	        btnRevokeAccess.setVisibility(View.VISIBLE);
	    } else {
	    	gmailLoginButton.setVisibility(View.VISIBLE);
	    	pclLoginbutton.setVisibility(View.VISIBLE);
	    	fbLoginButton.setVisibility(View.VISIBLE);
	        gmailllProfileLayout.setVisibility(View.GONE);
	        btnSignOut.setVisibility(View.GONE);
	        btnRevokeAccess.setVisibility(View.GONE);
	    }
	}
	
	/**
	 * Sign-in using gmail
	 * */
	private void signInWithGmail() {
   if (!mGoogleApiClient.isConnecting()) {
	    	
	    	gmailLoginSignInClicked = true;
	        resolveSignInError();
	        
	    } 
	}
	
	/**
	 * Sign-in using fb
	 * */
	private void signInWithFb() {
	    fbLoginSignInClicked = true;
	}
	
	/**
	 * Sign-in using pcl
	 * */
	private void signInWithPcl() {
	    pclLoginSignInClicked = true;
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
	 
	            gmailName.setText(personName);
	            gmailEmail.setText(email);
	 
	            // by default the profile url gives 50x50 px image only
	            // we can replace the value with whatever dimension we want by
	            // replacing sz=X
	            personPhotoUrl = personPhotoUrl.substring(0,
	                    personPhotoUrl.length() - 2)
	                    + PROFILE_PIC_SIZE;
	 
	            new LoadProfileImage(imgProfilePic).execute(personPhotoUrl);
	 
	        } else {
	            Toast.makeText(getApplicationContext(),
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
	    }
	}
	
	/**
	 * Revoking access from google
	 * */
	private void revokeGplusAccess() {
	    if (mGoogleApiClient.isConnected()) {
	        Plus.AccountApi.clearDefaultAccount(mGoogleApiClient);
	        Plus.AccountApi.revokeAccessAndDisconnect(mGoogleApiClient)
	                .setResultCallback(new ResultCallback<Status>() {
	                    @Override
	                    public void onResult(Status arg0) {
	                        Log.e(TAG, "User access revoked!");
	                        mGoogleApiClient.connect();
	                        updateUI(false);
	                    }
	 
	                });
	    }
	}
		
}
