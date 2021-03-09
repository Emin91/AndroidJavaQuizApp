package com.courses.triviatest.util;

import android.app.Activity;
import android.content.SharedPreferences;

public class Prefs {
    private SharedPreferences sharedPreferences;
    private final String HIGH_SCORE = "high_score";

    public Prefs(Activity activity) {
        this.sharedPreferences = activity.getPreferences(activity.MODE_PRIVATE);
    }

    public void saveHighScore(int score) {
        int currentScore = score;
        int lastScore = sharedPreferences.getInt(HIGH_SCORE, 0);
        if(currentScore > lastScore) {
            sharedPreferences.edit().putInt(HIGH_SCORE, currentScore).apply();
        }
    }

    public int getHighScore() {
        return sharedPreferences.getInt(HIGH_SCORE, 0);
    }

}
