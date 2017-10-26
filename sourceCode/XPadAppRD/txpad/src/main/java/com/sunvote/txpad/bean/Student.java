package com.sunvote.txpad.bean;

import android.text.TextUtils;

import com.sunvote.sunvotesdk.basestation.KeyBoard;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Elvis on 2017/9/15.
 * Email:Eluis@psunsky.com
 * 版权所有：长沙中天电子设计开发有限公司
 * Description: 易测（ETest）
 */
public class Student implements Serializable {


    /**
     * studentId : 1000001
     * studentNo : 1
     * studentName : 张三
     */

    private String studentId;
    private int studentNo;
    private String studentName;
    private String studentUID;
    private KeyBoard keyBoard;
    private boolean isSignReady = false;
    private String keypadId;

    private List<StudentQuestionAnswer> studentQuestionAnswerList = new ArrayList<>();

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public int getStudentNo() {
        return studentNo;
    }

    public void setStudentNo(int studentNo) {
        this.studentNo = studentNo;
    }

    public String getStudentUID() {
        return studentUID;
    }

    public void setStudentUID(String studentUID) {
        this.studentUID = studentUID;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public KeyBoard getKeyBoard() {
        if(keyBoard == null){
            keyBoard = new KeyBoard();
        }
        return keyBoard;
    }

    public void setKeyBoard(KeyBoard keyBoard) {
        this.keyBoard = keyBoard;
    }

    public List<StudentQuestionAnswer> getStudentQuestionAnswerList() {
        return studentQuestionAnswerList;
    }

    public void setStudentQuestionAnswerList(List<StudentQuestionAnswer> studentQuestionAnswerList) {
        this.studentQuestionAnswerList = studentQuestionAnswerList;
    }

    @Override
    public boolean equals(Object obj) {
        if(studentId != null && obj instanceof Student){
            return studentId.equals(((Student) obj).getStudentId());
        }
        return super.equals(obj);
    }

    public String getAnswerProgress(){
        if(studentQuestionAnswerList != null){
            int reply = 0 ;
            for(StudentQuestionAnswer studentQuestionAnswer : studentQuestionAnswerList){
                if(!TextUtils.isEmpty(studentQuestionAnswer.getAnswer())){
                    reply ++ ;
                }
            }
            return progressText(reply,studentQuestionAnswerList.size());
        }
        return "0%" ;
    }

    private String progressText(int reply,int all){
        float f1 = reply;
        float f2 = all ;
        if(f2 == 0){
            f2 = 1;
        }
        float f3 = (f1/f2);
        f3 = f3 + 0.005f ;
        int i3 = (int)(f3*100);
        return i3 + "%";
    }

    public boolean isSignReady() {
        return isSignReady;
    }

    public void setSignReady(boolean signReady) {
        isSignReady = signReady;
    }

    public void setKeypadId(String keypadId) {
        this.keypadId = keypadId;
    }

    public String getKeypadId() {
        return keypadId;
    }
}
