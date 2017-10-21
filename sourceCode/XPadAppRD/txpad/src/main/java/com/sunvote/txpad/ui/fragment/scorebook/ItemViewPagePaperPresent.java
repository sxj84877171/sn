package com.sunvote.txpad.ui.fragment.scorebook;

import com.sunvote.txpad.ApplicationData;
import com.sunvote.txpad.base.BaseFragmentPresent;
import com.sunvote.txpad.base.BaseSubscriber;
import com.sunvote.txpad.bean.PaperScore;
import com.sunvote.txpad.bean.ResponseDataBean;
import com.sunvote.txpad.cache.FileCache;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Elvis on 2017/9/30.
 * Email:Eluis@psunsky.com
 * 版权所有：长沙中天电子设计开发有限公司
 * Description: 易测（ETest）
 */
public class ItemViewPagePaperPresent extends BaseFragmentPresent<ItemViewPagePaperModel,IItemViewPagePaperView> {

    private boolean mn = true ;
    private boolean zt = true  ;
    private boolean personal = true ;
    private String startTime ;
    private String endTime ;

    private List<PaperScore> paperScoreList = new ArrayList<>();

    public void setStartTime(String startTime) {
        this.startTime = startTime;
        fill(0,0,0);
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
        fill(0,0,0);
    }

    @Override
    public void init() {
        getReportList();
        view.showChooseType(mn,zt,personal);
    }

    public void getReportList(){

        List<PaperScore> temp = (List<PaperScore>) FileCache.getFileCache().readEncryptObject("PaperScoreList" + ApplicationData.getInstance().getLoginInfo().getUserId()+
                ApplicationData.getInstance().getClassStudent().getClassName());
        if(temp != null){
            paperScoreList = temp;
            paperScoreList.addAll(model.getLocalPaperScore());
            List<PaperScore> ret = fillData();
            Collections.sort(ret,comparator);
            view.showData(ret);
            view.showTestPaperInfo(paperScoreList.size(),getMnPaper(),getZtPaper(),getPvPaper());
        }else{
            paperScoreList = model.getLocalPaperScore();
            List<PaperScore> ret = fillData();
            Collections.sort(ret,comparator);
            view.showData(ret);
            view.showTestPaperInfo(paperScoreList.size(),getMnPaper(),getZtPaper(),getPvPaper());
        }
        model.getReportList(ApplicationData.getInstance().getLoginInfo().getUserId(),
                ApplicationData.getInstance().getClassStudent().getClassName())
                .subscribe(new BaseSubscriber<ResponseDataBean<List<PaperScore>>>(){
                    @Override
                    public void onNext(ResponseDataBean<List<PaperScore>> listResponseDataBean) {
                        paperScoreList = listResponseDataBean.getData();
                        FileCache.getFileCache().saveEncryptObject("PaperScoreList" + ApplicationData.getInstance().getLoginInfo().getUserId()+
                                ApplicationData.getInstance().getClassStudent().getClassName(),paperScoreList);
                        paperScoreList.addAll(model.getLocalPaperScore());
                        List<PaperScore> ret = fillData();
                        Collections.sort(ret,comparator);
                        view.showData(ret);
                        view.showTestPaperInfo(paperScoreList.size(),getMnPaper(),getZtPaper(),getPvPaper());
                    }
                });
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
        List<PaperScore> ret = fillData();
        Collections.sort(ret,comparator);
        view.showData(ret);
    }

    private List<PaperScore> fillData(){
        List<PaperScore> ret = new ArrayList<>();
        if(paperScoreList != null) {
            for (PaperScore paperScore : paperScoreList) {
                if (startTime != null && endTime != null) {
                    if (startTime.compareTo(paperScore.getReportTime()) <= 0 &&
                            endTime.compareTo(paperScore.getReportTime()) >= 0) {
                        if (mn && "mn".equals(paperScore.getPaperType())) {
                            ret.add(paperScore);
                        } else if (zt && "zt".equals(paperScore.getPaperType())) {
                            ret.add(paperScore);
                        } else if (personal && "pv".equals(paperScore.getPaperType())) {
                            ret.add(paperScore);
                        }
                    }
                } else {
                    if (mn && "mn".equals(paperScore.getPaperType())) {
                        ret.add(paperScore);
                    } else if (zt && "zt".equals(paperScore.getPaperType())) {
                        ret.add(paperScore);
                    } else if (personal && "pv".equals(paperScore.getPaperType())) {
                        ret.add(paperScore);
                    }
                }
            }
        }
        return ret;
    }

    public int getZtPaper(){
        int count = 0 ;
        if(paperScoreList != null) {
            for (PaperScore paperScore : paperScoreList) {
                if ("zt".equals(paperScore.getPaperType())){
                    count ++ ;
                }
            }
        }
        return count;
    }

    public int getMnPaper(){
        int count = 0 ;
        if(paperScoreList != null) {
            for (PaperScore paperScore : paperScoreList) {
                if ("mn".equals(paperScore.getPaperType())){
                    count ++ ;
                }
            }
        }
        return count;
    }

    public int getPvPaper(){
        int count = 0 ;
        if(paperScoreList != null) {
            for (PaperScore paperScore : paperScoreList) {
                if ("pv".equals(paperScore.getPaperType())){
                    count ++ ;
                }
            }
        }
        return count;
    }

    private Comparator<PaperScore> comparator = new Comparator<PaperScore>() {
        @Override
        public int compare(PaperScore o1, PaperScore o2) {
            if(o1 == null){
                return -1;
            }
            if(o2 == null){
                return 1;
            }
            return o2.getReportTime().compareTo(o1.getReportTime());
        }
    };


}
