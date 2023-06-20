package com.johndev.momoplantsparent.adapter

import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.johndev.momoplantsparent.R
import com.johndev.momoplantsparent.common.dataAccess.OnChatListener
import com.johndev.momoplantsparent.common.entities.MessageEntity
import com.johndev.momoplantsparent.databinding.ItemChatBinding
import java.text.DateFormat
import java.util.Date

class ChatAdapter(private val messageList: MutableList<MessageEntity>, private val listener: OnChatListener) :
RecyclerView.Adapter<ChatAdapter.ViewHolder>(){

    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val view = LayoutInflater.from(context).inflate(R.layout.item_chat, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val message = messageList[position]
        holder.apply {
            setListener(message)
            with(binding) {
                var gravity = Gravity.END
                var backgroundMessage = ContextCompat.getDrawable(context, R.drawable.background_chat_support)
                var textColor = ContextCompat.getColor(context, R.color.md_theme_light_onPrimary)
                val marginHorizontal = context.resources.getDimensionPixelSize(R.dimen.chat_margin_horizontal)
                val params = tvMessage.layoutParams as ViewGroup.MarginLayoutParams
                params.apply {
                    marginStart = marginHorizontal
                    marginEnd = 0
                    topMargin = 0
                }
                if (position > 0 && message.isSentByMe() != messageList[position - 1].isSentByMe()) {
                    params.topMargin = context.resources.getDimensionPixelSize(R.dimen.common_padding_min)
                }
                if (!message.isSentByMe()) {
                    gravity = Gravity.START
                    backgroundMessage = ContextCompat.getDrawable(context, R.drawable.background_chat_client)
                    textColor = ContextCompat.getColor(context, R.color.black)
                    params.apply {
                        marginStart = 0
                        marginEnd = marginHorizontal
                    }
                }
                root.gravity = gravity
                tvMessage.apply {
                    layoutParams = params
                    background = backgroundMessage
                    setTextColor(textColor)
                    text = message.message
                }
                tvTime.text = DateFormat.getTimeInstance().format(Date(message.time.toLong()))
            }
        }
    }

    override fun getItemCount(): Int = messageList.size

    fun add(messageEntity: MessageEntity) {
        if (!messageList.contains(messageEntity)) {
            messageList.add(messageEntity)
            notifyItemInserted(messageList.size - 1)
        }
    }

    fun update(messageEntity: MessageEntity) {
        val index = messageList.indexOf(messageEntity)
        if (index != -1) {
            messageList[index] = messageEntity
            notifyItemChanged(index)
        }
    }

    fun delete(messageEntity: MessageEntity) {
        val index = messageList.indexOf(messageEntity)
        if (index != -1) {
            messageList.removeAt(index)
            notifyItemRemoved(index)
        }
    }

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val binding = ItemChatBinding.bind(view)

        fun setListener(messageEntity: MessageEntity) {
            binding.tvMessage.setOnLongClickListener {
                listener.deleteMessage(messageEntity)
                true
            }
        }
    }

}