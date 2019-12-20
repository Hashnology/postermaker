package com.ashu.postermaker.mycollage;

import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

public class MoveViewTouchListener implements View.OnTouchListener
{
    private GestureDetector mGestureDetector;
    private View mView;


    public MoveViewTouchListener(View view)
    {
        mGestureDetector = new GestureDetector(view.getContext(), mGestureListener);
        mView = view;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event)
    {
        return mGestureDetector.onTouchEvent(event);
    }

    private GestureDetector.OnGestureListener mGestureListener = new GestureDetector.SimpleOnGestureListener()
    {
        private float mMotionDownX, mMotionDownY;

        @Override
        public boolean onDown(MotionEvent e)
        {
            mMotionDownX = e.getRawX() - mView.getTranslationX();
            mMotionDownY = e.getRawY() - mView.getTranslationY();
            return true;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY)
        {
            mView.setTranslationX(e2.getRawX() - mMotionDownX);
            mView.setTranslationY(e2.getRawY() - mMotionDownY);
            return true;
        }
        @Override
        public boolean onSingleTapUp(MotionEvent e) {
//            if (g) {
//                if (groupselecteditems.contains(mView)) {
//                    mView.setBackgroundColor(Color.TRANSPARENT);
//                    groupselecteditems.remove(mView);
//                } else {
//                    mView.setBackgroundResource(border);
//                    groupselecteditems.add((MyTextView) mView);
//                }
//            } else {
//                toucheditem = mView;
//                xcountry = mView.getX();
//                ycountry = mView.getY();
//            }
            return true;
        }
        @Override
       public void onLongPress(MotionEvent e) {
//            if (g) {
//                if (groupselecteditems.size() > 0) {
//                    editGroup();
//                }
//            } else {
//                changtextfeatures((TextView) mView);
//            }
        }
    };
}
