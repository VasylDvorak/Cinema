package com.example.cinema.model.room_data_base

import android.database.Cursor
import androidx.room.*

@Dao
interface HistoryDao {

    @Query("SELECT * FROM HistoryEntity")
    fun all(): MutableList<HistoryEntity>

    @Query("SELECT * FROM HistoryEntity WHERE id_server = :id_server")
    fun getHistoryEntity(id_server: Int): HistoryEntity

    @Query("SELECT * FROM HistoryEntity WHERE isLike = 1 ")
    fun getDataIsLiked(): MutableList<HistoryEntity>

    @Query("SELECT * FROM HistoryEntity WHERE watched = 1 ")
    fun getDataWatched(): MutableList<HistoryEntity>

    @Query("SELECT * FROM HistoryEntity WHERE current_request = 1 ")
    fun getDataCurrentRequest(): MutableList<HistoryEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(entity: HistoryEntity)

    @Update
    fun update(entity: HistoryEntity)

    @Delete
    fun delete(entity: HistoryEntity)

    @Query("DELETE FROM HistoryEntity WHERE id_server = :id_server")
    fun deleteByIdServer(id_server: Int)

    @Query("DELETE FROM HistoryEntity WHERE id = :id")
    fun deleteById(id: Long)

    @Query("SELECT id, name, year, isLike, current_request FROM HistoryEntity " +
            "WHERE current_request = 1")
    fun getHistoryCursor() : Cursor

    @Query("SELECT id, name, year, isLike, current_request FROM HistoryEntity WHERE id = :id")
    fun getHistoryCursor(id: Long): Cursor


}
