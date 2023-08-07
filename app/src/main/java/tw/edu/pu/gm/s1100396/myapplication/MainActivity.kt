package tw.edu.pu.gm.s1100396.myapplication
import android.app.AlertDialog
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity

//第二版:多刪除記事(當使用者長按某個記事項目時，將彈出一個對話框，確認是否要刪除該記事)、文字大小、置中
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
        //設置 ListView 的項目點擊監聽器 (OnItemClickListener)
        //當點擊 ListView 中的某個項目時，這個監聽器會觸發。
        listViewNotes.setOnItemClickListener { _, _, position, _ ->
            //showEditNoteDialog(position)是一個自定義的函式，用於顯示編輯記事的對話框
            //當點擊ListView 中的項目時，這個方法將被調用並顯示一個對話框，使用者可以在這個對話框中編輯被點擊的項目的內容
            showEditNoteDialog(position)
        }

        listViewNotes.setOnItemLongClickListener { _, _, position, _ ->
            showDeleteNoteDialog(position)
            true
        }
    }
    //新增記事
    private fun showAddNoteDialog() {
        val editText = EditText(this)
        AlertDialog.Builder(this)
            //設置對話框的標題為 "新增記事"
            .setTitle("新增記事")
            .setView(editText)
            //設置對話框的 "確定" 按鈕，並設置點擊確定按鈕時的行為
            .setPositiveButton("確定") { _, _ ->
                val note = editText.text.toString()
                //檢查使用者輸入的文字是否不為空
                if (note.isNotBlank()) {
                    //用於儲存所有記事的列表
                    notesList.add(note)
                    (listViewNotes.adapter as ArrayAdapter<String>).notifyDataSetChanged()
                }
            }
            .setNegativeButton("取消", null)
            .show()
    }
    //編輯記事
    private fun showEditNoteDialog(position: Int) {
        val editText = EditText(this)
        editText.setText(notesList[position])
        AlertDialog.Builder(this)
            //設置對話框的標題為 "編輯記事"
            .setTitle("編輯記事")
            .setView(editText)
            //設置對話框的 "確定" 按鈕，並設置點擊確定按鈕時的行為
            .setPositiveButton("確定") { _, _ ->
                val updatedNote = editText.text.toString()
                //檢查使用者輸入的文字是否不為空
                if (updatedNote.isNotBlank()) {
                    notesList[position] = updatedNote
                    (listViewNotes.adapter as ArrayAdapter<String>).notifyDataSetChanged()
                }
            }
            .setNegativeButton("取消", null)
            .show()
    }


    //刪除記事

    //當使用者長按某個記事項目時，將彈出一個對話框，確認是否要刪除該記事
    private fun showDeleteNoteDialog(position: Int) {
        AlertDialog.Builder(this)
            //設置對話框的標題為 "刪除記事"
            .setTitle("刪除記事")
            .setMessage("確定要刪除這個記事嗎？")

            //設置對話框的 "確定" 按鈕，並設置點擊確定按鈕時的行為
            .setPositiveButton("確定") { _, _ ->
                notesList.removeAt(position)
                (listViewNotes.adapter as ArrayAdapter<String>).notifyDataSetChanged()
            }
            .setNegativeButton("取消", null)
            .show()
    }
}