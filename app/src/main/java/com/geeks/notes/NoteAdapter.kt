package com.geeks.notes

import android.content.Context
import android.graphics.BitmapFactory
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.geeks.notes.databinding.ItemNoteBinding
import java.io.File


class NoteAdapter(
    private val listener: Clickable,
) : RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {

    private var list = arrayListOf<Notes>()


    fun addNote(list: ArrayList<Notes>) {
        this.list = list
        notifyDataSetChanged()
    }

    fun changeNote(note: Notes, position: Int) {
        list[position] = note
        notifyItemChanged(position)
    }

    fun getList(): List<Notes>? {
        return list
    }

    fun sortNotes() {
        list.sortBy { it.title }
        notifyDataSetChanged()
    }

    fun removeNote(position: Int) {
        list.remove(list[position])
        notifyItemRemoved(position)
    }
    fun shareNote(position: Int){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val binding = ItemNoteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NoteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.onBind(list[position])
        holder.binding.itemBtnEdit.setOnClickListener {
            listener.edit(holder.adapterPosition)
        }
        holder.binding.itemBtnDelete.setOnClickListener {
            listener.delete(holder.adapterPosition)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class NoteViewHolder constructor(val binding: ItemNoteBinding) :
        ViewHolder(binding.root) {



        fun onBind(note: Notes) {
            binding.itemTvTitle.text = note.title
            binding.itemTvDes.text = note.desc
            binding.itemTvDate.text = note.date
            if (note.image != null) {
                val imageUri = Uri.parse(note.image)
                binding.itemImg.setImageURI(imageUri)

            }

        }
    }

    interface Clickable {
        fun edit(position: Int)
        fun delete(position: Int)
        fun share(position: Int)
    }
}