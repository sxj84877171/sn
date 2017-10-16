package com.sunvote.txpad.ui.namelist;

import com.sunvote.txpad.ApplicationData;
import com.sunvote.txpad.base.BasePresent;
import com.sunvote.txpad.base.BaseSubscriber;
import com.sunvote.txpad.bean.ClassStudent;
import com.sunvote.txpad.bean.ResponseDataBean;
import com.sunvote.txpad.bean.Student;
import com.sunvote.txpad.cache.FileCache;
import com.sunvote.util.LogUtil;

import java.util.List;

/**
 * Created by Elvis on 2017/9/14.
 * Email:Eluis@psunsky.com
 * 版权所有：长沙中天电子设计开发有限公司
 * Description: 易测（ETest）
 */
public class NameListPresent extends BasePresent<NameListModel,INameListView> {

    private List<ClassStudent> classStudentList;

    public void getClassList(){

        ResponseDataBean<List<ClassStudent>> cache = (ResponseDataBean<List<ClassStudent>>)FileCache.getFileCache().readObject("getClassList");
        if(cache != null){
            classStudentList = cache.getData();
            if(classStudentList != null) {
                int id = findSelect(classStudentList);
                view.showClassList(classStudentList,id);
                if (id < classStudentList.size()) {
                    getStudentList(classStudentList.get(id).getClassId());
                }
            }
        }
        mRxManager.add(model.getClassList().subscribe(new BaseSubscriber<ResponseDataBean<List<ClassStudent>>>(){
            @Override
            public void onNext(ResponseDataBean<List<ClassStudent>> listResponseDataBean) {
                FileCache.getFileCache().saveObject("getClassList",listResponseDataBean);
                classStudentList = listResponseDataBean.getData();
                if (classStudentList != null) {
                    int id = findSelect(classStudentList);
                    view.showClassList(classStudentList,id);
                    if (id < classStudentList.size()) {
                        getStudentList(classStudentList.get(id).getClassId());
                    }
                }
            }
        }));
    }

    public void getStudentList(final String classId){
        ResponseDataBean<List<Student>> cache = (ResponseDataBean<List<Student>>) FileCache.getFileCache().readObject("getStudentList&" + classId);
        if(cache != null){
            view.showStudents(cache.getData());
        }
        mRxManager.add(model.getStudentList(classId).subscribe(new BaseSubscriber<ResponseDataBean<List<Student>>>(){
            @Override
            public void onNext(ResponseDataBean<List<Student>> listResponseDataBean) {
                FileCache.getFileCache().saveObject("getStudentList&" + classId , listResponseDataBean);
                view.showStudents(listResponseDataBean.getData());
            }
        }));
    }

    public int findSelect(List<ClassStudent> list){
        int index = 0;
        if(list != null && ApplicationData.getInstance().getClassStudent() != null){
            for(ClassStudent classStudent : list){
                if(classStudent.getClassId().equals(ApplicationData.getInstance().getClassStudent().getClassId())){
                    return index;
                }
                index ++ ;
            }
        }
        return index;
    }
}
