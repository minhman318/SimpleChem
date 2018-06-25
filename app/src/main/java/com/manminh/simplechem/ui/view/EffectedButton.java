package com.manminh.simplechem.ui.view;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.TransitionDrawable;
import android.util.AttributeSet;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.MotionEvent;

public class EffectedButton extends AppCompatButton {
    private static final int DURATION = 250;
    private Rect mBound;

    public EffectedButton(Context context) {
        super(context);
    }

    public EffectedButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public EffectedButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        final TransitionDrawable trans = (TransitionDrawable) this.getBackground();
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
                Log.d("MOVE", "up");
                trans.reverseTransition(DURATION);
                performClick();
                break;
            case MotionEvent.ACTION_DOWN:
                Log.d("MOVE", "down");
                trans.startTransition(DURATION);
                mBound = new Rect(this.getLeft(), this.getTop(), this.getRight(), this.getBottom());
                break;
            case MotionEvent.ACTION_HOVER_MOVE:
                Log.d("MOVE", "move");
                if (mBound != null
                        && !mBound.contains(this.getLeft() + (int) event.getX(),
                        this.getTop() + (int) event.getY())) {
                    trans.reverseTransition(DURATION);
                }
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
