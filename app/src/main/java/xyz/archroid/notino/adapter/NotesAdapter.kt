package xyz.archroid.notino.adapter

import android.annotation.SuppressLint
import android.graphics.BitmapFactory
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_note.view.*
import xyz.archroid.notino.R
import xyz.archroid.notino.entities.Notes

class NotesAdapter(private val noteList: List<Notes>) :
    RecyclerView.Adapter<NotesAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_note, parent, false)
        )
    }

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.tv_title.text = noteList[position].title
        holder.itemView.tv_desc.text = noteList[position].noteText
        holder.itemView.tv_dateTime.text = noteList[position].dateTime

        if (noteList[position].color != null) {
            holder.itemView.colorView.setBackgroundColor(Color.parseColor(noteList[position].color))
        } else {
            holder.itemView.colorView.setBackgroundColor(R.color.ColorLightBlack)
        }

        if (noteList[position].imgPath != null) {
            holder.itemView.iv_image.setImageBitmap(BitmapFactory.decodeFile(noteList[position].imgPath))
            holder.itemView.iv_image.visibility = View.VISIBLE
        }

    }

    override fun getItemCount(): Int {
        return noteList.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    }
}