package com.sunvote.txpad.ui.fragment.exam;

import com.sunvote.txpad.base.BaseFragementModel;
import com.sunvote.txpad.base.BaseModel;
import com.sunvote.txpad.bean.ClassStudent;
import com.sunvote.txpad.bean.Paper;
import com.sunvote.txpad.bean.Question;
import com.sunvote.txpad.bean.ResponseDataBean;
import com.sunvote.txpad.bean.Student;

import java.util.ArrayList;
import java.util.List;

import retrofit2.http.Query;
import rx.Observable;
import rx.functions.Func2;
import rx.functions.Func5;

/**
 * Created by Elvis on 2017/9/12.
 * Email:Eluis@psunsky.com
 * 版权所有：长沙中天电子设计开发有限公司
 * Description:平板模块键盘投票功能模块
 */
public class PaperFragmentModel extends BaseFragementModel {

    public Observable<ResponseDataBean<List<Paper>>> getPaperList(String userId,String subjectId,String paperType){
        return apiService.getPaperList(userId,subjectId,paperType).compose(BaseModel.<ResponseDataBean<List<Paper>>>io_main());
    }

    public Observable<ResponseDataBean<List<Question>>> getPaperQuestion(String paperId,String paperType){
        return apiService.getPaperQuestion(paperId,paperType).compose(BaseModel.<ResponseDataBean<List<Question>>>io_main());
    }

    public Observable<ResponseDataBean<List<ClassStudent>>> getClassList(){
        return apiService.getClassList().compose(BaseModel.<ResponseDataBean<List<ClassStudent>>>io_main());
    }

    public Observable<ResponseDataBean<List<Student>>> getStudentList(String classId){
        return apiService.getStudentList(classId).compose(BaseModel.<ResponseDataBean<List<Student>>>io_main());
    }

    public Observable<ResponseDataBean<List<Paper>>> getCommonPaper(String subjectId,String paperType){
        return apiService.getCommonPaper(subjectId,paperType).compose(BaseModel.<ResponseDataBean<List<Paper>>>io_main());
    }

    public Observable<ResponseDataBean<List<Paper>>> getPaper(String userId,String subjectId){
        return  Observable.zip(getPaperList(userId,subjectId,"zt"),getPaperList(userId,subjectId,"mn"),
                getPaperList(userId,subjectId,"pv"),getCommonPaper(userId,"zt"),
                getCommonPaper(userId,"mn"),
                new Func5<ResponseDataBean<List<Paper>>, ResponseDataBean<List<Paper>>,
                        ResponseDataBean<List<Paper>>, ResponseDataBean<List<Paper>>,
                        ResponseDataBean<List<Paper>>, ResponseDataBean<List<Paper>>>() {
                    @Override
                    public ResponseDataBean<List<Paper>> call(ResponseDataBean<List<Paper>> listResponseDataBean1,
                                                              ResponseDataBean<List<Paper>> listResponseDataBean2,
                                                              ResponseDataBean<List<Paper>> listResponseDataBean3,
                                                              ResponseDataBean<List<Paper>> listResponseDataBean4,
                                                              ResponseDataBean<List<Paper>> listResponseDataBean5) {
                        ResponseDataBean<List<Paper>> ret = new ResponseDataBean<>();
                        ret.setData(new ArrayList<Paper>());
                        if(listResponseDataBean1.getData() != null){
                            ret.getData().addAll(listResponseDataBean1.getData());
                        }
                        if(listResponseDataBean2.getData() != null){
                            ret.getData().addAll(listResponseDataBean2.getData());
                        }
                        if(listResponseDataBean3.getData() != null){
                            ret.getData().addAll(listResponseDataBean3.getData());
                        }
                        if(listResponseDataBean4.getData() != null){
                            ret.getData().addAll(listResponseDataBean4.getData());
                        }
                        if(listResponseDataBean5.getData() != null){
                            ret.getData().addAll(listResponseDataBean5.getData());
                        }
                        return ret;
                    }
                }).compose(BaseModel.<ResponseDataBean<List<Paper>>>io_main());
    }

}
