package com.sunvote.txpad.server;

import com.sunvote.txpad.bean.ClassStudent;
import com.sunvote.txpad.bean.LoginInfo;
import com.sunvote.txpad.bean.Paper;
import com.sunvote.txpad.bean.Question;
import com.sunvote.txpad.bean.ResponseDataBean;
import com.sunvote.txpad.bean.Student;
import com.sunvote.txpad.bean.Subject;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Elvis on 2017/9/12.
 * Email:Eluis@psunsky.com
 * 版权所有：长沙中天电子设计开发有限公司
 * Description:平板模块键盘投票功能模块
 */
public interface ApiService {

    /**
     * 用户登陆
     *
     * @param username
     * @param password
     * @return LoginParam
     */
    @POST("user/login")
    public Observable<ResponseDataBean<LoginInfo>> login(@Query("userName") String username, @Query("password") String password);


    /**
     * 获取班级列表
     * /sunvoteAPI/class/getAll
     */
    @GET("class/getAll")
    public Observable<ResponseDataBean<List<ClassStudent>>> getClassList();


    /**
     * 获取班级学生列表
     *
     * @param classId 班级id
     * @return
     */
    @GET("class/getStudentList")
    public Observable<ResponseDataBean<List<Student>>> getStudentList(@Query("classId") String classId);


    /**
     * 获取科目列表
     *
     * @return
     */
    @GET("subject/getAll")
    public Observable<ResponseDataBean<List<Subject>>> getSubjectList();

    /**
     * 获取试卷列表
     *
     * @param userId    用户id
     * @param subjectId 科目id
     * @return
     */
    @GET("paper/getPaperList")
    public Observable<ResponseDataBean<List<Paper>>> getPaperList(@Query("userId") String userId, @Query("subjectId") String subjectId);


    @GET("paper/getPaperQuestion")
    public Observable<ResponseDataBean<List<Question>>> getPaperQuestion(@Query("paperId") String paperId);
}
