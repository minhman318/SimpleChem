package com.manminh.simplechem.ui.view;

import android.content.Context;
import android.graphics.drawable.TransitionDrawable;
import android.util.AttributeSet;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.MotionEvent;

import java.util.logging.Handler;

public class SearchButton extends AppCompatButton {
    private static final int DURATION = 300;

    public SearchButton(Context context) {
        super(context);
    }

    public SearchButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SearchButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        final TransitionDrawable trans = (TransitionDrawable) this.getBackground();
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
                trans.reverseTransition(DURATION);
                performClick();
                break;
            case MotionEvent.ACTION_DOWN:
                trans.startTransition(DURATION);
                break;
            default:
                break;
        }
        return true;
    }

    @Override
    public boolean performClick() {
        return super.performClick();
    }
}
