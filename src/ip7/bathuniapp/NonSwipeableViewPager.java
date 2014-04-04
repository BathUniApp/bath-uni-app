package ip7.bathuniapp;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class NonSwipeableViewPager extends ViewPager {
    public NonSwipeableViewPager(Context context) {
        super(context);
    }

    public NonSwipeableViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent arg0) {
        return false; // never allow swiping to switch between tabs
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return false; // never allow swiping to switch between tabs
    }
}
