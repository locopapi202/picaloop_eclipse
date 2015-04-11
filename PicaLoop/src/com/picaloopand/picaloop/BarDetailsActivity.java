package com.picaloopand.picaloop;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;

public class BarDetailsActivity extends ActionBarActivity {
	
	FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    
    SharedPreferences userProfile;
    
    public Button loopSubmit ;
    
	protected MyApplication app;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		userProfile = getSharedPreferences("userProfile", MODE_PRIVATE);
	       // Get the application instance
        app = (MyApplication)getApplication();
		
		setContentView(R.layout.activity_bar_details);
		 loopSubmit = (Button) findViewById(R.id.submitLoop);
		
	       // enabling action bar app icon and behaving it as toggle button
	       getSupportActionBar().setDisplayHomeAsUpEnabled(true);
	       getSupportActionBar().setHomeButtonEnabled(true);
		
//	displayDetailFragments();
		
	       loopSubmit.setOnClickListener(new OnClickListener() {
	           @Override
	           public void onClick(View view) {
	         	  submitLoop();
	           }
	           });
	   

	}
	
	private void submitLoop() {
		
		Intent intent = new Intent(this, LoopDetailsViewActivity.class);
		startActivity(intent);
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
	        case R.id.action_search:
	        	MyApplication.search(app);
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }

	}


	  
}
