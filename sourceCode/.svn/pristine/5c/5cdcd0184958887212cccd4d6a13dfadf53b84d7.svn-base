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
}
