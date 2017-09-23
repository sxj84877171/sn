package com.sunvote.txpad.ui.main;

import com.sunvote.sunvotesdk.BaseStationManager;
import com.sunvote.txpad.ApplicationData;
import com.sunvote.txpad.ApplicationDataManager;
import com.sunvote.txpad.base.BasePresent;
import com.sunvote.txpad.base.BaseSubscriber;
import com.sunvote.txpad.bean.ClassStudent;
import com.sunvote.txpad.bean.Paper;
import com.sunvote.txpad.bean.ResponseDataBean;

import java.util.List;

/**
 * Created by Elvis on 2017/9/18.
 * Email:Eluis@psunsky.com
 * 版权所有：长沙中天电子设计开发有限公司
 * Description: 易测（ETest）
 */
public class HomePresent extends BasePresent<HomeModel,IHomeActivityView> {

    public void init(){
        BaseStationManager.getInstance().init();
        ApplicationData.getInstance().restore();
        ApplicationDataManager.getInstance().registerKeyEventCallBack();
        if(ApplicationData.getInstance().getClassStudent() == null) {
            model.getClassList().subscribe(new BaseSubscriber<ResponseDataBean<List<ClassStudent>>>() {
                @Override
                public void onNext(ResponseDataBean<List<ClassStudent>> listResponseDataBean) {
                    super.onNext(listResponseDataBean);
                    List<ClassStudent> list = listResponseDataBean.getData();
                    if (list.size() > 0) {
                        ApplicationData.getInstance().setClassStudent(list.get(0));
                    }
                }
            });
        }
        model.getPaperList(ApplicationData.getInstance().getLoginInfo().getUserId(),ApplicationData.getInstance().getLoginInfo().getSubjectId()).subscribe(new BaseSubscriber<ResponseDataBean<List<Paper>>>(){
            @Override
            public void onNext(ResponseDataBean<List<Paper>> listResponseDataBean) {
                super.onNext(listResponseDataBean);

            }
        });
        showPaperFragment();
    }

    public void showManagerFrament(){
        view.showManagerFrament();
    }

    public void showPaperFragment(){
        view.showPaperFragment();
    }

    public void onDestroy(){
        BaseStationManager.getInstance().disconnect();
        ApplicationData.getInstance().commit();
        ApplicationDataManager.getInstance().unRegisterKeyEventCallBack();
    }

}
