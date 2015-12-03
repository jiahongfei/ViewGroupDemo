package com.jhf.viewgroup.demo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

import com.groupviewrolling.IBaseArticleLayout;

/**
 * 
 * @Title: ArticleDetailsLayout.java
 * @Package hbyc.china.medical.view.modules.home.articleviewpager.fragment
 * @ClassName: ArticleDetailsLayout
 * @Description: 详情
 * @author jiahongfei jiahongfeinew@163.com
 * @date 2015-7-13 下午3:23:22
 * @version V1.0.0
 */
public class ArticleDetailsLayout extends LinearLayout implements IBaseArticleLayout {

	private Context mContext;
	private ArticleDetailWebView mArticleDetailWebView;
	private ViewGroup mArticleDetailsLayout;

	public ArticleDetailsLayout(Context context) {
		super(context);
		mContext = context;
		LayoutInflater.from(mContext).inflate(
				R.layout.fragment_details_article, this, true);

		mArticleDetailsLayout = (ViewGroup)findViewById(R.id.articleDetailsLayout);

		mArticleDetailWebView = new ArticleDetailWebView(mContext);
		mArticleDetailWebView.loadUrl("http://www.baidu.com/");
		mArticleDetailWebView.setWebViewClient(new WebViewClient(){
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				mArticleDetailWebView.loadUrl(url);
				return true;
			}
		});
		LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT);
		mArticleDetailWebView.setLayoutParams(layoutParams);
		mArticleDetailsLayout.removeAllViews();
		mArticleDetailsLayout.addView(mArticleDetailWebView);
	}

	@Override
	public void setIsScroll(boolean isScroll) {
		mArticleDetailWebView.setIsScroll(isScroll);
	}

	@Override
	public boolean isScrollBottom() {
		return mArticleDetailWebView.isScrollBottom();
	}

}
