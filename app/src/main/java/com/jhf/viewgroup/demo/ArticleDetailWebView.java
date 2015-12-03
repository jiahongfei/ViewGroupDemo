package com.jhf.viewgroup.demo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;

/**
 * @Description: Webview展示文章详情，继承WebView主要是判断Html内容是否滚动到底了
 *
 * @version V1.0.0
 */
public class ArticleDetailWebView extends WebView {

	public interface ScrollInterface {
		public void onScrollChanged(int l, int t, int oldl, int oldt);
	}

	private static final String BASE_URL = "file:///android_asset/";
	private static final String MIME_TYPE = "text/html";
	private static final String ENCODING = "utf-8";
	private static final String BASIC_HTML = "basic.html";
	private static final String BASIC_CASE_DETAILS_HTML = "basic_case_detail.html";

	private Context mContext;
	private ScrollInterface mScrollInterface;

	private boolean isScrollBottom = false;
	/** true可以滑动，false禁止滑动 */
	private boolean isScroll = true;
	private float mWebViewContentHeight = 0.0F;

	public ArticleDetailWebView(Context context, int cacheMode) {
		super(context);
		init(context, cacheMode);
	}

	public ArticleDetailWebView(Context context) {
		super(context);
		init(context, WebSettings.LOAD_NO_CACHE);
	}

	public ArticleDetailWebView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context, WebSettings.LOAD_NO_CACHE);
	}

	public ArticleDetailWebView(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
		init(context, WebSettings.LOAD_NO_CACHE);
	}

	@SuppressLint("NewApi")
	private void init(Context context, int cacheMode) {
		mContext = context;

		this.setScrollBarStyle(0);
		this.setHorizontalScrollBarEnabled(false);
		this.getSettings().setLoadsImagesAutomatically(true);
		this.getSettings().setJavaScriptEnabled(true);
		this.getSettings().setCacheMode(cacheMode);
		this.getSettings().setUseWideViewPort(true);
		this.getSettings().setLoadWithOverviewMode(true);
		this.setWebChromeClient(new WebChromeClient());
		this.setBackgroundColor(0);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
			this.setLayerType(WebView.LAYER_TYPE_SOFTWARE, null);

	}

	@Override
	protected void onScrollChanged(int l, int t, int oldl, int oldt) {

		super.onScrollChanged(l, t, oldl, oldt);
		float webViewContentHeight = this.getContentHeight() * this.getScale();
		// float webViewContentHeight = this.getContentHeight() * mNewScale;
		this.mWebViewContentHeight = webViewContentHeight;
		float webViewCurrentHeight = (this.getHeight() + this.getScrollY());

		if ((webViewContentHeight - webViewCurrentHeight) <= 2.0F) {
			isScrollBottom = true;
		} else {
			isScrollBottom = false;
		}

		if (null != mScrollInterface) {
			mScrollInterface.onScrollChanged(l, t, oldl, oldt);
		}

	}

	/**
	 * true可以滑动，false禁止滑动
	 * 
	 * @param isScroll
	 */
	public void setIsScroll(boolean isScroll) {
		this.isScroll = isScroll;
	}

	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {
		if (!isScroll) {
			return true;
		}
		return super.dispatchKeyEvent(event);
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {

		if (!isScroll) {
			return true;
		}
		return super.onTouchEvent(ev);
	}

	public void setScrollBottom(boolean isScrollBottom) {
		this.isScrollBottom = isScrollBottom;
	}

	public boolean isScrollBottom() {
		return isScrollBottom;
	}

	public void setOnScrollChangeListener(ScrollInterface scrollInterface) {
		this.mScrollInterface = scrollInterface;
	}



}
