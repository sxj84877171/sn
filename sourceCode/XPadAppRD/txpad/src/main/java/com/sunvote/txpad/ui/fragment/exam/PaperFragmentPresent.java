package com.sunvote.txpad.ui.fragment.exam;

import com.sunvote.txpad.ApplicationData;
import com.sunvote.txpad.base.BaseFragmentPresent;
import com.sunvote.txpad.base.BaseSubscriber;
import com.sunvote.txpad.bean.ClassStudent;
import com.sunvote.txpad.bean.Paper;
import com.sunvote.txpad.bean.ResponseDataBean;
import com.sunvote.txpad.bean.Student;
import com.sunvote.txpad.cache.FileCache;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Elvis on 2017/9/12.
 * Email:Eluis@psunsky.com
 * 版权所有：长沙中天电子设计开发有限公司
 * Description: 易测（ETest）
 */
public class PaperFragmentPresent extends BaseFragmentPresent<PaperFragmentModel, IPaperFragmentView> {

    private boolean mn = true;
    private boolean zt = true;
    private boolean personal = true;
    private List<Paper> paperList ;

    public void init(){
        if(ApplicationData.getInstance().getClassStudent() == null) {
            view.showProgress();
            initClassStudent();
        }else if(ApplicationData.getInstance().getStudentList() == null){
            view.showProgress();
            initStudent(ApplicationData.getInstance().getClassStudent());
        }
    }

    public void getPaperList(boolean refreash) {
        final String userId = ApplicationData.getInstance().getLoginInfo().getUserId();
        final String subjectId = ApplicationData.getInstance().getLoginInfo().getSubjectId();
        if(!refreash) {
            ResponseDataBean<List<Paper>> cache = (ResponseDataBean<List<Paper>>) FileCache.getFileCache().readEncryptObject("getPaperList&" + userId + "&" + subjectId);
            if (cache != null) {
                paperList = cache.getData();
                view.addPaperList(fillData());
            }else{
                view.showProgress();
            }
        }else{
            view.showProgress();
        }
        mRxManager.add(model.getPaper(userId,subjectId).subscribe(new BaseSubscriber<ResponseDataBean<List<Paper>>>(){
            @Override
            public void onNext(ResponseDataBean<List<Paper>> listResponseDataBean) {
                if(listResponseDataBean != null){
                    paperList = listResponseDataBean.getData();
                    view.addPaperList(fillData());
                    FileCache.getFileCache().saveEncryptObject("getPaperList&" + userId + "&" + subjectId,listResponseDataBean);
                }
                view.dismissProgress();
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                view.showNetError();
            }

            @Override
            public void onCompleted() {
                super.onCompleted();
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

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                view.showNetError();
            }

            @Override
            public void onCompleted() {
                super.onCompleted();
                view.dismissProgress();
            }
        }));
    }

    public void fill(int mnType,int ztType,int personalType){
        if(mnType > 0){
            mn = !mn;
        }
        if(ztType > 0){
            zt = !zt;
        }
        if(personalType > 0){
            personal = !personal;
        }
        view.showChooseType(mn,zt,personal);
        List<Paper> ret = fillData();
//        Collections.sort(ret,comparator);
        view.showPaperList(ret);
    }

    public List<Paper> fillData(){
        List<Paper> ret = new ArrayList<>();
        for(Paper paper:paperList){
            if(mn){
                if("mn".equals(paper.getPaperType())){
                    ret.add(paper);
                }
            }
            if(zt){
                if("zt".equals(paper.getPaperType())){
                    ret.add(paper);
                }
            }
            if(personal){
                if("pv".equals(paper.getPaperType())){
                    ret.add(paper);
                }
            }
        }
        return ret;
    }
}
