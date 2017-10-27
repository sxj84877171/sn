package com.sunvote.txpad.server;

import com.sunvote.txpad.bean.ClassStudent;
import com.sunvote.txpad.bean.LoginInfo;
import com.sunvote.txpad.bean.Paper;
import com.sunvote.txpad.bean.PaperScore;
import com.sunvote.txpad.bean.Question;
import com.sunvote.txpad.bean.ResponseDataBean;
import com.sunvote.txpad.bean.Student;
import com.sunvote.txpad.bean.Subject;

import java.util.List;

import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
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
     * @param userName 用户名
     * @param password 密码
     * @return LoginParam
     */
    @POST("user/login")
    public Observable<ResponseDataBean<LoginInfo>> login(@Query("userName") String userName, @Query("password") String password);


    /**
     * 获取班级列表
     * @return List<ClassStudent> 班级列表
     */
    @GET("class/getAll")
    public Observable<ResponseDataBean<List<ClassStudent>>> getClassList();


    /**
     * 获取班级学生列表
     * @param classId 班级id
     * @return List<Student> 学生列表
     */
    @GET("class/getStudentList")
    public Observable<ResponseDataBean<List<Student>>> getStudentList(@Query("classId") String classId);


    /**
     * 获取科目列表
     * @return List<Subject> 科目列表
     */
    @GET("subject/getAll")
    public Observable<ResponseDataBean<List<Subject>>> getSubjectList();

    /**
     * 获取试卷列表
     * @param userId    用户id
     * @param subjectId 科目id
     * @param paperType 试卷类型(空传回所有类型，zt=真题，mn=模拟)
     * @return
     */
    @GET("paper/getPaperList")
    public Observable<ResponseDataBean<List<Paper>>> getPaperList(@Query("userId") String userId, @Query("subjectId") String subjectId,@Query("paperType") String paperType);

    /**
     * 获取公共试卷
     * @param subjectId 科目id
     * @param paperType 试卷类型(空传回所有类型，zt=真题，mn=模拟)
     * @return
     */
    @GET("paper/getCommonPaper")
    public Observable<ResponseDataBean<List<Paper>>> getCommonPaper(@Query("subjectId")String subjectId,@Query("paperType") String paperType);

    /**
     * 获取试卷的题目列表
     * @param paperId 试卷ID
     * @param paperType 试卷类型
     * @return
     */
    @GET("paper/getPaperQuestion")
    public Observable<ResponseDataBean<List<Question>>> getPaperQuestion(@Query("paperId") String paperId,@Query("paperType")String paperType);

    /***
     * 上传个人试卷
     * @param paper 试卷内容
     * @return
     */
    @POST("paper/addPaper")
    public Observable<ResponseDataBean<String>> addPaper(@Body Paper paper);
    /**
     * 添加公共试卷到个人试卷库
     * @param userId 用户ID
     * @param paperId 试卷Id
     * @return
     */
    @POST("paper/collectCommPaper")
    public Observable<ResponseDataBean<Void>> collectCommPaper(@Field("userId")String userId, @Field("paperId")String paperId);

    /**
     * 上传成绩报表时以post方式提交，参数设置为data=json字符串
     * @param paperScore
     * @return
     */
//    @POST("report/addReport")
//    public Observable<ResponseDataBean<Void>> addReport(@Body PaperScore paperScore);

    /**
     * 上传成绩报表时以post方式提交，参数设置为data=json字符串
     * @param data
     * @return
     */
    @FormUrlEncoded
    @POST("report/addReport")
    public Observable<ResponseDataBean<String>> addReport(@Field("data")String data);

//    @GET("report/addReport")
//    public Observable<ResponseDataBean<String>> addReport(@Query("data")String data);

    /**
     * 根据报表ID查询详细的报表信息
     * @param reportId 	报表id
     * @return
     */
    @GET("report/getReport")
    public Observable<ResponseDataBean<PaperScore>> getReport(@Query("reportId") String reportId);

    /**
     * 根据用户ID和班级名称获取服务器上已存储的报表列表
     * @param userId 	用户Id
     * @param className 班级名称
     * @return
     */
    @GET("report/getReportList")
    public Observable<ResponseDataBean<List<PaperScore>>> getReportList(@Query("userId")String userId,@Query("className")String className,@Query("paperType")String paperType);

    /**
     * 修改学生信息（单个或列表）
     * @param data 修改班级的学生信息，单个或列表，与添加的区别在于是否有studentId
     * @return 状态码：0-执行成功， 其他，执行失败
     */
    @FormUrlEncoded
    @POST("class/updateStudent")
    public Observable<ResponseDataBean<Void>> updateStudent(@Field("data")String data);

    /**
     * 修改学生信息（单个或列表）
     * @param data 修改班级的学生信息，单个或列表，与添加的区别在于是否有studentId
     * @return 状态码：0-执行成功， 其他，执行失败
     */
    @FormUrlEncoded
    @POST("class/addStudent")
    public Observable<ResponseDataBean<Void>> addStudent(@Field("data")String data);
}
