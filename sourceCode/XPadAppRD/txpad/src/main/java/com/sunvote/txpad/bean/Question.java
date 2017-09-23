package com.sunvote.txpad.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Elvis on 2017/9/15.
 * Email:Eluis@psunsky.com
 * 版权所有：长沙中天电子设计开发有限公司
 * Description: 易测（ETest）
 */
public class Question implements Serializable {


    /**
     * id : c588286d-285b-40d2-b409-92d2faaf8aaa
     * type : 1
     * content : 若在△ABC中，∠C=90°，有一点既在BC的对称轴上，又在AC的对称轴上，则该点一定是（　　）
     * options : ["C点","BC中点","AC中点","AB中点"]
     * answers : A
     * difficulty : 3
     * discuss : 此题主要考查了轴对称的性质以及直角三角形的性质，熟练应用直角三角形的性质是解题关键．
     * analyse : 利用直角三角形的性质，进而得出符合题意的图形即可．
     * method : <img alt="菁优网" src="http://img.jyeoo.net/quiz/images/201410/118/38290219.png" style="vertical-align:middle;FLOAT:right;" />解：如图所示：∵在△ABC中，∠C=90°，有一点既在BC的对称轴上，又在AC的对称轴上，<br />∴由直角三角形斜边的中点到三角形三个顶点的距离相等，则该点一定是AB中点．<br />故选：D．
     * pointCode : P2
     * pointName : 轴对称的性质
     */

    /**
     * 课程试题
     */
    private String id;
    /**
     *
     */
    private int type;

    /**
     * 题目内容
     */
    private String content;
    /**
     * 该题答案
     */
    private String answers;

    /**
     * 难度等级
     */
    private int difficulty;

    /**
     *
     */
    private String discuss;
    private String analyse;
    private String method;
    private String pointCode;
    private String pointName;
    private List<String> options;

    /**
     * 回答该问题的人数
     */
    private int reply = 0;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAnswers() {
        return answers;
    }

    public void setAnswers(String answers) {
        this.answers = answers;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public String getDiscuss() {
        return discuss;
    }

    public void setDiscuss(String discuss) {
        this.discuss = discuss;
    }

    public String getAnalyse() {
        return analyse;
    }

    public void setAnalyse(String analyse) {
        this.analyse = analyse;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getPointCode() {
        return pointCode;
    }

    public void setPointCode(String pointCode) {
        this.pointCode = pointCode;
    }

    public String getPointName() {
        return pointName;
    }

    public void setPointName(String pointName) {
        this.pointName = pointName;
    }

    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }

    public int getReply() {
        return reply;
    }

    public void setReply(int reply) {
        this.reply = reply;
    }
}
