package jp.ne.so_net.blog.sora0125.twitterclient

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.twitter.sdk.android.core.Callback
import com.twitter.sdk.android.core.Result
import com.twitter.sdk.android.core.TwitterException
import com.twitter.sdk.android.core.TwitterSession
import com.twitter.sdk.android.core.identity.TwitterLoginButton

class LoginActivity : AppCompatActivity() {
    // 定数クラスのインスタンス生成
    private val const = Constant()

    /**
     * Method Name：onCreate
     * summary    : Twitter認証ボタンの処理を行う
     **/
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val loginButton = findViewById<TwitterLoginButton>(R.id.login_button)
        loginButton.callback = object : Callback<TwitterSession>() {
            override fun success(result: Result<TwitterSession>?) {
                val toast: Toast = Toast.makeText(this@LoginActivity, const.COMN_TOAST_LOGIN_SUCESS_MSG, Toast.LENGTH_LONG)
                toast.show()
            }

            override fun failure(exception: TwitterException?) {
                val toast: Toast = Toast.makeText(this@LoginActivity, const.COMN_TOAST_LOGIN_FAIL_MSG, Toast.LENGTH_LONG)
                toast.show()
            }
        }
    }

    /**
     * Method Name：onActivityResult
     * summary    : ログインボタンを表示する
     **/
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        val loginButton = findViewById<TwitterLoginButton>(R.id.login_button)
        loginButton.onActivityResult(requestCode, resultCode, data)
    }
}
