package polarbear.mydiary

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintSet
import androidx.room.Room
import java.util.*

class WriteActivity : AppCompatActivity() {
    var db : AppDatabase? = null

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_write)

        val editDate : EditText = findViewById(R.id.editDate)
        val btnSave : Button = findViewById(R.id.btnSave)

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
            val content = WriteEntity("2020-05-04", "오늘하루는?", "어땠나요?", "할일", "202005041526")
            db?.diaryDao()?.insertAll(content)

            finish()
        }
    }
}