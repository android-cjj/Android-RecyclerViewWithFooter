#RecyclerViewWithFooter

 之所以会写这个库，是因为最近遇到朋友问推荐一个好的下拉刷新的库,然后我就给了我收集的[BeautifulRefreshLayout](https://github.com/android-cjj/BeautifulRefreshLayout)，但是，这时候又有个问题，明明这个库写的很好，就是因为没有加载更多，而另一个库设计的没前一个库好，只是功能比上个库多，所以选择了下一个。对于这种情况我是反对的，呵呵。
 
  举个例子吧，我觉得官方的SwipeRefreshLayout已经是个很好的控件了，就是没有上拉刷新，所以很多人没用，特别是初学android的朋友，因为我们需要有一个库可以解决一切问题，对于这种情况，我给的demo已经很好的解决。
 
  随意说说，为什么很多人不把上拉下拉融合在一起，至少我们的理解是他们是一体的，对，你这样想是没错。但是，我觉得这要看项目的具体情况。我之前写的[MaterialRefreshLayout](https://github.com/android-cjj/Android-MaterialRefreshLayout)就具备了，但是，上拉的时候需要用户自己拉，呵呵，我觉得很多余，另外，那个项目bug很多，慎用。呵呵... 所以，加载更多就是需要滑到底部自动加载，之前是  ListView.addFooterView(view) 的方法，常用作auto loading view ，但是RecyclerView 没有提供这个方法，没事，我写了 。
 
 说了那么多废话，我们还是说说这个项目的功能吧。顾名思义，其实它就是一个给RecyclerView加底部View的库


使用
==================
xml中的布局：
```xml
   <com.cjj.RecyclerViewWithFooter
            android:id="@+id/rv_load_more"
            android:layout_width="match_parent"
            android:layout_height="match_parent"/>
```
java中
```java
     mRecyclerViewWithFooter = (RecyclerViewWithFooter) this.findViewById(R.id.rv_load_more);
     mRecyclerViewWithFooter.setAdapter();
```
这样就可以了，是不是很简单，呵呵。

如果需要监听Rv滑到底部，
```java
 mRecyclerViewWithFooter.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                //加载数据
            }
        });
```
RecyclerViewWithFooter有三种类型，分别是：
```java
   /**
     * 表示现在是切换成 load 状态
     */
    public void setLoad() {
    }

    /**
     * 表示切换成没有更多数据状态
     *
     * @param end
     */
    public void setEnd(CharSequence end) {

    }

    /**
     * 表示切换成 无数据 为空状态
     *
     * @param empty
     * @param resId
     */
    public void setEmpty(CharSequence empty, @DrawableRes int resId) {
    
    }
```

底部FootView目前不设置setFootItem，默认为DefaultFootItem，内嵌入了MaterialFootItem，效果如下：

（1）这是默认的效果

![](https://github.com/android-cjj/Android-RecyclerViewWithFooter/blob/master/img/cjj2.jpg)

（2）这是Material风格的

![](https://github.com/android-cjj/Android-RecyclerViewWithFooter/blob/master/img/cjj1.jpg)

（3）你也可以自己定义

![](https://github.com/android-cjj/Android-RecyclerViewWithFooter/blob/master/img/cjj.jpg)

差不多就这些了，想了解更多自己看源码。还有项目肯定存在bug,选择权在于你，不要出了bug就找骂原作者，你没有资格，因为如果你不用，有个屁的bug,如果你用了出问题可以加
GitHub小伙伴交流群'' 477826523 
进群有要求，至少你Github有东西，别问为什么，原则问题，呵呵。我也是个菜鸟，也希望高手能帮我，呵呵







