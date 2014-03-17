package com.bignerdranch.android.criminalintent;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

public abstract class SingleFragmentActivity extends FragmentActivity {

	protected abstract Fragment createFragment();
	
	@Override
	public void onCreate(Bundle savedInstance) {
		super.onCreate(savedInstance);
		setContentView(R.layout.activity_fragment);
		
		// get the support fragment manager
		FragmentManager fm = getSupportFragmentManager();
		
		// find fragment by id. The id of the container view
		Fragment fragment = fm.findFragmentById(R.id.fragmentContainer);
		
		// check if fragment already exist in the list 
		if(fragment == null) {
			fragment = createFragment();
			
			// add new fragment in the FragmentManager's list
			fm.beginTransaction()
				.add(R.id.fragmentContainer, fragment)
				.commit();
		}
	}
}
