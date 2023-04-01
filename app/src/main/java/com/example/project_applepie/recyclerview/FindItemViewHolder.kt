package com.example.project_applepie.recyclerview

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.project_applepie.R

class FindItemViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView){
    val userImageView = itemView.findViewById<ImageView>(R.id.chatting_userImg)
    val chatSender = itemView.findViewById<TextView>(R.id.chatting_sender)
    val chatContent = itemView.findViewById<TextView>(R.id.chatting_content)
}