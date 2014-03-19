package com.bignerdranch.android.criminalintent;

import java.util.ArrayList;
import java.util.UUID;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

public class CrimePagerActivity extends FragmentActivity {

	private ViewPager mViewPager;
	private ArrayList<Crime> mCrimes;

	@Override
	protected void onCreate(Bundle bundle) {

		super.onCreate(bundle);
		setContentView(R.layout.activity_crime_pager);

		mCrimes = CrimeLab.get(this).getCrimes();
		bindViews();
	}

	private void bindViews() {
		mViewPager = (ViewPager) findViewById(R.id.viewPager);
		
		setUpContent();
	}
	
	private void setUpContent() {

		// get the Activity's FragmentManager
		FragmentManager fm = getSupportFragmentManager();
		
		mViewPager.setAdapter(new FragmentStatePagerAdapter(fm) {
			
			@Override
			public int getCount() {
				return mCrimes.size();
			}
			
			@Override
			public Fragment getItem(int index) {
				
				// retrieve the crime id of the position index
				Crime crime = mCrimes.get(index);
				
				return CrimeFragment.newInstance(crime.getId());
			}
		});		
		
		UUID crimeId = (UUID) getIntent()
				.getSerializableExtra(CrimeFragment.CRIME_ID);
		
		// search for crimeId in the list of crimes
		for (int i = 0; i < mCrimes.size(); i++) {
			
			Crime crime = mCrimes.get(i);
			
			if(crimeId.equals(crime.getId())) {
				mViewPager.setCurrentItem(i);
				break;
			}
		} 
	}
}
