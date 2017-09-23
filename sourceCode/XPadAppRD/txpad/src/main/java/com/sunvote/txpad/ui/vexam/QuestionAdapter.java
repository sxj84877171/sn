package com.sunvote.txpad.ui.vexam;

import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sunvote.txpad.R;
import com.sunvote.txpad.bean.Question;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Elvis on 2017/9/15.
 * Email:Eluis@psunsky.com
 * 版权所有：长沙中天电子设计开发有限公司
 * Description: 易测（ETest）
 */
public class QuestionAdapter extends BaseAdapter {

    private List<Question> questionList = new ArrayList<>();
    @Override
    public int getCount() {
        return questionList.size();
    }

    @Override
    public Object getItem(int i) {
        return questionList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder ;
        Question question = null ;
        if(i < questionList.size()){
            question = questionList.get(i);
        }
        if(view == null){
           view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_question,viewGroup,false);
           viewHolder = new ViewHolder();
            viewHolder.questionContent = view.findViewById(R.id.question_content);
            viewHolder.resolution = view.findViewById(R.id.resolution);
            viewHolder.questionNo = view.findViewById(R.id.question_no);
            view.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.questionNo.setText((i+1) + ".");
        viewHolder.resolution.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(onViewResolution != null){
                    Question question1 = null;
                    if(i < questionList.size()){
                        question1 = questionList.get(i);
                    }
                    onViewResolution.onViewResolution(question1,i+1);
                }

            }
        });

        if(question != null){
            StringBuilder content = new StringBuilder();
            content.append(question.getContent());
            List<String> optionList = question.getOptions();
            if(optionList != null){
                for(int j = 0 ; j < optionList.size() ; j++){
                    content.append("<br>").append(((char)('A' + j))).append(".  ").append(optionList.get(j));
                }
            }
            viewHolder.questionContent.setText(Html.fromHtml(content.toString(),new HtmlImageGetter(view.getContext()),null));
        }
        return view;
    }

    class ViewHolder{
        TextView resolution;
        TextView questionNo;
        TextView questionContent;
    }

    private OnViewResolution onViewResolution;

    public void setOnViewResolution(OnViewResolution onViewResolution) {
        this.onViewResolution = onViewResolution;
    }

    public interface OnViewResolution{
       void onViewResolution(Question question,int index);
    }

    public void setQuestionList(List<Question> questionList) {
        this.questionList = questionList;
        notifyDataSetChanged();
    }
}
