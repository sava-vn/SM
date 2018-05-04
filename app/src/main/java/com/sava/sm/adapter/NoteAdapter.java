package com.sava.sm.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sava.sm.R;
import com.sava.sm.control.Database;
import com.sava.sm.control.SFont;
import com.sava.sm.itf.RecyclerViewClickListener;
import com.sava.sm.model.MyNote;

import java.util.ArrayList;
import java.util.Random;


public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder> {
    private Context context;
    private ArrayList<MyNote> listMyNote;
    private RecyclerViewClickListener mListener;
    private Database database;

    public NoteAdapter(Context context, ArrayList<MyNote> listMyNote, RecyclerViewClickListener listener) {
        this.context = context;
        this.listMyNote = listMyNote;
        this.mListener = listener;
        database = new Database(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_note, parent, false);
        return new ViewHolder(view,mListener);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        MyNote note = listMyNote.get(position);
        if (note.isStatus())
            holder.imImportion.setImageResource(R.drawable.star_selected);
        else
            holder.imImportion.setImageResource(R.drawable.star_outline);
        holder.tvNoteTitle.setText(note.getmTitle());
        holder.tvNoteDate.setText(note.getDateString(MyNote.KIEU_NGAY_THANG_NAM));
        holder.linearLayoutIm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyNote myNote = listMyNote.get(position);
                if (myNote.isStatus()) {
                    holder.imImportion.setImageResource(R.drawable.star_outline);
                    myNote.setStatus(false);
                } else {
                    holder.imImportion.setImageResource(R.drawable.star_selected);
                    myNote.setStatus(true);
                }
                database.updateNoteStatus(myNote);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listMyNote.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView tvNoteTitle;
        TextView delete;
        TextView tvNoteDate;
        ImageView imImportion;
        LinearLayout linearLayoutIm;
        LinearLayout linearLayoutContent;
        public LinearLayout viewForeground;
        private  RecyclerViewClickListener mListener;

        public ViewHolder(View itemView ,RecyclerViewClickListener listener)  {
            super(itemView);
            tvNoteTitle = itemView.findViewById(R.id.tv_noteTitle);
            tvNoteDate = itemView.findViewById(R.id.tv_noteDate);
            imImportion = itemView.findViewById(R.id.im_importion);
            linearLayoutIm = itemView.findViewById(R.id.ll_im_importion);
            linearLayoutContent = itemView.findViewById(R.id.ll_content_item);
            viewForeground = itemView.findViewById(R.id.view_foreground);
            delete = itemView.findViewById(R.id.tv_delete);;
            SFont.setFont(context,"fonts/Geogrotesque-Light.ttf",tvNoteTitle,Typeface.BOLD);
            SFont.setFont(context,"fonts/FS Siruca.ttf",tvNoteDate,Typeface.BOLD);
            SFont.setFont(context,"fonts/FS Siruca.ttf",delete,Typeface.BOLD);
            mListener = listener;
            linearLayoutContent.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            mListener.onClick(linearLayoutContent,getAdapterPosition());
        }
    }

    public void removeItem(int position) {
        listMyNote.remove(position);
        notifyItemRemoved(position);
    }

    public void restoreItem(MyNote item, int position) {
        listMyNote.add(position, item);
        notifyItemInserted(position);
    }
}
