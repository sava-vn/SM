package com.sava.sm;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.sava.sm.Fragment.Frag.Note.NoteFragment;
import com.sava.sm.model.MyNote;

public class NoteView extends AppCompatActivity {
    public final static int DI =1;
    public final static String HANG_DI = "HD";
    public final static String HANG_VE_FRAGMENT = "HVFR";
    public final static int VE_FRAGMENT = 6;
    public final static int HANG_DA_DUOC_SUA =7;
    private Toolbar mToolbar;
    private TextView mtvNodeViewContent;
    private TextView mtvNodeViewTitle;
    private TextView mTvDate;
    private MyNote myNote;
    private boolean isEdit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_view);
        isEdit = false;
        mToolbar = findViewById(R.id.tb_noteView);
        mToolbar.setTitle("");
        myNote = getIntent().getExtras().getParcelable(NoteFragment.HANG_DI_NOT_VIEW);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mtvNodeViewContent = findViewById(R.id.tv_noteViewContent);
        mtvNodeViewContent.setText(myNote.getmContent());
        mtvNodeViewTitle = findViewById(R.id.tv_noteViewTitle);
        mTvDate = findViewById(R.id.tv_date);
        mTvDate.setText(myNote.getDateString(MyNote.KIEU_THU_NGAY_THANG));
        Typeface tf = Typeface.createFromAsset(getAssets(),
                "fonts/Geogrotesque-Light.ttf");
        mtvNodeViewTitle.setText(myNote.getmTitle());
        mtvNodeViewContent.setTypeface(tf);
        tf = Typeface.createFromAsset(getAssets(),
                "fonts/SVN-Aguda Regular.otf");
        mtvNodeViewTitle.setTypeface(tf,Typeface.BOLD);
        tf = Typeface.createFromAsset(getAssets(),
                "fonts/FS Siruca.ttf");
        mTvDate.setTypeface(tf);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_note_view,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()){
            case R.id.action_edit_note:
                intent = new Intent(NoteView.this,NoteEdit.class);
                intent.putExtra(HANG_DI,myNote);
                startActivityForResult(intent,DI);
                break;
            case R.id.action_delete_note :
                intent = new Intent();
                intent.putExtra(HANG_VE_FRAGMENT,myNote);
                setResult(VE_FRAGMENT,intent);
                finish();
                break;
            case android.R.id.home:
                if(isEdit){
                    intent = new Intent();
                    intent.putExtra(HANG_VE_FRAGMENT,myNote);
                    setResult(HANG_DA_DUOC_SUA,intent);
                }
                finish();
        }

        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==DI&& resultCode==NoteEdit.VE){
            myNote = data.getExtras().getParcelable(NoteEdit.HANG_VE);
            mtvNodeViewTitle.setText(myNote.getmTitle());
            mtvNodeViewContent.setText(myNote.getmContent());
            isEdit = true;
        }
    }
}
