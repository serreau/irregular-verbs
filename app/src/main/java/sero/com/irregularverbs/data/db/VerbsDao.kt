package sero.com.irregularverbs.data.db

import androidx.lifecycle.LiveData
import androidx.room.*
import java.time.LocalDateTime

@Dao
interface VerbsDao {
    @Query("SELECT * FROM VERBS")
     fun getAll(): LiveData<List<Verbs>>

    @Query("SELECT * FROM VERBS WHERE DATE < :date")
    fun getVerbsToRevise(date : LocalDateTime = LocalDateTime.now()): LiveData<List<Verbs>>

    @Query("SELECT * FROM VERBS WHERE DAY IS NOT NULL")
    fun getScheduledVerbs() : LiveData<List<Verbs>>

    @Query("SELECT COUNT(*) FROM VERBS WHERE DAY IS NOT NULL")
    fun countAll() : LiveData<Int>

    @Update
    suspend  fun updateVerb(verb: Verbs)

}

