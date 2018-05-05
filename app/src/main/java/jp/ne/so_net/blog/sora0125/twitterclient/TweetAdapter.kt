package jp.ne.so_net.blog.sora0125.twitterclient

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import com.twitter.sdk.android.core.models.Tweet

class TweetAdapter(private val context: Context, private val tweetList: List<Tweet>) : BaseAdapter() {
    private val layoutInflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    /**
     * Method Name : getCount
     * summary     : ツイートの取得数を取得
     */
    override fun getCount(): Int {
        return tweetList.size
    }

    /**
     * Method Name : getItem
     * summary     : アイテムを取得
     */
    override fun getItem(position: Int): Any {
        return tweetList[position]
    }

    /**
     * Method Name : getItemId
     * summary     : IDを取得
     */
    override fun getItemId(position: Int): Long {
        return tweetList[position].getId()
    }

    /**
     * Class Name : ViewHolder
     * summary    : データクラス
     */
    class ViewHolder {
        var profileIconImgView: ImageView? = null
        var screenNameTextView: TextView? = null
        var tweetTextTextView: TextView? = null
    }

    /**
     * Method Name : getView
     * summary     : ユーザ名とツイート本文をセットする。
     * @param     : position
     * @param     : convertView
     * @param     : parent
     * @return    : convertView
     */
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        // ViewHolder Pattern
        var v = convertView
        var holder = ViewHolder()

        if (v == null) {
            v = layoutInflater.inflate(R.layout.tweet_row, parent, false)
            holder.profileIconImgView = v?.findViewById<ImageView?>(R.id.profile_icon)
            holder.screenNameTextView = v?.findViewById<TextView?>(R.id.screen_name)
            holder.tweetTextTextView = v?.findViewById<TextView?>(R.id.tweet_text)
            v?.tag = holder
        }else {
            holder = v.tag as ViewHolder
        }

        // プロフィールアイコンをセット
        Picasso.with(context).load(tweetList[position].user.profileImageUrl).into(holder.profileIconImgView)
        // アカウント名とスクリーン名をセット
        holder.screenNameTextView?.text = (tweetList[position].user.name + " (@" + tweetList[position].user.screenName + ")")
        // ツイート本文をセット
        holder.tweetTextTextView?.text = tweetList[position].text

        return v
    }

}