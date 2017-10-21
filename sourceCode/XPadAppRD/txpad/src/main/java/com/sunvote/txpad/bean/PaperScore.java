package com.sunvote.txpad.bean;

import com.google.gson.Gson;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Elvis on 2017/9/29.
 * Email:Eluis@psunsky.com
 * 版权所有：长沙中天电子设计开发有限公司
 * Description: 易测（ETest）
 */
public class PaperScore implements Serializable{


    /**
     * reportId : 1
     * paperId : ZT0000001
     * paperType : 1
     * className : 班级1
     * userId : 100001
     * reportTime : 2017-6-27 09:24:01
     * rightRate : ["90.2%","80.9%","","60%"]
     *
     * {
     "reportId": "1",
     "paperId": "ZT0000001",
     "paperTitle": "测练试卷1",
     "paperType": "ZT",
     "reportTime": "2017-6-27 09:24:01"
     }
     */

    /**
     * 测练试卷1
     */
    private String paperName;
    /**
     * 报表ID，为空表示新建，不为空表示修改
     */
    private String reportId;
    /**
     * 试卷ID
     */
    private String paperId;
    /**
     * 试卷类型，0-公共试卷，1-私有试卷
     */
    private String paperType;
    /**
     * 	班级名称
     */
    private String className;
    /**
     * 	用户ID
     */
    private String userId;
    /**
     * 创建时间
     */
    private String reportTime;

    /**
     * 卷面总分
     */
    private int paperScore ;

    /**
     * 班级平均分
     */
    private float classAverage ;

    /**
     * 	单题正确率（数组形式）
     */
    private List<String> rightRate;
    /**
     * 报表详细内容（按学生）
     */
    private List<StudentScore> detail;

    public String getReportId() {
        return reportId;
    }

    public void setReportId(String reportId) {
        this.reportId = reportId;
    }

    public String getPaperId() {
        return paperId;
    }

    public void setPaperId(String paperId) {
        this.paperId = paperId;
    }

    public String getPaperType() {
        return paperType;
    }

    public void setPaperType(String paperType) {
        this.paperType = paperType;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getReportTime() {
        return reportTime;
    }

    public void setReportTime(String reportTime) {
        this.reportTime = reportTime;
    }

    public List<String> getRightRate() {
        if(rightRate == null){
            rightRate = new ArrayList<>();
        }
        return rightRate;
    }

    public void setRightRate(List<String> rightRate) {
        this.rightRate = rightRate;
    }

    public List<StudentScore> getDetail() {
        return detail;
    }

    public void setDetail(List<StudentScore> detail) {
        this.detail = detail;
    }

    public void setPaperScore(int paperScore) {
        this.paperScore = paperScore;
    }

    public int getPaperScore() {
        return paperScore;
    }

    public float getClassAverage() {
        return classAverage;
    }

    public void setClassAverage(float classAverage) {
        this.classAverage = classAverage;
    }

    @Override
    public int hashCode() {
        if(paperId != null) {
            return paperId.hashCode();
        }
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if(paperId != null && obj instanceof PaperScore){
            PaperScore paperScore = (PaperScore) obj;
            return paperId.equals(paperScore.getPaperId());
        }
        return super.equals(obj);
    }

    public String getPaperName() {
        return paperName;
    }

    public void setPaperName(String paperName) {
        this.paperName = paperName;
    }

    public String toJson(){
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
