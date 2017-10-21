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
     * @param paperType 试卷类型(空传回所有类型，zt=真题，mn=模拟)
     * @return
     */
    @Override
    public Observable<ResponseDataBean<List<Paper>>> getPaperList( String userId,  String subjectId,  String paperType) {
        return Api.getInstance().getService().getPaperList(userId,subjectId,paperType);
    }

    /**
     * 获取公共试卷
     *
     * @param subjectId 科目id
     * @param paperType 试卷类型(空传回所有类型，zt=真题，mn=模拟)
     * @return
     */
    @Override
    public Observable<ResponseDataBean<List<Paper>>> getCommonPaper(String subjectId,  String paperType) {
        return Api.getInstance().getService().getCommonPaper(subjectId,paperType);
    }

    /**
     * 获取试卷的题目列表
     * @param paperId   试卷ID
     * @param paperType 试卷类型
     * @return
     */
    @Override
    public Observable<ResponseDataBean<List<Question>>> getPaperQuestion( String paperId,  String paperType) {
        return Api.getInstance().getService().getPaperQuestion(paperId,paperType);
    }

    /***
     * 上传个人试卷
     * @param paper 试卷内容
     * @return
     */
    @Override
    public Observable<ResponseDataBean<String>> addPaper(Paper paper) {
        return Api.getInstance().getService().addPaper(paper);
    }

    /**
     * 添加公共试卷到个人试卷库
     *
     * @param userId  用户ID
     * @param paperId 试卷Id
     * @return
     */
    @Override
    public Observable<ResponseDataBean<Void>> collectCommPaper(String userId,String paperId) {
        return Api.getInstance().getService().collectCommPaper(userId,paperId);
    }

    /**
     * 上传成绩报表时以post方式提交，参数设置为data=json字符串
     *
     * @param paperScore
     * @return
     */
    @Override
    public Observable<ResponseDataBean<String>> addReport(String paperScore) {
        return Api.getInstance().getService().addReport(paperScore);
    }

    /**
     * 根据报表ID查询详细的报表信息
     * @param reportId 报表id
     * @return
     */
    @Override
    public Observable<ResponseDataBean<PaperScore>> getReport(String reportId) {
        return Api.getInstance().getService().getReport(reportId);
    }

    /**
     * 根据用户ID和班级名称获取服务器上已存储的报表列表
     *
     * @param userId    用户Id
     * @param className 班级名称
     * @return
     */
    @Override
    public Observable<ResponseDataBean<List<PaperScore>>> getReportList(String userId,String className,String paperType) {
        return Api.getInstance().getService().getReportList(userId,className,paperType);
    }

}
