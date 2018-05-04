package com.sava.sm;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.sava.sm.model.MyNote;

public class NoteEdit extends AppCompatActivity {
    private Toolbar mToolbar;
    private EditText edtNoteEditTitle;
    private EditText edtNoteEditContent;
    private MyNote myNote;
    public static final  String HANG_VE ="SX";
    public static final  int VE =2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_edit);
        mToolbar = findViewById(R.id.tb_noteEdit);
        mToolbar.setTitle("");
        edtNoteEditTitle = findViewById(R.id.edt_noteEditTitle);
        edtNoteEditContent = findViewById(R.id.edt_noteEditContent);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        myNote = getIntent().getParcelableExtra(NoteView.HANG_DI);
        edtNoteEditTitle.setText(myNote.getmTitle());
        edtNoteEditContent.setText(myNote.getmContent());
        Typeface tf = Typeface.createFromAsset(getAssets(),
                "fonts/Geogrotesque-Light.ttf");
        edtNoteEditContent.setTypeface(tf);
        tf = Typeface.createFromAsset(getAssets(),
                "fonts/SVN-Aguda Regular.otf");
        edtNoteEditTitle.setTypeface(tf,Typeface.BOLD);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_note_edit,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.action_save){
            myNote.setmTitle(edtNoteEditTitle.getText().toString());
            myNote.setmContent(edtNoteEditContent.getText().toString());
            Intent intent = new Intent();
            intent.putExtra(HANG_VE,myNote);
            setResult(VE,intent);
        }
        finish();
        return super.onOptionsItemSelected(item);
    }
}
