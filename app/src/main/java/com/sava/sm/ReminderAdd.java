package com.sava.sm;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sava.sm.control.SFont;
import com.sava.sm.model.Reminder;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.util.Calendar;
import java.util.Date;

public class ReminderAdd extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener ,DatePickerDialog.OnDateSetListener{
    public static final String RESULT_NEW_REMINDER ="RNR";
    public static final  int RESULT_REMINDERFRAGMENT =15;
    private EditText edtReminderTitle;
    private EditText edtReminderContent;
    private ImageView btnTime;
    private Toolbar mToolbar;
    private TextView tvrDate;
    private Date dateSelect =null;
    private Calendar calendarSelect;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder_add);
        initWidget();
    }
    public void initWidget(){
        edtReminderTitle = findViewById(R.id.edt_ReminderAddTitle);
        edtReminderContent = findViewById(R.id.edt_ReminderAddContent);
        btnTime   = findViewById(R.id.btn_time);
        tvrDate = findViewById(R.id.tv_rdate);
        SFont.setFont(this,"fonts/FS Siruca.ttf",tvrDate, Typeface.BOLD);
        mToolbar = findViewById(R.id.tb_reminderAdd);
        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Typeface tf = Typeface.createFromAsset(getAssets(),
                "fonts/Geogrotesque-Light.ttf");
        edtReminderContent.setTypeface(tf);
        tf = Typeface.createFromAsset(getAssets(),
                "fonts/SVN-Aguda Regular.otf");
        edtReminderTitle.setTypeface(tf,Typeface.BOLD);
        btnTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar now = Calendar.getInstance();
                DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(
                        ReminderAdd.this,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                datePickerDialog.setVersion(DatePickerDialog.Version.VERSION_1);
                datePickerDialog.show(getFragmentManager(), "SAVALE");
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_note_edit,menu);
        return super.onCreateOptionsMenu(menu);
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.action_save){
            if(dateSelect==null){
                Toast.makeText(this, "Hãy bấm vào icon để chọn ngày", Toast.LENGTH_SHORT).show();
                return false;
            }
            Reminder reminder = new Reminder();
            reminder.setmTitle(edtReminderTitle.getText().toString());
            reminder.setmContent(edtReminderContent.getText().toString());
            Intent intent = new Intent();
            intent.putExtra("date",dateSelect.getTime());
            intent.putExtra(RESULT_NEW_REMINDER,reminder);
            setResult(RESULT_REMINDERFRAGMENT,intent);
        }
        finish();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        calendarSelect  = Calendar.getInstance();
        calendarSelect.set(year,monthOfYear,dayOfMonth);
        TimePickerDialog timePickerDialog = TimePickerDialog.newInstance(
                ReminderAdd.this,
                calendarSelect.get(Calendar.HOUR_OF_DAY),
                calendarSelect.get(Calendar.MINUTE),
                true
        );
        timePickerDialog.show(getFragmentManager(),"Time");
    }

    @Override
    public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
        calendarSelect.set(Calendar.HOUR_OF_DAY,hourOfDay);
        calendarSelect.set(Calendar.MINUTE,minute);
        calendarSelect.set(Calendar.SECOND,0);
        dateSelect = calendarSelect.getTime();
        String s = (String) android.text.format.DateFormat.format("EEEE,dd,MMM     HH : mm",dateSelect);
        tvrDate.setText(s);
    }

}
