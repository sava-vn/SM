package com.sava.sm.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sava.sm.R;
import com.sava.sm.control.SFont;
import com.sava.sm.itf.RecyclerViewClickListener;
import com.sava.sm.itf.RecyclerViewLongClickListener;
import com.sava.sm.model.Reminder;

import java.util.ArrayList;

public class ReminderAdpater extends  RecyclerView.Adapter<ReminderAdpater.ViewHolder> {
    private Context mContext;
    private ArrayList<Reminder> mList;
    private RecyclerViewClickListener mListener;
    private RecyclerViewLongClickListener mLongListener;

    public ReminderAdpater(Context mContext, ArrayList<Reminder> mList, RecyclerViewClickListener listener, RecyclerViewLongClickListener longClickListener) {
        this.mContext = mContext;
        this.mList = mList;
        this.mListener = listener;
        this.mLongListener = longClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_reminder,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final Reminder reminder = mList.get(position);
        holder.ivReminderAvata.setImageResource(R.drawable.bell_ring);
        holder.tvReminderTitle.setText(reminder.getmTitle());
        String date = (String) android.text.format.DateFormat.format("dd-MM-yyyy",reminder.getmDate());
        holder.tvReminderDate.setText(date);
        String time = (String) android.text.format.DateFormat.format("HH : mm",reminder.getmDate());
        holder.tvReminderTime.setText(time);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{
        ImageView ivReminderAvata;
        TextView tvReminderTitle;
        TextView tvReminderDate;
        TextView tvReminderTime;
        LinearLayout llStatusItem;
        LinearLayout llContentItem;
        LinearLayout llTimeItem;
        public ViewHolder(View itemView) {
            super(itemView);
            ivReminderAvata = itemView.findViewById(R.id.iv_reminder_avata);
            tvReminderTitle = itemView.findViewById(R.id.tv_reminder_title);
            tvReminderDate =  itemView.findViewById(R.id.tv_reminder_date);
            tvReminderTime =  itemView.findViewById(R.id.tv_reminder_time);
            llStatusItem = itemView.findViewById(R.id.ll_status_item);
            llContentItem = itemView.findViewById(R.id.ll_content_item);
            llTimeItem = itemView.findViewById(R.id.ll_time_item);
            SFont.setFont(mContext,"fonts/Geogrotesque-Light.ttf",tvReminderTitle, Typeface.BOLD);
            SFont.setFont(mContext,"fonts/FS Siruca.ttf",tvReminderDate, Typeface.BOLD);
            SFont.setFont(mContext,"fonts/FS Siruca.ttf",tvReminderTime, Typeface.BOLD);
            llTimeItem.setOnClickListener(this);
            llStatusItem.setOnClickListener(this);
            llStatusItem.setOnLongClickListener(this);

        }

        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.ll_time_item:
                    mListener.onClick(view,getAdapterPosition());
                    break;
                case R.id.ll_status_item:
                    mListener.onClick2(view,getAdapterPosition(),this);
                    break;
            }
        }

        @Override
        public boolean onLongClick(View view) {
            mLongListener.onLongClick(view,getAdapterPosition());
            return true;
        }

    }
    public  void setBG(ViewHolder holder,int position){
        Reminder reminder = mList.get(position);
        if(reminder.getmStatus()>0){
            holder.ivReminderAvata.setBackground(mContext.getResources().getDrawable(R.drawable.y_custom_iv_2));
            reminder.setmStatus(0);
            holder.tvReminderTitle.setTextColor(Color.GRAY);
            holder.tvReminderDate.setTextColor(Color.GRAY);
            holder.tvReminderTime.setBackground(mContext.getResources().getDrawable(R.drawable.y_custom_textview2));
        }else {
            holder.ivReminderAvata.setBackground(mContext.getResources().getDrawable(R.drawable.y_custom_iv));
            reminder.setmStatus(1);
            holder.tvReminderTitle.setTextColor(mContext.getResources().getColor(R.color.white));
            holder.tvReminderDate.setTextColor(mContext.getResources().getColor(R.color.divider));
            holder.tvReminderTime.setBackground(mContext.getResources().getDrawable(R.drawable.y_custom_textview));
        }
    }
}
