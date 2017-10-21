package com.sunvote.txpad.ui.report;

import com.sunvote.txpad.base.BaseView;
import com.sunvote.txpad.bean.PaperScore;

/**
 * Created by Elvis on 2017/10/9.
 * Email:Eluis@psunsky.com
 * 版权所有：长沙中天电子设计开发有限公司
 * Description: 易测（ETest）
 */
public interface IPaperReportView extends BaseView {

    public void showStudentInfo(int high,int low);

    public void showReport(String html);

    public void showReportLittleInfo(PaperScore paperScore);

    public void showReportStudent(int high,int low);
}
