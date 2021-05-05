package polarbear.mydiary

import androidx.room.*
import io.reactivex.Single

interface BaseDao<T> {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(obj : T)

    @Delete
    fun delete(obj: T)

    @Update(onConflict = OnConflictStrategy.ABORT)
    fun update(obj: T)
}

@Dao
interface DiaryDAO : BaseDao<WriteEntity>{
    @Query("SELECT * FROM WriteDiary WHERE id = :id")
    fun selectById(id: Long): Single<WriteEntity>
    @Query("SELECT * FROM WriteDiary")
    fun selectAll(): Single<List<WriteEntity>>
    @Query("SELECT * FROM WriteDiary")
    fun getAll() : List<WriteEntity>
    @Query("DELETE FROM WriteDiary") //// WHERE id = :id"
//    fun delete(id : Long)
    fun delete()
    @Insert()
    fun insertAll(vararg WriteDiary : WriteEntity)
}