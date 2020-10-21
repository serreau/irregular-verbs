package sero.com.irregularverbs.data.repository

import android.content.Context
import sero.com.irregularverbs.data.db.CustomDatabase
import sero.com.irregularverbs.data.db.Verbs

object LocalVerbsRepository {
    lateinit var context : Context

    fun getAll() = CustomDatabase.getDatabase(context).verbsDao().getAll()
    fun getVerbsToRevise() = CustomDatabase.getDatabase(context).verbsDao().getVerbsToRevise()
    fun countByDay(day : Int) = CustomDatabase.getDatabase(context).verbsDao().countByDay(day)
    fun countByDay() = CustomDatabase.getDatabase(context).verbsDao().countByDay()
    fun countAll() = CustomDatabase.getDatabase(context).verbsDao().countAll()
    fun updateVerb(verb: Verbs) = CustomDatabase.getDatabase(context).verbsDao().updateVerb(verb)
}