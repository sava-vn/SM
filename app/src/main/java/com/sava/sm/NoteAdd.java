package com.sava.sm;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.sava.sm.Fragment.Frag.Note.NoteFragment;
import com.sava.sm.model.MyNote;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class NoteAdd extends AppCompatActivity {
    public static final String HANG_NODEADD_VE_NOTEFRAGMENT ="LKDJFLKKDSJ";
    public static  final  int NOTEADD_VE_NOTEFRAGMENT =11;
    private EditText edtNoteAddTitle;
    private EditText edtNoteAddContent;
    private Toolbar mToolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_add);
        initWidget();
        setFont();
    }
    public void initWidget(){
        mToolbar = findViewById(R.id.tb_noteAdd);
        edtNoteAddTitle = findViewById(R.id.edt_noteAddTitle);
        edtNoteAddContent = findViewById(R.id.edt_noteAddContent);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }
    public void setFont(){
        Typeface tf = Typeface.createFromAsset(getAssets(),
                "fonts/Geogrotesque-Light.ttf");
        edtNoteAddContent.setTypeface(tf);
        tf = Typeface.createFromAsset(getAssets(),
                "fonts/SVN-Aguda Regular.otf");
        edtNoteAddTitle.setTypeface(tf,Typeface.BOLD);
    }
    public void getNote(){
        Date date = Calendar.getInstance().getTime();
        MyNote myNote = new MyNote(edtNoteAddTitle.getText().toString(),edtNoteAddContent.getText().toString(),date,false);
        Intent intent = new Intent();
        intent.putExtra(HANG_NODEADD_VE_NOTEFRAGMENT,myNote);
        setResult(NOTEADD_VE_NOTEFRAGMENT,intent);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_note_edit,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                this.finish();
                break;
            case R.id.action_cancel:
                finish();
                break;
            case R.id.action_save:
                getNote();
                finish();
                break;
        }
        return true;
    }
}
