package tw.edu.pu.gm.s1100396.myapplication

import android.app.AlertDialog
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var listViewNotes: ListView
    private lateinit var btnAddNote: Button
    private val notesList = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listViewNotes = findViewById(R.id.listViewNotes)
        btnAddNote = findViewById(R.id.btnAddNote)

        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, notesList)
        listViewNotes.adapter = adapter

        btnAddNote.setOnClickListener {
            showAddNoteDialog()
        }

        listViewNotes.setOnItemClickListener { _, _, position, _ ->
            showEditNoteDialog(position)
        }
    }

    private fun showAddNoteDialog() {
        val editText = EditText(this)
        AlertDialog.Builder(this)
            .setTitle("新增記事")
            .setView(editText)
            .setPositiveButton("確定") { _, _ ->
                val note = editText.text.toString()
                if (note.isNotBlank()) {
                    notesList.add(note)
                    (listViewNotes.adapter as ArrayAdapter<String>).notifyDataSetChanged()
                }
            }
            .setNegativeButton("取消", null)
            .show()
    }

    private fun showEditNoteDialog(position: Int) {
        val editText = EditText(this)
        editText.setText(notesList[position])
        AlertDialog.Builder(this)
            .setTitle("編輯記事")
            .setView(editText)
            .setPositiveButton("確定") { _, _ ->
                val updatedNote = editText.text.toString()
                if (updatedNote.isNotBlank()) {
                    notesList[position] = updatedNote
                    (listViewNotes.adapter as ArrayAdapter<String>).notifyDataSetChanged()
                }
            }
            .setNegativeButton("取消", null)
            .show()
    }
}
