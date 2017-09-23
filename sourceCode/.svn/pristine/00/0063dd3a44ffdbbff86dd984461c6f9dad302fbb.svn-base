package com.sunvote.txpad;

import com.sunvote.txpad.bean.ClassStudent;
import com.sunvote.txpad.bean.LoginInfo;
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

    private ClassStudent classStudent;

    private List<Student> studentList;

    public void setClassStudent(ClassStudent classStudent) {
        this.classStudent = classStudent;
        commit();
    }

    public void setStudentList(List<Student> studentList) {
        this.studentList = studentList;
        commit();
    }

    public ClassStudent getClassStudent() {
        return classStudent;
    }

    public List<Student> getStudentList() {
        return studentList;
    }

    public  void commit(){
        FileCache.getFileCache().saveObject("ApplicationData",this);
    }

    public void restore(){
        ApplicationData data = (ApplicationData) FileCache.getFileCache().readObject("ApplicationData");
        if(data != null){
            setStudentList(data.getStudentList());
            setClassStudent(data.getClassStudent());
        }
    }
}
