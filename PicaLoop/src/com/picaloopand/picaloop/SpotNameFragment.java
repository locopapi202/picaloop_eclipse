package com.picaloopand.picaloop;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.Toast;



public class SpotNameFragment extends Fragment implements OnItemClickListener {
	private static final String LOG_TAG = "PicaLoop";

	private static final String PLACES_API_BASE = "https://maps.googleapis.com/maps/api/place";
	private static final String TYPE_AUTOCOMPLETE = "/autocomplete";
	private static final String OUT_JSON = "/json";

	private static final String API_KEY = "AIzaSyB5RX0sJ3hjaocCLJM6YoMsB471DanTYbg"; // move to webserver for final app **Danson
	
	Button okButton;
	Button cancelButton;
	
	String selectedSpot = null;
	
	SpotNameInterface spotName;

	public SpotNameFragment() {
	}

  @Override
  public void onAttach(Activity activity) {
    super.onAttach(activity);
        try {
        	spotName = (SpotNameInterface) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement SpotNameInterface");
        }
   }
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_spot_name,
				container, false);
		
	    AutoCompleteTextView autoCompView = (AutoCompleteTextView) rootView.findViewById(R.id.spotlocationautoComplete);
	    autoCompView.setAdapter(new PlacesAutoCompleteAdapter(getActivity(), R.layout.list_item));
	    autoCompView.setOnItemClickListener(this);
		
		okButton = (Button) rootView.findViewById(R.id.ok);
		cancelButton = (Button) rootView.findViewById(R.id.cancel);
		
		okButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	clickOk();
            }
        });
		
		cancelButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	clickCancel();
            }
        });
		
		return rootView;
	}
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
        selectedSpot = (String) parent.getItemAtPosition(position);
      //  Toast.makeText(getActivity(), selectedSpot, Toast.LENGTH_SHORT).show();
       		
	}
	
	public interface SpotNameInterface {
	    public void sendSpotName(String s);
	  }	
	
	private void clickCancel(){
		getActivity().finish();
		
	}
	
	private void clickOk(){
		if (selectedSpot == null){
			Toast.makeText(getActivity(), "Please enter the name of the Spot", Toast.LENGTH_SHORT).show();
			return;
		}
		if(selectedSpot.length() == 0){
			Toast.makeText(getActivity(), "Please enter the name of the Spot", Toast.LENGTH_SHORT).show();
			return;
		}
		
		spotName.sendSpotName(selectedSpot);
		getActivity().getFragmentManager().beginTransaction().remove(this).commit();
	}
	
	private ArrayList<String> autocomplete(String input) {
	    ArrayList<String> resultList = null;

	    HttpURLConnection conn = null;
	    StringBuilder jsonResults = new StringBuilder();
	    try {
	        StringBuilder sb = new StringBuilder(PLACES_API_BASE + TYPE_AUTOCOMPLETE + OUT_JSON);
	        sb.append("?key=" + API_KEY);
	        sb.append("&components=country:us"); //- USA
	        //sb.append("&components=country:ro"); //- Romania
	        sb.append("&input=" + URLEncoder.encode(input, "utf8"));

	        URL url = new URL(sb.toString());
	        conn = (HttpURLConnection) url.openConnection();
	        InputStreamReader in = new InputStreamReader(conn.getInputStream());

	        // Load the results into a StringBuilder
	        int read;
	        char[] buff = new char[1024];
	        while ((read = in.read(buff)) != -1) {
	            jsonResults.append(buff, 0, read);
	        }
	    } catch (MalformedURLException e) {
	        Log.e(LOG_TAG, "Error processing Places API URL", e);
	        return resultList;
	    } catch (IOException e) {
	        Log.e(LOG_TAG, "Error connecting to Places API", e);
	        return resultList;
	    } finally {
	        if (conn != null) {
	            conn.disconnect();
	        }
	    }

	    try {
	        // Create a JSON object hierarchy from the results
	        JSONObject jsonObj = new JSONObject(jsonResults.toString());
	        JSONArray predsJsonArray = jsonObj.getJSONArray("predictions");

	        // Extract the Place descriptions from the results
	        resultList = new ArrayList<String>(predsJsonArray.length());
	        for (int i = 0; i < predsJsonArray.length(); i++) {
	            resultList.add(predsJsonArray.getJSONObject(i).getString("description"));
	        }
	    } catch (JSONException e) {
	        Log.e(LOG_TAG, "Cannot process JSON results", e);
	    }

	    return resultList;
	}
	
	
	private class PlacesAutoCompleteAdapter extends ArrayAdapter<String> implements Filterable {
	    private ArrayList<String> resultList;

	    public PlacesAutoCompleteAdapter(Context context, int textViewResourceId) {
	        super(context, textViewResourceId);
	    }

	    @Override
	    public int getCount() {
	        return resultList.size();
	    }

	    @Override
	    public String getItem(int index) {
	        return resultList.get(index);
	    }

	    @Override
	    public Filter getFilter() {
	        Filter filter = new Filter() {
	            @Override
	            protected FilterResults performFiltering(CharSequence constraint) {
	                FilterResults filterResults = new FilterResults();
	                if (constraint != null) {
	                    // Retrieve the autocomplete results.
	                    resultList = autocomplete(constraint.toString());

	                    // Assign the data to the FilterResults
	                    filterResults.values = resultList;
	                    filterResults.count = resultList.size();
	                }
	                return filterResults;
	            }

	            @Override
	            protected void publishResults(CharSequence constraint, FilterResults results) {
	                if (results != null && results.count > 0) {
	                    notifyDataSetChanged();
	                }
	                else {
	                    notifyDataSetInvalidated();
	                }
	            }};
	        return filter;
	    }
	}
	
	
}