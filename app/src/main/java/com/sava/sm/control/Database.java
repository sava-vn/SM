package com.sava.sm.control;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.sava.sm.model.MyNote;
import com.sava.sm.model.Reminder;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Mr.Sang on 1/20/2018.
 */

public class Database extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "MY_DATA";
    private static final String TABLE_NOTE = "NOTE";
    private static final String ID_NOTE = "idNote";
    private static final String TITLE_NOTE = "title_note";
    private static final String CONTENT_NOTE = "content_note";
    private static final String DATE_NOTE = "date_note";
    private static final String STATUS_NOTE = "status_note";
    private static final int VERSION = 1;

    private static final String TABLE_REMINDER = "REMINDER";
    private static final String ID_REMINDER = "idR";
    private static final String TITLE_REMINDER = "titleR";
    private static final String CONTENT_REMINDER = "contentR";
    private static final String TIME_REMINDER = "timeR";
    private static final String STATUS_REMINDER ="statusR";

    private String createTableReminder = "Create Table " +
            TABLE_REMINDER + " ( " +
            ID_REMINDER + " integer primary key , " +
            TITLE_REMINDER+ " text, " +
            CONTENT_REMINDER+ " text , " +
            TIME_REMINDER + " text , " +
            STATUS_REMINDER + " int )";
    private Context context;

    private String createTableNote = "Create Table " +
            TABLE_NOTE + " ( " +
            ID_NOTE + " integer primary key , " +
            TITLE_NOTE + " text, " +
            CONTENT_NOTE + " text , " +
            DATE_NOTE + " text , " +
            STATUS_NOTE + " int )";

    public Database(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(createTableNote);
        sqLiteDatabase.execSQL(createTableReminder);
    }

    public void insertReminder(Reminder reminder){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TITLE_REMINDER, reminder.getmTitle());
        values.put(CONTENT_REMINDER, reminder.getmContent());
        values.put(TIME_REMINDER, String.valueOf(reminder.getmDate().getTime()));
        values.put(STATUS_REMINDER, reminder.getmStatus());
        db.insert(TABLE_REMINDER, null, values);
        db.close();
    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void addNote(MyNote myNote) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TITLE_NOTE, myNote.getmTitle());
        values.put(CONTENT_NOTE, myNote.getmContent());
        values.put(DATE_NOTE, myNote.getmDate());
        values.put(STATUS_NOTE, myNote.isStatus() ? 1 : 0);
        db.insert(TABLE_NOTE, null, values);
        db.close();
    }
    public ArrayList<Reminder> getAllReminder(){
        ArrayList<Reminder> list = new ArrayList<>();
        String sql = " SELECT * FROM " + TABLE_REMINDER;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor  = db.rawQuery(sql,null);
        if(cursor.moveToFirst()){
            do{
                Reminder reminder = new Reminder();
                reminder.setmID(cursor.getInt(0));
                reminder.setmTitle(cursor.getString(1));
                reminder.setmContent(cursor.getString(2));
                String date = cursor.getString(3);
                Long ldate = Long.valueOf(date);
                reminder.setmDate(new Date(ldate));
                reminder.setmStatus(cursor.getInt(4));
                list.add(reminder);
            }while (cursor.moveToNext());
        }
        return list;
    }
    public void deleteReminder(Reminder reminder){
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_REMINDER, TITLE_REMINDER + " = ?", new String[]{reminder.getmTitle()});
        db.close();
    }
    public ArrayList<MyNote> getAllNote() {
        ArrayList<MyNote> list = new ArrayList<>();
        String query = "SELECT * FROM  " + TABLE_NOTE;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                MyNote myNote = new MyNote();
                myNote.setmID(cursor.getInt(0));
                myNote.setmTitle(cursor.getString(1));
                myNote.setmContent(cursor.getString(2));
                myNote.setmDate(cursor.getString(3));
                myNote.setStatus(cursor.getInt(4) == 1 ? true : false);
                list.add(myNote);
            } while (cursor.moveToNext());
            db.close();
        }
        return list;
    }
    public ArrayList<MyNote> getAllNoteStar() {
        ArrayList<MyNote> list = new ArrayList<>();
        String query = "SELECT * FROM  " + TABLE_NOTE  + " WHERE " + STATUS_NOTE + " = 1";
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                MyNote myNote = new MyNote();
                myNote.setmID(cursor.getInt(0));
                myNote.setmTitle(cursor.getString(1));
                myNote.setmContent(cursor.getString(2));
                myNote.setmDate(cursor.getString(3));
                myNote.setStatus(true);
                list.add(myNote);
            } while (cursor.moveToNext());
            db.close();
        }
        return list;
    }

    public void deleteNote(MyNote myNote) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_NOTE, ID_NOTE + " = ?", new String[]{String.valueOf(myNote.getmID())});
        db.close();
    }

    public int updateNote(MyNote myNote) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TITLE_NOTE, myNote.getmTitle());
        values.put(CONTENT_NOTE, myNote.getmContent());
        values.put(DATE_NOTE, myNote.getmDate());
        values.put(STATUS_NOTE, myNote.isStatus() ? 1 : 0);
        return db.update(TABLE_NOTE, values, ID_NOTE + " = ? ", new String[]{String.valueOf(myNote.getmID())});
    }

    public int updateNoteStatus(MyNote myNote) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(STATUS_NOTE, myNote.isStatus() ? 1 : 0);
        return db.update(TABLE_NOTE, values, ID_NOTE + " = ? ", new String[]{String.valueOf(myNote.getmID())});
    }

    public void deleteList(ArrayList<MyNote> listDelete) {
        for (MyNote myNote : listDelete)
            deleteNote(myNote);
        listDelete.clear();
    }
    public int updateReminderStatus(Reminder reminder){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(STATUS_REMINDER,reminder.getmStatus());
        return db.update(TABLE_REMINDER,values,TITLE_REMINDER + " = ?",new String[]{reminder.getmTitle()});
    }
}
