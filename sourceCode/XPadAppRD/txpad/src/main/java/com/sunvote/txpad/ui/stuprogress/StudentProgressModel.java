package com.sunvote.txpad.ui.stuprogress;

import com.sunvote.txpad.base.BaseModel;
import com.sunvote.txpad.bean.ClassStudent;
import com.sunvote.txpad.bean.ResponseDataBean;

import rx.Observable;

/**
 * Created by Elvis on 2017/9/27.
 * Email:Eluis@psunsky.com
 * 版权所有：长沙中天电子设计开发有限公司
 * Description: 易测（ETest）
 */
public class StudentProgressModel extends BaseModel {

    public Observable<ResponseDataBean<Void>> updateStudent(ClassStudent classStudent){
        return apiService.updateStudent(classStudent.toJson()).compose(BaseModel.<ResponseDataBean<Void>>io_main());
    }

}
