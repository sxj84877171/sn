package com.sunvote.txpad.ui.namelist;

import android.app.ActionBar;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.sunvote.txpad.R;
import com.sunvote.txpad.bean.Student;

/**
 * Created by Elvis on 2017/9/26.
 * Email:Eluis@psunsky.com
 * 版权所有：长沙中天电子设计开发有限公司
 * Description: 易测（ETest）
 */
public class EditStudentDialog extends Dialog {
    private Context context;
    private View rootView ;
    private TextView title ;
    private TextView studentNo;
    private TextView studentName;
    private TextView studentId;
    private TextView studentSn;
    private TextView cancel;
    private TextView save ;
    private OnEditClickListener onEditClickListener;
    private Student student ;
    private Object moreInfo;

    public void setOnEditClickListener(OnEditClickListener onEditClickListener) {
        this.onEditClickListener = onEditClickListener;
    }

    public EditStudentDialog(Context context) {
        this(context,0);

    }

    public EditStudentDialog( Context context,  int themeResId) {
        super(context, themeResId);
        this.context = context;
    }

    protected EditStudentDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init(){
        rootView = LayoutInflater.from(context).inflate(R.layout.item_edit_student,null);
        setContentView(rootView);
        initViewById();
        initListener();
    }

    private void initListener() {
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String studentNoStr = studentNo.getText().toString();
                String studentNameStr = studentName.getText().toString();
                String studentIdStr = studentId.getText().toString();
                String studentSnStr = studentSn.getText().toString();
                if(onEditClickListener != null){
                    onEditClickListener.onCancelClick(view,studentNoStr,studentNameStr,studentIdStr,studentSnStr,moreInfo);
                }
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String studentNoStr = studentNo.getText().toString();
                String studentNameStr = studentName.getText().toString();
                String studentIdStr = studentId.getText().toString();
                String studentSnStr = studentSn.getText().toString();
                if(TextUtils.isEmpty(studentNoStr) || TextUtils.isEmpty(studentNameStr)){
                    Toast.makeText(context,R.string.name_list_name_id_empty,Toast.LENGTH_SHORT).show();
                    return;
                }
                if(onEditClickListener != null){
                    onEditClickListener.onSaveClick(view,studentNoStr,studentNameStr,studentIdStr,studentSnStr,moreInfo);
                }
            }
        });
    }

    private void initViewById(){
        title = rootView.findViewById(R.id.edit_student_title);
        studentNo = rootView.findViewById(R.id.student_no);
        studentName = rootView.findViewById(R.id.student_name);
        studentId = rootView.findViewById(R.id.student_id);
        studentSn = rootView.findViewById(R.id.student_sn);
        cancel = rootView.findViewById(R.id.cancel);
        save = rootView.findViewById(R.id.save);
    }

    public void showStudentInfo(Student student,boolean isEdit,Object moreInfo){
        this.student = student;
        this.moreInfo = moreInfo;
        if(isEdit){
            title.setText(R.string.name_list_edit_student);
        }else{
            title.setText(R.string.name_list_add_student);
        }
        if(student != null) {
            studentNo.setText(student.getStudentId());
            studentName.setText(student.getStudentName());
            studentId.setText(student.getKeyBoard().getKeyId());
            studentSn.setText(student.getKeyBoard().getKeySn());
        }
    }


    interface OnEditClickListener{
        void onCancelClick(View view , String no,String name,String id ,String sn,Object moreInfo);

        void onSaveClick(View view , String no,String name,String id ,String sn,Object moreInfo);
    }
}
