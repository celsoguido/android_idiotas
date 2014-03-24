package com.bignerdranch.android.hellomoon;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

public class HelloMoonActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_hello_moon);
		
		addMainFragment();
	}
	
	private void addMainFragment() {
		
		// get the fragment manager
		FragmentManager fm = getSupportFragmentManager();
		
		// search for hello moon fragment container
		Fragment fragment = fm.findFragmentById(R.id.fragmentContainer);
		
		if(fragment == null) {
			fragment = new HelloMoonFragment();
			
			fm.beginTransaction()
				.add(R.id.fragmentContainer, fragment)
				.commit();
		}
	}
}
