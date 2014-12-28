package com.picaloopand.picaloop;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.plus.Plus;

public class CreateALoopActivity extends ActionBarActivity {
	
	SharedPreferences userProfile;
	String userSignInMethod;
	public static Editor editProfile;
	
	SharedPreferences selectedSpots;
    public static Editor editSelectedSpots;
    public static final String TRUE = "TRUE";
    public static final String FALSE = "FALSE";
    

    
    
	//Spot Icons
	public ImageButton drinksButton ;
	public ImageButton foodButton ;
	public ImageButton movieButton ;
	public ImageButton spotSubmit ;
	
	//Display when selected
	public ImageView drinksSelect ;
	public ImageView foodSelect ;
	public ImageView movieSelect ;
	
	protected MyApplication app;
	
	// Google client to interact with Google API
	public static GoogleApiClient mGoogleApiClient;
	
	private static final String GOOGLESIGNIN = "google";
	private static final String FACEBOOKSIGNIN = "facebook";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
        userProfile = getSharedPreferences("userProfile", MODE_PRIVATE);
        userSignInMethod = userProfile.getString("userSignIn", null);
        
        selectedSpots = getSharedPreferences("selectedSpots", MODE_PRIVATE);
        editSelectedSpots = selectedSpots.edit();
		
        editSelectedSpots.clear();
        editSelectedSpots.putString("drinks", FALSE);
        editSelectedSpots.putString("food", FALSE);
        editSelectedSpots.putString("movie", FALSE);
        editSelectedSpots.commit();
        
        // Get the application instance
        app = (MyApplication)getApplication();
		
		setContentView(R.layout.activity_create_aloop);
		
        // Initializing google plus api client
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Plus.API)
                .addScope(Plus.SCOPE_PLUS_LOGIN)
                .build();
	
        drinksButton = (ImageButton) findViewById(R.id.drinksButton);
        foodButton = (ImageButton) findViewById(R.id.foodButton);
        movieButton = (ImageButton) findViewById(R.id.movieButton);
        spotSubmit = (ImageButton) findViewById(R.id.spotSubmit);
        
        drinksSelect = (ImageView) findViewById(R.id.drinksSelect);
        foodSelect = (ImageView) findViewById(R.id.foodSelect);
        movieSelect = (ImageView) findViewById(R.id.movieSelect);
       
        drinksButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
            	  if (drinksSelect.getVisibility() == 0){
            		  drinksSelect.setVisibility(View.GONE);
                	  editSelectedSpots.putString("drinks", FALSE);
                	  editSelectedSpots.commit();
            	  }else
            		  drinksSelect.setVisibility(View.VISIBLE);
            	  editSelectedSpots.putString("drinks", TRUE);
            	  editSelectedSpots.commit();
            	  }
          });		
        foodButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
          	  if (foodSelect.getVisibility() == 0){
        		  foodSelect.setVisibility(View.GONE);
        		  editSelectedSpots.putString("food", FALSE);
            	  editSelectedSpots.commit();
        	  }else
        		  foodSelect.setVisibility(View.VISIBLE);
        	      editSelectedSpots.putString("food", TRUE);
        	      editSelectedSpots.commit();
            }
          });	
        movieButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
            	  if (movieSelect.getVisibility() == 0){
            		  movieSelect.setVisibility(View.GONE);
            		  editSelectedSpots.putString("movie", FALSE);
                	  editSelectedSpots.commit();
            	  }else
            		  movieSelect.setVisibility(View.VISIBLE);
            	     editSelectedSpots.putString("movie", TRUE);
            	     editSelectedSpots.commit();
            }
          });	
        
        
      spotSubmit.setOnClickListener(new OnClickListener() {
          @Override
          public void onClick(View view) {
        	//Toast.makeText(getApplicationContext(), "Start Loop Details Activity!", Toast.LENGTH_LONG).show();
        	  submitSpots();
          }
          });
	}
	
	private void submitSpots() {
		//Toast.makeText(getApplicationContext(), "Google Sign in Clicked!", Toast.LENGTH_LONG).show();
		Intent intent = new Intent(this, LoopDetailsActivity.class);
		startActivity(intent);
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
	        	signOut();
	        	return true;
	        case R.id.action_profile:
	            openProfile();
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
		Toast.makeText(getApplicationContext(), "Settings Clicked!", Toast.LENGTH_LONG).show();
	}

	private void openProfile() {
		// TODO Auto-generated method stub
		Toast.makeText(getApplicationContext(), "Profile Clicked!", Toast.LENGTH_LONG).show();
	}

	private void signOut() {
		// TODO Auto-generated method stub
		Toast.makeText(getApplicationContext(), "signOut Clicked!", Toast.LENGTH_LONG).show();
		
		if(userSignInMethod != null){
			editProfile = userProfile.edit();
	        editProfile.putString("userSignIn", null);
	        editProfile.commit();
			if(userSignInMethod.contains(GOOGLESIGNIN)){
				//MyApplication.signOutFromGplus(app, mGoogleApiClient);
        		Intent intent = new Intent(this, WelcomeActivity.class);
        		startActivity(intent);
			}
			if(userSignInMethod.contains(FACEBOOKSIGNIN)){
				Toast.makeText(getApplicationContext(), "signOut Clicked! facebooklogin", Toast.LENGTH_LONG).show();
        		Intent intent = new Intent(this, WelcomeActivity.class);
        		startActivity(intent);
			}
         }
		
	}  
}
