package com.sunvote.txpad;

import android.text.TextUtils;
import android.widget.TextView;

import com.sunvote.sunvotesdk.BaseStationManager;
import com.sunvote.sunvotesdk.basestation.DefaultKeyEventCallBack;
import com.sunvote.sunvotesdk.basestation.KeyBoard;
import com.sunvote.txpad.bean.Paper;
import com.sunvote.txpad.bean.PaperScore;
import com.sunvote.txpad.bean.Question;
import com.sunvote.txpad.bean.QuestionSudentAnswer;
import com.sunvote.txpad.bean.ResponseDataBean;
import com.sunvote.txpad.bean.Student;
import com.sunvote.txpad.bean.StudentQuestionAnswer;
import com.sunvote.txpad.bean.StudentScore;
import com.sunvote.txpad.cache.FileCache;
import com.sunvote.txpad.cache.SpCache;
import com.sunvote.util.LogUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import cn.sunars.sdk.SunARS;

/**
 * Created by Elvis on 2017/9/21.
 * Email:Eluis@psunsky.com
 * 版权所有：长沙中天电子设计开发有限公司
 * Description: 易测（ETest）
 */
public class ApplicationDataManager {

    public static final String TAG = "ApplicationDataManager";
    public static final int MODE_CLASS_STUDENT = 1;
    public static final int MODE_NO_CLASS_STUDENT = 2;
    private static final ApplicationDataManager ourInstance = new ApplicationDataManager();
    protected SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private int mode = MODE_CLASS_STUDENT;
    private Map<Integer, Integer> keyBoardReplace = null;
    /**
     * 注册到基站，监听所有信息的处理，对逻辑的处理。
     */
    private DefaultKeyEventCallBack keyEventCallBack = new DefaultKeyEventCallBack() {
        @Override
        public void keyEventKeyResultInfo(final String keyId, final String info, final float time) {
            super.keyEventKeyResultInfo(keyId,info,time);
            if ("1".equals(info)) {
                Student oldStudent = null;
                boolean beSign = isStudentKeyIdSign(keyId);
                if(beSign){
                    oldStudent = findStudentKeyId(keyId);
                }
               Student newStudent = addStudentKeyIdSign(keyId);
                if (oldStudent != null && newStudent != null) {
                    if(!oldStudent.equals(newStudent)){
                        KeyBoard keyBoard = oldStudent.getKeyBoard();
                        if (keyBoard != null) {
                            if (keyId.equals(keyBoard.getKeyId())) {
                                keyBoard.setKeySn(null);
                                keyBoard.setKeyId(null);
                                keyBoard.setSign(false);
                            }
                        }
                    }
//                    cleanStudentKeyIdSign(keyId);
                }
                saveStudentSignInfo();
                LogUtil.i(TAG, "sign " + (newStudent != null ? "success":"fail") + "! keyId:" + keyId + ",info:" + info + ",time:" + time);
            } else {
                answer(keyId, info);
            }

        }

        @Override
        public void keyEventKeyResultStats(String keyId, String info, float time) {
            super.keyEventKeyResultStats(keyId, info, time);
            if (BaseStationManager.getInstance().getBaseStationInfo().getMode() == SunARS.VoteType_KeyPadTest) {
                setKeyboardOnline(keyId, info);
            }
        }

        @Override
        public void keyEventKeyResultLoginInfo(final String keyId, final String info, float time) {
            cleanKeyIdSign(keyId);
            String ret = keyIdSign(info, keyId);
            if (ret == null) {
                BaseStationManager.getInstance().sendKeyboradSignFail(keyId);
            } else if (!ret.equals(keyId)) {
                BaseStationManager.getInstance().sendKeyboradSignFail(keyId);
                BaseStationManager.getInstance().sendKeyboradSignFail(ret);
                cleanKeyIdSign(keyId);
            }
            saveStudentSignInfo();
        }
    };

    private ApplicationDataManager() {
    }

    public static ApplicationDataManager getInstance() {
        return ourInstance;
    }

    public void registerKeyEventCallBack() {
        BaseStationManager.getInstance().registerKeyEventCallBack(keyEventCallBack);
    }

    public void unRegisterKeyEventCallBack() {
        BaseStationManager.getInstance().unRegisterKeyEventCallBack(keyEventCallBack);
    }

    public int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    public void setKeyBoardReplace(Map<Integer, Integer> keyBoardReplace) {
        this.keyBoardReplace = keyBoardReplace;
    }

    /**
     * 答题结果
     * 首先根据key 找到对应的学生。
     * 然后根据info找到问题编号，根据编号找到对应的问题
     * 在学生中对应的问题，填写答案
     * 在问题中，添加该学生的答案
     *
     * @param keyId 键盘ID
     * @param info  键盘答题信息
     */
    private void answer(String keyId, String info) {
        Student student = findStudentByKeyId(keyId);
        Question question = findQuestionByInfo(info);
        String answer = getQuestionAnswerByInfo(info);
        if (student != null && question != null) {
            fillQuestionAnswer(student, question, answer);
            fillStudentAnswer(student, question, answer);
            ApplicationData.getInstance().commit();
            LogUtil.i("Answer", "answer success! , keyId:" + keyId + ",Info: " + info);
        }
    }

    /**
     * 该键盘是否签到
     *
     * @param keyId 键盘信息
     * @return 是否已签到
     */
    private boolean isStudentKeyIdSign(String keyId) {
        List<Student> studentList = ApplicationData.getInstance().getStudentList();
        for (Student student : studentList) {
            KeyBoard keyBoard = student.getKeyBoard();
            if (keyBoard != null) {
                if (keyId.equals(student.getKeypadId())) {
                    return keyBoard.isSign();
                }
            }
        }
        return false;
    }
    /**
     * 该键盘是否签到
     *
     * @param keyId 键盘信息
     * @return 是否已签到
     */
    public Student findStudentKeyId(String keyId) {
        List<Student> studentList = ApplicationData.getInstance().getStudentList();
        for (Student student : studentList) {
            if(keyId.equals(student.getKeypadId())){
                return student;
            }
        }
        return null;
    }

    /**
     * 该键盘是否签到
     *
     * @param keyId 键盘信息
     * @return 是否已签到
     */
    private void cleanStudentKeyIdSign(String keyId) {
        List<Student> studentList = ApplicationData.getInstance().getStudentList();
        for (Student student : studentList) {
            KeyBoard keyBoard = student.getKeyBoard();
            if (keyBoard != null) {
                if (keyId.equals(keyBoard.getKeyId())) {
                    keyBoard.setKeyId(null);
                    keyBoard.setSign(false);
                }
            }
        }
    }

    /**
     * 自动配对键盘签到信息
     *
     * @param keyId
     * @return
     */
    private Student addStudentKeyIdSign(String keyId) {
        List<Student> studentList = ApplicationData.getInstance().getStudentList();
        for (Student student : studentList) {
            KeyBoard keyBoard = student.getKeyBoard();
            if (keyId.equals(student.getKeypadId())) {
                    keyBoard.setKeyId(keyId);
                    keyBoard.setSign(true);
                    student.setSignReady(false);
                    return student;
            }
        }
        return null;
    }

    /**
     * 判断该键盘是否已签到
     *
     * @param keyId 键盘ID
     * @return 是否已签到
     */
    private boolean isStudentKeyIDSign(String keyId) {
        List<Student> studentList = ApplicationData.getInstance().getStudentList();
        for (Student student : studentList) {
            KeyBoard keyBoard = student.getKeyBoard();
            if (keyBoard != null) {
                if (keyId.equals(keyBoard.getKeyId())) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 添加学生签到信息
     *
     * @param keyId TODO
     * @return
     */
    private boolean addStudentSnSign(String keyId) {
        List<Student> studentList = ApplicationData.getInstance().getStudentList();
        for (Student student : studentList) {
            KeyBoard keyBoard = student.getKeyBoard();
            if (keyBoard != null) {
                if (keyBoard.getKeyId() == null) {
                    keyBoard.setKeyId(keyId);
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 获取已签到学生人数
     *
     * @return 已签到人数
     */
    public int getSignStudent() {
        int count = 0;
        List<Student> studentList = ApplicationData.getInstance().getStudentList();
        if (studentList != null) {
            for (Student student : studentList) {
                KeyBoard keyBoard = student.getKeyBoard();
                if (keyBoard != null) {
                    if (keyBoard.isSign()) {
                        count++;
                    }
                }
            }
        }
        return count;
    }

    public int getBlindKeyboardCount(int mode){
        int count = 0;
        List<Student> studentList = ApplicationData.getInstance().getStudentList();
        if (studentList != null) {
            for (Student student : studentList) {
               switch (mode){
                   case 1:
                       if(!TextUtils.isEmpty(student.getKeyBoard().getKeyId())){
                            count ++;
                        }
                       break;
                   case 2:
                       if(!TextUtils.isEmpty(student.getKeyBoard().getKeySn())){
                           count ++;
                       }
                       break;
                   case 3:
                       if(!TextUtils.isEmpty(student.getKeypadId())){
                           count ++;
                       }
                       break;
               }
            }
        }
        return count;
    }

    /**
     * 获取键盘弱电人数
     *
     * @return 人数
     */
    public int getKeyboarWeak() {
        int count = 0;
        List<Student> studentList = ApplicationData.getInstance().getStudentList();
        if (studentList != null) {
            for (Student student : studentList) {
                KeyBoard keyBoard = student.getKeyBoard();
                if (keyBoard != null) {
                    if (keyBoard.isWeak()) {
                        count++;
                    }
                }
            }
        }
        return count;
    }

    /**
     * 获取键盘签到冲突个数
     *
     * @return 学生人数
     */
    public int getKeyboardConflict() {
        int count = 0;
        List<Student> studentList = ApplicationData.getInstance().getStudentList();
        if (studentList != null) {
            for (Student student : studentList) {
                KeyBoard keyBoard = student.getKeyBoard();
                if (keyBoard != null) {
                    if (keyBoard.isConflict()) {
                        count++;
                    }
                }
            }
        }
        return count;
    }

    /**
     * 清除签到信息
     */
    public void clearSign() {
        List<Student> studentList = ApplicationData.getInstance().getStudentList();
        if (studentList != null) {
            for (Student student : studentList) {
                KeyBoard keyBoard = student.getKeyBoard();
                if (keyBoard != null) {
                    keyBoard.setKeyId(null);
                    keyBoard.setKeySn(null);
                    keyBoard.setSign(false);
                    keyBoard.setConflict(false);
                }
            }
        }
        saveStudentSignInfo();
    }

    /**
     * UID 后台签到
     *
     * @param no    学生学号
     * @param keyId 键盘编号
     * @return 键盘编号，成功返回当前编号，失败返回旧的编号
     */
    public String keyIdSign(String no, String keyId) {
        List<Student> studentList = ApplicationData.getInstance().getStudentList();
        if (studentList != null) {
            for (Student student : studentList) {
                if (student.getStudentUID() != null && student.getStudentUID().equals(no)) {
                    KeyBoard keyBoard = student.getKeyBoard();
                    int mode = SpCache.getInstance().getInt(Constants.SAVE_SIGN_MODE_KEY, 1);
                    if(mode == 1) {
                        if (TextUtils.isEmpty(keyBoard.getKeyId())) {
                            keyBoard.setKeyId(keyId);
                            keyBoard.setSign(true);
                            keyBoard.setConflict(false);
                            return keyId;
                        } else {
                            keyBoard.setConflict(true);
                            String keyIdOld = keyBoard.getKeyId();
                            keyBoard.setKeyId(null);
                            keyBoard.setSign(false);
                            return keyIdOld;
                        }
                    }else if(mode == 2){
                        if (TextUtils.isEmpty(keyBoard.getKeySn())) {
                            keyBoard.setKeySn(keyId);
                            keyBoard.setSign(true);
                            keyBoard.setConflict(false);
                            return keyId;
                        } else {
                            keyBoard.setConflict(true);
                            String keyIdOld = keyBoard.getKeyId();
                            keyBoard.setKeySn(null);
                            keyBoard.setSign(false);
                            return keyIdOld;
                        }
                    }
                }
            }
        }
        return null;
    }

    /**
     * UID 清除签到
     *
     * @param keyId 键盘编号
     * @return 键盘编号，成功返回当前编号，失败返回旧的编号
     */
    public boolean cleanKeyIdSign(String keyId) {
        List<Student> studentList = ApplicationData.getInstance().getStudentList();
        if (studentList != null) {
            for (Student student : studentList) {
                    KeyBoard keyBoard = student.getKeyBoard();
                    if (keyId.equals(keyBoard.getKeyId()) || keyId.equals(keyBoard.getKeySn())) {
                        keyBoard.setKeyId(null);
                        keyBoard.setKeySn(null);
                        keyBoard.setSign(false);
                        keyBoard.setConflict(false);
                        return true;
                    }
            }
        }
        return false;
    }

    /**
     * 指定具体的编号键盘在线
     *
     * @param keyId 键盘编号
     * @param info  键盘信息
     */
    public void setKeyboardOnline(String keyId, String info) {
        List<Student> studentList = ApplicationData.getInstance().getStudentList();
        if (studentList != null) {
            for (Student student : studentList) {
                if (keyId.equals(student.getKeyBoard().getKeyId())) {
                    student.getKeyBoard().setOnline(true);
                    String[] strs = info.split(",");
                    if (strs.length >= 3) {
                        float dm = Float.parseFloat(strs[2]);
                        if (dm < 3.6f) {
                            student.getKeyBoard().setWeak(true);
                        }
                    }
                    return;
                }
            }
        }
        Student student = new Student();
        student.getKeyBoard().setKeyId(keyId);
        student.getKeyBoard().setOnline(true);
        studentList.add(student);
        ApplicationData.getInstance().setStudentList(studentList);
    }

    /**
     * 设置所有键盘都为离线状态
     */
    public void setOfflineAllKeyboard() {
        List<Student> studentList = ApplicationData.getInstance().getStudentList();
        List<Student> temp = new ArrayList<>();
        if (studentList != null) {
            for (Student student : studentList) {
                student.getKeyBoard().setOnline(false);
                student.getKeyBoard().setWeak(false);
                if (TextUtils.isEmpty(student.getStudentId())) {
                    temp.add(student);
                }
            }
        }
        for (Student student : temp) {
            studentList.remove(student);
        }
        ApplicationData.getInstance().setStudentList(studentList);
    }

    /**
     * 获取当前学生数量
     *
     * @return
     */
    public int getStudentCount() {
        int count = 0;
        List<Student> studentList = ApplicationData.getInstance().getStudentList();
        if (studentList != null) {
            count = studentList.size();
        }
        return count;
    }

    /**
     * 获取当前在线学生总数
     *
     * @return 在线学生总数
     */
    public int getOnlineStudentCount() {
        int count = 0;
        List<Student> studentList = ApplicationData.getInstance().getStudentList();
        if (studentList != null) {
            for (Student student : studentList) {
                if (student.getKeyBoard().isOnline()) {
                    count++;
                }
            }
        }
        return count;
    }

    /**
     * 获取学生键盘弱电个数
     *
     * @return
     */
    public int getWeakStudentCount() {
        int count = 0;
        List<Student> studentList = ApplicationData.getInstance().getStudentList();
        if (studentList != null) {
            for (Student student : studentList) {
                if (student.getKeyBoard().isWeak()) {
                    count++;
                }
            }
        }
        return count;
    }

    /**
     * 获取当前考试试卷
     *
     * @return
     */
    public Paper getCurrentPaper() {
        if (mode == MODE_CLASS_STUDENT) {
            return ApplicationData.getInstance().getClassPaper();
        } else {
            return ApplicationData.getInstance().getNoClassPaper();
        }
    }

    /**
     * 通过键盘ID，查找对应学生
     *
     * @param keyId 键盘ID
     * @return 学生个人信息
     */
    public Student findStudentByKeyId(String keyId) {
        List<Student> studentList = ApplicationData.getInstance().getStudentList();
        int mode = SpCache.getInstance().getInt(Constants.SAVE_SIGN_MODE_KEY, 1);
        if (studentList != null) {
            for (Student student : studentList) {
                if(mode == 1){
                    if(keyId.equals(student.getKeyBoard().getKeyId())){
                        return student;
                    }
                }
                if(mode == 2){
                    if(keyId.equals(student.getKeyBoard().getKeySn())){
                        return student;
                    }
                }
                if(mode == 3){
                    if(keyId.equals(student.getKeypadId())){
                        return student;
                    }
                }
            }
        }
        return null;
    }

    /**
     * 通过键盘返回信息，查找对应回答的问题
     *
     * @param info 键盘提交信息
     * @return 问题
     */
    public Question findQuestionByInfo(String info) {
        if (info.contains(":")) {
            String[] infos = info.split(":");
            int id = Integer.parseInt(infos[0]);
            if (id <= getCurrentPaper().getQuesCount() && id > 0) {
                return getCurrentPaper().getQuestionList().get(id - 1);
            }
        }
        return null;
    }

    /**
     * 通过键盘提交的信息，获取该问题的答案
     *
     * @param info 键盘提交的信息
     * @return
     */
    public String getQuestionAnswerByInfo(String info) {
        if (info.contains(":")) {
            String[] infos = info.split(":");
            if (infos.length == 2) {
                return infos[1];
            }
        }
        return "";
    }

    /**
     * 填充学生所回答问题的答案
     *
     * @param student  学生
     * @param question 问题
     * @param answer   答案
     */
    private void fillStudentAnswer(Student student, Question question, String answer) {
        List<StudentQuestionAnswer> questionAnswerList = student.getStudentQuestionAnswerList();
        if (questionAnswerList != null) {
            for (StudentQuestionAnswer answer1 : questionAnswerList) {
                if (question.equals(answer1.getQuestion())) {
                    answer1.setAnswer(answer);
                }
            }

        }
    }

    /**
     * 填充问题所对应学生的答案
     *
     * @param student  学生
     * @param question 问题
     * @param answer   答案
     */
    private void fillQuestionAnswer(Student student, Question question, String answer) {
        List<QuestionSudentAnswer> answerList = question.getStudentAnswer();
        if (answerList != null) {
            for (QuestionSudentAnswer questionSudentAnswer : answerList) {
                if (student.equals(questionSudentAnswer.getStudent())) {
                    questionSudentAnswer.setAnswer(answer);
                }
            }
        }
    }

    /**
     * 初始化学生问题答案
     */
    public void fillStudentQuestion() {
        List<Question> questions = getCurrentPaper().getQuestionList();
        List<Student> studentList = ApplicationData.getInstance().getStudentList();
        if (studentList != null && questions != null) {
            for (Student student : studentList) {
                student.getStudentQuestionAnswerList().clear();
                for (Question question : questions) {
                    student.getStudentQuestionAnswerList().add(new StudentQuestionAnswer(question, ""));
                }
            }
        }

    }

    /**
     * 初始化问题对应学生的答案
     */
    public void fillQuestionStudent() {
        List<Question> questions = getCurrentPaper().getQuestionList();
        List<Student> studentList = ApplicationData.getInstance().getStudentList();
        if (studentList != null && questions != null) {
            for (Question question : questions) {
                question.getStudentAnswer().clear();
                for (Student student : studentList) {
                    question.getStudentAnswer().add(new QuestionSudentAnswer(student, ""));
                }
            }
        }
    }

    /**
     * 获取全部完成答题的学生人数
     *
     * @return
     */
    public int getCompeleteExamStudent() {
        int count = 0;
        List<Student> studentList = ApplicationData.getInstance().getStudentList();
        if (studentList != null) {
            for (Student student : studentList) {
                List<StudentQuestionAnswer> answers = student.getStudentQuestionAnswerList();
                boolean compelete = true;
                for (StudentQuestionAnswer answer : answers) {
                    if (TextUtils.isEmpty(answer.getAnswer())) {
                        compelete = false;
                        break;
                    }
                }
                if (compelete) {
                    count++;
                }
            }
        }
        return count;
    }

    /**
     * 获取正在做题（已开始并且未完成）的人数
     *
     * @return
     */
    public int getAnsweringExamStudent() {
        int count = 0;
        List<Student> studentList = ApplicationData.getInstance().getStudentList();
        if (studentList != null) {
            for (Student student : studentList) {
                List<StudentQuestionAnswer> answers = student.getStudentQuestionAnswerList();
                boolean answered = false;
                boolean noanswer = false;
                if (answers != null) {
                    for (StudentQuestionAnswer answer : answers) {
                        if (TextUtils.isEmpty(answer.getAnswer())) {
                            noanswer = true;
                        } else {
                            answered = true;
                        }
                    }
                }
                if (answered && noanswer) {
                    count++;
                }
            }
        }
        return count;
    }


    /**
     * 获取当前还没有回答任何问题的学生人数
     *
     * @return 人数
     */
    public int getNoanswerExamStudent() {
        int count = 0;
        List<Student> studentList = ApplicationData.getInstance().getStudentList();
        for (Student student : studentList) {
            List<StudentQuestionAnswer> answers = student.getStudentQuestionAnswerList();
            boolean noanswer = true;
            for (StudentQuestionAnswer answer : answers) {
                if (!TextUtils.isEmpty(answer.getAnswer())) {
                    noanswer = false;
                    break;
                }
            }
            if (noanswer) {
                count++;
            }
        }
        return count;
    }

    /**
     * 根据考试情况，生成一次考试成绩
     *
     * @return 一次报表试卷
     */
    public PaperScore transferPaperScore() {
        PaperScore paperScore = new PaperScore();
        paperScore.setClassName(ApplicationData.getInstance().getClassStudent().getClassName());
        paperScore.setPaperScore(ApplicationData.getInstance().getClassPaper().getTotalScore());
        paperScore.setPaperId(getCurrentPaper().getPaperId());
        paperScore.setPaperName(getCurrentPaper().getPaperName());
        paperScore.setReportTime(simpleDateFormat.format(new Date()));
        paperScore.setRightRate(getListRate());
        paperScore.setPaperType(getCurrentPaper().getPaperType());
        paperScore.setUserId(ApplicationData.getInstance().getLoginInfo().getUserId());
        List<StudentScore> list = getStudentExamInfo();
        paperScore.setDetail(list);
        paperScore.setClassAverage(getAvlStudentScore(list));
        return paperScore;
    }

    public float getAvlStudentScore(List<StudentScore> list) {
        float avl = 0f;
        int count = 0 ;
        if (list != null) {
            for (StudentScore studentScore : list) {
                if(ApplicationDataHelper.getInstance().isEffectiveStduent(studentScore)) {
                    int socre = studentScore.getScore();
                    avl += socre;
                    count++;
                }
            }
            if(count > 0){
                avl = avl / count;
            }
        }
        return avl;
    }

    /**
     * 获取学生结果答题情况
     *
     * @return
     */
    private List<StudentScore> getStudentExamInfo() {

        List<StudentScore> ret = new ArrayList<>();
        List<Student> students = ApplicationData.getInstance().getStudentList();
        if (students != null) {
            for (Student student : students) {
                StudentScore studentScore = new StudentScore();
                studentScore.setKeyId(student.getKeyBoard().getKeyId());
                studentScore.setKeySn(student.getKeyBoard().getKeySn());
                studentScore.setStudentId(student.getStudentId());
                studentScore.setStudentName(student.getStudentName());
                List<String> list = new ArrayList<>();
                int totalScore = 0;
                List<StudentQuestionAnswer> studentQuestionAnswer = student.getStudentQuestionAnswerList();
                for (StudentQuestionAnswer answer : studentQuestionAnswer) {
                    String answerStr = answer.getAnswer();
                    if (answer.getAnswer().equals(answer.getQuestion().getAnswers())) {
                        answerStr += ",1,1";//如"A,0"表示选择A答错，"B,1"表示选择B答对）
                        totalScore += answer.getQuestion().getScore();
                    } else {
                        answerStr += ",0";
                        if(!TextUtils.isEmpty(student.getKeyBoard().getKeyId())){
                            answerStr += ",1";
                        }else{
                            answerStr += ",0";
                        }
                    }
                    list.add(answerStr);
                }
                studentScore.setScore(totalScore);
                studentScore.setTotalScore(studentScore.getSubjectiveScore() + studentScore.getScore());
                studentScore.setAnswer(list);
                ret.add(studentScore);
            }
        }
        return ret;
    }

    /**
     * 获取试卷单题成绩
     *
     * @return
     */
    private List<String> getListRate() {
        List<String> ret = new ArrayList<>();
        List<Question> questions = getCurrentPaper().getQuestionList();
        if (questions != null) {
            for (Question question : questions) {
                List<QuestionSudentAnswer> questionSudentAnswers = question.getStudentAnswer();
                int all = 0 ;
                int right = 0;
                for (QuestionSudentAnswer answer : questionSudentAnswers) {
                    if(!TextUtils.isEmpty(answer.getStudent().getKeyBoard().getKeyId())){
                        all++;
                    }
                    if (answer.getAnswer().equals(question.getAnswers())) {
                        right++;
                    }
                }
                ret.add(progressText(right, all));
            }
        }
        return ret;
    }


    /**
     * 转换百分数，保留一位小数
     *
     * @param reply
     * @param all
     * @return
     */
    public String progressText(float reply, float all) {
        float f1 = reply;
        float f2 = all;
        if (f2 == 0) {
            f2 = 1;
        }
        float f3 = (f1 / f2);
        f3 = f3 + 0.0005f;
        int i3 = (int) (f3 * 1000);
        return (i3 / 10) + "." + (i3 % 10) + "";
    }

    public void saveStudentSignInfo(){
        ResponseDataBean<List<Student>> responseDataBean = new ResponseDataBean<>();
        responseDataBean.setCode("0");
        responseDataBean.setMsg("");
        responseDataBean.setData(ApplicationData.getInstance().getStudentList());
        FileCache.getFileCache().saveEncryptObject("getStudentList&" + ApplicationData.getInstance().getClassStudent().getClassId(),responseDataBean);
    }
}
