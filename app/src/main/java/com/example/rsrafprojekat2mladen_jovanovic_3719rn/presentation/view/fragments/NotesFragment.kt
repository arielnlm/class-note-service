package com.example.rsrafprojekat2mladen_jovanovic_3719rn.presentation.view.fragments

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rsrafprojekat2mladen_jovanovic_3719rn.R
import com.example.rsrafprojekat2mladen_jovanovic_3719rn.data.models.Note
import com.example.rsrafprojekat2mladen_jovanovic_3719rn.databinding.FragmentNotesBinding
import com.example.rsrafprojekat2mladen_jovanovic_3719rn.presentation.contract.MainContract
import com.example.rsrafprojekat2mladen_jovanovic_3719rn.presentation.view.StatisticsActivity
import com.example.rsrafprojekat2mladen_jovanovic_3719rn.presentation.view.activities.AddNoteActivity
import com.example.rsrafprojekat2mladen_jovanovic_3719rn.presentation.view.recycler.adapter.NotesAdapter
import com.example.rsrafprojekat2mladen_jovanovic_3719rn.presentation.view.states.NotesState
import com.example.rsrafprojekat2mladen_jovanovic_3719rn.presentation.viewmodel.MainViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import timber.log.Timber
import java.io.Serializable
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [NotesFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class NotesFragment : Fragment(R.layout.fragment_notes) {

    // Koristimo by sharedViewModel jer sada view modele instanciramo kroz koin
    private val mainViewModel: MainContract.ViewModel by sharedViewModel<MainViewModel>()

    private var _binding: FragmentNotesBinding? = null

    private val binding get() = _binding!!

    private lateinit var adapter: NotesAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNotesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init(){
        initUI()
        initObservers()
    }

    private fun initUI(){
        initRecycler()
        initListeners()
    }
    private fun initRecycler(){
        binding.notesListRv.layoutManager = LinearLayoutManager(context)
        adapter = NotesAdapter(::archiveAction, ::deleteAction, ::startEditActivity)
        binding.notesListRv.adapter = adapter
    }

    private fun initListeners(){
        binding.notesSearchEt.doAfterTextChanged {
            val filter = it.toString()
            mainViewModel.getNotesByTitleOrContent(filter)
        }

        binding.addNoteBttn.setOnClickListener{
            val intent = Intent(context, AddNoteActivity::class.java)
            startActivityForResult(intent, 12)

        }

        binding.statsBttn.setOnClickListener{
                // mainViewModel.getAllNotes()
             mainViewModel.getFiveLastDaysNote(::startStatsActivity)
        }
    }

    private fun startStatsActivity(input: IntArray){
        val intent = Intent(context, StatisticsActivity::class.java)
        intent.putExtra("days", input)
        startActivity(intent)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // 12 add note
        if(requestCode == 12) {
            if (resultCode == Activity.RESULT_OK) {
                mainViewModel.insertNote(
                    data?.extras?.get("return_note") as Note
                )
                Timber.e("Dodao sam novi note")
            } else if (resultCode == Activity.RESULT_CANCELED) {

            }
        }
        // 13 update note
        else if(requestCode == 13){
            if (resultCode == Activity.RESULT_OK) {
                val note: Note = data?.extras?.get("return_note") as Note
                mainViewModel.updateNote(
                    note.id!!,
                    note.title,
                    note.content,
                    note.archieved,
                    note.date
                )
                Timber.e("Update sam note")
            }
        }
    }

    private fun initObservers(){
        mainViewModel.notesState.observe(viewLifecycleOwner, Observer {
            Timber.e(it.toString())
            renderState(it)
        })

        binding.archiveSwitch.setOnCheckedChangeListener({ _, isChecked ->
            if(isChecked)
                mainViewModel.getArchievedNotes()
            else
                mainViewModel.getAllNotes()
        })
        mainViewModel.getAllNotes()
        //mainViewModel.getAllNotes2()
    }

    private fun renderState(state: NotesState) {
        when (state) {
            is NotesState.Success -> {
                Timber.e("HELLOOOO")
                showLoadingState(false)
                adapter.submitList(state.notes)
            }
            is NotesState.Error -> {
                showLoadingState(false)
                //Toast.makeText(context, state.message, Toast.LENGTH_SHORT).show()
            }
            is NotesState.Loading -> {
                showLoadingState(true)
            }
        }
    }

    // TODO: Loading states
    private fun showLoadingState(loading: Boolean) {
        //binding.inputEt.isVisible = !loading
        //binding.listRv.isVisible = !loading
        //binding.loadingPb.isVisible = loading
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun archiveAction(note: Note){
        Timber.e("Note archived!")
        mainViewModel.updateNote(note.id!!, note.title, note.content, !note.archieved, note.date)
    }

    private fun startEditActivity(note: Note){
        val intent = Intent(context, AddNoteActivity::class.java)
        intent.putExtra("note", note as Serializable)
        startActivityForResult(intent, 13)
    }

    private fun deleteAction(note: Note){
        Timber.e("Trying to delete...")
        mainViewModel.deleteNote(note.id!!)
    }

}















