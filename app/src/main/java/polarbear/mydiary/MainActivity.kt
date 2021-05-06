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
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import polarbear.mydiary.databinding.ActivityMainBinding
import polarbear.mydiary.databinding.DetailBinding

class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"
    var db : AppDatabase? = null
    var readList = mutableListOf<WriteEntity>()
    var adapter: MainListAdapter?=null
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_main)
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        setSupportActionBar(binding.toolBar)
        val ab : androidx.appcompat.app.ActionBar = supportActionBar!!
        ab.setDisplayShowTitleEnabled(false)
        binding.toolBar.title = "하루일기"

        //db 초기화
        db = AppDatabase.getInstance(this)
//        val btnInsert : Button = findViewById(R.id.btnInsert)

        binding.listView.layoutManager = LinearLayoutManager(this)
        binding.listView.setHasFixedSize(true)
        adapter = MainListAdapter(readList,this)
        binding.listView.adapter = adapter

//        이전에 저장한 내용 불러와 추가
        CoroutineScope(Dispatchers.IO).launch { // 코루틴
            val savedContents = db!!.diaryDao().getAll()
            if (savedContents.isNotEmpty()){
                readList.addAll(savedContents)
                Log.i(TAG, "onCreate: 1111111 $binding.listView.size")
                binding.emptyText.visibility = View.GONE
                binding.listView.visibility = View.VISIBLE
            }else{
                binding.emptyText.visibility = View.VISIBLE
                binding.listView.visibility = View.GONE
            }
        }
//        val savedContents = db!!.diaryDao().getAll()


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