package com.groupviewrolling.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewConfiguration;

/**
 * 
 * @Title: ScreenUtils.java
 * @Package com.android.support.framework.utils
 * @ClassName: ScreenUtils
 * @Description: 和屏幕相关的(屏幕宽高等）
 * @author jiahongfei jiahongfeinew@163.com
 * @date Nov 5, 2014 2:22:11 PM
 * @version V1.0.0
 */
public class ScreenUtils {

	/**
	 * 获取屏幕宽高
	 * 
	 * @param context
	 * @return 数组第一个元素宽，第二个元素高
	 */
	public static int[] getScreenBounds(Context context) {

		int[] screenBounds = new int[2];

		DisplayMetrics displayMetrics = context.getResources()
				.getDisplayMetrics();
		screenBounds[0] = displayMetrics.widthPixels;
		screenBounds[1] = displayMetrics.heightPixels;
		return screenBounds;
	}

	/**
	 * 根据dip返回当前设备上的px值
	 * 
	 * @param context
	 * @param dip
	 * @return
	 */
	public static int dipToPx(Context context, int dip) {
		int px = 0;
		DisplayMetrics dm = new DisplayMetrics();
		dm = context.getApplicationContext().getResources().getDisplayMetrics();
		float density = dm.density;
		px = (int) (dip * density);
		return px;
	}

	public static int getTouchSlop(Context context) {
		final ViewConfiguration configuration = ViewConfiguration.get(context);
		return configuration.getScaledTouchSlop();
	}

	public static int getStatusBarHeight(Activity activity) {
		Rect frame = new Rect();
		activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
		int statusBarHeight = frame.top;
		return statusBarHeight;
	}

	/**
	 * 这个方法是将view的左上角坐标存入数组中.此坐标是相对当前activity而言.
	 * 若是普通activity,则y坐标为可见的状态栏高度+可见的标题栏高度+view左上角到标题栏底部的距离.
	 * 可见的意思是:在隐藏了状态栏/标题栏的情况下,它们的高度以0计算.
	 * 若是对话框式的activity,则y坐标为可见的标题栏高度+view到标题栏底部的距离. 此时是无视状态栏的有无的.
	 * 
	 * @param view
	 * @return
	 */
	public static int[] getLocationInWindow(View view) {
		int[] position = new int[2];
		view.getLocationInWindow(position);
		System.out.println("getLocationInWindow:" + position[0] + ","
				+ position[1]);
		return position;
	}

	/**
	 * 这个方法跟上面的差不多,也是将view的左上角坐标存入数组中.但此坐标是相对整个屏幕而言. y坐标为view左上角到屏幕顶部的距离.
	 * 
	 * @param view
	 * @return
	 */
	public static int[] getLocationOnScreen(View view) {
		int[] position = new int[2];
		view.getLocationOnScreen(position);
		System.out.println("getLocationOnScreen:" + position[0] + ","
				+ position[1]);
		return position;
	}

	/**
	 * 这个方法是构建一个Rect用来"套"这个view.此Rect的坐标是相对当前activity而言.
	 * 若是普通activity,则Rect的top为可见的状态栏高度+可见的标题栏高度+Rect左上角到标题栏底部的距离.
	 * 若是对话框式的activity,则y坐标为Rect的top为可见的标题栏高度+Rect左上角到标题栏底部的距离. 此时是无视状态栏的有无的.
	 * 
	 * @param view
	 * @return
	 */
	public static Rect getGlobalVisibleRect(View view) {
		Rect viewRect = new Rect();
		view.getGlobalVisibleRect(viewRect);
		System.out.println(viewRect);
		return viewRect;
	}

	/**
	 * 这个方法获得的Rect的top和left都是0,也就是说,仅仅能通过这个Rect得到View的宽度和高度....
	 * 
	 * @param view
	 * @return
	 */
	public static Rect getLocalVisibleRect(View view) {
		Rect globeRect = new Rect();
		view.getLocalVisibleRect(globeRect);
		return globeRect;
	}

}
