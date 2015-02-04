package com.picaloopand.picaloop;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

import android.support.v7.app.ActionBarActivity;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;
import android.widget.Toast;

public class LoopDetailsViewActivity extends ActionBarActivity {

ImageAdapter imgAdpt;
SharedPreferences spotRanks;

private String[] spotsStrings = {
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

List<Integer> ints = new ArrayList<Integer>();


private Integer[] spotsDrawables = {
		R.drawable.ic_local_bar_black_48dp,
		R.drawable.ic_local_restaurant_black_48dp,
		R.drawable.ic_local_movies_black_48dp,
		R.drawable.ic_directions_bike_black_48dp,
		R.drawable.ic_local_grocery_store_black_48dp,
		R.drawable.ic_directions_ferry_black_48dp,
		R.drawable.ic_hotel_black_48dp,
		R.drawable.ic_flight_black_48dp,
		R.drawable.ic_local_see_black_48dp,
		R.drawable.ic_local_library_black_48dp,
		R.drawable.ic_landscape_black_48dp,
		R.drawable.ic_palette_black_48dp
		};
private Integer[] spotsBackground = {
		R.drawable.icons_square,
		R.drawable.icons_square,
		R.drawable.icons_square,
		R.drawable.icons_square,
		R.drawable.icons_square,
		R.drawable.icons_square,
		R.drawable.icons_square,
		R.drawable.icons_square,
		R.drawable.icons_square
		};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		spotRanks = getSharedPreferences("spotRanks", MODE_PRIVATE);
		
		//for (int i=0;i<spotsDrawables.length;i++){
		//ints.add(spotsDrawables[i]);
		//}
			
		setContentView(R.layout.activity_loop_details_view);
		
		Integer[] spots = {
				};
		
		for (int i=0;i<spotsStrings.length;i++){
			if (spotRanks.getInt(spotsStrings[i],0) != 0){
				ints.add(spotsDrawables[i]);
			}
    	  }

	    for (int i=ints.size();i<9;i++){
		  ints.add(R.drawable.icons_square);
	      }
	  
	  spots = ints.toArray(spots);
	  int loop_length = spotRanks.getInt("Counter", 0) - 1;
	  
	  
	  switch (loop_length){
	  case 2: spots[3]=spots[0];
	          spotsBackground[3]=R.drawable.icons;
	          spots[0]=R.drawable.icons_square;
	          spots[5]=spots[1];
	          spotsBackground[5]=R.drawable.icons;
	          spots[1]=R.drawable.icons_square;
	          spots[4]=R.drawable.ic_launcher_profile;
	          spotsBackground[4]=R.drawable.icons;
	          break;
	  case 3: spots[8]=spots[2];
	          spotsBackground[8]=R.drawable.icons;
		      spots[2]=R.drawable.icons_square;
		      spots[6]=spots[1];
		      spotsBackground[6]=R.drawable.icons;
		      spots[1]=R.drawable.icons_square;
		      spots[1]=spots[0];
		      spotsBackground[1]=R.drawable.icons;
		      spots[0]=R.drawable.icons_square;
		      spots[4]=R.drawable.ic_launcher_profile;
		      spotsBackground[4]=R.drawable.icons;
		      break;
	  case 4: spotsBackground[0]=R.drawable.icons;
		  
		      spots[8]=spots[3];
		      spotsBackground[8]=R.drawable.icons;
		      spots[3]=R.drawable.icons_square;
		      
		      spots[6]=spots[2];
		      spotsBackground[6]=R.drawable.icons;
		      spots[2]=R.drawable.icons_square;
		      
		      spots[2]=spots[1];
		      spotsBackground[2]=R.drawable.icons;
		      spots[1]=R.drawable.icons_square;
		      
		      spots[4]=R.drawable.ic_launcher_profile;
		      spotsBackground[4]=R.drawable.icons;
		      break;

		      }
	    
	  
	  GridView gridview = (GridView) findViewById(R.id.gridview);
	  imgAdpt = new ImageAdapter(this, spots, spotsBackground, spotRanks.getInt("Counter", 0)-1);
	  gridview.setAdapter(imgAdpt);
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
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	

}
