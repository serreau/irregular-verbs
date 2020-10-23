package sero.com.irregularverbs.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [Verbs::class], version = 1)
@TypeConverters(Converters::class)
abstract class CustomDatabase : RoomDatabase() {
    abstract fun verbsDao(): VerbsDao

    companion object {

        @Volatile
        private var INSTANCE: CustomDatabase? = null

        fun getDatabase(context: Context): CustomDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                INSTANCE = Room.databaseBuilder(
                    context.applicationContext,
                    CustomDatabase::class.java,
                    "irregular_verbs_database"
                )
                    .createFromAsset("database/verbs_database")
                    .build()

                return INSTANCE as CustomDatabase
            }
        }
    }
}