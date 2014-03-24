package com.bignerdranch.android.hellomoon;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

public class HelloMoonFragment extends Fragment {

	private Button mPlayButton;
	private Button mStopButton;
	private AudioPlayer mAudioPlayer;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		// inflate the layout of the fragment
		View view = inflater.inflate(R.layout.fragment_hello_moon, container, false);
		
		bindViews(view);
		
		return view;
	}
	
	private void bindViews(View view) {
		
		mPlayButton = (Button) view.findViewById(R.id.playButton);
		mStopButton = (Button) view.findViewById(R.id.stopButton);
		
		setUpListeners();
	}
	
	private void setUpListeners() {

		mPlayButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
			}
		});

		mStopButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
			}
		});
	}
}
