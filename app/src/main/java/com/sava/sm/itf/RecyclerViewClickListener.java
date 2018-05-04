package com.sava.sm.itf;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Mr.Sang on 1/21/2018.
 */

public interface RecyclerViewClickListener {
    void onClick(View view, int position);
    void onClick2(View view, int position , RecyclerView.ViewHolder viewHolder);
}
