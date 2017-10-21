package com.sunvote.txpad.ui.namelist;

import com.sunvote.txpad.base.BaseModel;
import com.sunvote.txpad.bean.ClassStudent;
import com.sunvote.txpad.bean.ResponseDataBean;
import com.sunvote.txpad.bean.Student;

import java.util.List;

import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Elvis on 2017/9/14.
 * Email:Eluis@psunsky.com
 * 版权所有：长沙中天电子设计开发有限公司
 * Description: 易测（ETest）
 */
public class NameListModel extends BaseModel {

    public Observable<ResponseDataBean<List<ClassStudent>>> getClassList(){
        return apiService.getClassList().compose(BaseModel.<ResponseDataBean<List<ClassStudent>>>io_main());
    }

    public Observable<ResponseDataBean<List<Student>>> getStudentList(String classId){
        return apiService.getStudentList(classId).compose(BaseModel.<ResponseDataBean<List<Student>>>io_main());
    }

    public ResponseDataBean<List<Student>> mergeData(ResponseDataBean<List<Student>> cache,ResponseDataBean<List<Student>> newData){
        if(newData == null){
            return cache;
        }

        if(cache == null){
            return newData;
        }

        List<Student> students = newData.getData();
        List<Student> cacheStudents = cache.getData();
        if(students != null && cacheStudents != null){
            for(Student student : students){
                for(Student cstudent:cacheStudents){
                    if(student.equals(cstudent)){
                        student.setKeyBoard(cstudent.getKeyBoard());
                    }
                }
            }
        }
        return newData;
    }
}
