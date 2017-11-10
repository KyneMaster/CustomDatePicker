package com.anche.kyne.customdatepicker;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button systomBtn, customBtn_1, customBtn_2;
    private TextView systomTx, customTx_1, customTx_2;
    private int mYear, mMonth, mDay;
    private final int DATE_DIALOG = 1;
    private CustomDatePicker customDatePicker1, customDatePicker2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
    }

    private void initView() {
        systomBtn = (Button) findViewById(R.id.systomBtn);
        systomTx = (TextView) findViewById(R.id.systomTx);
        customBtn_1 = (Button) findViewById(R.id.customBtn_1);
        customTx_1 = (TextView) findViewById(R.id.customTx_1);
        customBtn_2 = (Button) findViewById(R.id.customBtn_2);
        customTx_2 = (TextView) findViewById(R.id.customTx_2);
        systomBtn.setOnClickListener(this);
        customBtn_1.setOnClickListener(this);
        customBtn_2.setOnClickListener(this);
    }

    private void initData() {
        final Calendar ca = Calendar.getInstance();
        mYear = ca.get(Calendar.YEAR);
        mMonth = ca.get(Calendar.MONTH);
        mDay = ca.get(Calendar.DAY_OF_MONTH);

        initDatePicker();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.systomBtn:
                //系统方式
                Calendar calendar1 = Calendar.getInstance();
                int year1 = calendar1.get(Calendar.YEAR);
                int monthOfYear1 = calendar1.get(Calendar.MONTH);
                int dayOfMonth1 = calendar1.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog1 = new DatePickerDialog(this, AlertDialog.THEME_HOLO_LIGHT, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        systomTx.setText(i + "-" + i1 + "-" + i2);
                        Toast.makeText(MainActivity.this, i + "-" + (i1 + 1) + "-" + i2, Toast.LENGTH_SHORT).show();
                    }
                }, year1, monthOfYear1, dayOfMonth1);
                datePickerDialog1.show();
                break;
            case R.id.customBtn_1:
                //简单的日历形式日期选择
                showDialog(DATE_DIALOG);
                break;
            case R.id.customBtn_2:
                //
                customDatePicker1.show(customTx_2.getText().toString());
                break;
        }
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_DIALOG:
                return new DatePickerDialog(this, mdateListener, mYear, mMonth, mDay);
        }
        return null;
    }

    /**
     * 设置日期 利用StringBuffer追加
     */
    public void display() {
        customTx_1.setText(new StringBuffer().append(mYear).append("-").append(mMonth + 1).append("-").append(mDay).append(" "));
    }

    private DatePickerDialog.OnDateSetListener mdateListener = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            mYear = year;
            mMonth = monthOfYear;
            mDay = dayOfMonth;
            display();
        }
    };

    private void initDatePicker() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
        String now = sdf.format(new Date());
        customTx_2.setText(now.split(" ")[0]);//日期
//        customTx_2.setText(now);//时间

        customDatePicker1 = new CustomDatePicker(this, new CustomDatePicker.ResultHandler() {
            @Override
            public void handle(String time) { // 回调接口，获得选中的时间
                customTx_2.setText(time.split(" ")[0]);
            }
        }, "2010-01-01 00:00", now); // 初始化日期格式请用：yyyy-MM-dd HH:mm，否则不能正常运行
        customDatePicker1.showSpecificTime(false); // 不显示时和分
        customDatePicker1.setIsLoop(false); // 不允许循环滚动

        customDatePicker2 = new CustomDatePicker(this, new CustomDatePicker.ResultHandler() {
            @Override
            public void handle(String time) { // 回调接口，获得选中的时间
                customTx_2.setText(time);
            }
        }, "2010-01-01 00:00", now); // 初始化日期格式请用：yyyy-MM-dd HH:mm，否则不能正常运行
        customDatePicker2.showSpecificTime(true); // 显示时和分
        customDatePicker2.setIsLoop(true); // 允许循环滚动
    }

}
