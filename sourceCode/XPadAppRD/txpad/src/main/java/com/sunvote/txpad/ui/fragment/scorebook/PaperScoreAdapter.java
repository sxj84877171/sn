package com.sunvote.txpad.ui.fragment.scorebook;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sunvote.txpad.ApplicationDataHelper;
import com.sunvote.txpad.ApplicationDataManager;
import com.sunvote.txpad.R;
import com.sunvote.txpad.bean.PaperScore;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Elvis on 2017/9/30.
 * Email:Eluis@psunsky.com
 * 版权所有：长沙中天电子设计开发有限公司
 * Description: 易测（ETest）
 */
public class PaperScoreAdapter extends BaseAdapter {

    private List<PaperScore> paperScoreList = new ArrayList<>();
    private OnItemPaperListener onItemPaperListener;

    public void setOnItemPaperListener(OnItemPaperListener onItemPaperListener) {
        this.onItemPaperListener = onItemPaperListener;
    }

    public void setPaperScoreList(List<PaperScore> paperScoreList) {
        this.paperScoreList = paperScoreList;
        notifyDataSetChanged();
    }

    public void addPaperScoreList(List<PaperScore> paperScoreList) {
        for(PaperScore temp : paperScoreList){
            if(!isInList(temp)){
                this.paperScoreList.add(temp);
            }
        }
        notifyDataSetChanged();
    }

    private boolean isInList(PaperScore paperScore){
        for(PaperScore temp : paperScoreList){
            if(temp.equals(paperScore)){
                return true;
            }
        }
        return false;
    }

    @Override
    public int getCount() {
        return paperScoreList.size();
    }

    @Override
    public Object getItem(int i) {
        return paperScoreList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder ;
        if(view == null){
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_paper_little_info,null);
            viewHolder = new ViewHolder();
            viewHolder.paperScoreType = view.findViewById(R.id.paper_score_type);
            viewHolder.paperScoreName = view.findViewById(R.id.paper_score_name);
            viewHolder.paperScoreTime = view.findViewById(R.id.paper_score_time);
            viewHolder.paperScoreAll = view.findViewById(R.id.paper_score_all);
            viewHolder.paperScoreAvl = view.findViewById(R.id.paper_score_avl);
            viewHolder.paperScoreAvlPercent = view.findViewById(R.id.paper_score_avl_percent);
            view.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) view.getTag();
        }
        final PaperScore paperScore = paperScoreList.get(i);
        viewHolder.paperScoreType.setText(viewGroup.getContext().getString(ApplicationDataHelper.getInstance().getPaperType(paperScore.getPaperType())));
        viewHolder.paperScoreName.setText(paperScore.getPaperName());
        viewHolder.paperScoreTime.setText(paperScore.getReportTime());
        viewHolder.paperScoreAll.setText("" + paperScore.getPaperScore());
        viewHolder.paperScoreAvl.setText("" + paperScore.getClassAverage());
        viewHolder.paperScoreAvlPercent.setText(ApplicationDataManager.getInstance().progressText(paperScore.getClassAverage(),paperScore.getPaperScore()) + "%");
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(onItemPaperListener != null){
                    onItemPaperListener.onItemOnClick(view,i,paperScore);
                }
            }
        });
        return view;
    }

    class ViewHolder{
        TextView paperScoreType;
        TextView paperScoreName;
        TextView paperScoreTime;
        TextView paperScoreAll;
        TextView paperScoreAvl;
        TextView paperScoreAvlPercent;
    }

    interface OnItemPaperListener{
        void onItemOnClick(View view , int i , PaperScore paperScore);
    }
}
