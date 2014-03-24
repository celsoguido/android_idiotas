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

	private AudioPlayer mAudioPlayer = new AudioPlayer();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setRetainInstance(true);
	}

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

		setButtonText();

		setUpListeners();
	}

	private void setUpListeners() {

		mPlayButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				playResumeAudio();
			}
		});

		mStopButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				stopAudio();
			}
		});
	}

	@Override
	public void onDestroy() {
		super.onDestroy();

		// stop the audio to release the resources
		mAudioPlayer.stop();
	}

	private void playResumeAudio() {

		// get the string to show in the button: "pause" or "resume"
		mAudioPlayer.play(getActivity());

		setButtonText();
	}

	private void setButtonText() {

		int message = 0;

		if(mAudioPlayer.isPlaying()) {
			message = R.string.hellomoon_pause;
		} else {
			message = R.string.hellomoon_play;
		}

		mPlayButton.setText(message);
	}

	private void stopAudio() {

		mPlayButton.setText(R.string.hellomoon_play);
		mAudioPlayer.stop();
	}
}
