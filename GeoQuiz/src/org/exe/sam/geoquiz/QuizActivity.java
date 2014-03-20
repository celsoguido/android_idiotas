package org.exe.sam.geoquiz;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends Activity {

	private static final String KEY_INDEX
		= "org.exe.sam.geoquiz.KEY_INDEX";
	
	private static final String INDEX_CHEATED_QUESTION
			= "org.exe.sam.geoquiz.INDEX_CHEATED_QUESTION";

	private static final String TAG 					= "QuizActivity";
	private static final int CHEAT_QUESTION_REQUEST		= 100;
	
	private Button mTrueButton;
	private Button mFalseButton;
	private Button mNextQuestionButton;
	private Button mPreviousQuestionButton;
	private Button mCheatButton;

	private TextView mQuestionText;

	private List<TrueFalse> mQuestionBank; 
	private int mCurrentIndex = 0;
	private int mIndexCheatedQuestion = -1;
	private boolean mUserCheated = false;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_quiz);
		
		bindViews();
		
		// set the current index
		if(savedInstanceState != null) {
			mCurrentIndex = savedInstanceState.getInt(KEY_INDEX, 0);
			mIndexCheatedQuestion = savedInstanceState.getInt(INDEX_CHEATED_QUESTION, -1);
			
			mUserCheated = savedInstanceState.getBoolean(CheatActivity.CHEAT_ANSWER_CHECKED, false);
		}
		
		initQuestionBank();
		setCurrentQuestionText();
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {

		super.onSaveInstanceState(outState);
		Log.i(TAG, "onSaveInstanceState");
		
		outState.putInt(KEY_INDEX, mCurrentIndex);
		outState.putInt(INDEX_CHEATED_QUESTION, mIndexCheatedQuestion);
		
		outState.putBoolean(CheatActivity.CHEAT_ANSWER_CHECKED, mUserCheated);
		
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		
		if(requestCode == CHEAT_QUESTION_REQUEST) {
			
			if(resultCode == RESULT_OK) {
				
				if(data != null) {
					
					mUserCheated = data.getBooleanExtra(CheatActivity.CHEAT_ANSWER_CHECKED, false);
					
					if(mUserCheated) {
						mIndexCheatedQuestion = mCurrentIndex;
					}
				}					
			}
		}
	}

	private void initQuestionBank() {
		
		mQuestionBank = new ArrayList<TrueFalse>();
		
		// add question text and answer to bank of questions
		mQuestionBank.add(new TrueFalse(R.string.question_oceans, true));
		mQuestionBank.add(new TrueFalse(R.string.question_mideast, false));
		mQuestionBank.add(new TrueFalse(R.string.question_africa, true));
		mQuestionBank.add(new TrueFalse(R.string.question_americas, false));
		mQuestionBank.add(new TrueFalse(R.string.question_asia, true));
	}

	private void bindViews() {

		mQuestionText = (TextView) findViewById(R.id.question_text);
		
		mTrueButton = (Button) findViewById(R.id.true_button);
		mTrueButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				checkAnswer(true);
			}
		});

		mFalseButton = (Button) findViewById(R.id.false_button);
		mFalseButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				checkAnswer(false);
			}
		});
		
		mNextQuestionButton = (Button) findViewById(R.id.next_question_button);
		mNextQuestionButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				// show next question
				showQuestion(true);
			}
		});

		mPreviousQuestionButton = (Button) findViewById(R.id.previous_question_button);
		mPreviousQuestionButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				// show previous question
				showQuestion(false);
			}
		});
		
		mCheatButton = (Button) findViewById(R.id.cheat_button);
		mCheatButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {

				callCheatActivity();
			}
		});
	}

	private void showQuestion(boolean isNext) {
		
		// update the current index of question bank
		if(isNext) {

			// validate current index
			if(mCurrentIndex + 1 >= mQuestionBank.size()) {

				return;
			}			
			
			// user clicked on next button
			mCurrentIndex++;	
		} else {
			
			// validate current index
			if(mCurrentIndex <= 0) {

				return;
			}			
			
			// user clicked on previous button
			mCurrentIndex--;
		}

		// update the question text
		setCurrentQuestionText();

		// reset flag indicating the user cheated
		//mUserCheated = false;
		
		// check whether the user cheated the current question
		mUserCheated = (mIndexCheatedQuestion == mCurrentIndex);
	}
	
	private void callCheatActivity() {

		Intent mIntent = new Intent(getApplicationContext(), CheatActivity.class);

		// get current TrueFalse question
		TrueFalse mTrueFalse = mQuestionBank.get(mCurrentIndex);

		// put the correct answer and pass it through intent
		mIntent.putExtra(CheatActivity.CHEAT_ANSWER, mTrueFalse.isTrueQuestion());

		// start cheat activity
		startActivityForResult(mIntent, CHEAT_QUESTION_REQUEST);
	}
	
	private void setCurrentQuestionText() {
		
		// update the question text
		TrueFalse mTrueFalseModel = mQuestionBank.get(mCurrentIndex);
		mQuestionText.setText(getResources().getString(mTrueFalseModel.getQuestion()));
	}
	
	private void checkAnswer(boolean userPressedTrue) {
		
		// get the current TrueFalse object
		TrueFalse mTrueFalse = mQuestionBank.get(mCurrentIndex);
		
		// create variable to handle the message
		int mMessage = 0;
		
		if(!mUserCheated) {
			if(userPressedTrue == mTrueFalse.isTrueQuestion()) {
				
				mMessage = R.string.correct_answer_toast;
			} else {
				
				mMessage = R.string.incorrect_answer_toast;
			}		
		} else {
			mMessage = R.string.user_cheated_message;
		}

		Toast.makeText(getApplicationContext(), 
				mMessage, 
				Toast.LENGTH_SHORT).show();
	}
}
