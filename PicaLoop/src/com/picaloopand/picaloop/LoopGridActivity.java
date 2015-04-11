package com.picaloopand.picaloop;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.androidquery.AQuery;

public class LoopGridActivity extends BaseAdapter {

	SharedPreferences spotRanks;
	SharedPreferences userProfile;
	private final LayoutInflater mInflater;
	public static Editor editSpotRanks;
	
	
	private Context mContext;
	
	// Keep all Images in array
    public Integer[] mThumbIds = {
        };
    public Integer[] mIcons = {
    };

    public String[] mSpotNames = {
    };
	// Constructor
	public LoopGridActivity(Context c) {
	      mContext = c;
	      mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	   }
	   
 
	public LoopGridActivity(Context c, Integer[] spots, String[] spotnames, Integer[] icons) {
		      mContext = c;
		      mThumbIds = spots;
		      mSpotNames = spotnames;
		      mIcons = icons;
		      
		      mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
	        // RelativeLayout as used in the xml view
	        RelativeLayout customView = null;
	        if (convertView == null) {
	        	convertView = (RelativeLayout) mInflater.inflate(R.layout.loop_grid_text, null);
	        					
	        } 

	         ImageView loop_image = (ImageView) convertView.findViewById(R.id.loop_image);
	         loop_image.setScaleType(ImageView.ScaleType.CENTER_CROP);
	         loop_image.setPadding(1, 1, 1, 1);
	         loop_image.setImageResource(mThumbIds[position]);
	         
	         TextView loop_info = (TextView) convertView.findViewById(R.id.loop_info);
	         loop_info.setText(mSpotNames[position]);
	         
	         ImageView loop_icon = (ImageView) convertView.findViewById(R.id.loop_icon);
	         loop_icon.setScaleType(ImageView.ScaleType.CENTER_CROP);
	         loop_icon.setPadding(1, 1, 1, 1);
	         loop_icon.setImageResource(mIcons[position]);
	         
			 return convertView;
			 

	    }
       

	}