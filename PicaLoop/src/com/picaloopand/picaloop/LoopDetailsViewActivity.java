package com.picaloopand.picaloop;

import java.util.List;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;

public class LoopDetailsViewActivity extends ActionBarActivity {
	SharedPreferences spotRanks;
	/*
	private Integer[] spots = {
			R.drawable.bar_wine,
			R.drawable.hiking,
			R.drawable.hotel,
			R.drawable.movie,
			R.drawable.photography,
			R.drawable.restuarant
			};
	*/

    private Integer[] spots = {
			R.drawable.bar_wine,
            R.drawable.restuarant,
            R.drawable.movie,
            R.drawable.hotel
			};

	private Integer[] icons = {
			R.drawable.ic_local_bar_black_48dp,
            R.drawable.ic_local_restaurant_black_48dp,
            R.drawable.ic_local_movies_black_48dp,
            R.drawable.ic_local_hotel_black_48dp
			};

	private String[] spotnames = {
    		"Bahama breeze","Minado","UA King of Prussia","Radisson"
    };

	LoopGridActivity loopGrid;
	
	
	SharedPreferences userProfile;
	protected MyApplication app;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		
		setContentView(R.layout.activity_loop_details_view);
		app = (MyApplication)getApplication();
		spotRanks = getSharedPreferences("spotRanks", MODE_PRIVATE);

		  GridView gridview = (GridView) findViewById(R.id.gridview2);
		  loopGrid = new LoopGridActivity(this,spots,spotnames,icons);
		  gridview.setAdapter(loopGrid);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.loop_details_view, menu);
		return true;
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
