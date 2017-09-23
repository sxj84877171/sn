package com.sunvote.txpad.ui.exam;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.sunvote.txpad.ApplicationDataHelper;
import com.sunvote.txpad.R;
import com.sunvote.txpad.bean.Question;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Elvis on 2017/9/14.
 * Email:Eluis@psunsky.com
 * 版权所有：长沙中天电子设计开发有限公司
 * Description: 易测（ETest）
 */
public class ExamQuestionAdapter extends BaseAdapter {

    private List<Question> questionList = new ArrayList<>();

    private int students = 1 ;

    public void setStudents(int students) {
        this.students = students;
    }

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
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if(view == null){
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_examination,viewGroup,false);
            viewHolder = new ViewHolder();
            viewHolder.examQuestionNo = view.findViewById(R.id.exam_question_no);
            viewHolder.examQuestionType = view.findViewById(R.id.exam_question_type);
            viewHolder.examQuestionContent = view.findViewById(R.id.exam_question_content);
            viewHolder.examProgressText = view.findViewById(R.id.exam_progress_text);
            viewHolder.examProgressView = view.findViewById(R.id.exam_progress_view);
            view.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) view.getTag();
        }
        Question question = questionList.get(i);
        viewHolder.examQuestionNo.setText((i+1) + ".");
        viewHolder.examQuestionType.setText(getString(viewHolder.examQuestionType, ApplicationDataHelper.getInstance().getQuestionType(question.getType())));
        viewHolder.examQuestionContent.setText(Html.fromHtml(question.getContent()));
        viewHolder.examProgressView.setProgress(question.getReply());
        viewHolder.examProgressView.setMax(students);
        viewHolder.examProgressText.setText(progressText(question.getReply(),students));
        return view;
    }

    public void setQuestionList(List<Question> questionList) {
        this.questionList = questionList;
        notifyDataSetChanged();
    }

    class ViewHolder {
        TextView examQuestionNo;
        TextView examQuestionType;
        TextView examQuestionContent;
        TextView examProgressText;
        ProgressBar examProgressView ;
    }

    public String getString(View view, int resId){
        if(resId == 0){
            return "" ;
        }
        return view.getContext().getString(resId);
    }

    public String progressText(int reply,int all){
        float f1 = reply;
        float f2 = all ;
        if(f2 == 0){
            f2 = 1;
        }
        float f3 = (f1/f2);
        f3 = f3 + 0.005f ;
        int i3 = (int)(f3*100);
        return i3 + "%";
    }
}
