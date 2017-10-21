package com.sunvote.txpad.bean;

import android.support.annotation.NonNull;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Elvis on 2017/9/29.
 * Email:Eluis@psunsky.com
 * 版权所有：长沙中天电子设计开发有限公司
 * Description: 易测（ETest）
 */
public class StudentScore implements Comparable<StudentScore>,Serializable {


    /**
     * keySn :键盘SN
     * keyId : 2
     * studentId : 100002
     * studentName : 学生2
     * answer : ["A,0","B,1","","C,1"]
     * subjectiveScore : 10
     * totalScore : 80
     *
     * "keySn": "",
     "keyId": "3",
     "studentId": "100003",
     "studentName": "学生3",
     "score":8,
     "answer": [
     "A,0",
     "B,1",
     "",
     "C,1"
     ],
     "subjectiveScore": 10,
     "totalScore": 80
     */

    /**
     * 键盘SN
     */
    private String keySn;
    /**
     * 键盘id
     */
    private String keyId;
    /**
     * 学生学号
     */
    private String studentId;
    /**
     * 学生姓名
     */
    private String studentName;

    /**
     * 	客观题得分
     */
    private int score;
    /**
     * 主观题得分
     */
    private int subjectiveScore;
    /**
     * 总分
     */
    private int totalScore;
    /**
     * 名次
     */
    private int rang = 1;
    /**
     * 答题情况（数组形式，每项为"答案,是否答对"，如"A,0"表示选择A答错，"B,1"表示选择B答对）
     */
    private List<String> answer;

    public String getKeySn() {
        return keySn;
    }

    public void setKeySn(String keySn) {
        this.keySn = keySn;
    }

    public String getKeyId() {
        return keyId;
    }

    public void setKeyId(String keyId) {
        this.keyId = keyId;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public int getSubjectiveScore() {
        return subjectiveScore;
    }

    public void setSubjectiveScore(int subjectiveScore) {
        this.subjectiveScore = subjectiveScore;
    }

    public int getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
    }

    public List<String> getAnswer() {
        return answer;
    }

    public void setAnswer(List<String> answer) {
        this.answer = answer;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getScore() {
        return score;
    }

    public int getRang() {
        return rang;
    }

    public void setRang(int rang) {
        this.rang = rang;
    }

    @Override
    public int compareTo(StudentScore studentScore) {
        return totalScore - studentScore.getTotalScore();
    }
}
