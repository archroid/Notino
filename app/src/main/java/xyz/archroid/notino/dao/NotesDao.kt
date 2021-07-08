package xyz.archroid.notino.dao

import android.provider.ContactsContract
import androidx.room.*
import xyz.archroid.notino.entities.Notes

@Dao
interface NotesDao {
    @get:Query("SELECT * FROM notes ORDER BY id DESC")
    val allNote: List<Notes>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNotes(note:Notes)

    @Delete
    fun deleteNotes(note:Notes)
}