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

    private GridView students ;

    @Override
    public void initView() {
        students = getViewById(R.id.students);
        switchIdSn = getViewById(R.id.switch_id_sn);
        classNameList = getViewById(R.id.class_name_list);
        add = getViewById(R.id.add);
        edit = getViewById(R.id.edit);
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
                showToast("后续实现");
            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapter.setEdit();
            }
        });
    }

    @Override
    public void initData() {
        adapter = new StudentAdapter();
        adapter.setMode(StudentAdapter.MODE_NO);
        students.setAdapter(adapter);
        classAdapter = new ClassAdapter();
        classNameList.setAdapter(classAdapter);
        present.getClassList();
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
}
