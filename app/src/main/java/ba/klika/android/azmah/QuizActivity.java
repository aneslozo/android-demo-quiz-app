package ba.klika.android.azmah;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends AppCompatActivity {

    //Constants
    private static final String TAG = "QuizActivity";
    private static final String QUESTION_KEY_INDEX = "QUESTION_KEY_INDEX";
    private static final int CHEAT_CODE = 0;

    //Buttons
    private Button mButtonTrue;
    private Button mButtonFalse;
    private ImageButton mButtonNext;
    private ImageButton mButtonPrevious;
    private Button mButtonCheat;

    //TextViews
    private TextView mTextQuestion;

    //Arrays
    private Question[] mCollectionQuestions = new Question[]{
            new Question(R.string.text_question_one, true),
            new Question(R.string.text_question_two, true),
            new Question(R.string.text_question_three, false),
            new Question(R.string.text_question_four, false),
            new Question(R.string.text_question_five, true)
    };

    //Variables
    private int mCurrentQuestionIndex = 0;
    private boolean mIsCheater;

    //Start
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate(Bundle) called");
        setContentView(R.layout.activity_quiz);

        // Testing error log on different way by commenting line below!
        mTextQuestion = (TextView) findViewById(R.id.text_question);
        try {
            mTextQuestion.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mCurrentQuestionIndex = (mCurrentQuestionIndex + 1) % mCollectionQuestions.length;
                    mIsCheater = false;
                    updateQuestionText();
                }
            });
        } catch (ArrayIndexOutOfBoundsException ex) {
            Log.e(TAG, "Testing error log for setContentView!", ex);
        }

        mButtonTrue = (Button) findViewById(R.id.button_true);
        mButtonTrue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(true);
            }
        });
        mButtonFalse = (Button) findViewById(R.id.button_false);
        mButtonFalse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(false);
            }
        });

        mButtonNext = (ImageButton) findViewById(R.id.button_next);
        mButtonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentQuestionIndex = (mCurrentQuestionIndex + 1) % mCollectionQuestions.length;
                mIsCheater = false;
                updateQuestionText();
            }
        });

        mButtonPrevious = (ImageButton) findViewById(R.id.button_previous);
        mButtonPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mCurrentQuestionIndex == 0) {
                    mCurrentQuestionIndex = mCollectionQuestions.length - 1;
                } else {
                    mCurrentQuestionIndex = (mCurrentQuestionIndex - 1) % mCollectionQuestions.length;
                }
                mIsCheater = false;
                updateQuestionText();
            }
        });

        //Cheating button creating a new Intent
        mButtonCheat = (Button) findViewById(R.id.button_cheat);
        mButtonCheat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean answerIsTrue = mCollectionQuestions[mCurrentQuestionIndex].isAnswerTrue();
                Intent i = CheatActivity.newItent(QuizActivity.this, answerIsTrue);
                startActivityForResult(i, CHEAT_CODE);
            }
        });

        if (savedInstanceState != null) {
            mCurrentQuestionIndex = savedInstanceState.getInt(QUESTION_KEY_INDEX, 0);
        }

        updateQuestionText();
    }

    //Function to update question text
    private void updateQuestionText() {
        // Log.d(TAG, "Updating question text for question #" + mCurrentQuestionIndex, new Exception());
        int question = mCollectionQuestions[mCurrentQuestionIndex].getTextResourceId();
        mTextQuestion.setText(question);
        Log.d(TAG, "Current question index: " + mCurrentQuestionIndex);

    }

    //Function to check answer
    private void checkAnswer(boolean userPressedTrue) {
        boolean isAnswerTrue = mCollectionQuestions[mCurrentQuestionIndex].isAnswerTrue();

        int messageResourceId = 0;

        if (mIsCheater) {
            messageResourceId = R.string.toast_judgment;
        } else {
            if (userPressedTrue == isAnswerTrue) {
                messageResourceId = R.string.toast_correct_answer;
            } else {
                messageResourceId = R.string.toast_incorrect_answer;
            }
        }

        Toast.makeText(this, messageResourceId, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }

        if (requestCode == CHEAT_CODE) {
            if (data == null) {
                return;
            }
            mIsCheater = CheatActivity.wasAnswerShown(data);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        Log.i(TAG, "onSaveInstanceState(Bundle) called");
        savedInstanceState.putInt(QUESTION_KEY_INDEX, mCurrentQuestionIndex);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart() called");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause() called");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy() called");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume() called");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop() called");
    }
}
