package org.exe.sam.geoquiz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CheatActivity extends Activity {

	public static final String CHEAT_ANSWER
		= "org.exe.sam.geoquiz.CHEAT_ANSWER";

	public static final String CHEAT_ANSWER_CHECKED 	
		= "org.exe.sam.geoquiz.CHEAT_ANSWER_CHECKED";

	private Button mShowAnswerButton;
	private Button mDoneButton;
	private TextView mAnswerTextView;
	
	private boolean mAnswerIsTrue 			= false;
	private boolean mUserCheckedCorrectAnswer 	= false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cheat);
		
		bindViews();
		
		Intent mIntent = getIntent();
		
		if(mIntent != null) {
			mAnswerIsTrue = mIntent.getBooleanExtra(CHEAT_ANSWER, false);
		}
		
		if(savedInstanceState != null) {
			mUserCheckedCorrectAnswer = savedInstanceState.
					getBoolean(CHEAT_ANSWER_CHECKED, false);
			
			// show correct answer but only whether the user already clicked on "cheat" button
			if(mUserCheckedCorrectAnswer) {
				showCorrectAnswer();
			}
		}
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		
		outState.putBoolean(CHEAT_ANSWER_CHECKED, mUserCheckedCorrectAnswer);
	}

	@Override
	public void onBackPressed() {
		
		// do not use "super.onBackPressed();" when expecting RESULT_OK in the calling Activity
		//super.onBackPressed();
		
		exitCheatActivity();
	}

	private void bindViews() {
		
		mAnswerTextView = (TextView) findViewById(R.id.answerTextView);
		
		mShowAnswerButton = (Button) findViewById(R.id.showAnswerButton);
		mShowAnswerButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				showCorrectAnswer();
			}
		});
		
		mDoneButton = (Button) findViewById(R.id.doneButton);
		mDoneButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				exitCheatActivity();
			}
		});
	}

	private void exitCheatActivity() {
		
		Intent mIntent = new Intent();
		mIntent.putExtra(CHEAT_ANSWER_CHECKED, mUserCheckedCorrectAnswer);

		setResult(RESULT_OK, mIntent);
		finish();
	}
	
	private void showCorrectAnswer() {
		
		int mMessage = R.string.false_button;
				
		// check the correct answer
		if(mAnswerIsTrue) {
			mMessage = R.string.true_button;
		}
		
		// show correct answer
		mAnswerTextView.setText(mMessage);
		
		// mark flag indicating the user checked the correct answer
		mUserCheckedCorrectAnswer = true;
	}
}
