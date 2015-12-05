# ViewGroupDemo
新闻客户端详情页，上面WebView新闻内容，下面ListView评论，可以上下滑动，类似今日头条，搜狐新闻等

#####介绍
上面展示文章内容用WebView下面展示这个文章的评论用ListView，可以上下滑动来切换文章内容和评论，程序已经发布，经过测试拿来即用

#####扩展
只要实现了IBaseArticleLayout和IBaseCommentLayout接口将这两个View放到ArticleDetailsViewGroup容器中可以自定义其他的上下切换的控件
