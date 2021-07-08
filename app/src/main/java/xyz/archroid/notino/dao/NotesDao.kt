package xyz.archroid.notino.dao

import androidx.room.*
import xyz.archroid.notino.entities.Notes

@Dao
interface NotesDao {
    @get:Query("SELECT * FROM notes ORDER BY id DESC")
    val allNote: List<Notes>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNotes(note: Notes)

    @Delete
    suspend fun deleteNotes(note: Notes)
}