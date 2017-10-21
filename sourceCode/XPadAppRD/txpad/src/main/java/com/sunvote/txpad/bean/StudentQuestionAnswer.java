package com.sunvote.txpad.bean;

import java.io.Serializable;

/**
 * Created by Elvis on 2017/9/25.
 * Email:Eluis@psunsky.com
 * 版权所有：长沙中天电子设计开发有限公司
 * Description: 易测（ETest）
 *
 * 学生的所有答题的情况的bean类，是给Student bean类使用
 * 注意区别QuestionStudentAnswer， 这个是这个问题所有学生的答题情况
 *
 */
public class StudentQuestionAnswer implements Serializable {

    public StudentQuestionAnswer(){

    }

    public StudentQuestionAnswer(Question question,String answer){
        this.question = question;
        this.answer = answer;
    }

    private Question question ;

    private String answer ;

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }
}
