package com.sunvote.txpad.ui.fragment.exam;

import com.sunvote.txpad.ApplicationData;
import com.sunvote.txpad.base.BaseFragmentPresent;
import com.sunvote.txpad.base.BaseSubscriber;
import com.sunvote.txpad.bean.Paper;
import com.sunvote.txpad.bean.ResponseDataBean;
import com.sunvote.txpad.cache.FileCache;

import java.util.List;

/**
 * Created by Elvis on 2017/9/12.
 * Email:Eluis@psunsky.com
 * 版权所有：长沙中天电子设计开发有限公司
 * Description: 易测（ETest）
 */
public class PaperFragmentPresent extends BaseFragmentPresent<PaperFragmentModel, IPaperFragmentView> {

    public void getPaperList(boolean refreash) {
        final String userId = ApplicationData.getInstance().getLoginInfo().getUserId();
        final String subjectId = ApplicationData.getInstance().getLoginInfo().getSubjectId();
        if(!refreash) {
            ResponseDataBean<List<Paper>> cache = (ResponseDataBean<List<Paper>>) FileCache.getFileCache().readObject("getPaperList&" + userId + "&" + subjectId);
            if (cache != null) {
                view.showPaperList(cache.getData());
            }
        }else{
            view.showProgress();
        }
        model.getPaperList(userId,subjectId)
                .subscribe(new BaseSubscriber<ResponseDataBean<List<Paper>>>() {
                    @Override
                    public void onNext(ResponseDataBean<List<Paper>> listResponseDataBean) {
                        FileCache.getFileCache().saveObject("getPaperList&" + userId + "&" + subjectId,listResponseDataBean);
                        if(listResponseDataBean != null){
                            view.showPaperList(listResponseDataBean.getData());
                        }
                        view.dismissProgress();
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.dismissProgress();
                    }
                });
    }

    public void getPaperQuestion() {

    }

}
