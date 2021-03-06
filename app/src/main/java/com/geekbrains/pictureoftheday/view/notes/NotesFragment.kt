package com.geekbrains.pictureoftheday.view.notes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.geekbrains.pictureoftheday.model.notes.Note
import com.geekbrains.pictureoftheday.R
import com.geekbrains.pictureoftheday.viewModel.meteorite.ItemTouchHelperCallback
import com.geekbrains.pictureoftheday.viewModel.notes.NotesRecyclerAdapter
import kotlinx.android.synthetic.main.fragment_notes.*

class NotesFragment : Fragment() {

    private lateinit var adapter: NotesRecyclerAdapter

    private val notes = arrayListOf(
        Note("")
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_notes, container, false)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        adapter = NotesRecyclerAdapter(notes)
        val recyclerView: RecyclerView = requireView().findViewById(R.id.recyclerView_notes)

        recyclerView.adapter = adapter
        add_note.setOnClickListener {
            adapter.addNote()
            recyclerView.scrollToPosition(0)
        }

        ItemTouchHelper(ItemTouchHelperCallback(adapter))
            .attachToRecyclerView(recyclerView)
    }

}
