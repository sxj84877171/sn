package com.sunvote.txpad.ui.fragment.scorebook;

import com.sunvote.txpad.base.BaseFragementModel;
import com.sunvote.txpad.base.BaseModel;
import com.sunvote.txpad.bean.PaperScore;
import com.sunvote.txpad.bean.ResponseDataBean;
import com.sunvote.txpad.cache.FileCache;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;

/**
 * Created by Elvis on 2017/9/30.
 * Email:Eluis@psunsky.com
 * 版权所有：长沙中天电子设计开发有限公司
 * Description: 易测（ETest）
 */
public class ItemViewPagePaperModel extends BaseFragementModel {

    public Observable<ResponseDataBean<List<PaperScore>>> getReportList(String userId,String className){
        return apiService.getReportList(userId,className,null).compose(BaseModel.<ResponseDataBean<List<PaperScore>>>io_main());
    }

    public List<PaperScore> getLocalPaperScore(){
        List<PaperScore> ret = new ArrayList<>();
        List<String> filenames = FileCache.getFileCache().getStartWithFilename("PaperScore");
        for(String filename:filenames) {
            final String trueName = filename.substring(0, filename.indexOf("."));
            PaperScore paperScore = (PaperScore) FileCache.getFileCache().readUnencryptObject(trueName);
            ret.add(paperScore);
        }
        return ret;
    }

}
