package com.picaloopand.picaloop;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

//Comment for pull sample

public class AppSettingsFragment extends Fragment {

	
	View view;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_app_settings,
				container, false);
		return view;
	
	} 
	
	
}
