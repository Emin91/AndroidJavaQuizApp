package com.courses.triviatest;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.courses.triviatest.data.AnswerListAsyncResponse;
import com.courses.triviatest.data.QuestionBank;
import com.courses.triviatest.model.Question;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "TAG";
    private Button btn_true, btn_false, btn_next, btn_prev;
    private TextView text_question_counter, text_card_question, text_correct_answers;
    private CardView cardView;
    private int currentQuestionIndex = 0;
    private int countOfCorrectAnswer = 0;
    private List<Question> questionList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
                text_correct_answers.setText(getResources().getString(R.string.text_correct) + " " + countOfCorrectAnswer);
                text_question_counter.setText(currentQuestionIndex + " out of " + questionList.size());
                text_card_question.setText(questionArrayList.get(currentQuestionIndex).getAnswer());
            }
        });
    }

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
                text_card_question.setTextColor(getResources().getColor(R.color.black));
                cardView.setCardBackgroundColor(getResources().getColor(R.color.white));
                if(currentQuestionIndex > 0) {
                    btn_false.setEnabled(false);
                    btn_true.setEnabled(false);
                    currentQuestionIndex = (currentQuestionIndex - 1) % questionList.size();
                    updateQuestion();
                }
                break;
            case R.id.btn_next:
                btn_false.setEnabled(true);
                btn_true.setEnabled(true);
                text_card_question.setTextColor(getResources().getColor(R.color.black));
                cardView.setCardBackgroundColor(getResources().getColor(R.color.white));
                currentQuestionIndex = (currentQuestionIndex + 1) % questionList.size();
                updateQuestion();
                break;
            default:
                break;
        }
    }

    private void checkAnswer(boolean userChooseCorrect) {
        boolean answerIsTrue = questionList.get(currentQuestionIndex).isAnswerTrue();
        if(userChooseCorrect == answerIsTrue) {
            btn_false.setEnabled(false);
            btn_true.setEnabled(false);
            fadeView();
            countOfCorrectAnswer = (countOfCorrectAnswer + 1);
            text_card_question.setTextColor(getResources().getColor(R.color.white));
            cardView.setCardBackgroundColor(getResources().getColor(R.color.card_true));
        } else {
            btn_false.setEnabled(false);
            btn_true.setEnabled(false);
            shakeAnimation();
            text_card_question.setTextColor(getResources().getColor(R.color.white));
            cardView.setCardBackgroundColor(getResources().getColor(R.color.card_false));
        }
    }

    private void updateQuestion() {
        String question = questionList.get(currentQuestionIndex).getAnswer();
        text_correct_answers.setText(getResources().getString(R.string.text_correct) + " " + countOfCorrectAnswer);
        text_card_question.setText(question);
        text_question_counter.setText(currentQuestionIndex + " out of " + questionList.size());
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

