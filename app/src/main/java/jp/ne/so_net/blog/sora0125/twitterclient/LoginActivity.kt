package jp.ne.so_net.blog.sora0125.twitterclient

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.twitter.sdk.android.core.*
import com.twitter.sdk.android.core.identity.TwitterLoginButton

class LoginActivity : AppCompatActivity() {
    /**
     * Method Name : onCreate
     * summary     : Twitter認証ボタンの処理を行う
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // セッションを取得し、存在していればタイムライン画面へ遷移
        if (TwitterCore.getInstance().sessionManager.activeSession != null) {
            val toast: Toast = Toast.makeText(this, COMN_TOAST_SES_YES_MSG, Toast.LENGTH_LONG)
            toast.show()

            // タイムライン画面へ遷移
            val intent = Intent(this@LoginActivity, TimelineActivity::class.java)
            startActivity(intent)
        }

        setContentView(R.layout.activity_login)

        // Twitter認証ボタン処理
        val loginButton = findViewById<TwitterLoginButton>(R.id.login_button)
        loginButton.callback = object : Callback<TwitterSession>() {
            override fun success(result: Result<TwitterSession>?) {
                val toast: Toast = Toast.makeText(this@LoginActivity, COMN_TOAST_LOGIN_SUCESS_MSG, Toast.LENGTH_LONG)
                toast.show()
            }

            override fun failure(exception: TwitterException?) {
                val toast: Toast = Toast.makeText(this@LoginActivity, COMN_TOAST_LOGIN_FAIL_MSG, Toast.LENGTH_LONG)
                toast.show()
            }
        }
    }

    /**
     * Method Name : onActivityResult
     * summary     : ログインボタンを表示する
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        val loginButton = findViewById<TwitterLoginButton>(R.id.login_button)
        loginButton.onActivityResult(requestCode, resultCode, data)
    }
}
