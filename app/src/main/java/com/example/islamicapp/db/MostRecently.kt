package com.example.islamicapp.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MostRecently(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    @ColumnInfo("titleEng")
    val titleEng: String,
    @ColumnInfo("titleArabic")
    val titleArabic: String,
    @ColumnInfo("image")
    val image: String,
    @ColumnInfo("total_verses")
    val total_verses: String
)

