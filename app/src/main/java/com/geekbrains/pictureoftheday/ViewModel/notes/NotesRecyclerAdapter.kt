package com.geekbrains.pictureoftheday.ViewModel.notes

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.geekbrains.pictureoftheday.Model.notes.Note
import com.geekbrains.pictureoftheday.R
import com.geekbrains.pictureoftheday.ViewModel.meteorite.ItemTouchHelperAdapter
import com.geekbrains.pictureoftheday.ViewModel.meteorite.ItemTouchHelperViewHolder


class NotesRecyclerAdapter(
    private var notes: MutableList<Note>
) : RecyclerView.Adapter<NotesRecyclerAdapter.NotesViewHolder>(), ItemTouchHelperAdapter {
    class NotesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), ItemTouchHelperViewHolder {
        val headline: TextView = itemView.findViewById(R.id.headline)

        override fun onItemSelected() {
            itemView.setBackgroundColor(Color.LTGRAY)
        }

        override fun onItemClear() {
            itemView.setBackgroundColor(Color.WHITE)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NotesViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.notes_recyclerview_item, parent, false)
        return NotesViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        holder.headline.text = notes[position].headline
    }

    override fun getItemCount(): Int {
        return notes.size
    }

    fun addNote() {
        notes.add(Note(""))
        notifyItemInserted(0)
    }

    override fun onItemMove(fromPosition: Int, toPosition: Int) {
        notes.removeAt(fromPosition).apply {
            notes.add(if (toPosition > fromPosition) toPosition - 1 else toPosition, this)
        }
        notifyItemMoved(fromPosition, toPosition)
    }

    override fun onItemDismiss(position: Int) {
        notes.removeAt(position)
        notifyItemRemoved(position)
    }

}