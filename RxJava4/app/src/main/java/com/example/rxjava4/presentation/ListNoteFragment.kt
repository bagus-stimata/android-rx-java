package com.example.rxjava4.presentation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rxjava4.databinding.FragmentListNoteBinding
import com.example.rxjava4.presentation.recyclerview.CharactersAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListNoteFragment : Fragment(), CharactersAdapter.CharacterItemListener  {

    lateinit var binding: FragmentListNoteBinding

    private val noteViewModel: NoteViewModel by viewModels()
    private lateinit var adapter: CharactersAdapter


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
        setupRecyclerView()
        setupObservers()
    }

    companion object {
        fun newInstance() = ListNoteFragment()
    }

    private fun setupRecyclerView() {
        adapter = CharactersAdapter(this)
        binding.charactersRv.layoutManager = LinearLayoutManager(requireContext())
        binding.charactersRv.adapter = adapter
    }

    private fun setupObservers() {
//        noteViewModel.listenNotesResult().observe(viewLifecycleOwner, Observer {
//            when (it.status) {
//                Resource.Status.SUCCESS -> {
//                    binding.progressBar.visibility = View.GONE
//                    if (!it.data.isNullOrEmpty()) adapter.setItems(ArrayList(it.data))
//                }
//                Resource.Status.ERROR ->
//                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
//
//                Resource.Status.LOADING ->
//                    binding.progressBar.visibility = View.VISIBLE
//            }
//        })

        noteViewModel.listenNotesResult().observe(this.viewLifecycleOwner, Observer {
            it?.let {
                adapter.setItems(ArrayList(it))
            }
            Log.d("result", "#result Setup Opserver Dipanggil Aja")
        })

    }

    override fun onClickedCharacter(characterId: Int) {
       println("Oke bisa")
    }


}