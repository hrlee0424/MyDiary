package polarbear.mydiary

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    val TAG = "MainActivity"
    var db : AppDatabase? = null
    var readList = mutableListOf<WriteEntity>()

    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_main)
        super.onCreate(savedInstanceState)

        //db 초기화
        db = AppDatabase.getInstance(this)
        val btnInsert : Button = findViewById(R.id.btnInsert)
        val listView : RecyclerView = findViewById(R.id.listView)

//        이전에 저장한 내용 불러와 추가하기
        val savedContents = db!!.diaryDao().getAll()
        if (savedContents.isNotEmpty()){
            readList.addAll(savedContents)
            Log.i(TAG, "onCreate: " + readList.get(0))
        }

        btnInsert.setOnClickListener {
            val content = WriteEntity(1,"2020-05-04", "오늘하루는?", "어땠나요?", "할일", "202005041526")
            db?.diaryDao()?.insertAll(content)

        }
    }
}