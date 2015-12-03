package com.groupviewrolling;


import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Scroller;

import com.groupviewrolling.utils.ScreenUtils;

/**
 * 
 * @Title: ArticleDetailsViewGroup.java
 * @ClassName: ArticleDetailsViewGroup
 * @Description: 文章详情ViewGroup，用来切换详情和评论
 * @author jiahongfei jiahongfeinew@163.com
 * @date 2015-3-4 上午10:25:08
 * @version V1.0.0
 */
public class ArticleDetailsViewGroup extends ViewGroup {

	private VelocityTracker mVelocityTracker; // 用于判断甩动手势
	private static final int SNAP_VELOCITY = 600; // X轴速度基值，大于该值时进行切换
	private Scroller mScroller; // 滑动控制
	private int mCurScreen; // 当前页面为第几屏
	private int mDefaultScreen = 0;
	private float mLastMotionY;
	private float mLastMotionDispatchY = 0;
	private int mTouchSlop = 0;
	private int mWebViewHeight = -1;

	private boolean isScrollBy = false;

	private Context mContext;

	private IBaseArticleLayout mIBaseArticleLayout;
	private IBaseCommentLayout mIBaseCommentLayout;

	private OnViewChangeListener mOnViewChangeListener;

	public ArticleDetailsViewGroup(Context context) {
		super(context);
		init(context);
	}

	public ArticleDetailsViewGroup(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public ArticleDetailsViewGroup(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}

	private void init(Context context) {
		mContext = context;
		mCurScreen = mDefaultScreen;
		mWebViewHeight = -1;
		mScroller = new Scroller(context);

		mTouchSlop = ScreenUtils.getTouchSlop(mContext);
		System.out.println("touchSlop : " + mTouchSlop);
		mTouchSlop = 1;

	}

	public void setWebViewHeight(int webViewHeight) {
		this.mWebViewHeight = webViewHeight;
	}

	/**
	 * 设置文章
	 * 
	 * @param articleView
	 */
	public void addArticleView(View articleView) {
		if (articleView instanceof IBaseArticleLayout) {
			mIBaseArticleLayout = (IBaseArticleLayout) articleView;
			addView(articleView, 0);
		} else {
			// articleView没有实现IBaseArticleLayout接口
			throw new RuntimeException(
					"articleView No interface IBaseArticleLayout");
		}

	}

	public void addCommentView(View commentView) {
		if (commentView instanceof IBaseCommentLayout) {
			mIBaseCommentLayout = (IBaseCommentLayout) commentView;
			addView(commentView, 1);
		} else {
			// articleView没有实现IBaseCommentLayout接口
			throw new RuntimeException(
					"articleView No interface IBaseCommentLayout");
		}
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		// if (changed) {
		int childTop = 0;
		final int childCount = getChildCount();
		for (int i = 0; i < childCount; i++) {
			final View childView = getChildAt(i);
			if (childView.getVisibility() != View.GONE) {
				int childHeight = 0;
				if (0 == i && -1 != mWebViewHeight) {
					childHeight = mWebViewHeight;
				} else {
					childHeight = childView.getMeasuredHeight();
				}
				childView.layout(0, childTop, childView.getMeasuredWidth(),
						childTop + childHeight);
				childTop += childHeight;
			}
		}
		// }
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

		int widthMode = MeasureSpec.getMode(widthMeasureSpec);
		int heightMode = MeasureSpec.getMode(heightMeasureSpec);
		int widthSize = MeasureSpec.getSize(widthMeasureSpec);
		int heightSize = MeasureSpec.getSize(heightMeasureSpec);

		final int count = getChildCount();
		for (int i = 0; i < count; i++) {
			if (0 == i && -1 != mWebViewHeight) {
				getChildAt(i).measure(widthMeasureSpec,
						MeasureSpec.EXACTLY + mWebViewHeight);
			} else {
				getChildAt(i).measure(widthMeasureSpec, heightMeasureSpec);
			}
		}
		
		if(-1 != mWebViewHeight){
			scrollTo(0, mCurScreen * mWebViewHeight);
		}else {
			scrollTo(0, mCurScreen * getHeight());
		}
		

		setMeasuredDimension(widthSize, heightSize);

	}

	public void snapToDestination() {

		int screenHeight = 0;
		if (-1 == mWebViewHeight) {
			screenHeight = getHeight();
		} else {
			screenHeight = mWebViewHeight;
		}

		final int destScreen = (getScrollY() + screenHeight / 2) / screenHeight;
		snapToScreen(destScreen);
	}

	// 使屏幕移动到第whichScreen+1屏
	public void snapToScreen(int whichScreen) {

		int webViewHeight = 0;

		if (-1 == mWebViewHeight) {
			webViewHeight = getHeight();
		} else {
			webViewHeight = mWebViewHeight;
		}

		// if (getScrollY() != (whichScreen * webViewHeight)) {
		final int delta = whichScreen * webViewHeight - getScrollY();
		mScroller.startScroll(0, getScrollY(), 0, delta,
				(int) (Math.abs(delta) * 0.5));
		mCurScreen = whichScreen;
		invalidate();

		if (mOnViewChangeListener != null) {
			mOnViewChangeListener.OnViewChange(mCurScreen);
		}
		// }
	}

	@Override
	public void computeScroll() {
		if (mScroller.computeScrollOffset()) {
			scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
			postInvalidate();
		}
		super.computeScroll();

	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent event) {

		final int action = event.getAction();
		final float x = event.getX();
		final float y = event.getY();

		switch (action) {
		case MotionEvent.ACTION_DOWN:

			if (mVelocityTracker == null) {
				mVelocityTracker = VelocityTracker.obtain();
				mVelocityTracker.addMovement(event);
			}
			if (!mScroller.isFinished()) {
				mScroller.abortAnimation();
			}
			mLastMotionY = y;

			break;

		case MotionEvent.ACTION_MOVE:

			int tempDeltaY = (int) (mLastMotionY - y);
			if (tempDeltaY < 0 && Math.abs(tempDeltaY) >= mTouchSlop) {
				// 向下
				// System.out.println("向下");
				if (isScrollBy || (mIBaseCommentLayout.isFirstViewTop())) {
					if (isScrollBy || 1 == mCurScreen) {
						if (0 == mLastMotionDispatchY) {
							mLastMotionDispatchY = y;
						}
						int deltaY = (int) (mLastMotionDispatchY - y);
						if (IsCanMove(deltaY)) {
							if (mVelocityTracker != null) {
								mVelocityTracker.addMovement(event);
							}
							mLastMotionDispatchY = y;
							mIBaseArticleLayout.setIsScroll(false);
							mIBaseCommentLayout.setIsScroll(false);
							// 正向或者负向移动，屏幕跟随手指移动
							if (deltaY + getScrollY() < 0) {
								scrollTo(0, 0);
							} else {
								scrollBy(0, deltaY);
							}
							// }
						} else {
							return true;
						}
					}
				}
			} else if (tempDeltaY > 0 && Math.abs(tempDeltaY) >= mTouchSlop) {
				// 向上
				if (isScrollBy || mIBaseArticleLayout.isScrollBottom()) {
					if (isScrollBy || 0 == mCurScreen) {
						if (0 == mLastMotionDispatchY) {
							mLastMotionDispatchY = y;
						}
						int deltaY = (int) (mLastMotionDispatchY - y);
						if (IsCanMove(deltaY)) {
							if (mVelocityTracker != null) {
								mVelocityTracker.addMovement(event);
							}
							mLastMotionY = y;
							mLastMotionDispatchY = y;
							mIBaseArticleLayout.setIsScroll(false);
							mIBaseCommentLayout.setIsScroll(false);
							// 正向或者负向移动，屏幕跟随手指移动
							if (deltaY + getScrollY() > getHeight()) {
								scrollTo(0, getHeight());
							} else {
								scrollBy(0, deltaY);
							}
						} else {
							return true;
						}
					}
				}
			} else {
				return true;
			}

			mLastMotionY = y;

			break;

		case MotionEvent.ACTION_UP:

			isScrollBy = false;
			mLastMotionDispatchY = 0;
			mIBaseArticleLayout.setIsScroll(true);
			mIBaseCommentLayout.setIsScroll(true);

			int velocityY = 0;
			if (mIBaseArticleLayout.isScrollBottom()
					&& mIBaseCommentLayout.isFirstViewTop()
					&& mVelocityTracker != null) {
				mVelocityTracker.addMovement(event);
				mVelocityTracker.computeCurrentVelocity(1000);
				// 得到Y轴方向手指移动速度
				velocityY = (int) mVelocityTracker.getYVelocity();
			}
			if (velocityY > SNAP_VELOCITY && mCurScreen > 0) {
				// Fling enough to move top
				snapToScreen(mCurScreen - 1);
			} else if (velocityY < -SNAP_VELOCITY
					&& mCurScreen < getChildCount() - 1) {
				// Fling enough to move bottom
				snapToScreen(mCurScreen + 1);
			} else {
				snapToDestination();
			}

			if (mVelocityTracker != null) {
				mVelocityTracker.recycle();
				mVelocityTracker = null;
			}
			break;
		case MotionEvent.ACTION_CANCEL: {
			break;
		}
		}
		return super.dispatchTouchEvent(event);

	}

	@Override
	public void scrollBy(int x, int y) {
		isScrollBy = true;
		super.scrollBy(x, y);
	}

	private boolean IsCanMove(int deltaY) {

		int webViewHeight = 0;
		if (-1 == mWebViewHeight) {
			webViewHeight = getHeight();
		} else {
			webViewHeight = mWebViewHeight;
		}

		// deltaY<0说明手指向下划
		if (getScrollY() <= 0 && deltaY < 0) {
			return false;
		}
		// deltaX>0说明手指向上划
		if (getScrollY() >= (getChildCount() - 1) * webViewHeight && deltaY > 0) {
			return false;
		}
		return true;
	}

	public void SetOnViewChangeListener(OnViewChangeListener listener) {
		mOnViewChangeListener = listener;
	}

	public int getCurrentItem() {
		return mCurScreen;
	}

	public void setCurrentItem(int item) {
		snapToScreen(item);
	}

	/**
	 * 判断Scroller是否完成
	 * 
	 * @return
	 */
	public boolean isScrollerFinished() {
		return mScroller.isFinished();
	}

}
