package com.picaloopand.picaloop;

import android.R.integer;
import android.R.string;
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
		
	SharedPreferences selectedSpots;
    public static Editor editSelectedSpots;
    public static final String TRUE = "TRUE";
    public static final String FALSE = "FALSE";
    
    //Ordering constants
    int counter = 1;
    int drinkStatus = 0;

	//Spot Icons
	public ImageButton drinksButton ;
	public ImageButton foodButton ;
	public ImageButton movieButton ;
	public ImageButton bikeButton ;
	public ImageButton groceryButton ;
	public ImageButton waterButton ;
	public ImageButton hotelButton ;
	public ImageButton airportButton ;
	public ImageButton photoButton ;
	public ImageButton libraryButton ;
	public ImageButton natureButton ;
	public ImageButton paintingButton ;
	public ImageButton spotSubmit ;

	
	//Display when selected
	public ImageView drinksSelect ;
	public ImageView foodSelect ;
	public ImageView movieSelect ;
	public ImageView bikeSelect ;
	public ImageView grocerySelect ;
	public ImageView waterSelect ;
	public ImageView hotelSelect ;
	public ImageView airportSelect ;
	public ImageView photoSelect ;
	public ImageView librarySelect ;
	public ImageView natureSelect ;
	public ImageView paintingSelect ;
	protected MyApplication app;
	
	// Google client to interact with Google API
	public static GoogleApiClient mGoogleApiClient;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		

		userProfile = getSharedPreferences("userProfile", MODE_PRIVATE);
       // Get the application instance
        userProfile = getSharedPreferences("userProfile", MODE_PRIVATE);

        
        selectedSpots = getSharedPreferences("selectedSpots", MODE_PRIVATE);
        editSelectedSpots = selectedSpots.edit();
		
        editSelectedSpots.clear();
        editSelectedSpots.putString("drinks", FALSE);
        editSelectedSpots.putString("food", FALSE);
        editSelectedSpots.putString("movie", FALSE);
        editSelectedSpots.putString("bike", FALSE);
        editSelectedSpots.putString("grocery", FALSE);
        editSelectedSpots.putString("water", FALSE);
        editSelectedSpots.putString("hotel", FALSE);
        editSelectedSpots.putString("airport", FALSE);
        editSelectedSpots.putString("photo", FALSE);
        editSelectedSpots.putString("library", FALSE);
        editSelectedSpots.putString("nature", FALSE);
        editSelectedSpots.putString("painting", FALSE);
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
        bikeButton = (ImageButton) findViewById(R.id.bikeButton);
        groceryButton = (ImageButton) findViewById(R.id.groceryButton);
        waterButton = (ImageButton) findViewById(R.id.waterButton);
        hotelButton = (ImageButton) findViewById(R.id.hotelButton);
        airportButton = (ImageButton) findViewById(R.id.airportButton);
        photoButton = (ImageButton) findViewById(R.id.photoButton);
        libraryButton = (ImageButton) findViewById(R.id.libraryButton);
        natureButton = (ImageButton) findViewById(R.id.natureButton);
        paintingButton = (ImageButton) findViewById(R.id.paintingButton);
        spotSubmit = (ImageButton) findViewById(R.id.spotSubmit);
        
        drinksSelect = (ImageView) findViewById(R.id.drinksSelect);
        foodSelect = (ImageView) findViewById(R.id.foodSelect);
        movieSelect = (ImageView) findViewById(R.id.movieSelect);
        bikeSelect = (ImageView) findViewById(R.id.bikeSelect);
        grocerySelect = (ImageView) findViewById(R.id.grocerySelect);
        waterSelect = (ImageView) findViewById(R.id.waterSelect);
        hotelSelect = (ImageView) findViewById(R.id.hotelSelect);
        airportSelect = (ImageView) findViewById(R.id.airportSelect);
        photoSelect = (ImageView) findViewById(R.id.photoSelect);
        librarySelect = (ImageView) findViewById(R.id.librarySelect);
        natureSelect = (ImageView) findViewById(R.id.natureSelect);
        paintingSelect = (ImageView) findViewById(R.id.paintingSelect);

        drinksButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
            	  int black = R.drawable.ic_local_bar_black_48dp;
            	  int grey = R.drawable.ic_local_bar_grey600_48dp;
      		      pickCounter("drinks",drinksSelect,drinksButton,black,grey);
            	  Toast.makeText(getApplicationContext(), Integer.toString(counter), Toast.LENGTH_LONG).show();
               }
          });		
        foodButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
            	if (selectedSpots.getString("food", null) == FALSE){
        		  foodSelect.setVisibility(View.VISIBLE);
        		  editSelectedSpots.putString("food", TRUE);
            	  editSelectedSpots.commit();
            	  counter++;
        	  }else if (selectedSpots.getString("food", null) == TRUE){
        		  foodSelect.setVisibility(View.GONE);
        	      editSelectedSpots.putString("food", FALSE);
        	      editSelectedSpots.commit();
        	      counter--;
                  }
            Toast.makeText(getApplicationContext(), Integer.toString(counter), Toast.LENGTH_LONG).show();
            }
          });	
        
       movieButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
            	if (selectedSpots.getString("movie", null) == FALSE){
        		  movieSelect.setVisibility(View.VISIBLE);
        		  editSelectedSpots.putString("movie", TRUE);
            	  editSelectedSpots.commit();
            	  counter++;
        	  }else if (selectedSpots.getString("movie", null) == TRUE){
        		  movieSelect.setVisibility(View.GONE);
        	      editSelectedSpots.putString("movie", FALSE);
        	      editSelectedSpots.commit();
        	      counter--;
                  }
            Toast.makeText(getApplicationContext(), Integer.toString(counter), Toast.LENGTH_LONG).show();
            }
          });
        
       bikeButton.setOnClickListener(new OnClickListener() {
           @Override
           public void onClick(View view) {
           	if (selectedSpots.getString("bike", null) == FALSE){
       		  bikeSelect.setVisibility(View.VISIBLE);
       		  editSelectedSpots.putString("bike", TRUE);
           	  editSelectedSpots.commit();
           	  counter++;
       	  }else if (selectedSpots.getString("bike", null) == TRUE){
       		  bikeSelect.setVisibility(View.GONE);
       	      editSelectedSpots.putString("bike", FALSE);
       	      editSelectedSpots.commit();
       	      counter--;
                 }
           Toast.makeText(getApplicationContext(), Integer.toString(counter), Toast.LENGTH_LONG).show();
           }
         });
        
       groceryButton.setOnClickListener(new OnClickListener() {
           @Override
           public void onClick(View view) {
           	if (selectedSpots.getString("grocery", null) == FALSE){
       		  grocerySelect.setVisibility(View.VISIBLE);
       		  editSelectedSpots.putString("grocery", TRUE);
           	  editSelectedSpots.commit();
           	  counter++;
       	  }else if (selectedSpots.getString("grocery", null) == TRUE){
       		  grocerySelect.setVisibility(View.GONE);
       	      editSelectedSpots.putString("grocery", FALSE);
       	      editSelectedSpots.commit();
       	      counter--;
                 }
           Toast.makeText(getApplicationContext(), Integer.toString(counter), Toast.LENGTH_LONG).show();
           }
         });
        
       waterButton.setOnClickListener(new OnClickListener() {
           @Override
           public void onClick(View view) {
           	if (selectedSpots.getString("water", null) == FALSE){
       		  waterSelect.setVisibility(View.VISIBLE);
       		  editSelectedSpots.putString("water", TRUE);
           	  editSelectedSpots.commit();
           	  counter++;
       	  }else if (selectedSpots.getString("water", null) == TRUE){
       		  waterSelect.setVisibility(View.GONE);
       	      editSelectedSpots.putString("water", FALSE);
       	      editSelectedSpots.commit();
       	      counter--;
                 }
           Toast.makeText(getApplicationContext(), Integer.toString(counter), Toast.LENGTH_LONG).show();
           }
         });
        
       hotelButton.setOnClickListener(new OnClickListener() {
           @Override
           public void onClick(View view) {
           	if (selectedSpots.getString("hotel", null) == FALSE){
       		  hotelSelect.setVisibility(View.VISIBLE);
       		  editSelectedSpots.putString("hotel", TRUE);
           	  editSelectedSpots.commit();
           	  counter++;
       	  }else if (selectedSpots.getString("hotel", null) == TRUE){
       		  hotelSelect.setVisibility(View.GONE);
       	      editSelectedSpots.putString("hotel", FALSE);
       	      editSelectedSpots.commit();
       	      counter--;
                 }
           Toast.makeText(getApplicationContext(), Integer.toString(counter), Toast.LENGTH_LONG).show();
           }
         });
        
       airportButton.setOnClickListener(new OnClickListener() {
           @Override
           public void onClick(View view) {
           	if (selectedSpots.getString("airport", null) == FALSE){
       		  airportSelect.setVisibility(View.VISIBLE);
       		  editSelectedSpots.putString("airport", TRUE);
           	  editSelectedSpots.commit();
           	  counter++;
       	  }else if (selectedSpots.getString("airport", null) == TRUE){
       		  airportSelect.setVisibility(View.GONE);
       	      editSelectedSpots.putString("airport", FALSE);
       	      editSelectedSpots.commit();
       	      counter--;
                 }
           Toast.makeText(getApplicationContext(), Integer.toString(counter), Toast.LENGTH_LONG).show();
           }
         });
        
       photoButton.setOnClickListener(new OnClickListener() {
           @Override
           public void onClick(View view) {
           	if (selectedSpots.getString("photo", null) == FALSE){
       		  photoSelect.setVisibility(View.VISIBLE);
       		  editSelectedSpots.putString("photo", TRUE);
           	  editSelectedSpots.commit();
           	  counter++;
       	  }else if (selectedSpots.getString("photo", null) == TRUE){
       		  photoSelect.setVisibility(View.GONE);
       	      editSelectedSpots.putString("photo", FALSE);
       	      editSelectedSpots.commit();
       	      counter--;
                 }
           Toast.makeText(getApplicationContext(), Integer.toString(counter), Toast.LENGTH_LONG).show();
           }
         });
        
       libraryButton.setOnClickListener(new OnClickListener() {
           @Override
           public void onClick(View view) {
           	if (selectedSpots.getString("library", null) == FALSE){
       		  librarySelect.setVisibility(View.VISIBLE);
       		  editSelectedSpots.putString("library", TRUE);
           	  editSelectedSpots.commit();
           	  counter++;
       	  }else if (selectedSpots.getString("library", null) == TRUE){
       		  librarySelect.setVisibility(View.GONE);
       	      editSelectedSpots.putString("library", FALSE);
       	      editSelectedSpots.commit();
       	      counter--;
                 }
           Toast.makeText(getApplicationContext(), Integer.toString(counter), Toast.LENGTH_LONG).show();
           }
         });
        
       natureButton.setOnClickListener(new OnClickListener() {
           @Override
           public void onClick(View view) {
           	if (selectedSpots.getString("nature", null) == FALSE){
       		  natureSelect.setVisibility(View.VISIBLE);
       		  editSelectedSpots.putString("nature", TRUE);
           	  editSelectedSpots.commit();
           	  counter++;
       	  }else if (selectedSpots.getString("nature", null) == TRUE){
       		  natureSelect.setVisibility(View.GONE);
       	      editSelectedSpots.putString("nature", FALSE);
       	      editSelectedSpots.commit();
       	      counter--;
                 }
           Toast.makeText(getApplicationContext(), Integer.toString(counter), Toast.LENGTH_LONG).show();
           }
         });
        
       paintingButton.setOnClickListener(new OnClickListener() {
           @Override
           public void onClick(View view) {
           	if (selectedSpots.getString("painting", null) == FALSE){
       		  paintingSelect.setVisibility(View.VISIBLE);
       		  editSelectedSpots.putString("painting", TRUE);
           	  editSelectedSpots.commit();
           	  counter++;
       	  }else if (selectedSpots.getString("painting", null) == TRUE){
       		  paintingSelect.setVisibility(View.GONE);
       	      editSelectedSpots.putString("painting", FALSE);
       	      editSelectedSpots.commit();
       	      counter--;
                 }
           Toast.makeText(getApplicationContext(), Integer.toString(counter), Toast.LENGTH_LONG).show();
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
	
    private void pickCounter(String item,View view1,ImageButton view2,int black,int grey){

        if (selectedSpots.getString(item, null) == FALSE){
     		editSelectedSpots.putString(item, TRUE);
           	editSelectedSpots.commit();
           	view1.setVisibility(View.VISIBLE);
           	view2.setImageResource(grey);
           	counter++;
        }else if (selectedSpots.getString(item, null) == TRUE){
   		  editSelectedSpots.putString(item, FALSE);
       	  editSelectedSpots.commit();
       	  view1.setVisibility(View.GONE);
       	  view2.setImageResource(black);
        	counter--;
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
	        	MyApplication.signOut(app, userProfile);
	        	//finish();
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
	//	Toast.makeText(getApplicationContext(), "Settings Clicked!", Toast.LENGTH_LONG).show();

   
	}

	private void openProfile() {
		// TODO Auto-generated method stub
		Toast.makeText(getApplicationContext(), "Profile Clicked!", Toast.LENGTH_LONG).show();
	}

	/*private void signOut() {
		// TODO Auto-generated method stub
		//Toast.makeText(getApplicationContext(), "signOut Clicked!", Toast.LENGTH_LONG).show();
		
	}  */
}
