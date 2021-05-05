package polarbear.mydiary

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.core.view.get
import androidx.core.view.size
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    val TAG = "MainActivity"
    var db : AppDatabase? = null
    var readList = mutableListOf<WriteEntity>()
    var adapter: MainListAdapter?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_main)
        super.onCreate(savedInstanceState)

        val toolBar : Toolbar = findViewById(R.id.toolBar)
        setSupportActionBar(toolBar)

        //db 초기화
        db = AppDatabase.getInstance(this)
//        val btnInsert : Button = findViewById(R.id.btnInsert)
        val listView : RecyclerView = findViewById(R.id.listView)
        val emptyText : TextView = findViewById(R.id.emptyText)


        listView.layoutManager = LinearLayoutManager(this)
        listView.setHasFixedSize(true)
        adapter = MainListAdapter(readList,this)
        listView.adapter = adapter

//        이전에 저장한 내용 불러와 추가
        val savedContents = db!!.diaryDao().getAll()
        if (savedContents.isNotEmpty()){
            readList.addAll(savedContents)
            Log.i(TAG, "onCreate: 1111111 " + listView.size)
            emptyText.visibility = View.GONE
            listView.visibility = View.VISIBLE
        }else{
            emptyText.visibility = View.VISIBLE
            listView.visibility = View.GONE
        }

        //insert
//        btnInsert.setOnClickListener {
////            val content = WriteEntity("2020-05-04", "오늘하루는?", "어땠나요?", "할일", "202005041526")
////            db!!.diaryDao().insertAll(content)
//            db?.diaryDao()?.delete()
//            adapter!!.notifyDataSetChanged()
//        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        return super.onCreateOptionsMenu(menu)
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.write -> {
                val writeIntent = Intent(this, WriteActivity::class.java)
                startActivity(writeIntent)
                return true
            }
            R.id.search -> {
                val writeIntent = Intent(this, WriteActivity::class.java)
                startActivity(writeIntent)
                return true
            }
            else -> {
                return super.onOptionsItemSelected(item)
            }
        }
    }
}