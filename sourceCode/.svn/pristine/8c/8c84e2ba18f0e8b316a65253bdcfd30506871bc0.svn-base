package com.sunvote.txpad.ui.main;

import com.sunvote.txpad.base.BaseModel;
import com.sunvote.txpad.bean.ClassStudent;
import com.sunvote.txpad.bean.Paper;
import com.sunvote.txpad.bean.Question;
import com.sunvote.txpad.bean.ResponseDataBean;
import com.sunvote.txpad.bean.Student;

import java.util.List;

import rx.Observable;

/**
 * Created by Elvis on 2017/9/18.
 * Email:Eluis@psunsky.com
 * 版权所有：长沙中天电子设计开发有限公司
 * Description: 易测（ETest）
 */
public class HomeModel extends BaseModel {

    public Observable<ResponseDataBean<List<ClassStudent>>> getClassList(){
        return apiService.getClassList().compose(BaseModel.<ResponseDataBean<List<ClassStudent>>>io_main());
    }

    public Observable<ResponseDataBean<List<Student>>> getStudentList(String classId){
        return apiService.getStudentList(classId).compose(BaseModel.<ResponseDataBean<List<Student>>>io_main());
    }

    public Observable<ResponseDataBean<List<Paper>>> getPaperList(String userId, String subjectId){
        return apiService.getPaperList(userId,subjectId).compose(BaseModel.<ResponseDataBean<List<Paper>>>io_main());
    }

    public Observable<ResponseDataBean<List<Question>>> getPaperQuestion(String paperId){
        return apiService.getPaperQuestion(paperId).compose(BaseModel.<ResponseDataBean<List<Question>>>io_main());
    }

}
