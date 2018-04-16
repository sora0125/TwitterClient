package jp.ne.so_net.blog.sora0125.twitterclient

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.twitter.sdk.android.core.models.Tweet

class TweetAdapter(context: Context, tweetList: List<Tweet>) : BaseAdapter() {

    private val context: Context = context
    private val layoutInflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    private val tweetList: List<Tweet> = tweetList

    /**
     * Method Name：getCount
     * summary    : サイズを取得
     **/
    override fun getCount(): Int {
        return tweetList.size
    }

    /**
     * Method Name：getItem
     * summary    : アイテムを取得
     **/
    override fun getItem(position: Int): Any {
        return tweetList.get(position)
    }

    /**
     * Method Name：getItemId
     * summary    : IDを取得
     **/
    override fun getItemId(position: Int): Long {
        return tweetList.get(position).getId()
    }

    /**
     * Method Name：getView
     * summary    : ユーザ名とツイート本文をセットする。
     * @param    : position,convertView,parent
     * @return   : convertView
     **/
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        val convertView: View? = layoutInflater.inflate(R.layout.tweet_row, parent, false)

        val screenNameTextView: TextView? = convertView?.findViewById<TextView?>(R.id.screen_name)
        val TweetTextTextView: TextView? = convertView?.findViewById<TextView?>(R.id.tweet_text)

        screenNameTextView?.text = tweetList.get(position).user.name
        TweetTextTextView?.text = tweetList.get(position).text

        return convertView
    }

}