package com.example.android.quizapp;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewAnimator;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.viewAnimator) ViewAnimator viewAnimator;
    @BindView(R.id.name_field) EditText enteredName;
    @BindView(R.id.q6) EditText countryFirst;
    @BindView(R.id.summaryText) TextView summaryText;
    @BindView(R.id.summaryText2) TextView summaryText2;
    @BindView(R.id.overallTitles) CheckBox overallTitles;
    @BindView(R.id.goldOlympic) CheckBox goldOlympic;
    @BindView(R.id.goldWC) CheckBox goldWC;
    @BindView(R.id.qOneRadioGroup) RadioGroup radioGroupQ1;
    @BindView(R.id.qTwoRadioGroup) RadioGroup radioGroupQ2;
    @BindView(R.id.qThreeRadioGroup) RadioGroup radioGroupQ3;
    @BindView(R.id.qFiveRadioGroup) RadioGroup radioGroupQ5;
    @BindView(R.id.q1b) RadioButton allTimeDHWinner;
    @BindView(R.id.q2c) RadioButton maxSpeedRecorded;
    @BindView(R.id.q3d) RadioButton theGradient;
    @BindView(R.id.q5b) RadioButton accelerateTime;
    @BindView(R.id.checkBoxesContainer) LinearLayout checkBoxesContainer;

    private int correctAnswersCounter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        Animation animationIn = AnimationUtils.loadAnimation(MainActivity.this, android.R.anim.slide_in_left);
        Animation animationOut = AnimationUtils.loadAnimation(MainActivity.this, android.R.anim.slide_out_right);

        viewAnimator.setInAnimation(animationIn);
        viewAnimator.setOutAnimation(animationOut);
    }

    /**
     * This method invokes an common email intent and creates an email's subject and content
     *
     */
    public void shareTheScoreWithFriends(View view) {
        String emailContent = getString(R.string.emailContent, enteredName.getText().toString(), correctAnswersCounter);
        String emailSubject = getString(R.string.emailSubject, enteredName.getText().toString());

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, emailSubject);
        intent.putExtra(Intent.EXTRA_TEXT, emailContent);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    /**
     * This method checks if the name is provided.
     */
    public void isNameProvided(View view) {
        if (enteredName.getText().toString().equals("")) {
            Toast.makeText(this, getString(R.string.toast_no_name), Toast.LENGTH_SHORT).show();
            return;
        } else {
            viewAnimator.showNext();
        }
    }

    /**
     * This method is responsible for taking user to summary screen and creating it.
     */
    public void goToSummary(View view) {
        checkTheAnswers();
        showSummarizeToast(enteredName.getText().toString());
        viewAnimator.showNext();

        summaryText.setText(getString(R.string.summary, enteredName.getText().toString(), correctAnswersCounter));
        if (correctAnswersCounter < 3) {
            summaryText2.setText(getString(R.string.summary2a));
        } else if (correctAnswersCounter >= 3 && correctAnswersCounter < 5)
            summaryText2.setText(getString(R.string.summary2b));
        else {
            summaryText2.setText(getString(R.string.summary2c));
        }

    }

    /**
     * This method shows Toast message with the right answers score.
     */
    public void showSummarizeToast(String enteredName) {
        Toast.makeText(this, getString(R.string.summary, enteredName, correctAnswersCounter), Toast.LENGTH_LONG).show();
    }

    /**
     * This method change the view to the next one.
     */
    public void goNext(View view) {
        viewAnimator.showNext();
    }

    /**
     * This method change the view to the previous one.
     */
    public void goBack(View view) {
        viewAnimator.showPrevious();
    }

    /**
     * This method checks which answers are correct, counts right answers and return the number of correct answers.
     */
    public int checkTheAnswers() {
        correctAnswersCounter = 0;

        if (allTimeDHWinner.isChecked()) {
            correctAnswersCounter++;
        }

        if (maxSpeedRecorded.isChecked()) {
            correctAnswersCounter++;
        }

        if (theGradient.isChecked()) {
            correctAnswersCounter++;
        }

        if (overallTitles.isChecked() && goldOlympic.isChecked() && goldWC.isChecked()) {
            correctAnswersCounter++;
        }

        if (accelerateTime.isChecked()) {
            correctAnswersCounter++;
        }

        if (countryFirst.getText().toString().toLowerCase().equals("sweden")
                || countryFirst.getText().toString().toLowerCase().equals("szwecja") ) {
            correctAnswersCounter++;
        }

        return correctAnswersCounter;
    }

    /**
     * This method clears all data provided in previous turn.
     */
    public void clearData() {
        enteredName.setText("");
        countryFirst.setText("");
        radioGroupQ1.clearCheck();
        radioGroupQ2.clearCheck();
        radioGroupQ3.clearCheck();
        radioGroupQ5.clearCheck();

        for (int i = 0; i < checkBoxesContainer.getChildCount(); i++) {
            View checkBoxesContainerChild = checkBoxesContainer.getChildAt(i);
            if (checkBoxesContainerChild instanceof CheckBox) {
                ((CheckBox) checkBoxesContainerChild).setChecked(false);
            }
        }
    }

    /**
     * This method sets default view and invoke clearing data method.
     */
    public void startAgain(View view) {
        clearData();
        viewAnimator.setDisplayedChild(0);
    }

}


