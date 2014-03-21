package com.bignerdranch.android.criminalintent;

import java.util.Date;
import java.util.UUID;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
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

	public static final int REQUEST_DATE = 0;

	private static final String DIALOG_DATE_TAG =
			"date";

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

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {

		if(resultCode != Activity.RESULT_OK)
			return;

		// get the date crime from the dialog fragment
		if(requestCode == REQUEST_DATE) {

			mCrime.setDate((Date)data.getSerializableExtra(
					DatePickerFragment.CRIME_DATE_EXTRA));

			updateDate();
		}
	}

	private void bindViews(View v) {

		mEditText = (EditText) v.findViewById(R.id.crime_title);

		mCrimeDateButton = (Button) v.findViewById(R.id.crime_date);

		mCrimeSolvedCheckBox = (CheckBox) v.findViewById(R.id.crime_solved);

		setUpListeners();

		setUpContent();
	}

	private void setUpContent() {

		mEditText.setText(mCrime.getTitle());

		updateDate();

		mCrimeSolvedCheckBox.setChecked(mCrime.isSolved());
	}

	private void setUpListeners() {

		// set crime title edit text listener
		mEditText.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(
					CharSequence s, int start, int before, int count) {

				mCrime.setTitle(s.toString());
			}

			@Override
			public void beforeTextChanged(
					CharSequence s, int start, int count, int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {
			}
		});

		// set crime date button listener
		mCrimeDateButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				showDialogDateCrime();
			}
		});

		// set crime solved status checkbox listener
		mCrimeSolvedCheckBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				mCrime.setSolved(isChecked);
			}
		});
	}

	private void showDialogDateCrime() {

		// get the fragment support manager
		FragmentManager fm = getActivity()
				.getSupportFragmentManager();

		DatePickerFragment dialog = DatePickerFragment
				.newInstance(mCrime.getDate());

		dialog.setTargetFragment(CrimeFragment.this, REQUEST_DATE);
		dialog.show(fm, DIALOG_DATE_TAG);
	}

	private void updateDate() {

		mCrimeDateButton.setText(
				DateFormat.format("EEEE, LLL d, yyyy", mCrime.getDate()));
	}
}
