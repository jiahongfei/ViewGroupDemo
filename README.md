# ViewGroupDemo
新闻客户端详情页，上面WebView新闻内容，下面ListView评论，可以上下滑动，类似今日头条，搜狐新闻等

#####介绍
上面展示文章内容用WebView下面展示这个文章的评论用ListView，可以上下滑动来切换文章内容和评论，程序已经发布，经过测试拿来即用

#####扩展
只要实现了IBaseArticleLayout和IBaseCommentLayout接口将这两个View放到ArticleDetailsViewGroup容器中可以自定义其他的上下切换的控件

#####使用方法
MainActivity.java<br>
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //一下两个Layout分别实现了IBaseArticleLayout和IBaseCommentLayout接口
        ArticleDetailsLayout articleDetailsLayout = new ArticleDetailsLayout(this);
        ArticleCommentLayout articleCommentLayout = new ArticleCommentLayout(this);

        ArticleDetailsViewGroup articleDetailsViewGroup = (ArticleDetailsViewGroup)findViewById(R.id.articleDetailsViewGroup);

        articleDetailsViewGroup.addArticleView(articleDetailsLayout);
        articleDetailsViewGroup.addCommentView(articleCommentLayout);
        articleDetailsViewGroup.requestLayout();
    }
}

activity_main.xml<br>
<?xml version="1.0" encoding="utf-8"?>
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools" android:layout_width="match_parent"
    android:layout_height="match_parent" android:paddingLeft="@dimen/activity_horizontal_margin"
    android:paddingRight="@dimen/activity_horizontal_margin"
    android:paddingTop="@dimen/activity_vertical_margin"
    android:paddingBottom="@dimen/activity_vertical_margin" tools:context=".MainActivity">

    <com.groupviewrolling.ArticleDetailsViewGroup
        android:id="@+id/articleDetailsViewGroup"
        android:layout_width="fill_parent"
        android:layout_height="fill_parent"
        android:visibility="visible" >
    </com.groupviewrolling.ArticleDetailsViewGroup>
</RelativeLayout>
