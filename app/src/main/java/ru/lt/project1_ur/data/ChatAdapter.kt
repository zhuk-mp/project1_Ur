package ru.lt.project1_ur.data

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import jp.wasabeef.picasso.transformations.CropCircleTransformation
import ru.lt.project1_ur.R
import ru.lt.project1_ur.state.ProjectViewState

class ChatAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val messages = mutableListOf<ProjectViewState.Message>()

    companion object {
        const val VIEW_TYPE_USER = 1
        const val VIEW_TYPE_BOT = 2
    }

    fun setMessages(newMessages: List<ProjectViewState.Message>) {
        val size = this.messages.size
        messages.clear()
        messages.addAll(newMessages)
        notifyItemInserted(size)
    }

    override fun getItemViewType(position: Int): Int {
        return if (messages[position].isUser) VIEW_TYPE_USER else VIEW_TYPE_BOT
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_USER) {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_message_bot, parent, false)
            UserMessageViewHolder(view)
        } else {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_message_user, parent, false)
            BotMessageViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val message = messages[position]
        if (holder is UserMessageViewHolder) {
            holder.bind(message)
        } else if (holder is BotMessageViewHolder) {
            holder.bind(message)
        }
    }

    override fun getItemCount(): Int = messages.size

    class UserMessageViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(message: ProjectViewState.Message) {
            itemView.findViewById<TextView>(R.id.textView_message).apply {
                text = message.text
            }
        }
    }

    class BotMessageViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(message: ProjectViewState.Message) {
            itemView.findViewById<TextView>(R.id.textView_message).apply {
                text = message.text
            }
            val imageView: ImageView = itemView.findViewById(R.id.imageView_avatar)
            val imageUrl = message.avatar

            Picasso.get()
                .load(imageUrl)
                .resize(200, 200)
                .centerCrop()
                .transform(CropCircleTransformation())
                .into(imageView)
        }
    }
}
