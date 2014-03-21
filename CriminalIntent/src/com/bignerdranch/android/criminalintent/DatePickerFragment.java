package com.bignerdranch.android.criminalintent;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;

public class DatePickerFragment extends DialogFragment {

	public static final String CRIME_DATE_EXTRA =
			"com.bignerdranch.android.criminalintent.CRIME_DATE_EXTRA";

	private Date mCrimeDate;

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {

		// inflate the layout with DatePicker
		View view = getActivity().getLayoutInflater()
				.inflate(R.layout.dialog_date, null);

		bindViews(view);

		return new AlertDialog.Builder(getActivity())
			.setView(view)
			.setTitle(R.string.date_picker_title)
			.setPositiveButton(
					android.R.string.ok,
					new OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							sendResult(Activity.RESULT_OK);
						}
					})
			.create();
	}

	private void sendResult(int resultCode) {

		if(getTargetFragment() == null)
			return;

		Intent intent = new Intent();
		intent.putExtra(CRIME_DATE_EXTRA, mCrimeDate);

		getTargetFragment()
			.onActivityResult(getTargetRequestCode(), resultCode, intent);
	}

	private void bindViews(View view) {

		// set current date to crime date
		DatePicker datePicker = (DatePicker) view.findViewById(R.id.dialog_date_datePicker);

		// get the date from arguments
		mCrimeDate = (Date) getArguments().getSerializable(CRIME_DATE_EXTRA);

		// create a calendar to get the year, month and day
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(mCrimeDate);

		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		int day = calendar.get(Calendar.DAY_OF_MONTH);

		datePicker.init(year, month, day, new OnDateChangedListener() {

			@Override
			public void onDateChanged(DatePicker view, int year, int monthOfYear,
					int dayOfMonth) {

				// translate year, month and day into a Date using GregorianCalendar
				mCrimeDate = new GregorianCalendar(
						year, monthOfYear, dayOfMonth).getTime();

				// update arguments to preserve selected value on rotation
				getArguments().putSerializable(CRIME_DATE_EXTRA, mCrimeDate);
			}
		});
	}

	public static DatePickerFragment newInstance(Date crimeDate) {

		// create intent and package up the crime date
		Bundle bundle = new Bundle();
		bundle.putSerializable(CRIME_DATE_EXTRA, crimeDate);

		// set the DatePickerFragment's Argument
		DatePickerFragment datePickerDialog = new DatePickerFragment();
		datePickerDialog.setArguments(bundle);

		return datePickerDialog;
	}
}
