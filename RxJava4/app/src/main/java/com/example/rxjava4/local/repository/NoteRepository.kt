package com.example.rxjava4.local.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.rxjava4.local.dao.NoteDao
import com.example.rxjava4.local.entity.NoteEntity
import com.example.rxjava4.model.Note
import java.util.*
import javax.inject.Inject


/**
 * Created by wisnu on 8/7/18
 */
class NoteRepository @Inject constructor(private val noteDao: NoteDao) {

    fun saveNote(content: String) {
        val noteDb = NoteEntity(id = generateUuid(), content = content)
//        println("Repository saveToNote: ${noteDb.id} and ${noteDb.content} Start")
        noteDao.insertNote(noteDb)
//        println("Repository saveToNote: ${noteDb.id} and ${noteDb.content} End")
//        println("Oke lho sampai sini")
    }

    fun loadNotesLiveData(): LiveData<List<NoteEntity>> {
        return noteDao.loadAllNotesLiveData()
    }

//    private fun generateUuid(): Int = UUID.randomUUID().toString().toInt()
    private fun generateUuid(): Int = Random().nextInt()

    fun deleteNote(note: Note) {
        noteDao.deleteNote(note.id)
    }
    fun deleteAllNote() {
        noteDao.deleteAllNote()
    }

}