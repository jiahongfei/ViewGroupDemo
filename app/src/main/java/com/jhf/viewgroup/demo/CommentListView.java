package com.jhf.viewgroup.demo;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ListView;

/**
 * 评论ListView，继承ListView主要是拿到是否滚动到顶
 * Created by jiahongfei on 15/12/1.
 */
public class CommentListView extends ListView{

    /** true listview在最顶端，false不是在最顶端 */
    private boolean isFirstViewTop = false;
    /**true可以滑动，false不能滑动*/
    private boolean isScroll = true;

    public CommentListView(Context context) {
        super(context);
    }

    public CommentListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CommentListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void dispatchDraw(Canvas canvas) {
        /**
         * This is a bit hacky, but Samsung's ListView has got a bug in it
         * when using Header/Footer Views and the list is empty. This masks
         * the issue so that it doesn't cause an FC. See Issue #66.
         */
        try {
            super.dispatchDraw(canvas);

            if (0 == this.getFirstVisiblePosition()) {
                View firstView = this.getChildAt(0);
                if (null != firstView) {
                    int firstTop = firstView.getTop();
                    // System.out.println("firstTop : " + firstTop);
                    if (Math.abs(firstTop) <= 5) {
                        isFirstViewTop = true;
                    } else {
                        isFirstViewTop = false;
                    }
                } else {
                    if (getAdapter().getCount() <= 0) {
                        isFirstViewTop = true;
                    } else {
                        isFirstViewTop = false;
                    }
                }
            } else {

                if (getAdapter().getCount() <= 0) {
                    isFirstViewTop = true;
                } else {
                    isFirstViewTop = false;
                }
            }
            getParent().requestDisallowInterceptTouchEvent(
                    !isFirstViewTop);
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        if (!isScroll) {
            return true;
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (!isScroll) {
            return true;
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {

        if (!isScroll) {
            return true;
        }

        return super.onTouchEvent(ev);
    }
    /**
     * true可以滑动，false不能滑动
     * @param isScroll
     */
    public void setIsScroll(boolean isScroll) {
        this.isScroll = isScroll;
    }

    public boolean isFirstViewTop() {
        return isFirstViewTop;
    }



}
