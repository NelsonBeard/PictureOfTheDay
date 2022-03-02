package com.geekbrains.pictureoftheday.ViewModel.notes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.geekbrains.pictureoftheday.Model.notes.Note
import com.geekbrains.pictureoftheday.R


class NotesRecyclerAdapter(
    private var notes: MutableList<Note>
) : RecyclerView.Adapter<NotesRecyclerAdapter.NotesViewHolder>() {
    class NotesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val headline: TextView = itemView.findViewById(R.id.headline)
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

}