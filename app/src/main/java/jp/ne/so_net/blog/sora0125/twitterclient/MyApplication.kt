package jp.ne.so_net.blog.sora0125.twitterclient

import android.app.Application
import android.util.Log
import com.twitter.sdk.android.core.DefaultLogger
import com.twitter.sdk.android.core.Twitter
import com.twitter.sdk.android.core.TwitterAuthConfig
import com.twitter.sdk.android.core.TwitterConfig

class MyApplication : Application(){
    // 開発者認証キー
    private val CONSUMER_KEY = APP_MYAPP_CONSUMER_KEY
    private val CONSUMER_SECRET = APP_MYAPP_CONSUMER_SECRET

    /**
     * Method Name : onCreate
     * summary     : Twitter Kitの初期化を行う
     */
    override fun onCreate() {
        super.onCreate()

        // Twitter Kitの初期化（カスタム）
        val config = TwitterConfig.Builder(this)
                .logger(DefaultLogger(Log.DEBUG))
                .twitterAuthConfig(TwitterAuthConfig(CONSUMER_KEY, CONSUMER_SECRET))
                .debug(true)
                .build()
        Twitter.initialize(config)
    }
}