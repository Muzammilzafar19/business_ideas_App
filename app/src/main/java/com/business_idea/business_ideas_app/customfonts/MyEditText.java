package com.business_idea.business_ideas_app.customfonts;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

/**
 * Created by kamran on 27/6/16.
 */
public class MyEditText extends androidx.appcompat.widget.AppCompatEditText {

    public MyEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public MyEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyEditText(Context context) {
        super(context);
        init();
    }

    private void init() {
        if (!isInEditMode()) {
            Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/OpenSans-Light.ttf");
            setTypeface(tf);
        }
    }

}