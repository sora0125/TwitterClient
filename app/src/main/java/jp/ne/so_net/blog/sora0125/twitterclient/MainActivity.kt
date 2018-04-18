package jp.ne.so_net.blog.sora0125.twitterclient

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.twitter.sdk.android.core.TwitterCore

class MainActivity : AppCompatActivity() {
    /**
     * Method Name：onCreate
     * summary    : 初期処理を実行。セッションをチェックし、次画面に遷移させる。
     **/
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // セッションを取得し、存在しなければログイン画面へ遷移
        if (TwitterCore.getInstance().sessionManager.activeSession == null) {
            val intent = Intent(this@MainActivity, LoginActivity::class.java)
            startActivity(intent)
        } else {
            val toast: Toast = Toast.makeText(this, COMN_TOAST_SES_YES_MSG, Toast.LENGTH_LONG)
            toast.show()

            // タイムライン画面へ遷移
            val intent = Intent(this@MainActivity, TimelineActivity::class.java)
            startActivity(intent)
        }
    }
}
