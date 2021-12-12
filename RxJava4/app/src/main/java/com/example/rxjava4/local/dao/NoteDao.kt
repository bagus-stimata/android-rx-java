package com.example.rxjava4.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.rxjava4.Constants
import com.example.rxjava4.local.entity.NoteEntity


/**
 * Created by wisnu on 8/7/18
 */
@Dao
interface NoteDao {

//    @Query("SELECT * FROM ${Constants.Table.NOTE}")
    @Query("SELECT * FROM fNote")
    fun loadAllNotesLiveData(): LiveData<List<NoteEntity>>

    @Insert
    fun insertNote(note: NoteEntity)

//    @Query("DELETE FROM ${Constants.Table.NOTE} WHERE id=:noteId")
    @Query("DELETE FROM fnote WHERE id=:noteId")
    fun deleteNote(noteId: Int)

    @Query("DELETE FROM fnote ")
    fun deleteAllNote()

}