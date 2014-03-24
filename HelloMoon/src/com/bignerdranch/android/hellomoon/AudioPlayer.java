package com.bignerdranch.android.hellomoon;

import android.content.Context;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;

public class AudioPlayer {

	private MediaPlayer mMediaPlayer;

	private void initMediaPlayer(Context context) {

		mMediaPlayer = MediaPlayer.create(context, R.raw.one_small_step);

		// set listener to release the hardware when finish music
		mMediaPlayer.setOnCompletionListener(new OnCompletionListener() {

			@Override
			public void onCompletion(MediaPlayer mp) {

				// stop current play
				stop();
			}
		});
	}

	public void play(Context context) {

		if(mMediaPlayer == null) {
			initMediaPlayer(context);
		}

		pauseOrResume();
	}

	public void stop() {

		if(mMediaPlayer == null)
			return;

		// release the audio decoder hardware and other system resources
		mMediaPlayer.release();
		mMediaPlayer = null;
	}

	private void pauseOrResume() {

		if(mMediaPlayer.isPlaying()) {

			pause();
		} else {

			resume();
		}
	}

	public void pause() {

		mMediaPlayer.pause();
	}

	public void resume() {

		mMediaPlayer.start();
	}

	public boolean isPlaying() {

		if(mMediaPlayer == null)
			return false;

		return mMediaPlayer.isPlaying();
	}
}
