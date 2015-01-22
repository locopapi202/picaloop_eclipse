package com.picaloopand.picaloop;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

public class BarDetailsActivity extends ActionBarActivity {
	
	FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    
    SharedPreferences userProfile;
    
	protected MyApplication app;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		userProfile = getSharedPreferences("userProfile", MODE_PRIVATE);
	       // Get the application instance
        app = (MyApplication)getApplication();
		
		setContentView(R.layout.activity_bar_details);
		
//	displayDetailFragments();
		
		  
	}

	public void displayDetailFragments(){
		// get fragment manager
				 fragmentManager = getFragmentManager();
				
				 // add
				 fragmentTransaction = fragmentManager.beginTransaction();
				 fragmentTransaction.add(R.id.bar_details, new LocationFragment());
				 fragmentTransaction.commit();
	}
	
    @Override
	 public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.create_loop_actionbar_menu, menu);
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


	  
}
