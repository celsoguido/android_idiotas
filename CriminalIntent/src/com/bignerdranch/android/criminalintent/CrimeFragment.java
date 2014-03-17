package com.bignerdranch.android.criminalintent;

import java.util.UUID;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;

public class CrimeFragment extends Fragment {

	public static final String CRIME_ID = 
			"com.bignerdranch.android.criminalintent.CRIME_ID";
	
	private Crime mCrime;
	private EditText mEditText;
	private Button mCrimeDateButton;
	private CheckBox mCrimeSolvedCheckBox;

	/**
	 * method to set the arguments of an instance of CrimeFragment.
	 * This method is called after creating the fragment and before
	 * it is attached to an activity. 
	 * */
	public static CrimeFragment newInstance(UUID crimeId) {
		
		// create the bundle and attach crime id
		Bundle bundle = new Bundle();
		bundle.putSerializable(CRIME_ID, crimeId);
		
		// put bundle in fragment's arguments
		CrimeFragment crimeFragment = new CrimeFragment();
		crimeFragment.setArguments(bundle);
		
		return crimeFragment;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		UUID crimeId = (UUID) getArguments().getSerializable(CRIME_ID);
		
		mCrime = CrimeLab.get(getActivity()).getCrime(crimeId);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent,
			Bundle savedInstanceState) {
		
		View v = inflater.inflate(R.layout.fragment_crime, parent, false);
		
		bindViews(v);
		
		return v;
	}

	private void bindViews(View v) {

		mEditText = (EditText) v.findViewById(R.id.crime_title);
		
		mCrimeDateButton = (Button) v.findViewById(R.id.crime_date);
		mCrimeDateButton.setEnabled(false);
		
		mCrimeSolvedCheckBox = (CheckBox) v.findViewById(R.id.crime_solved);

		setUpListeners();
		
		setUpContent();
	}

	private void setUpContent() {
		
		mEditText.setText(mCrime.getTitle());
		
		mCrimeDateButton.setText(
				DateFormat.format("EEEE, LLL d, yyyy", mCrime.getDate()));
		
		mCrimeSolvedCheckBox.setChecked(mCrime.isSolved());
	}
	
	private void setUpListeners() {
		
		// set crime title edit text listener
		mEditText.addTextChangedListener(new TextWatcher() {

			public void onTextChanged(
					CharSequence s, int start, int before, int count) {
				
				mCrime.setTitle(s.toString());
			}

			public void beforeTextChanged(
					CharSequence s, int start, int count, int after) {
			}

			public void afterTextChanged(Editable s) {
			}
		});
		
		// set crime date button listener
		mCrimeDateButton.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
			}
		});

		// set crime solved status checkbox listener
		mCrimeSolvedCheckBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				mCrime.setSolved(isChecked);
			}
		});
	}
}
