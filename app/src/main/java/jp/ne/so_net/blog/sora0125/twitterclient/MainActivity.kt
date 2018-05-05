package jp.ne.so_net.blog.sora0125.twitterclient

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.Toast
import com.twitter.sdk.android.core.*
import com.twitter.sdk.android.core.models.Tweet
import com.twitter.sdk.android.core.services.StatusesService
import com.twitter.sdk.android.tweetui.CompactTweetView
import com.twitter.sdk.android.tweetui.TweetView
import retrofit2.Call

class MainActivity : AppCompatActivity() {
    /**
     * Method Name : onCreate
     * summary     : ツイートを表示する
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_embedded_tweet)

        // インテントからツイートIDを取得
        val myLayout: LinearLayout = findViewById(R.id.my_tweet_layout)
        val i: Intent = this.intent
        val tweetId: Long = i.getLongExtra("tweetId", 0)

        val twitterApiClient: TwitterApiClient = TwitterCore.getInstance().apiClient
        val statusesService: StatusesService = twitterApiClient.statusesService

        // ツイートを取得
        val call: Call<Tweet> = statusesService.show(tweetId, null, null, null)
        call.enqueue(object: Callback<Tweet>() {
            override fun success(result: Result<Tweet>?) {
                // CompactTweetViewを追加
                myLayout.addView(CompactTweetView(this@MainActivity, result?.data, R.style.tw__TweetDarkWithActionsStyle))
                val toast: Toast = Toast.makeText(this@MainActivity, COMN_TOAST_TWEET_GET_SUCESS_MSG, Toast.LENGTH_LONG)
                toast.show()
            }
            override fun failure(exception: TwitterException?) {
                val toast: Toast = Toast.makeText(this@MainActivity, COMN_TOAST_TWEET_GET_FAIL_MSG, Toast.LENGTH_LONG)
                toast.show()
            }
        })

    }
}
