package com.geeks.notes

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.NavHostFragment
import com.geeks.notes.databinding.FragmentNoteBinding

class NoteFragment : Fragment(), NoteAdapter.Clickable {

    private var adapter: NoteAdapter? = null
    private var navHostFragment: NavHostFragment? = null
    private lateinit var binding: FragmentNoteBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val noteBinding = FragmentNoteBinding.inflate(inflater, container, false)
        binding = noteBinding
        return noteBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = NoteAdapter(this)
        binding.rvNotes.adapter = adapter
        adapter!!.addNote(MainActivity.getList())
        Log.e("bekzat", "${MainActivity.getList()}")
        navHostFragment =
            requireActivity().supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as
                    NavHostFragment
        val navController = navHostFragment!!.navController

        //Инициализация обработчика нажатия на кнопку и перехода на SecondFragment
        binding.btnAdd.setOnClickListener { v: View? ->
            navController.navigate(R.id.addNoteFragment)
        }
        binding.btnSort.setOnClickListener { adapter!!.sortNotes() }

        requireActivity().supportFragmentManager.setFragmentResultListener(
            "change_note", this
        ) { _, result ->
            val note: Notes? = result.getSerializable("edit_note") as Notes?
            note?.let { adapter!!.changeNote(it, result.getInt("position")) }
        }
    }

    override fun edit(position: Int) {
        val note: Notes = adapter!!.getList()!![position]
        val bundle = Bundle()
        note.title?.let { Log.e("ololo", it) }
        bundle.putString("changeTitle", note.title)
        bundle.putString("changeDesc", note.desc)
        bundle.putString("changeDate", note.date)
        bundle.putInt("position", position)
        val navController = navHostFragment!!.navController
        navController.navigate(R.id.addNoteFragment, bundle)


    }

    override fun delete(position: Int) {
        AlertDialog.Builder(requireContext())
            .setTitle("Удалить заметку?")
            .setMessage("Если вы удалите заметку, то никогда ее не вернете!!!")
            .setNegativeButton("Нет", null)
            .setPositiveButton("Да, я уверен!") {
                    _, _ ->
                adapter?.removeNote(position)
            }
            .show()
    }
}