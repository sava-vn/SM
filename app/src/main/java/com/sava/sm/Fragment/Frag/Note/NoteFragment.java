package com.sava.sm.Fragment.Frag.Note;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.sava.sm.NoteAdd;
import com.sava.sm.NoteView;
import com.sava.sm.R;
import com.sava.sm.ReminderAdd;
import com.sava.sm.adapter.NoteAdapter;

import com.sava.sm.control.Database;
import com.sava.sm.control.RecyclerItemTouchHelper;
import com.sava.sm.itf.RecyclerViewClickListener;
import com.sava.sm.model.MyNote;


import java.util.ArrayList;

public class NoteFragment extends Fragment implements RecyclerItemTouchHelper.RecyclerItemTouchHelperListener {
    public static final int DI_NOTE_VIEW = 9;
    public static final int NOTEFRAGMENT_DI_NOTEADD = 10;
    public static final String HANG_DI_NOT_VIEW = "myNote";
    private Database database;
    private boolean star ;

    private RecyclerView mRecyclerView;
    private ArrayList<MyNote> listMyNote;
    private NoteAdapter adapter;
    private CoordinatorLayout coordinatorLayout;
    private ArrayList<MyNote> listDelete;

    private FloatingActionMenu fab;
    private FloatingActionButton fab1;
    private FloatingActionButton fab2;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_note, container, false);
        initWidget(view);
        initFab();
        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new RecyclerItemTouchHelper(0, ItemTouchHelper.RIGHT, this);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(mRecyclerView);
        fab.setClosedOnTouchOutside(true);
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == DI_NOTE_VIEW && resultCode == NoteView.VE_FRAGMENT) {
            MyNote noteDelete = data.getExtras().getParcelable(NoteView.HANG_VE_FRAGMENT);
            for (MyNote note : listMyNote) {
                if (note.getmID() == noteDelete.getmID()) {
                    listMyNote.remove(note);
                    listDelete.add(note);
                    adapter.notifyDataSetChanged();
                    return;
                }
            }
        }
        if (requestCode == DI_NOTE_VIEW && resultCode == NoteView.HANG_DA_DUOC_SUA) {
            MyNote noteEdit = data.getExtras().getParcelable(NoteView.HANG_VE_FRAGMENT);
            for (MyNote note : listMyNote) {
                if (note.getmID() == noteEdit.getmID()) {
                    note.setmTitle(noteEdit.getmTitle());
                    note.setmContent(noteEdit.getmContent());
                    database.updateNote(noteEdit);
                    adapter.notifyDataSetChanged();
                    return;
                }
            }
        }
        if (requestCode == NOTEFRAGMENT_DI_NOTEADD && resultCode == NoteAdd.NOTEADD_VE_NOTEFRAGMENT) {
            MyNote newNote = data.getExtras().getParcelable(NoteAdd.HANG_NODEADD_VE_NOTEFRAGMENT);
            listMyNote.add(newNote);
            database.addNote(newNote);
            adapter.notifyDataSetChanged();
        }
    }

    private void initFab() {
        fab = getActivity().findViewById(R.id.fab);
        fab1 = getActivity().findViewById(R.id.fab1);
        fab2 = getActivity().findViewById(R.id.fab2);
        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), NoteAdd.class);
                startActivityForResult(intent, NOTEFRAGMENT_DI_NOTEADD);
                fab.close(true);
            }
        });
    }

    private void initWidget(View view) {
        listDelete = new ArrayList<>();
        coordinatorLayout = getActivity().findViewById(R.id.coordinatorLayout);
        mRecyclerView = view.findViewById(R.id.rv_note);

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(llm);
        database = new Database(getActivity());
        listMyNote = database.getAllNote();
        adapter = new NoteAdapter(getActivity(), listMyNote, new RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position) {
                Intent intent = new Intent(getActivity(), NoteView.class);
                intent.putExtra(HANG_DI_NOT_VIEW, listMyNote.get(position));
                startActivityForResult(intent, DI_NOTE_VIEW);
                if(fab.isOpened()){
                    fab.close(true);
                }
            }

            @Override
            public void onClick2(View view, int position,RecyclerView.ViewHolder viewHolder) {

            }
        });
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(fab.isOpened())
                    fab.close(true);
                return false;
            }
        });
        star = false;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        getActivity().getMenuInflater().inflate(R.menu.menu_ip, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        database.deleteList(listDelete);
        listMyNote.clear();
        if(!star){
            listMyNote.addAll(database.getAllNoteStar());
            star = true;
        }else{
            listMyNote.addAll(database.getAllNote());
            star = false;
        }
        adapter.notifyDataSetChanged();
        return true;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        if (viewHolder instanceof NoteAdapter.ViewHolder) {
            final int deletedIndex = viewHolder.getAdapterPosition();
            final MyNote deletedNote = listMyNote.get(deletedIndex);
            adapter.removeItem(deletedIndex);
            listDelete.add(deletedNote);
            adapter.notifyDataSetChanged();
            Snackbar snackbar = Snackbar.make(coordinatorLayout, deletedNote.getmTitle() + "\nĐã được xóa bỏ!", Snackbar.LENGTH_LONG);
            snackbar.setAction("Hoàn tác", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    adapter.restoreItem(deletedNote, deletedIndex);
                    listDelete.remove(deletedNote);
                    return;
                }
            });
            snackbar.setActionTextColor(Color.YELLOW);
            snackbar.show();
        }
    }
    @Override
    public void onStop() {
        super.onStop();
        database.deleteList(listDelete);
    }
}
