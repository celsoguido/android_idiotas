package com.bignerdranch.android.criminalintent;

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

	private Crime mCrime;
	private EditText mEditText;
	private Button mCrimeDateButton;
	private CheckBox mCrimeSolvedCheckBox;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		
		mCrime = new Crime();
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

		mCrimeDateButton.setText(
				DateFormat.format("EEEE, LLL d, yyyy", mCrime.getDate()));
		mCrimeDateButton.setEnabled(false);
		
		mCrimeSolvedCheckBox = (CheckBox) v.findViewById(R.id.crime_solved);

		setUpListeners();
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
