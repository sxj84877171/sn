package com.sunvote.txpad.ui.fragment.scorebook;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.sunvote.txpad.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Elvis on 2017/9/29.
 * Email:Eluis@psunsky.com
 * 版权所有：长沙中天电子设计开发有限公司
 * Description: 易测（ETest）
 */
public class ScoreBookContentAdapter extends PagerAdapter {

    private BaseActivity mBaseActivity;
    private List<View> viewList = new ArrayList<>();
    private ItemViewPagePaper itemViewPagePaper;
    private ItemViewStudentExamInfo itemViewStudentExamInfo;

    public ScoreBookContentAdapter(BaseActivity baseActivity){
        this.mBaseActivity = baseActivity ;
        itemViewPagePaper = new ItemViewPagePaper(mBaseActivity);
        viewList.add(itemViewPagePaper.getRootView());
//        itemViewStudentExamInfo = new ItemViewStudentExamInfo(mBaseActivity);
//        viewList.add(itemViewStudentExamInfo.getRootView());

    }

    /**
     * Return the number of views available.
     */
    @Override
    public int getCount() {
        return viewList.size();
    }

    /**
     * Determines whether a page View is associated with a specific key object
     * as returned by {@link #instantiateItem(ViewGroup, int)}. This method is
     * required for a PagerAdapter to function properly.
     *
     * @param view   Page View to check for association with <code>object</code>
     * @param object Object to check for association with <code>view</code>
     * @return true if <code>view</code> is associated with the key object <code>object</code>
     */
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(viewList.get(position));
        return viewList.get(position);
    }

    public void setStartTime(String startTime){
        if(itemViewPagePaper != null){
            itemViewPagePaper.setStartTime(startTime);
        }
        //
    }

    public void setEndTime(String endTime){
        if(itemViewPagePaper != null){
            itemViewPagePaper.setEndTime(endTime);
        }
    }
}
