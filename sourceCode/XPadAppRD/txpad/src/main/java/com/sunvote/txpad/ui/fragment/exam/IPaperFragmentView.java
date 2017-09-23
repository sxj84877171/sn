package com.sunvote.txpad.ui.fragment.exam;

import com.sunvote.txpad.base.BaseFragmentView;
import com.sunvote.txpad.bean.ClassStudent;
import com.sunvote.txpad.bean.Paper;

import java.util.List;

/**
 * Created by Elvis on 2017/9/18.
 * Email:Eluis@psunsky.com
 * 版权所有：长沙中天电子设计开发有限公司
 * Description: 易测（ETest）
 */
public interface IPaperFragmentView extends BaseFragmentView {

    /**
     * 跳转到预览试题
     * @param classStudent 班级学生
     * @param paper 试卷
     */
    public void gotoPreviewPaper(ClassStudent classStudent, Paper paper);

    /**
     *
     * @param classStudent 班级学生
     * @param paper 试卷
     */
    public void gotoFollowTest(ClassStudent classStudent,Paper paper);

    /**
     *
     * @param classStudent 班级学生
     * @param paper 试卷
     */
    public void gotoPhaseTest(ClassStudent classStudent,Paper paper);


    public void showPaperList(List<Paper> papers);
}
