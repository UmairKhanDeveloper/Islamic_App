
package com.example.islamicapp.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface MostRecentlyDao {

    @Query("SELECT * FROM MostRecently ORDER BY id DESC")
    fun getAllMostRecently(): LiveData<List<MostRecently>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(mostRecently: MostRecently)

    @Delete
    suspend fun delete(mostRecently: MostRecently)

    @Update
    suspend fun update(mostRecently: MostRecently)
}
