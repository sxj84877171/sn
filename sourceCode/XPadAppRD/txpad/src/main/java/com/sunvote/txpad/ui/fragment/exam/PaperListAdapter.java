package com.sunvote.txpad.ui.fragment.exam;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sunvote.txpad.R;
import com.sunvote.txpad.bean.Paper;

import java.util.ArrayList;
import java.util.List;

/**
 *  * Created by Elvis on 2017/9/15.
 * Email:Eluis@psunsky.com
 * 版权所有：长沙中天电子设计开发有限公司
 * Description: 易测（ETest）
 */
public class PaperListAdapter extends BaseAdapter {

    public static final int FOLLOWUPTEST = 1;
    public static final int PHASETEST = 2;
    public static final int PREVIEW = 3 ;

    private List<Paper> paperList = new ArrayList<>();
    private OnItemTypeClickListener onItemTypeClickListener;

    public void setOnItemTypeClickListener(OnItemTypeClickListener onItemTypeClickListener) {
        this.onItemTypeClickListener = onItemTypeClickListener;
    }

    public void setPaperList(List<Paper> paperList) {
        this.paperList = paperList;
        notifyDataSetChanged();
    }

    public void addPaperList(List<Paper> paperList){
        if(this.paperList == null){
            this.paperList = new ArrayList<>();
        }
        for(Paper temp:paperList){
            if(!isInList(temp)){
                this.paperList.add(temp);
            }
        }
        notifyDataSetChanged();
    }

    private boolean isInList(Paper paper){
        for(Paper paper1 : paperList){
            if(paper1.getPaperId().equals(paper.getPaperId())){
                return true;
            }
        }
        return false;
    }

    @Override
    public int getCount() {
        return paperList.size();
    }

    @Override
    public Object getItem(int i) {
        return paperList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if(view == null){
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_examination_course,null);
            viewHolder = new ViewHolder();
            viewHolder.paperName = view.findViewById(R.id.paper_name);
            viewHolder.createTime = view.findViewById(R.id.create_time);
            viewHolder.questionCount = view.findViewById(R.id.question_count);
            viewHolder.followUpTest = view.findViewById(R.id.follow_up_test);
            viewHolder.phaseTest = view.findViewById(R.id.phase_test);
            viewHolder.preview = view.findViewById(R.id.preview);
            view.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) view.getTag();
        }

        Paper paper = null;
        if(i < paperList.size()){
            paper = paperList.get(i);
        }

        if(paper != null){
            viewHolder.paperName.setText(paper.getPaperName());
            viewHolder.createTime.setText(paper.getCreateTime());
            viewHolder.questionCount.setText(paper.getQuesCount() + view.getContext().getString(R.string.examination_view_question_flag));
        }
        final Paper paper1 = paper;
        viewHolder.preview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(onItemTypeClickListener != null){
                    onItemTypeClickListener.onClick(paper1,i,PREVIEW);
                }
            }
        });
        viewHolder.phaseTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(onItemTypeClickListener != null){
                    onItemTypeClickListener.onClick(paper1,i,PHASETEST);
                }
            }
        });

        viewHolder.followUpTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(onItemTypeClickListener != null){
                    onItemTypeClickListener.onClick(paper1,i,FOLLOWUPTEST);
                }
            }
        });
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(onItemTypeClickListener != null){
                    onItemTypeClickListener.onClick(paper1,i,PREVIEW);
                }
            }
        });
        return view;
    }

    interface OnItemTypeClickListener{

        void onClick(Paper paper,int index , int type);
    }

    class ViewHolder{
        TextView paperName ;
        TextView createTime ;
        TextView questionCount;
        View followUpTest;
        View phaseTest;
        View preview;
    }
}
