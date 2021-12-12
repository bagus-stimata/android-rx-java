package com.example.rxjava4.presentation

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.rxjava4.local.repository.NoteRepository
import com.example.rxjava4.map
import com.example.rxjava4.model.Note
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Inject
import javax.inject.Singleton

class NoteViewModel @ViewModelInject constructor(private val noteRepository: NoteRepository) : ViewModel() {
    private lateinit var notesResult: LiveData<List<Note>>
    init {
        subscribeNoteResult()
    }

    fun saveNote(content: String) {
        noteRepository.saveNote(content)
    }


    fun listenNotesResult(): LiveData<List<Note>> {
        return notesResult
    }

    private fun subscribeNoteResult() {
        notesResult = noteRepository.loadNotesLiveData().map { data ->
            data.reversed().map { Note(it.id, it.content) }
        }
    }


    fun deleteAllNote() {
        noteRepository.deleteAllNote()
    }



}

class NoteRepositoryDummy{
}

@InstallIn(ApplicationComponent::class)
@Module
class MyModuleSingleton{

    @Singleton
    @Provides
    fun provideSomeStringMengisiSecaraOtomatis(): String{
        return "###AND Provide Some String BOS gini"
    }
    @Singleton
    @Provides
    fun provideRepositoryDummy(): NoteRepositoryDummy{
        return NoteRepositoryDummy()
    }
}
