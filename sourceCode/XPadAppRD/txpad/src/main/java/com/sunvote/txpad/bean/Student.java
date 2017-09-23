package com.sunvote.txpad.bean;

import com.sunvote.sunvotesdk.basestation.KeyBoard;

import java.io.Serializable;

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
    private KeyBoard keyBoard;

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
}
