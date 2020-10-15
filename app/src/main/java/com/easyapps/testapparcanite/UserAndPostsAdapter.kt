package com.easyapps.testapparcanite

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.Toast
import com.easyapps.testapparcanite.model.Post
import com.easyapps.testapparcanite.model.User
import kotlinx.android.synthetic.main.post_item.view.*
import kotlinx.android.synthetic.main.user_item.view.*

class UserAndPostsAdapter(private val context: Context) : BaseExpandableListAdapter() {
    private var userList: List<User> = listOf()
    private var postMap: Map<Long, List<Post>> = mapOf()

    override fun getGroup(groupPosition: Int): Any {
        return userList[groupPosition]
    }

    override fun getGroupCount(): Int {
        return userList.size
    }

    override fun getGroupId(groupPosition: Int): Long {
        return userList[groupPosition].id
    }

    override fun getGroupView(
        groupPosition: Int,
        isExpanded: Boolean,
        convertView: View?,
        parent: ViewGroup?
    ): View {
        val view: View =
            convertView ?: LayoutInflater.from(context).inflate(R.layout.user_item, null)

        val user = userList[groupPosition]

        view.tvName.text = user.name
        view.tvEmail.text = user.email
        view.tvPhone.text = user.phone

        view.btnLink.setOnClickListener {
            openLink(user.website)
        }

        view.btnSendEmail.setOnClickListener {
            sendEmail(user.email)
        }

        return view
    }


    override fun getChild(groupPosition: Int, childPosition: Int): Any {
        val userId = userList[groupPosition].id
        return postMap[userId]!![childPosition]
    }

    override fun getChildrenCount(groupPosition: Int): Int {
        val userId = userList[groupPosition].id

        return postMap.getOrElse(userId) { return 0 }.size

    }

    override fun getChildId(groupPosition: Int, childPosition: Int): Long {
        val userId = userList[groupPosition].id
        return postMap[userId]!![childPosition].id
    }

    override fun getChildView(
        groupPosition: Int,
        childPosition: Int,
        isLastChild: Boolean,
        convertView: View?,
        parent: ViewGroup?
    ): View {
        val view: View =
            convertView ?: LayoutInflater.from(context).inflate(R.layout.post_item, null)

        val post = getChild(groupPosition, childPosition) as Post

        view.tvTitle.text = post.title
        view.tvBody.text = post.body

        return view
    }


    override fun isChildSelectable(groupPosition: Int, childPosition: Int): Boolean {
        return true
    }

    override fun hasStableIds(): Boolean {
        return true
    }

    fun setDataAndUpdate(userList: List<User>, postMap: Map<Long, List<Post>>) {
        this.userList = userList
        this.postMap = postMap
        notifyDataSetChanged()
    }

    private fun openLink(url: String) {
        val intent =
            Intent(Intent.ACTION_VIEW, Uri.parse("https://$url"))

        context.startActivity(intent)
    }

    private fun sendEmail(email: String) {
        val subject =
            "${context.getString(R.string.email_from)} ${context.getString(R.string.app_name)}"
        val message = context.getString(R.string.hello)

        val intent = Intent(Intent.ACTION_SEND).apply {
            data = Uri.parse("mailto:")
            type = "text/plain"

            putExtra(Intent.EXTRA_EMAIL, arrayOf(email))
            putExtra(Intent.EXTRA_SUBJECT, subject)
            putExtra(Intent.EXTRA_TEXT, message)
        }

        try {
            context.startActivity(
                Intent.createChooser(
                    intent,
                    context.getString(R.string.choose_email_client)
                )
            )
        } catch (e: Exception) {
            Toast.makeText(
                context,
                context.getString(R.string.email_client_not_selected),
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}