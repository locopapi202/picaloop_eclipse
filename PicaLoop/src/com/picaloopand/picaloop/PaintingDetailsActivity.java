package com.picaloopand.picaloop;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
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

public class PaintingDetailsActivity extends ActionBarActivity {
	
	FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
   
    SharedPreferences userProfile;
    SharedPreferences spotRanks;
    public static Editor editSpotRanks;
    
	String StringProjectName = "com.picaloopand.picaloop.";
	String StingPartialActivityName = "DetailsActivity";
	String StringSpotName= "Painting";
	
	
    private String[] spots = {
            "Drinks",
            "Food",
            "Movie",
            "Bike",
            "Grocery",
            "Water",
            "Hotel",
            "Airport",
            "Photo",
            "Library",
            "Nature",
            "Painting"
        };
    public Button spotSubmit ;
    
    ImageView image;
    
	protected MyApplication app;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		userProfile = getSharedPreferences("userProfile", MODE_PRIVATE);
		spotRanks = getSharedPreferences("spotRanks", MODE_PRIVATE);
        editSpotRanks = spotRanks.edit();
	       // Get the application instance
        app = (MyApplication)getApplication();
		
		setContentView(R.layout.activity_painting_details);
        
		 spotSubmit = (Button) findViewById(R.id.spotSubmit);
		
//	displayDetailFragments();
	      spotSubmit.setOnClickListener(new OnClickListener() {
	          @Override
	          public void onClick(View view) {
	        	  try {
					submitSpots();
				} catch (ClassNotFoundException e) {
					Toast.makeText(getApplicationContext(), "Class not found!", Toast.LENGTH_LONG).show();
					e.printStackTrace();
				}
	          }
	          });
		  
	}
    
	private void submitSpots() throws ClassNotFoundException {
		
		Class<?> dummyClass = null;
    	for (int i=0;i<spots.length;i++){
    		if (spotRanks.getInt(spots[i],0) == spotRanks.getInt("Painting",0) + 1){
                StringSpotName = spots[i];
                //Toast.makeText(getApplicationContext(), spots[i], Toast.LENGTH_LONG).show();
    		}
    	}
		
		int pageCount = spotRanks.getInt("PageCount", 0);
		if ( pageCount == spotRanks.getInt("Counter", 0)  ){
			dummyClass = LoopDetailsViewActivity.class;
		}else {
			pageCount++;
	     	editSpotRanks.putInt("PageCount", pageCount);
	     	editSpotRanks.commit();
			dummyClass = Class.forName(StringProjectName+StringSpotName+StingPartialActivityName);
		}
		
	
		Intent intent = new Intent(this, dummyClass);
		startActivity(intent);
	}
	public void displayDetailFragments(){
		// get fragment manager
				 fragmentManager = getFragmentManager();
				
				 // add
				 fragmentTransaction = fragmentManager.beginTransaction();
				 fragmentTransaction.add(R.id.food_details, new LocationFragment());
				 fragmentTransaction.commit();
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
   	        	MyApplication.openProfile(app);
   	            return true;
   	        case R.id.action_settings:
   	        	MyApplication.openSettings(app);
   	            return true;
   	        default:
   	            return super.onOptionsItemSelected(item);
   	    }

   	}


	private void openSettings() {
		// TODO Auto-generated method stub
		//Toast.makeText(getApplicationContext(), "Settings Clicked!", Toast.LENGTH_LONG).show();
 
	}

	private void openProfile() {
		// TODO Auto-generated method stub
		Toast.makeText(getApplicationContext(), "Profile Clicked!", Toast.LENGTH_LONG).show();
	}

	  
}
