package com.example.rxjava4.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rxjava4.databinding.FragmentListNoteBinding
import com.example.rxjava4.presentation.recyclerview.NoteAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MutableNoteFragment : Fragment() {

    lateinit var binding: FragmentListNoteBinding

    private val noteViewModel: NoteViewModel by viewModels()

    private val adapter: NoteAdapter by lazy {
        NoteAdapter { position -> doOnLongItemClickListener(position) }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

//        return inflater.inflate(R.layout.fragment_list_note, container, false)
        binding = FragmentListNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()
        initListenNotesResult()

//        binding.charactersRv.adapter = adapter
//        initListenNotesResult()


//        Log.d("result", "Hello Test " + noteViewModel.listenNotesResult().value?.size)
    }

    private fun initListenNotesResult() {
        noteViewModel.listenNotesResult().observe(this.viewLifecycleOwner, Observer {
            it?.let {
                adapter.updateData(it.toMutableList())
            }
        })
    }

    private fun initRecyclerView() {
        binding.charactersRv.layoutManager = LinearLayoutManager(activity)
        binding.charactersRv.adapter = adapter
    }

    
    companion object {
        fun newInstance() = ListNoteFragment()
    }

    private fun doOnLongItemClickListener(position: Int) {
        println("Oke ON long klik listener")
//        bottomSheetDialog?.show()
//
//        bottomSheetDialog?.delete_item_note_button?.setOnClickListener { view ->
//            val note = adapter.getDataAtPosition(position)
//
//            val disposable = Observable.just(true)
//                    .observeOn(Schedulers.io())
//                    .doOnNext { noteViewModel.deleteNote(note) }
//                    .observeOn(AndroidSchedulers.mainThread())
//                    .subscribe { bottomSheetDialog?.dismiss() }
//            compositeDisposable.add(disposable)
//        }
    }

}