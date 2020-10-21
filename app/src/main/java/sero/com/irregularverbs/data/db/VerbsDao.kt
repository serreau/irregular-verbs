package sero.com.irregularverbs.data.db

import androidx.lifecycle.LiveData
import androidx.room.*
import java.time.LocalDateTime

@Dao
interface VerbsDao {
    @Query("SELECT * FROM VERBS")
    fun getAll(): List<Verbs>

    @Query("SELECT * FROM VERBS WHERE DATE < :date")
    fun getVerbsToRevise(date : LocalDateTime = LocalDateTime.now()): LiveData<List<Verbs>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg verbs: Verbs)

    @Query("SELECT COUNT(*) FROM VERBS WHERE DAY = :day")
    fun countByDay(day : Int) : Int

    @Query("SELECT COUNT(*) FROM VERBS WHERE DAY IS NULL")
    fun countByDay() : Int

    @Query("SELECT COUNT(*) FROM VERBS WHERE DAY IS NOT NULL")
    fun countAll() : Int

    @Update
    fun updateVerb(verb: Verbs)
}

