package com.sunvote.txpad.ui.fragment.exam;

import com.sunvote.txpad.ApplicationData;
import com.sunvote.txpad.base.BaseFragmentPresent;
import com.sunvote.txpad.base.BaseSubscriber;
import com.sunvote.txpad.bean.ClassStudent;
import com.sunvote.txpad.bean.Paper;
import com.sunvote.txpad.bean.ResponseDataBean;
import com.sunvote.txpad.bean.Student;
import com.sunvote.txpad.cache.FileCache;

import java.util.List;

import rx.Observable;

/**
 * Created by Elvis on 2017/9/12.
 * Email:Eluis@psunsky.com
 * 版权所有：长沙中天电子设计开发有限公司
 * Description: 易测（ETest）
 */
public class PaperFragmentPresent extends BaseFragmentPresent<PaperFragmentModel, IPaperFragmentView> {

    public void init(){
        if(ApplicationData.getInstance().getClassStudent() == null) {
            initClassStudent();
        }else if(ApplicationData.getInstance().getStudentList() == null){
            initStudent(ApplicationData.getInstance().getClassStudent());
        }
    }

    public void getPaperList(boolean refreash) {
        final String userId = ApplicationData.getInstance().getLoginInfo().getUserId();
        final String subjectId = ApplicationData.getInstance().getLoginInfo().getSubjectId();
        if(!refreash) {
            ResponseDataBean<List<Paper>> cache = (ResponseDataBean<List<Paper>>) FileCache.getFileCache().readObject("getPaperList&" + userId + "&" + subjectId);
            if (cache != null) {
                view.addPaperList(cache.getData());
            }
        }else{
            view.showProgress();
        }
        mRxManager.add(model.getPaper(userId,subjectId).subscribe(new BaseSubscriber<ResponseDataBean<List<Paper>>>(){
            @Override
            public void onNext(ResponseDataBean<List<Paper>> listResponseDataBean) {
                if(listResponseDataBean != null){
                    view.addPaperList(listResponseDataBean.getData());
                    FileCache.getFileCache().saveObject("getPaperList&" + userId + "&" + subjectId,listResponseDataBean);
                }
                view.dismissProgress();
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                view.dismissProgress();
            }
        }));
    }

    public void getPaperQuestion() {

    }

    public void initClassStudent(){
        mRxManager.add(model.getClassList().subscribe(new BaseSubscriber<ResponseDataBean<List<ClassStudent>>>() {
            @Override
            public void onNext(ResponseDataBean<List<ClassStudent>> listResponseDataBean) {
                List<ClassStudent> list = listResponseDataBean.getData();
                if (list.size() > 0) {
                    ClassStudent classStudent = list.get(0);
                    ApplicationData.getInstance().setClassStudent(classStudent);
                    view.showClassInfo(classStudent);
                    initStudent(classStudent);
                }
            }
        }));
    }

    public void initStudent(ClassStudent classStudent){
        mRxManager.add(model.getStudentList(classStudent.getClassId()).subscribe(new BaseSubscriber<ResponseDataBean<List<Student>>>(){
            @Override
            public void onNext(ResponseDataBean<List<Student>> listResponseDataBean) {
                if("200".equals(listResponseDataBean.getCode())){
                    List<Student> studentList = listResponseDataBean.getData();
                    ApplicationData.getInstance().setStudentList(studentList);
                }
            }
        }));
    }

}
