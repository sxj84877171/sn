package com.sunvote.txpad.server;

import com.sunvote.txpad.bean.ClassStudent;
import com.sunvote.txpad.bean.LoginInfo;
import com.sunvote.txpad.bean.Paper;
import com.sunvote.txpad.bean.Question;
import com.sunvote.txpad.bean.ResponseDataBean;
import com.sunvote.txpad.bean.Student;
import com.sunvote.txpad.bean.Subject;

import java.util.List;

import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Elvis on 2017/9/12.
 * Email:Eluis@psunsky.com
 * 版权所有：长沙中天电子设计开发有限公司
 * Description:平板模块键盘投票功能模块
 */
public class ApiServiceImpl implements ApiService {
    /**
     * 登陆
     * @param username 用户名
     * @param password 密码
     * @return LoginParam
     */
    @Override
    public Observable<ResponseDataBean<LoginInfo>> login(String username, String password) {
        return Api.getInstance().getService().login(username,password);
    }

    /**
     * 获取班级列表
     * /sunvoteAPI/class/getAll
     */
    @Override
    public Observable<ResponseDataBean<List<ClassStudent>>> getClassList() {
        return Api.getInstance().getService().getClassList();
    }

    /**
     * 获取班级学生列表
     * @param classId 班级id
     * @return
     */
    @Override
    public Observable<ResponseDataBean<List<Student>>> getStudentList( String classId) {
        return Api.getInstance().getService().getStudentList(classId);
    }

    /**
     * 获取科目列表
     *
     * @return
     */
    @Override
    public Observable<ResponseDataBean<List<Subject>>> getSubjectList() {
        return Api.getInstance().getService().getSubjectList();
    }

    /**
     * 获取试卷列表
     *
     * @param userId    用户id
     * @param subjectId 科目id
     * @return
     */
    @Override
    public Observable<ResponseDataBean<List<Paper>>> getPaperList(@Query("userId") String userId, @Query("subjectId") String subjectId) {
        return Api.getInstance().getService().getPaperList(userId,subjectId);
    }

    /**
     * 获取试卷的题目列表
     * @param paperId 试卷ID
     * @return
     */
    @Override
    public Observable<ResponseDataBean<List<Question>>> getPaperQuestion(@Query("paperId") String paperId) {
        return Api.getInstance().getService().getPaperQuestion(paperId);
    }
}
