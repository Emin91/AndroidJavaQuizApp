package com.courses.triviatest;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.courses.triviatest.data.AnswerListAsyncResponse;
import com.courses.triviatest.data.QuestionBank;
import com.courses.triviatest.model.Question;
import com.courses.triviatest.model.Score;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "TAG";
    private Button btn_true;
    private Button btn_false;
    private Button btn_prev, btn_next;
    private TextView text_question_counter, text_card_question, text_correct_answers;
    private CardView cardView;
    private int scoreCounter = 0;
    private int currentQuestionIndex = 0;
    private List<Question> questionList;
    private Score score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        score = new Score();

        btn_true = findViewById(R.id.btn_true);
        btn_false = findViewById(R.id.btn_false);
        btn_next = findViewById(R.id.btn_next);
        btn_prev = findViewById(R.id.btn_prev);
        text_question_counter = findViewById(R.id.text_question_counter);
        text_card_question = findViewById(R.id.text_card_question);
        text_correct_answers = findViewById(R.id.text_correct_answers);
        cardView = findViewById(R.id.cardView);

        btn_next.setOnClickListener(this);
        btn_prev.setOnClickListener(this);
        btn_true.setOnClickListener(this);
        btn_false.setOnClickListener(this);

        questionList = new QuestionBank().getQuestions(new AnswerListAsyncResponse() {
            @Override
            public void processFinish(ArrayList<Question> questionArrayList) {
                text_correct_answers.setText(String.valueOf(getResources().getString(R.string.text_correct) + " " + score.getScore()));
                text_question_counter.setText(String.valueOf((currentQuestionIndex + 1) +  " out of " + questionList.size()));
                text_card_question.setText(questionArrayList.get(currentQuestionIndex).getAnswer());
            }
        });
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_true:
                checkAnswer(true);
                updateQuestion();
                break;
            case R.id.btn_false:
                checkAnswer(false);
                updateQuestion();
                break;
            case R.id.btn_prev:
                changeCardStyle();
                if(currentQuestionIndex > 0) {
                    currentQuestionIndex = (currentQuestionIndex - 1) % questionList.size();
                    updateQuestion();
                }
                break;
            case R.id.btn_next:
                changeCardStyle();
                currentQuestionIndex = (currentQuestionIndex + 1) % questionList.size();
                updateQuestion();
                break;
            default:
                break;
        }
    }

    private void changeCardStyle() {
        text_card_question.setTextColor(getResources().getColor(R.color.black));
        cardView.setCardBackgroundColor(getResources().getColor(R.color.white));
    }

    private void checkAnswer(boolean userChooseCorrect) {
        boolean answerIsTrue = questionList.get(currentQuestionIndex).isAnswerTrue();
        if(userChooseCorrect == answerIsTrue) {
            fadeView();
            addPoints();
            questionList.get(currentQuestionIndex).setAnswered(true);
            text_card_question.setTextColor(getResources().getColor(R.color.white));
            cardView.setCardBackgroundColor(getResources().getColor(R.color.card_true));
        } else {
            shakeAnimation();
            questionList.get(currentQuestionIndex).setAnswered(true);
            text_card_question.setTextColor(getResources().getColor(R.color.white));
            cardView.setCardBackgroundColor(getResources().getColor(R.color.card_false));
        }
    }

    private void disableAnswerButtonsIsAnswered(Boolean answer) {
        btn_false.setEnabled(answer);
        btn_true.setEnabled(answer);
    }

    private void addPoints() {
        scoreCounter += 1;
        score.setScore(scoreCounter);
    }

    private void updateQuestion() {
        Question question = questionList.get(currentQuestionIndex);
        text_correct_answers.setText(String.valueOf(getResources().getString(R.string.text_correct) + " " + score.getScore()));
        text_card_question.setText(question.getAnswer());
        if (question.isAnswered()) {
            disableAnswerButtonsIsAnswered(false);
        } else {
            disableAnswerButtonsIsAnswered(true);
        }
        text_question_counter.setText(String.valueOf((currentQuestionIndex + 1) + " out of " + questionList.size()));
    }

    private void fadeView() {
        AlphaAnimation alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
        alphaAnimation.setDuration(500);
        alphaAnimation.setRepeatCount(1);
        alphaAnimation.setRepeatMode(Animation.REVERSE);
        cardView.setAnimation(alphaAnimation);
    }

    private void shakeAnimation() {
        Animation shake = AnimationUtils.loadAnimation(MainActivity.this, R.anim.shake_animation);
        cardView.setAnimation(shake);
    }
}

