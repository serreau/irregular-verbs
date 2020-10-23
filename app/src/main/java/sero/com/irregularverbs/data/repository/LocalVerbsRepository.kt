package sero.com.irregularverbs.data.repository

import android.content.Context
import androidx.lifecycle.LiveData
import sero.com.irregularverbs.data.db.CustomDatabase
import sero.com.irregularverbs.data.db.Verbs

object LocalVerbsRepository {
    lateinit var context : Context

    fun getAll() : LiveData<List<Verbs>> = CustomDatabase.getDatabase(context).verbsDao().getAll()
    fun getVerbsToRevise() = CustomDatabase.getDatabase(context).verbsDao().getVerbsToRevise()
    fun countAll() = CustomDatabase.getDatabase(context).verbsDao().countAll()
    suspend fun updateVerb(verb: Verbs) = CustomDatabase.getDatabase(context).verbsDao().updateVerb(verb)
}