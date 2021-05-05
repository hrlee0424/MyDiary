package polarbear.mydiary

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.w3c.dom.Text

class DetailActivity : AppCompatActivity() {
    private val TAG = "DetailActivity"
    var db : AppDatabase? = null
    var id : Long = 0
    private var savedContents : WriteEntity? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val title : TextView = findViewById(R.id.title)
        val contents : TextView = findViewById(R.id.contents)
        val date : TextView = findViewById(R.id.date)
        val toolBar : Toolbar = findViewById(R.id.toolBar)
        setSupportActionBar(toolBar)
        val ab : androidx.appcompat.app.ActionBar = supportActionBar!!
        ab.setDisplayHomeAsUpEnabled(true)
        ab.setDisplayShowTitleEnabled(false)
//        toolBar.title = "상세보기"

        if (intent.hasExtra("id")){
            id = intent.getLongExtra("id", 0)
            Log.i(TAG, "onCreate: id $id")
        }

        //db 초기화
        db = AppDatabase.getInstance(this)
        CoroutineScope(Dispatchers.IO).launch { // 다른애 한테 일 시키기
            savedContents = db!!.diaryDao().selectById(id = id)
            Log.i(TAG, "onCreate: 222222222222 $savedContents!!.title")
            val mTitle = savedContents?.title
            val mContents = savedContents?.contents
            val mTodo = savedContents?.todo
            val mDate = savedContents?.date
            val mRegDate = savedContents?.regDate

            title.text = mTitle
            contents.text = mContents
            date.text = mDate
        }



    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

}