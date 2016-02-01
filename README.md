RecyclerViewWithFooter
==============================



但是，这时候又有个问题，明明这个库写的很好，就是因为没有加载更多，而另一个库设计的没前一个库好，只是功能比上个库多，所以选择了下一个。对于这种情况我是反对的，呵呵。
举个例子吧，我觉得官方的SwipeRefreshLayout已经是个很好的控件了，就是没有上拉刷新，所以很多人没用，特别是初学android的朋友，因为我们需要有一个库可以解决一切问题，对于
这种情况，我给的demo已经很好的解决。
    随意说说，为什么很多人不把上拉下拉融合在一起，至少我们的理解是他们是一体的，对，你这样想是没错。但是，我觉得这要看项目的具体情况。我之前写的
[MaterialRefreshLayout](https://github.com/android-cjj/Android-MaterialRefreshLayout)就具备了，但是，上拉的时候需要用户自己拉，呵呵，我觉得很多余，另外，那个项目
bug很多，慎用。呵呵... 所以，加载更多就是需要滑到底部自动加载，之前是  ListView.addFooterView(view) 的方法，常用作auto loading view ，但是RecyclerView
没有提供这个方法，没事，我写了 ，呵呵。
    
