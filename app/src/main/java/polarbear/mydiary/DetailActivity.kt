package polarbear.mydiary

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.w3c.dom.Text
import polarbear.mydiary.databinding.DetailBinding

class DetailActivity : AppCompatActivity() {
    private val TAG = "DetailActivity"
    var db : AppDatabase? = null
    var id : Long = 0
    private var savedContents : WriteEntity? = null
    private lateinit var binding: DetailBinding
    var mTitle : String? = null
    var mContents : String? = null
    var mTodo : String? = null
    var mDate : String? = null
    var mRegDate : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail)
        binding.detailActivity = this

        setSupportActionBar(binding.toolBar)
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
            mTitle = savedContents?.title
            mContents = savedContents?.contents
            mTodo = savedContents?.todo
            mDate = savedContents?.date
            mRegDate = savedContents?.regDate

            binding.invalidateAll()
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.detail_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> {
                finish()
                return true
            }
            R.id.modify -> {

                return true
            }
            R.id.delete -> {

                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

}