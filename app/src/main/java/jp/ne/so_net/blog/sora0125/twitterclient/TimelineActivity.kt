package jp.ne.so_net.blog.sora0125.twitterclient

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.widget.ListView
import android.widget.Toast
import com.twitter.sdk.android.core.*
import com.twitter.sdk.android.core.models.Tweet
import com.twitter.sdk.android.core.services.StatusesService

import kotlinx.android.synthetic.main.activity_timeline.*
import retrofit2.Call

class TimelineActivity : AppCompatActivity() {
    private lateinit var adapter: TweetAdapter
    private  var tweetList: MutableList<Tweet> = mutableListOf()

    /**
     * Method Name：onCreate
     * summary    : アダプターをリストビューにセットし、タイムラインを表示する
     **/
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_timeline)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }

        val listView = findViewById<ListView>(R.id.my_list_view)
        adapter = TweetAdapter(this, tweetList)
        listView.adapter = adapter

        getHomeTimeline()

    }

    /**
     * Method Name：getHomeTimeline
     * summary    : タイムラインを取得する
     **/
    private fun getHomeTimeline() {

        val twitterApiClient: TwitterApiClient = TwitterCore.getInstance().apiClient
        val statusesService: StatusesService = twitterApiClient.statusesService

        val call: Call<MutableList<Tweet>> =
                statusesService.homeTimeline(ACT_TIMELINE_GET_COUNT, null, null, false, false, false, true)
        call.enqueue(object: Callback<MutableList<Tweet>>() {
            override fun success(result: Result<MutableList<Tweet>>?) {
                // ListViewのListに取得したツイートのリストを追加
                tweetList.addAll(result?.data as Iterable<Tweet>)
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
}
