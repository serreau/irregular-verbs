package sero.com.irregularverbs.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(tableName = "VERBS")
data class Verbs(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "ID") val id: Int,
    @ColumnInfo(name = "INFINITIVE") val infinitive: String,
    @ColumnInfo(name = "PAST") val past: String,
    @ColumnInfo(name = "PAST_PARTICIPLE") val pastParticiple: String,
    @ColumnInfo(name = "FRENCH_TRANSLATION") val frenchTranslation: String,
    @ColumnInfo(name = "DAY") val day: Int? = null,
    @ColumnInfo(name = "DATE") val date: LocalDateTime? = null
)