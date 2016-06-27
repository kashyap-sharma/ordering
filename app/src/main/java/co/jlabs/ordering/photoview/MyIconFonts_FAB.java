package co.jlabs.ordering.photoview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.Button;


public class MyIconFonts_FAB extends Button {


    public MyIconFonts_FAB(Context context) {
      super(context);
        Typeface tf = FontCache.get("fonts/materialdesignicons-webfont.ttf", context);
        if(tf != null) {
            this.setTypeface(tf);

        }
    }

    public MyIconFonts_FAB(Context context, AttributeSet attrs) {
        super(context, attrs);
        Typeface tf = FontCache.get("fonts/materialdesignicons-webfont.ttf", context);
        if(tf != null) {
            this.setTypeface(tf);

        }
    }

    public MyIconFonts_FAB(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        Typeface tf = FontCache.get("fonts/materialdesignicons-webfont.ttf", context);
        if(tf != null) {
            this.setTypeface(tf);

        }
    }

    protected void onDraw (Canvas canvas) {
        super.onDraw(canvas);
    }

}