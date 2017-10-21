package com.sunvote.txpad;

import com.sunvote.txpad.bean.ClassStudent;
import com.sunvote.txpad.bean.LoginInfo;
import com.sunvote.txpad.bean.Paper;
import com.sunvote.txpad.bean.Student;
import com.sunvote.txpad.cache.FileCache;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Elvis on 2017/9/15.
 * Email:Eluis@psunsky.com
 * 版权所有：长沙中天电子设计开发有限公司
 * Description: 易测（ETest）
 */
public class ApplicationData implements Serializable{
    private static final ApplicationData ourInstance = new ApplicationData();

    public static ApplicationData getInstance() {
        return ourInstance;
    }

    protected ApplicationData() {
    }

    /**
     * 用户登录信息，包含用户的个人信息
     */
    private LoginInfo loginInfo;

    public void setLoginInfo(LoginInfo loginInfo) {
        this.loginInfo = loginInfo;
    }

    public LoginInfo getLoginInfo() {
        if(loginInfo == null){
            loginInfo = new LoginInfo();
        }
        return loginInfo;
    }

    /**
     * 当前班级信息。
     * 该信息和试卷信息为2个方面。
     *如果当前需要签到，设备检测等信息，则使用班级信息，班级信息关联学生信息，试卷信息，答题信息
     * 如果是随堂测，则使用试卷信息，无需关注班级信息，试卷信息关联键盘信息，答题信息
     */
    private ClassStudent classStudent;

    public void setClassStudent(ClassStudent classStudent) {
        this.classStudent = classStudent;
        commit();
    }

    public void setStudentList(List<Student> studentList) {
        if(classStudent != null){
            classStudent.setStudentList(studentList);
        }
        commit();
    }

    public void setClassPaper(Paper paper){
        if(classStudent != null){
            classStudent.setPaper(paper);
        }
        commit();
    }

    public Paper getClassPaper(){
        if(classStudent != null){
            return classStudent.getPaper();
        }
        return null;
    }

    public ClassStudent getClassStudent() {
        return classStudent;
    }

    public List<Student> getStudentList() {
        if(classStudent != null){
            return classStudent.getStudentList();
        }
        return null;
    }

    /**
     * 当前试卷信息。
     * 该信息和试卷信息为2个方面。
     *如果当前需要签到，设备检测等信息，则使用班级信息，班级信息关联学生信息，试卷信息，答题信息
     * 如果是随堂测，则使用试卷信息，无需关注班级信息，试卷信息关联键盘信息，答题信息
     */
    private Paper noClassPaper;

    public void setNoClassPaper(Paper noClassPaper) {
        this.noClassPaper = noClassPaper;
    }

    public Paper getNoClassPaper() {
        return noClassPaper;
    }

    /**
     * 持久化保存信息
     */
    public  void commit(){
        FileCache.getFileCache().saveUnencryptObject("ApplicationData" ,this);
    }

    /**
     * 从硬盘恢复信息。
     * 保存上次的信息， 另外一个原因是断电保护，程序闪退
     */
    public void restore(){
        ApplicationData data = (ApplicationData) FileCache.getFileCache().readUnencryptObject("ApplicationData");
        if (data != null) {
            setClassStudent(data.getClassStudent());
            setNoClassPaper(data.getNoClassPaper());
        }
    }
}
