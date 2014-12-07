package com.picaloopand.picaloop;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;


public class WelcomeActivity extends Activity implements OnClickListener {
	
	Button googleLoginButton;
	Button fbLoginButton;
	Button pclLoginbutton;
	
		
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        
        googleLoginButton = (Button) findViewById(R.id.googleLoginButton);
        googleLoginButton.setOnClickListener(this);
        fbLoginButton = (Button) findViewById(R.id.fbLoginButton);
        fbLoginButton.setOnClickListener(this);
        pclLoginbutton = (Button) findViewById(R.id.pclLoginbutton);
        pclLoginbutton.setOnClickListener(this);
              
    }
 
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
      //  getMenuInflater().inflate(R.menu.welcome, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
       /* int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item); */
    	return true;
    }


	@Override
	public void onClick(View view) {
		  switch (view.getId()) {
	        case R.id.googleLoginButton:
	            // gmail Signin button clicked
	            signInWithGoogle();
	            break;
	        case R.id.fbLoginButton:
	            // fb Signin button clicked
	        	signInWithFb();
	            break;
	        case R.id.pclLoginbutton:
	            // pcl Signin button clicked
	        	signInWithPcl();
	            break;
	        }
	}
	

	/**
	 * Sign-in using gmail
	 * */
	private void signInWithGoogle() {
		//Toast.makeText(getApplicationContext(), "Google Sign in Clicked!", Toast.LENGTH_LONG).show();
		Intent intent = new Intent(this, GoogleLoginActivity.class);
		startActivity(intent);
	}
	/**
	 * Sign-in using fb
	 * */
	private void signInWithFb() {
		Toast.makeText(getApplicationContext(), "FB Sign in Clicked!", Toast.LENGTH_LONG).show();
	}
	
	/**
	 * Sign-in using pcl
	 * */
	private void signInWithPcl() {
		Toast.makeText(getApplicationContext(), "PCL Sign in Clicked!", Toast.LENGTH_LONG).show();
	}
	
	
}
