package com.example.rxjava4.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.rxjava4.Constants
import java.io.Serializable
import java.util.*


/**
 * Created by wisnu on 8/7/18
 */
@Entity(
//    tableName = Constants.Table.NOTE
    tableName = "fNote"
)
class NoteEntity(
    @PrimaryKey
    val id: Int = 0,
    val content: String = "",
    var created: Date = Date()

): Serializable