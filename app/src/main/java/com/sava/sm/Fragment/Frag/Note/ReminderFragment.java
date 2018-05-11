package com.sava.sm.Fragment.Frag.Note;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.sava.sm.R;
import com.sava.sm.ReminderAdd;
import com.sava.sm.adapter.ReminderAdpater;
import com.sava.sm.control.AlarmReceiver;
import com.sava.sm.control.Database;
import com.sava.sm.control.SwipeController;
import com.sava.sm.itf.RecyclerViewClickListener;
import com.sava.sm.itf.RecyclerViewLongClickListener;
import com.sava.sm.itf.SwipeControllerActions;
import com.sava.sm.model.Reminder;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class ReminderFragment extends Fragment implements TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener {
    public static final int REQUEST_REMINDERADD = 1;
    private ReminderAdpater mReminderAdpater;
    private ArrayList<Reminder> mList;
    private RecyclerView mRecyclerView;
    private Date dateSelect;
    private Calendar calendarSelect;
    private FloatingActionMenu fab;
    private FloatingActionButton fab2;
    private SwipeController swipeController = null;
    private int iReminderSelect;
    private Database database;
    private AlarmManager alarmManager;
    private ArrayList<PendingIntent> pendingIntentArrayList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reminder, container, false);
        initWidget(view);
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ReminderAdd.class);
                startActivityForResult(intent, REQUEST_REMINDERADD);
                fab.close(true);
            }
        });
        return view;
    }

    public void initWidget(View view) {
        //Anh xa
        fab = getActivity().findViewById(R.id.fab);
        fab2 = getActivity().findViewById(R.id.fab2);
        mRecyclerView = view.findViewById(R.id.rv_reminder);

        //Khoi tao gia tri báo thức, pendingIntent
        alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
        pendingIntentArrayList = new ArrayList<>();

        //Khởi tạo database
        database = new Database(getActivity());
        mList = database.getAllReminder();

        loadAllPadingInentAndSetAlarm();
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);

        //Tao adapter
        mReminderAdpater = new ReminderAdpater(getActivity(), mList, new RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position) {
                iReminderSelect = position;
                Calendar now = Calendar.getInstance();
                DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(
                        ReminderFragment.this,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                datePickerDialog.setVersion(DatePickerDialog.Version.VERSION_1);
                datePickerDialog.show(getActivity().getFragmentManager(), "SAVALE");
                if (fab.isOpened())
                    fab.close(true);
            }

            @Override
            public void onClick2(View view, int position, RecyclerView.ViewHolder viewHolder) {
                Reminder reminder = mList.get(position);
                if(reminder.getmStatus()>0){
                    reminder.setmStatus(0);
                    pendingIntentArrayList.get(position).cancel();
                }else{
                    if(reminder.getmDate().getTime()<System.currentTimeMillis()){
                        Toast.makeText(getActivity(), "Thời gian không đúng !", Toast.LENGTH_SHORT).show();
                        return;
                    }else{
                        reminder.setmStatus((int)System.currentTimeMillis());
                        pendingIntentArrayList.set(position,createAlarm(reminder));
                    }
                }
                mReminderAdpater.changeViewItem((ReminderAdpater.ViewHolder) viewHolder, position);
                if (fab.isOpened())
                    fab.close(true);
            }
        }, new RecyclerViewLongClickListener() {
            @Override
            public void onLongClick(View view, int position) {
            }
        });

        //Tạo action vuốt trái
        mRecyclerView.setAdapter(mReminderAdpater);
        swipeController = new SwipeController(new SwipeControllerActions() {
            @Override
            public void onRightClicked(int position) {
                database.deleteReminder(mList.get(position));
                mList.remove(position);
                mReminderAdpater.notifyDataSetChanged();
            }
        });
        ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeController);
        itemTouchhelper.attachToRecyclerView(mRecyclerView);
        mRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
                swipeController.onDraw(c);
            }
        });
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        dateSelect = new Date();
        calendarSelect = Calendar.getInstance();
        calendarSelect.set(year, monthOfYear, dayOfMonth);
        TimePickerDialog timePickerDialog = TimePickerDialog.newInstance(
                ReminderFragment.this,
                calendarSelect.get(Calendar.HOUR_OF_DAY),
                calendarSelect.get(Calendar.MINUTE),
                true
        );
        timePickerDialog.show(getActivity().getFragmentManager(), "Time");

    }

    @Override
    public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
        calendarSelect.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calendarSelect.set(Calendar.MINUTE, minute);
        calendarSelect.set(Calendar.SECOND, 0);
        dateSelect = calendarSelect.getTime();
        if(dateSelect.getTime()<System.currentTimeMillis()){
            Toast.makeText(getActivity(), "Thời gian không đúng", Toast.LENGTH_SHORT).show();
            return;
        }
        Reminder reminder = mList.get(iReminderSelect);
        if(reminder.getmStatus()>0){
            pendingIntentArrayList.get(iReminderSelect).cancel();
        }
        reminder.setmDate(dateSelect);
        reminder.setmStatus((int) System.currentTimeMillis());
        pendingIntentArrayList.set(iReminderSelect,createAlarm(reminder));
        mReminderAdpater.notifyDataSetChanged();
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_REMINDERADD && resultCode == ReminderAdd.RESULT_REMINDERFRAGMENT) {
            //Tạo reminder mới, lấy dữ liệu từ data, thêm vào list, database
            Reminder reminder = new Reminder();
            reminder.setmTitle(data.getExtras().getString("TITLE"));
            reminder.setmContent(data.getExtras().getString("CONTENT"));
            reminder.setmDate(new Date(data.getExtras().getLong("DATE")));
            int pendingID = (int) System.currentTimeMillis();
            reminder.setmStatus(pendingID);
            mList.add(reminder);
            mReminderAdpater.notifyDataSetChanged();
            database.insertReminder(reminder);

            //Tạo báo thức
            pendingIntentArrayList.add(createAlarm(reminder));
        }
    }
    public void loadAllPadingInentAndSetAlarm(){
        for (Reminder reminder: mList) {
            PendingIntent pendingIntent = null;
            if(reminder.getmStatus()>0){
                Intent intent = new Intent(getActivity(),AlarmReceiver.class);
                intent.putExtra("TITLE",reminder.getmTitle());
                intent.putExtra("CONTENT",reminder.getmContent());
                pendingIntent = PendingIntent.getBroadcast(getActivity(),reminder.getmStatus(),intent,PendingIntent.FLAG_UPDATE_CURRENT);
                if(reminder.getmDate().getTime()<System.currentTimeMillis()){
                    pendingIntent.cancel();
                    reminder.setmStatus(0);
                }
            }
            if(reminder.getmStatus()>0){
                Log.e("ALARM","ON");
            }else{
                Log.e("ALARM","OFF");
            }
            pendingIntentArrayList.add(pendingIntent);
        }
    }
    public PendingIntent createAlarm(Reminder reminder){
        Intent intent = new Intent(getActivity(),AlarmReceiver.class);
        intent.putExtra("TITLE",reminder.getmTitle());
        intent.putExtra("CONTENT",reminder.getmContent());
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getActivity(),reminder.getmStatus(),intent,PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP,reminder.getmDate().getTime(),pendingIntent);
        return  pendingIntent;
    }
}
