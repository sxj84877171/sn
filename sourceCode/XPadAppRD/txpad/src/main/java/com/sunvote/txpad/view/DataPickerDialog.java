package com.sunvote.txpad.view;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.sunvote.txpad.R;

import java.lang.reflect.Field;
import java.util.Calendar;

/**
 * Created by Elvis on 2017/9/30.
 * Email:Eluis@psunsky.com
 * 版权所有：长沙中天电子设计开发有限公司
 * Description: 易测（ETest）
 */
public class DataPickerDialog extends Dialog {
    private Context mContext ;
    private DatePicker datePicker;
    public Object object ;
    public int what = 0;
    private OnDatePickerListener onDatePickerListener;

    public void setOnDatePickerListener(OnDatePickerListener onDatePickerListener) {
        this.onDatePickerListener = onDatePickerListener;
    }

    public DataPickerDialog(Context context) {
        super(context);
        mContext = context;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        datePicker = new DatePicker(mContext);
        setContentView(R.layout.dialog_date_picker);
        init();
    }

    private void init(){
        TextView cancel = findViewById(R.id.cancel);
        TextView submit = findViewById(R.id.submit);
        datePicker = findViewById(R.id.date_picker);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int year = datePicker.getYear();
                int month = datePicker.getMonth();
                int dayOfMonth = datePicker.getDayOfMonth();
                String date = year + "年" + (month + 1) + month + "月" + dayOfMonth  + "日";
                if(onDatePickerListener != null){
                    onDatePickerListener.onDatePicker(date,year,month+1,dayOfMonth,object,what);
                }
                dismiss();
            }
        });
        datePicker.init(Calendar.getInstance().get(Calendar.YEAR),Calendar.getInstance().get(Calendar.MONTH),Calendar.getInstance().get(Calendar.DAY_OF_MONTH),dateChangedListener);
    }

    /**
     * 隐藏了 分
     * @param mDatePicker
     */
    private void hideDay(DatePicker mDatePicker) {
        try {
            /* 处理android5.0以上的特殊情况 */
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                int daySpinnerId = Resources.getSystem().getIdentifier("day", "id", "android");
                if (daySpinnerId != 0) {
                    View daySpinner = mDatePicker.findViewById(daySpinnerId);
                    if (daySpinner != null) {
                        daySpinner.setVisibility(View.GONE);
                    }
                }
            } else {
                Field[] datePickerfFields = mDatePicker.getClass().getDeclaredFields();
                for (Field datePickerField : datePickerfFields) {
                    if ("mDaySpinner".equals(datePickerField.getName()) || ("mDayPicker").equals(datePickerField.getName())) {
                        datePickerField.setAccessible(true);
                        Object dayPicker = new Object();
                        try {
                            dayPicker = datePickerField.get(mDatePicker);
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        } catch (IllegalArgumentException e) {
                            e.printStackTrace();
                        }
                        ((View) dayPicker).setVisibility(View.GONE);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void initDate(int year,int monthOfYear,int dayOfMonth){
        if(datePicker != null){
            datePicker.init(year, monthOfYear-1, dayOfMonth,dateChangedListener);
            setTitle(year + "-" + monthOfYear + "-" + dayOfMonth);
        }
    }


    private DatePicker.OnDateChangedListener dateChangedListener = new DatePicker.OnDateChangedListener() {
        @Override
        public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            setTitle(year + "-" + monthOfYear + "-" + dayOfMonth);
        }
    };


    public interface OnDatePickerListener{
        void onDatePicker(String data,int year,int month,int dayOfMonth,Object object,int what);
    }
}
