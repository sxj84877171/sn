package com.sunvote.txpad.ui.sign;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sunvote.sunvotesdk.basestation.KeyBoard;
import com.sunvote.txpad.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Elvis on 2017/9/25.
 * Email:Eluis@psunsky.com
 * 版权所有：长沙中天电子设计开发有限公司
 * Description: 易测（ETest）
 */
public class KeyboardAdapter extends BaseAdapter {

    private List<KeyBoard> keyBoardList = new ArrayList<>();

    public void setKeyBoardList(List<KeyBoard> keyBoardList) {
        this.keyBoardList = keyBoardList;
        notifyDataSetChanged();
    }

    public boolean addKeyboard(KeyBoard keyBoard){
        boolean has = false;
        if(keyBoard != null && keyBoard.getKeyId() != null){
            for(KeyBoard keyBoard1 : keyBoardList){
                if(keyBoard.getKeyId().equals(keyBoard1.getKeyId())){
                    has = true;
                }
            }
        }
        if(!has){
            keyBoardList.add(keyBoard);
            notifyDataSetChanged();
            return true;
        }
        return false;
    }

    @Override
    public int getCount() {
        return keyBoardList.size();
    }

    @Override
    public Object getItem(int i) {
        return keyBoardList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View rootView, ViewGroup viewGroup) {
       ViewHolder viewHolder = null;
        if(rootView == null) {
            rootView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.student, null);
            viewHolder = new ViewHolder();
            viewHolder.studentPresent = rootView.findViewById(R.id.student_present);
            viewHolder.studentName = rootView.findViewById(R.id.student_name);
            viewHolder.studentItem = rootView.findViewById(R.id.student_item);
            rootView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) rootView.getTag();
        }
        KeyBoard keyBoard = keyBoardList.get(i);
        viewHolder.studentName.setText(keyBoard.getKeyId());
        viewHolder.studentPresent.setText(keyBoard.getKeySn());
        return rootView;
    }

    class ViewHolder{
        View studentItem;
        TextView studentName;
        TextView studentPresent;
    }
}
