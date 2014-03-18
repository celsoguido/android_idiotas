package com.bignerdranch.android.criminalintent;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

public class CrimeListFragment extends ListFragment {

	private ArrayList<Crime> mCrimes;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getActivity().setTitle(R.string.crimes_title);
		
		mCrimes = CrimeLab.get(getActivity()).getCrimes();
		 
		CrimeAdapter adapter = new CrimeAdapter(mCrimes);
		setListAdapter(adapter);
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		Crime crime = ((CrimeAdapter)getListAdapter()).getItem(position);
		
		Intent intent = new Intent(getActivity(), CrimeActivity.class);
		
		// pass crime_id as serializable object
		intent.putExtra(CrimeFragment.CRIME_ID, crime.getId());
		startActivity(intent);
	}
	
	@Override
	public void onResume() {

		super.onResume();
		((CrimeAdapter)getListAdapter()).notifyDataSetChanged();
	}

	private class CrimeAdapter extends ArrayAdapter<Crime> {
		
		public CrimeAdapter(ArrayList<Crime> crimes) {
			
			// set 0 for view parameter because we are not going to use
			// any defined view.
			super(getActivity(), 0, crimes);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			// check whether convertView is already instantiated
			if(convertView == null) {
				convertView = getActivity().getLayoutInflater()
						.inflate(R.layout.list_item_crime, null);
			}
			
			Crime crime = getItem(position);
			
			// bind views
			TextView titleTextView = (TextView) convertView
					.findViewById(R.id.crime_list_item_title_text_view);
			
			TextView dateTextView = (TextView) convertView
					.findViewById(R.id.crime_list_item_date_text_view);
			
			CheckBox solvedCheckBox = (CheckBox) convertView
					.findViewById(R.id.crime_list_item_solved_checkbox);
			
			// populate views
			titleTextView.setText(crime.getTitle());
			dateTextView.setText(crime.getDate().toString());
			solvedCheckBox.setChecked(crime.isSolved());
			
			return convertView;
		}
	}
}
