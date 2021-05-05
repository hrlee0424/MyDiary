package polarbear.mydiary

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintSet
import androidx.room.Room
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.*

class WriteActivity : AppCompatActivity() {
    val TAG = "WriteActivity"
    var db : AppDatabase? = null

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_write)

        val editDate : EditText = findViewById(R.id.editDate)
        val btnSave : Button = findViewById(R.id.btnSave)
        val editTitle : EditText = findViewById(R.id.editTitle)
        val editContents : EditText = findViewById(R.id.editContents)
        val editTodo : EditText = findViewById(R.id.editTodo)

        db = AppDatabase.getInstance(this)


        editDate.setOnTouchListener { v, event ->
            when(event.action){
                MotionEvent.ACTION_DOWN -> {
                    val calendar = Calendar.getInstance()
                    val year = calendar.get(Calendar.YEAR)
                    val month = calendar.get(Calendar.MONTH)
                    val day = calendar.get(Calendar.DAY_OF_MONTH)

                    val listener = DatePickerDialog.OnDateSetListener { _, i, i2, i3 ->
                        editDate.setText("${i}년 ${i2+1}월 ${i3}일")
                    }

                    val picker = DatePickerDialog(this, listener, year, month, day)
                    picker.show()
                }
            }
            true
        }


        btnSave.setOnClickListener {
            /*val db = Room.databaseBuilder(
                applicationContext,
                AppDatabase::class.java, "WriteDiary"
            ).build()*/
            val mDate = editDate.text.toString()
            val mTitle = editTitle.text.toString()
            val mContents = editContents.text.toString()
            val mTodo = editTodo.text.toString()
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
                db?.diaryDao()?.insertAll(content)
                finish()
            }
        }
    }
}