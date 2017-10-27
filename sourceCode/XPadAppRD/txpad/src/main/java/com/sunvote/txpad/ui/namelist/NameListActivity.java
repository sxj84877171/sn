package com.sunvote.txpad.ui.namelist;

import android.view.View;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.Switch;

import com.sunvote.txpad.ApplicationData;
import com.sunvote.txpad.R;
import com.sunvote.txpad.base.BaseActivity;
import com.sunvote.txpad.base.BasePresent;
import com.sunvote.txpad.bean.ClassStudent;
import com.sunvote.txpad.bean.Student;
import com.sunvote.txpad.ui.sign.StudentAdapter;

import java.util.List;

/**
 * Created by Elvis on 2017/9/14.
 * Email:Eluis@psunsky.com
 * 版权所有：长沙中天电子设计开发有限公司
 * Description: 易测（ETest）
 */
public class NameListActivity extends BaseActivity implements INameListView{

    private NameListPresent present;
    private StudentAdapter adapter;
    private Switch switchIdSn;
    private Spinner classNameList;
    private ClassAdapter classAdapter;
    private View add ;
    private View edit ;
    private View back;
    private EditStudentDialog editStudentDialog ;
    @Override
    public int getLayoutID() {
        return R.layout.activiity_name_list;
    }

    @Override
    public void initMVP() {
        present = new NameListPresent();
        present.setModel(new NameListModel());
        present.setView(this);
    }

    @Override
    public BasePresent getBasePresent() {
        return present;
    }

    private GridView students ;

    @Override
    public void initView() {
        students = getViewById(R.id.students);
        switchIdSn = getViewById(R.id.switch_id_sn);
        classNameList = getViewById(R.id.class_name_list);
        add = getViewById(R.id.add);
        edit = getViewById(R.id.edit);
        back = getViewById(R.id.back);
    }

    @Override
    public void initListener() {
        switchIdSn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(!b){
                    adapter.setMode(StudentAdapter.MODE_NO);
                }else{
                    adapter.setMode(StudentAdapter.MODE_SN);
                }
                adapter.notifyDataSetChanged();
            }
        });

        classNameList.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                present.getStudentList(classAdapter.getClasList().get(i).getClassId());
                ApplicationData.getInstance().setClassStudent(classAdapter.getClasList().get(i));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                if(classAdapter.getClasList().size() > 0) {
                    classNameList.setSelection(0);
                }
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showToast("请期待");
//                editStudentDialog.showStudentInfo(null,false,null);
//                editStudentDialog.show();
            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showToast("请期待");
//                adapter.setCanClick(!adapter.isCanClick());
//                showEditButtonState(adapter.isCanClick());
            }
        });

        editStudentDialog.setOnEditClickListener(new EditStudentDialog.OnEditClickListener() {
            @Override
            public void onCancelClick(View view, String no, String name, String id, String sn, Object moreInfo) {
                editStudentDialog.dismiss();

            }

            @Override
            public void onSaveClick(View view, String no, String name, String id, String sn, Object moreInfo) {
                editStudentDialog.dismiss();
                if(moreInfo == null) {
                    Student student = new Student();
                    student.setStudentName(name);
                    student.setStudentId(no);
                    student.setStudentUID(id);
                    student.setKeypadId(sn);
                    present.addStudent(student);

                }else{
                    Student student = (Student)moreInfo;
                    student.setStudentName(name);
                    student.setStudentId(no);
                    student.setStudentUID(id);
                    student.setKeypadId(sn);
                    present.updateStudent(student);
                }
            }
        });

        adapter.setOnItemClickListener(new StudentAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, Student student, int index) {
                editStudentDialog.showStudentInfo(student,true,student);
                editStudentDialog.show();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    public void initData() {
        adapter = new StudentAdapter();
        switch (present.getMode()){
            case 1:adapter.setMode(StudentAdapter.MODE_NO);break;
            case 2:adapter.setMode(StudentAdapter.MODE_NO);break;
            case 3:adapter.setMode(StudentAdapter.MODE_SN);break;
        }
        students.setAdapter(adapter);
        classAdapter = new ClassAdapter();
        classNameList.setAdapter(classAdapter);
        present.getClassList();
        editStudentDialog = new EditStudentDialog(this);
        editStudentDialog.create();
    }


    @Override
    public void showStudents(List<Student> studentList) {
        adapter.showStudents(studentList);
        ApplicationData.getInstance().setStudentList(studentList);
    }

    @Override
    public void showClassList(List<ClassStudent> classList,int select) {
        classAdapter.setClasList(classList);
        if(select < classList.size()){
            classNameList.setSelection(select);
        }
    }

    public void showEditButtonState(boolean isEdit){
        if(isEdit){
            edit.setBackgroundResource(R.drawable.rancnge_background_55);
        }else{
            edit.setBackgroundResource(R.drawable.rancnge_background);
        }
    }
}
