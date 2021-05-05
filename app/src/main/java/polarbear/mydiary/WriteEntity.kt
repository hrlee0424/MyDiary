package polarbear.mydiary

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "WriteDiary")
data class WriteEntity( var date: String, var title: String, var contents: String,
                       var todo: String, var regDate: String){
    @PrimaryKey(autoGenerate = true) var id: Long = 0
}
// val id: Long,

