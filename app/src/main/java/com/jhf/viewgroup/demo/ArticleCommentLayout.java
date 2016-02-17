package com.jhf.viewgroup.demo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.groupviewrolling.ArticleDetailsViewGroup;
import com.groupviewrolling.IBaseCommentLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @Description: 评论列表
 * @version V1.0.0
 */
public class ArticleCommentLayout extends LinearLayout implements
		View.OnClickListener, IBaseCommentLayout {

	private Context mContext;

	protected CommentListView mPullToRefreshListView;

	private ArticleAdapter mArticleAdapter;

	private List<String> mArticleComments = new ArrayList<String>();

	private ArticleDetailsViewGroup articleDetailsViewGroup;

	public ArticleCommentLayout(Context context, ArticleDetailsViewGroup articleDetailsViewGroup) {
		super(context);
		mContext = context;
		this.articleDetailsViewGroup = articleDetailsViewGroup;
		afterViews();
	}

	public void afterViews() {
		LayoutInflater.from(mContext).inflate(
				R.layout.fragment_article_comment, this, true);

		for (int i = 0; i < 60; i++){
			mArticleComments.add(i + "");
		}
		
		mPullToRefreshListView = (CommentListView) findViewById(R.id.articleListView);
		mPullToRefreshListView.setOverScrollMode(View.OVER_SCROLL_NEVER);
		mArticleAdapter = new ArticleAdapter();
		mPullToRefreshListView.setAdapter(mArticleAdapter);
		mPullToRefreshListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				if(articleDetailsViewGroup.isScrollerFinished()){
					return ;
				}
				Toast.makeText(mContext, "position : " + position, Toast.LENGTH_SHORT).show();
			}
		});
	}

	@Override
	public boolean isFirstViewTop() {
		return mPullToRefreshListView
				.isFirstViewTop();
	}

	@Override
	public void setIsScroll(boolean isScroll) {
		mPullToRefreshListView
				.setIsScroll(isScroll);
	}

	@Override
	public void onClick(View v) {

	}

	class ArticleAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			return mArticleComments.size();
		}

		@Override
		public Object getItem(int position) {
			return mArticleComments.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			TextView textView = null;

			if(null == convertView){
				convertView = LayoutInflater.from(mContext).inflate(R.layout.adapter_article, null);

			}else {

			}
			textView = (TextView) convertView.findViewById(R.id.textView);
			textView.setText(mArticleComments.get(position));

			return convertView;
		}
	}

}
