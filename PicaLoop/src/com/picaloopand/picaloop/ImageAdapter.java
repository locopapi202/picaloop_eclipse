package com.picaloopand.picaloop;

import android.support.v7.app.ActionBarActivity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;

public class ImageAdapter extends BaseAdapter {
	SharedPreferences spotRanks;
	public static Editor editSpotRanks;
	
	
	private Context mContext;

	   // Constructor
	   public ImageAdapter(Context c) {
	      mContext = c;
	   }

	   public int getCount() {
	      return mThumbIds.length;
	   }

	   public Object getItem(int position) {
	      return null;
	   }

	   public long getItemId(int position) {
	      return 0;
	   }
	   // create a new ImageView for each item referenced by the Adapter
	   public View getView(int position, View convertView, ViewGroup parent) {
	      ImageButton imageButton;
	     
	      if (convertView == null) {
	      imageButton = new ImageButton(mContext);
	      imageButton.setLayoutParams(new GridView.LayoutParams(270, 270));
	      //imageButton.setScaleType(ImageButton.ScaleType.CENTER_INSIDE);
	      } else {
	      imageButton = (ImageButton) convertView;
	      }
	    	     
	      
	      imageButton.setBackgroundResource(R.drawable.icons);
	      imageButton.setImageResource(mThumbIds[position]);


	      return imageButton;
	   }
       
	   // Keep all Images in array
	   public Integer[] mThumbIds = {
      };
	}