package com.sunvote.txpad.bean;

import java.io.Serializable;

/**
 * Created by Elvis on 2017/9/25.
 * Email:Eluis@psunsky.com
 * 版权所有：长沙中天电子设计开发有限公司
 * Description: 易测（ETest）
 *
 * 这个问题所有学生的答题情况的bean类 给Question bean类引用
 * 注意区别StudentQuestionAnswer， 这个是学生的所有答题的情况
 */
public class QuestionSudentAnswer implements Serializable{

    public QuestionSudentAnswer(){

    }

    public QuestionSudentAnswer(Student student,String answer){
        this.student = student;
        this.answer = answer;
    }

    private Student student;

    private String answer;

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }
}
