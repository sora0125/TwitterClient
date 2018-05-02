package jp.ne.so_net.blog.sora0125.twitterclient

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.ListView
import android.widget.Toast
import com.twitter.sdk.android.core.*
import com.twitter.sdk.android.core.models.Tweet
import com.twitter.sdk.android.core.services.StatusesService

import kotlinx.android.synthetic.main.activity_timeline.*
import kotlinx.android.synthetic.main.content_timeline.*
import retrofit2.Call

class TimelineActivity : AppCompatActivity() {
    private lateinit var adapter: TweetAdapter
    private  var tweetList: MutableList<Tweet> = mutableListOf()
    private lateinit var mSwipeRefreshLayout: SwipeRefreshLayout
    var sinceId: Long? = null
    var maxId: Long? = null
    var updateCnt: Int = 1

    /**
     * Method Name：onCreate
     * summary    : アダプターをリストビューにセットし、タイムラインを表示する
     **/
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_timeline)
        setSupportActionBar(toolbar)

        // ボタンタップ時の処理
        fab.setOnClickListener { view ->
            getHomeTimeline()
            Snackbar.make(view, "Update…", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }

        // SwipeRefreshLayoutの設定
        mSwipeRefreshLayout = findViewById(R.id.refresh)
        // 下方向へスワイプした時の処理
        refresh.setOnRefreshListener {
            getFutureHomeTimeline()
            mSwipeRefreshLayout.isRefreshing = false
        }

        // タイムライン取得処理
        val listView = findViewById<ListView>(R.id.my_list_view)
        adapter = TweetAdapter(this, tweetList)
        listView.adapter = adapter
        getHomeTimeline()

    }

    /**
     * Method Name：getHomeTimeline
     * summary    : 現在・過去のタイムラインを取得する
     **/
    private fun getHomeTimeline() {

        val twitterApiClient: TwitterApiClient = TwitterCore.getInstance().apiClient
        val statusesService: StatusesService = twitterApiClient.statusesService
        // 重複ツイート対策で＋１する
        val count = ACT_TIMELINE_GET_COUNT + 1

        val call: Call<MutableList<Tweet>> =
                statusesService.homeTimeline(count, null, maxId, false, false, false, true)
        call.enqueue(object: Callback<MutableList<Tweet>>() {
            override fun success(result: Result<MutableList<Tweet>>?) {
                // 重複してるツイートを削除
                val mutableDataList: MutableList<Tweet>? = result?.data?.toMutableList()
                mutableDataList?.removeAt(0)
                // ListViewのListに取得したツイートのリストを追加
                tweetList.addAll(mutableDataList as Iterable<Tweet>)
                // 現在のツイート数
                val currentTweetCnt = (count - 1) * updateCnt - 1
                // 最も過去のツイートIDを取得
                maxId = adapter.getItemId(currentTweetCnt)
                // 更新された回数
                updateCnt += 1
                // ListViewの表示を更新
                adapter.notifyDataSetChanged()
                val toast: Toast = Toast.makeText(this@TimelineActivity, COMN_TOAST_TIMELINE_SUCESS_MSG, Toast.LENGTH_LONG)
                toast.show()
            }
            override fun failure(exception: TwitterException?) {
                val toast: Toast = Toast.makeText(this@TimelineActivity, COMN_TOAST_TIMELINE_FAIL_MSG, Toast.LENGTH_LONG)
                toast.show()
            }
        })
    }

    /**
     * Method Name：getFutureHomeTimeline
     * summary    : 最新のタイムラインを取得する
     **/
    private fun getFutureHomeTimeline() {

        val twitterApiClient: TwitterApiClient = TwitterCore.getInstance().apiClient
        val statusesService: StatusesService = twitterApiClient.statusesService

        val call: Call<MutableList<Tweet>> =
                statusesService.homeTimeline(ACT_TIMELINE_GET_COUNT, sinceId, null, false, false, false, true)
        call.enqueue(object: Callback<MutableList<Tweet>>() {
            override fun success(result: Result<MutableList<Tweet>>?) {
                tweetList.addAll(0,result?.data as Collection<Tweet>)
                // 最新のツイートIDを取得
                sinceId = adapter.getItemId(0)
                // ListViewの表示を更新
                adapter.notifyDataSetChanged()
                val toast: Toast = Toast.makeText(this@TimelineActivity, COMN_TOAST_TIMELINE_SUCESS_MSG, Toast.LENGTH_LONG)
                toast.show()
            }
            override fun failure(exception: TwitterException?) {
                Log.d("tag:","debug",exception)
                val toast: Toast = Toast.makeText(this@TimelineActivity, COMN_TOAST_TIMELINE_FAIL_MSG, Toast.LENGTH_LONG)
                toast.show()
            }
        })
    }
}
