package com.example.project_applepie.recyclerview.profileRecycle

import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.project_applepie.R

class MyTeamHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
    val teamImg = itemView.findViewById<ImageView>(R.id.lmt_img)
    val teamTitle = itemView.findViewById<TextView>(R.id.lmt_name)
    val teamDelete = itemView.findViewById<ImageButton>(R.id.lmt_delete)
    val teamDone = itemView.findViewById<ImageButton>(R.id.lmt_done)
}