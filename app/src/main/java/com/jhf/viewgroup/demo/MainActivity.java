package com.jhf.viewgroup.demo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.groupviewrolling.ArticleDetailsViewGroup;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArticleDetailsViewGroup articleDetailsViewGroup = (ArticleDetailsViewGroup)findViewById(R.id.articleDetailsViewGroup);

        ArticleDetailsLayout articleDetailsLayout = new ArticleDetailsLayout(this,articleDetailsViewGroup);
        ArticleCommentLayout articleCommentLayout = new ArticleCommentLayout(this,articleDetailsViewGroup);

        articleDetailsViewGroup.addArticleView(articleDetailsLayout);
        articleDetailsViewGroup.addCommentView(articleCommentLayout);
        articleDetailsViewGroup.requestLayout();
    }
}
