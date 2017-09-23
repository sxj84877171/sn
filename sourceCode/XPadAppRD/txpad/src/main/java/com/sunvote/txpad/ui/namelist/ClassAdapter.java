package com.sunvote.txpad.ui.namelist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sunvote.txpad.R;
import com.sunvote.txpad.bean.ClassStudent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Elvis on 2017/9/15.
 * Email:Eluis@psunsky.com
 * 版权所有：长沙中天电子设计开发有限公司
 * Description: 易测（ETest）
 */
public class ClassAdapter extends BaseAdapter {

    private List<ClassStudent> clasList = new ArrayList<>();
    @Override
    public int getCount() {
        return clasList.size();
    }

    @Override
    public Object getItem(int i) {
        return clasList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_spinner_class,null,false);
        TextView textView = (TextView) view;
        textView.setText(clasList.get(i).getClassName());
        return textView;
    }

    public void setClasList(List<ClassStudent> clasList) {
        this.clasList = clasList;
        notifyDataSetChanged();
    }

    public List<ClassStudent> getClasList() {
        return clasList;
    }
}
