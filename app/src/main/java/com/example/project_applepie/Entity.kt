package com.example.project_applepie

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

// 임시 작업

@Entity
data class Entity(
    var title: String,
    var date: String,
    var time: String
){
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}
