package com.sava.sm.control;

import android.content.Context;
import android.graphics.Typeface;
import android.widget.TextView;

/**
 * Created by Mr.Sang on 2/9/2018.
 */

public class SFont {
    public static void setFont(Context context, String fontName, TextView textView, int style){
        Typeface typeface = Typeface.createFromAsset(context.getAssets(),fontName);
        textView.setTypeface(typeface,style);
    }
}
