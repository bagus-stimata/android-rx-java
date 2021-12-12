package com.example.rxjava4.presentation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.rxjava4.databinding.FragmentCreateNoteBinding
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

@AndroidEntryPoint
class CreateNoteFragment : Fragment() {

    lateinit var binding: FragmentCreateNoteBinding
    // implementation "androidx.navigation:navigation-fragment-ktx:$nav_version"
    private val noteViewModel: NoteViewModel by viewModels()

//    lateinit var noteViewModel: NoteViewModel

    private val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_create_note, container, false)
        binding = FragmentCreateNoteBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initSaveButton()
        initDeleteButton()
    }

    companion object {
        fun newInstance() = CreateNoteFragment()
    }


    private fun initSaveButton() {

        binding.createNoteButton.setOnClickListener { view ->

            val disposable = Observable.just(binding.noteTextField.text.toString())
                .observeOn(Schedulers.io())
                .map { binding.noteTextField.text.toString() }
                .doOnNext {
//                    println("==== START TO SAVE: doOnNext: ${it}")
                    noteViewModel.saveNote(it)
//                    Log.d("result", "Saved Bos")
                }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        binding.noteTextField.text.clear()
                    }, {}, {}
                )
            compositeDisposable.add(disposable)

        }
    }


    private fun initDeleteButton() {

        binding.deleteNoteButton.setOnClickListener { view ->

            val disposable = Observable.just(true)
                .observeOn(Schedulers.io())
                .map { it }
                .doOnNext {
//                    println("==== START TO SAVE: doOnNext: ${it}")
                    noteViewModel.deleteAllNote()
//                    Log.d("result", "Saved Bos")
                }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                       Log.d("result", "#result Next hapus semua")
                    },
                    {
                        Log.d("result", "#result Error hapus semua")
                    },
                    {
                        Log.d("result", "#result Commplete hapus semua")

                    }
                )
            compositeDisposable.add(disposable)

        }
    }

}