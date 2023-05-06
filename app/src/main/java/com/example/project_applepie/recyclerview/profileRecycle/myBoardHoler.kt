package com.example.project_applepie.recyclerview.profileRecycle

import android.provider.ContactsContract.CommonDataKinds.Im
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.project_applepie.R

class myBoardHoler(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val boardImg = itemView.findViewById<ImageView>(R.id.lmb_img)
    val boardName = itemView.findViewById<TextView>(R.id.lmb_name)
    val boardDelte = itemView.findViewById<ImageView>(R.id.lmb_delete)
    val boardModify = itemView.findViewById<ImageView>(R.id.lmb_modi)
}