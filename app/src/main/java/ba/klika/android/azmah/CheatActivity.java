package ba.klika.android.azmah;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.util.Log;

public class CheatActivity extends AppCompatActivity {

    //Constants
    private static final String EXTRA_ANSWER_IS_TRUE = "ba.klika.android.azmah.answer_is_true";
    private static final String EXTRA_ANSWER_SHOWN = "ba.klika.android.azmah.answer_shown";
    private static final String TAG = "CheatActivity";
    private static final String CHEAT_KEY_INDEX = "CHEAT_KEY_INDEX";
    private static final String IS_CLICKED = "IS_CLICKED";

    //Buttons
    private Button mButtonShowAnswer;

    //TextViews
    private TextView mTextAnswerView;

    //Variables
    private boolean mAnswerIsTrue;
    private boolean mIsClicked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat);

        mTextAnswerView = (TextView) findViewById(R.id.text_answer_view);

        mButtonShowAnswer = (Button) findViewById(R.id.button_show_answer);
        mButtonShowAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIsClicked = true;
                if (mAnswerIsTrue) {
                    mTextAnswerView.setText(R.string.button_true);
                } else {
                    mTextAnswerView.setText(R.string.button_false);
                }
                setAnswerShownResults(true);
            }
        });

        if (savedInstanceState != null) {
            mAnswerIsTrue = savedInstanceState.getBoolean(CHEAT_KEY_INDEX);
            mIsClicked = savedInstanceState.getBoolean(IS_CLICKED);
            if (mIsClicked) {
                if (mAnswerIsTrue) {
                    mTextAnswerView.setText(R.string.button_true);
                } else {
                    mTextAnswerView.setText(R.string.button_false);
                }
                setAnswerShownResults(true);
            } else {
                return;
            }
        } else {
            mAnswerIsTrue = getIntent().getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false);
        }
    }

    //This static method allows us to create an Intent properly configured with the extras CheatActivity will need.
    public static Intent newItent(Context packageContext, boolean answerIsTrue) {
        Intent i = new Intent(packageContext, CheatActivity.class);
        i.putExtra(EXTRA_ANSWER_IS_TRUE, answerIsTrue);
        return i;
    }

    public static boolean wasAnswerShown(Intent results) {
        return results.getBooleanExtra(EXTRA_ANSWER_SHOWN, false);
    }

    private void setAnswerShownResults(boolean isAnswerShown) {
        Intent data = new Intent();
        data.putExtra(EXTRA_ANSWER_SHOWN, isAnswerShown);
        setResult(RESULT_OK, data);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        Log.i(TAG, "onSaveInstanceState(Bundle) CheatActivity called");
        savedInstanceState.putBoolean(CHEAT_KEY_INDEX, mAnswerIsTrue);
        savedInstanceState.putBoolean(IS_CLICKED, mIsClicked);
    }
}
