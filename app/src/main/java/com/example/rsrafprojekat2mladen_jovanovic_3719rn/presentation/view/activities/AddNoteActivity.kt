package com.example.rsrafprojekat2mladen_jovanovic_3719rn.presentation.view.activities

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.rsrafprojekat2mladen_jovanovic_3719rn.R
import com.example.rsrafprojekat2mladen_jovanovic_3719rn.data.models.Note
import com.example.rsrafprojekat2mladen_jovanovic_3719rn.databinding.ActivityAddNoteBinding
import com.example.rsrafprojekat2mladen_jovanovic_3719rn.presentation.contract.MainContract
import com.example.rsrafprojekat2mladen_jovanovic_3719rn.presentation.viewmodel.MainViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.Serializable
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class AddNoteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddNoteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if(intent.extras == null){
            init(null)
        }else{
            val note = intent.extras?.get("note") as Note
            init(note)
        }

    }

    private fun init(note: Note?){
        initUI(note)
        initListeners(note)
    }

    private fun initListeners(note: Note?){
        binding.izmeniBttn.setOnClickListener{
            if(note == null){
                val title = binding.titleEt.text.toString()
                val content = binding.contentEt.text.toString()
                if(title.isNotEmpty() && content.isNotEmpty()){
                    val currentId = UUID.randomUUID().mostSignificantBits.toInt()
                    val newNote = Note(currentId, title, content, false, LocalDateTime.now().format(
                        DateTimeFormatter.ofPattern("yyyy-MM-dd")))

                    val returnIntent  = Intent()
                    returnIntent.putExtra("return_note", newNote as Serializable)
                    setResult(Activity.RESULT_OK, returnIntent)
                    finish()
                }
            }
            else{
                val title = binding.titleEt.text.toString()
                val content = binding.contentEt.text.toString()
                if(title.isNotEmpty() && content.isNotEmpty()){
                    val newNote = Note(note.id, title, content, note.archieved, note.date)
                    val returnIntent  = Intent()
                    returnIntent.putExtra("return_note", newNote as Serializable)
                    setResult(Activity.RESULT_OK, returnIntent)
                    finish()
                }
            }


        }

        binding.odustaniBttn.setOnClickListener{
            setResult(Activity.RESULT_CANCELED)
            finish()
        }
    }


    private fun initUI(note: Note?){
        if(note != null){
            binding.contentEt.setText(note.content)
            binding.titleEt.setText(note.title)
        }
    }
}