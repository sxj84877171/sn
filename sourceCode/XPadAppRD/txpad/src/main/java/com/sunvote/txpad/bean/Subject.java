package com.sunvote.txpad.bean;

import java.io.Serializable;

/**
 * Created by Elvis on 2017/9/15.
 * Email:Eluis@psunsky.com
 * 版权所有：长沙中天电子设计开发有限公司
 * Description: 易测（ETest）
 */
public class Subject implements Serializable {


    /**
     * subjectCode : 20
     * subjectEname : math
     * subjectZname : 初中数学
     */

    /**
     * 课程编号
     */
    private String subjectCode;
    /**
     * 课程英文名
     */
    private String subjectEname;

    /**
     * 课程中文名
     */
    private String subjectZname;

    public String getSubjectCode() {
        return subjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    public String getSubjectEname() {
        return subjectEname;
    }

    public void setSubjectEname(String subjectEname) {
        this.subjectEname = subjectEname;
    }

    public String getSubjectZname() {
        return subjectZname;
    }

    public void setSubjectZname(String subjectZname) {
        this.subjectZname = subjectZname;
    }
}
