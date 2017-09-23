package com.sunvote.txpad.ui.sign;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sunvote.sunvotesdk.basestation.KeyBoard;
import com.sunvote.txpad.R;
import com.sunvote.txpad.bean.Student;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Elvis on 2017/9/13.
 * Email:Eluis@psunsky.com
 * 版权所有：长沙中天电子设计开发有限公司
 * Description:易测（ETest）
 */
public class StudentAdapter extends BaseAdapter {

    public static final int MODE_NO = 1;
    public static final int MODE_SN = 2;
    public static final int MODE_SIGN_KEYID = 3 ;
    public static final int MODE_SIGN_KEYSN = 4 ;
    public static final int MODE_CHECK_ONLINE = 5;
    public static final int EDIT_NO = 1 ;
    public static final int EDIT_YES = 2 ;

    private int mode = MODE_SIGN_KEYID;
    private int isEdit = EDIT_NO ;

    private List<Student> studentList = new ArrayList<>();

    public void showStudents(List<Student> students){
        this.studentList = students;
        notifyDataSetChanged();
    }


    public void setMode(int mode) {
        this.mode = mode;
    }

    public void setEdit(){
        isEdit = EDIT_YES;
        notifyDataSetChanged();
    }

    public void closeEdit(){
        isEdit = EDIT_NO ;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return studentList.size();
    }

    @Override
    public Object getItem(int i) {
        return studentList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    /**
     * @param i
     * @param rootView
     * @param viewGroup
     * @return
     */
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
        Student student = studentList.get(i);
        KeyBoard keyBoard = student.getKeyBoard();
        viewHolder.studentName.setText(student.getStudentName());
        if(mode == MODE_NO) {
            displayNoMode(viewHolder, "" + student.getStudentNo());
        }else if(mode == MODE_SN){
            displaySnMode(viewHolder, student.getStudentId());
        }else if(mode == MODE_SIGN_KEYID){
            displaySignKeyIdMode(viewHolder, keyBoard);
        }else if(mode == MODE_SIGN_KEYSN){
            displaySignKeySnMode(viewHolder,keyBoard);
        }else if(mode == MODE_CHECK_ONLINE){
            displayOnlineCheckMode(viewHolder, keyBoard);
        }
        return rootView;
    }

    private void displayOnlineCheckMode(ViewHolder viewHolder, KeyBoard keyBoard) {
        if (keyBoard.isOnline()) {
            viewHolder.studentItem.setBackgroundResource(R.drawable.rectangle_online);
        } else {
            viewHolder.studentItem.setBackgroundResource(R.drawable.rectangle_offline);
        }
    }

    private void displaySignKeyIdMode(ViewHolder viewHolder, KeyBoard keyBoard) {
        if (keyBoard != null && keyBoard.getKeyId() != null) {
            viewHolder.studentPresent.setVisibility(View.VISIBLE);
            viewHolder.studentPresent.setText(keyBoard.getKeyId());
        } else {
            viewHolder.studentPresent.setVisibility(View.INVISIBLE);
        }
        if (keyBoard != null) {
            if (keyBoard.isWeak()) {
                viewHolder.studentItem.setBackgroundResource(R.drawable.rectangle_weak_current);
            } else {
                if (keyBoard.isSign()) {
                    viewHolder.studentItem.setBackgroundResource(R.drawable.rectangle_online);
                } else {
                    viewHolder.studentItem.setBackgroundResource(R.drawable.rectangle_offline);
                }
            }
        }
    }

    private void displaySignKeySnMode(ViewHolder viewHolder, KeyBoard keyBoard) {
        if(keyBoard != null && keyBoard.getKeyId() != null){
            viewHolder.studentPresent.setVisibility(View.VISIBLE);
            viewHolder.studentPresent.setText(keyBoard.getKeySn());
        }else {
            viewHolder.studentPresent.setVisibility(View.INVISIBLE);
        }
        if(keyBoard != null){
            if(keyBoard.isWeak()){
                viewHolder.studentItem.setBackgroundResource(R.drawable.rectangle_weak_current);
            }else {
                if (keyBoard.isSign()) {
                    viewHolder.studentItem.setBackgroundResource(R.drawable.rectangle_online);
                } else {
                    viewHolder.studentItem.setBackgroundResource(R.drawable.rectangle_offline);
                }
            }
        }
    }

    private void displayNoMode(ViewHolder viewHolder, String text) {
        viewHolder.studentPresent.setVisibility(View.VISIBLE);
        viewHolder.studentPresent.setText(text);
    }

    private void displaySnMode(ViewHolder viewHolder, String text) {
        viewHolder.studentPresent.setVisibility(View.VISIBLE);
        viewHolder.studentPresent.setText(text);
    }

    class ViewHolder{
        View studentItem;
        TextView studentName;
        TextView studentPresent;
    }
}
