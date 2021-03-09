package com.courses.triviatest.data;

import com.courses.triviatest.model.Question;

import java.util.ArrayList;

public interface AnswerListAsyncResponse {
    void processFinish(ArrayList<Question> questionArrayList);

}
