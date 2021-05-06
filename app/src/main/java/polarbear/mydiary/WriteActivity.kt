package polarbear.mydiary

import android.annotation.SuppressLint
import android.app.ActionBar
import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.MotionEvent
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import androidx.constraintlayout.widget.ConstraintSet
import androidx.databinding.DataBindingUtil
import androidx.room.Room
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import polarbear.mydiary.databinding.ActivityMainBinding
import polarbear.mydiary.databinding.WriteBinding
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.*

class WriteActivity : AppCompatActivity() {
    private val TAG = "WriteActivity"
    var db : AppDatabase? = null
    private lateinit var binding: WriteBinding
    var dat : String ?= null

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_write)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_write)
        binding.writeActivity = this

        setSupportActionBar(binding.toolBar)
        val ab : androidx.appcompat.app.ActionBar = supportActionBar!!
        ab.setDisplayHomeAsUpEnabled(true)
        ab.setDisplayShowTitleEnabled(false)
        binding.toolBar.title = "작성하기"

        db = AppDatabase.getInstance(this)

        binding.editDate.setOnTouchListener { v, event ->
            when(event.action){
                MotionEvent.ACTION_DOWN -> {
                    val calendar = Calendar.getInstance()
                    val year = calendar.get(Calendar.YEAR)
                    val month = calendar.get(Calendar.MONTH)
                    val day = calendar.get(Calendar.DAY_OF_MONTH)

                    val listener = DatePickerDialog.OnDateSetListener { _, i, i2, i3 ->
                        dat = "${i}년 ${i2+1}월 ${i3}일"
                        binding.invalidateAll()
//                        binding.editDate.setText("${i}년 ${i2+1}월 ${i3}일")
                    }

                    val picker = DatePickerDialog(this, listener, year, month, day)
                    picker.show()
                }
            }
            true
        }

    }

    fun addDiary(){
        val mDate = binding.editDate.text.toString()
        val mTitle = binding.editTitle.text.toString()
        val mContents = binding.editContents.text.toString()
        val mTodo = binding.editTodo.text.toString()
//            val dateTime : LocalDateTime = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
//                LocalDateTime.now()
        //현재날짜시간
        val now : Long = System.currentTimeMillis()
        val date = Date(now)
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale("ko", "KR"))
        val stringTime = dateFormat.format(date)
//            val longTime = dateFormat.parse(stringTime).time
        Log.i(TAG, "onCreate stringTime:$stringTime")

        if(mDate.isEmpty() || mTitle.isEmpty() || mContents.isEmpty() || mTodo.isEmpty()){
            Toast.makeText(applicationContext, "양식에 맞게 내용을 입력해주세요.", Toast.LENGTH_SHORT).show()
        }else{
            val content = WriteEntity(mDate, mTitle, mContents, mTodo, stringTime)
            CoroutineScope(Dispatchers.IO).launch {
                db?.diaryDao()?.insertAll(content)
            }
            finish()
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