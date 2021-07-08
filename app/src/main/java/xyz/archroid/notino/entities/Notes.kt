package xyz.archroid.notino.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable


@Entity(tableName = "Notes")
data class Notes (
    @PrimaryKey(autoGenerate = true)
    var id: Int,

    @ColumnInfo(name = "title")
    var title: String,

    @ColumnInfo(name = "subtitle")
    var subTitle: String,

    @ColumnInfo(name = "date_time")
    var dataTime: String,

    @ColumnInfo(name = "img_url")
    var imgUrl: String,

    @ColumnInfo(name = "color")
    var color: String


):Serializable {
    override fun toString(): String {
        return "$title : $dataTime"
    }
}