package polarbear.mydiary

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "WriteDiary")
data class WriteEntity(@PrimaryKey(autoGenerate = true) val id: Long, var date: String, var title: String, var contents: String,
                       var todo: String, var regDate: String)


