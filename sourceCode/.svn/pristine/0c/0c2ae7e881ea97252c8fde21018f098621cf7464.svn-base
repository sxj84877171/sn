package com.sunvote.txpad.ui.fragment.scorebook;

import com.sunvote.txpad.ApplicationData;
import com.sunvote.txpad.base.BasePresent;
import com.sunvote.util.DateUtil;

import java.util.Date;

/**
 * Created by Elvis on 2017/9/29.
 * Email:Eluis@psunsky.com
 * 版权所有：长沙中天电子设计开发有限公司
 * Description: 易测（ETest）
 */
public class ScoreBookPresent extends BasePresent<ScoreBookModel,IScoreBookFragmentView> {

    private String startTime ;
    private String endTime ;

    @Override
    public void init() {
        view.showClassInfo(ApplicationData.getInstance().getClassStudent());
        startTime = DateUtil.getPreMonthDay();
        view.showStartTime(startTime);
        endTime = DateUtil.getMonthDay();
        view.showEndTime(endTime);
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getStartTime() {
        return startTime;
    }
}
