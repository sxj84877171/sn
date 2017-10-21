package com.sunvote.txpad.bean;

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
public class Paper implements Serializable {


    /**
     * paperId : 1
     * paperName : 测练试卷名称
     * createTime : 2017-06-27
     * teacherId : 1000001
     * subjectId : 25
     * quesCount : 10
     *
     * {
     "paperId": "25000001",
     "paperName": "地理第一章测试卷",
     "paperTitle": "测练试卷1",
     "createTime": "2017-9-14",
     "quesCount": 5,
     "totalScore": 0,
     "userId": "67982",
     "subjectId": "25",
     "paperType": "zt"
     }
     */

    private String paperId;
    private String paperName;
    private String createTime;
    private String teacherId;
    private String subjectId;
    private int totalScore;
    private String userId;
    private String paperType;
    private int quesCount;
    private List<Question> questionList = new ArrayList<>();

    private List<KeyBoard> keyBoardList = new ArrayList<>();

    public String getPaperId() {
        return paperId;
    }

    public void setPaperId(String paperId) {
        this.paperId = paperId;
    }

    public String getPaperName() {
        return paperName;
    }

    public void setPaperName(String paperName) {
        this.paperName = paperName;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public int getQuesCount() {
        return quesCount;
    }

    public void setQuesCount(int quesCount) {
        this.quesCount = quesCount;
    }

    public void setQuestionList(List<Question> questionList) {
        this.questionList = questionList;
    }

    public List<Question> getQuestionList() {
        return questionList;
    }

    public void setKeyBoardList(List<KeyBoard> keyBoardList) {
        this.keyBoardList = keyBoardList;
    }

    public List<KeyBoard> getKeyBoardList() {
        return keyBoardList;
    }

    public int getTotalScore() {
        return totalScore;
    }

    public String getPaperType() {
        return paperType;
    }

    public String getUserId() {
        return userId;
    }

    public void setPaperType(String paperType) {
        this.paperType = paperType;
    }

    public void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
