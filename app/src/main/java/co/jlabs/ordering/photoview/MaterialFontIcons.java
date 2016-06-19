package co.jlabs.ordering.photoview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by JLabs on 12/23/15.
 */
public class MaterialFontIcons  extends TextView {


    public MaterialFontIcons(Context context) {
        super(context);
        Typeface tf = FontCache.get("fonts/material_icons.ttf", context);
        if(tf != null) {
            this.setTypeface(tf);
        }
    }

    public MaterialFontIcons(Context context, AttributeSet attrs) {
        super(context, attrs);
        Typeface tf = FontCache.get("fonts/material_icons.ttf", context);
        if(tf != null) {
            this.setTypeface(tf);
        }
    }

    public MaterialFontIcons(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        Typeface tf = FontCache.get("fonts/material_icons.ttf", context);
        if(tf != null) {
            this.setTypeface(tf);
        }
    }

    protected void onDraw (Canvas canvas) {
        super.onDraw(canvas);
    }

}