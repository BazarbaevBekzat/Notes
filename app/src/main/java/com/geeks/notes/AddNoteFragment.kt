package com.geeks.notes

import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.geeks.notes.databinding.FragmentAddNoteBinding
import java.io.FileNotFoundException
import java.text.SimpleDateFormat
import java.util.*


class AddNoteFragment : Fragment() {
    private lateinit var binding: FragmentAddNoteBinding
    private var imgUri: Uri? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val addNoteBinding = FragmentAddNoteBinding.inflate(inflater, container, false)
        binding = addNoteBinding
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bundle = Bundle()
        if (arguments != null) {
            binding.etTitle.setText(requireArguments().getString("changeTitle"))
            binding.etDes.setText(requireArguments().getString("changeDesc"))
            binding.etDate.setText(requireArguments().getString("changeDate"))
            binding.btnSave.setOnClickListener {
                val changeTitle = binding.etTitle.text.toString()
                val changeDesk = binding.etDes.text.toString()
                val position = requireArguments().getInt("position")
                val sdf = SimpleDateFormat("dd/MM/yyyy_HH:mm", Locale.getDefault())
                val changeDate = sdf.format(Date())
                val note = Notes(changeTitle, changeDesk, changeDate, "")
                bundle.putSerializable("edit_note", note)
                bundle.putInt("position", position)
                requireActivity().supportFragmentManager.setFragmentResult("change_note", bundle)
                requireActivity().supportFragmentManager.popBackStack()
            }
        } else {
            binding.btnSave.setOnClickListener {
                val title = binding.etTitle.text.toString()
                val desc = binding.etDes.text.toString()
                val sdf = SimpleDateFormat("dd/MM/yyyy_HH:mm", Locale.getDefault())
                val date = sdf.format(Date())
                val note = Notes(title, desc, date, imgUri.toString())
                Log.e("ololo", imgUri.toString())
                bundle.putSerializable("note", note)
                requireActivity().supportFragmentManager.setFragmentResult("new_note", bundle)
                requireActivity().supportFragmentManager.popBackStack()
            }

            binding.imgAdd.setOnClickListener {
                val photoPickerIntent = Intent(Intent.ACTION_PICK)
                photoPickerIntent.type = "image/*"
                startActivityForResult(photoPickerIntent, 1)
            }
        }

        fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
            super.onActivityResult(requestCode, resultCode, data)
            if (requestCode == Activity.RESULT_OK) {
                try {
                    val imgUri = data?.data
                    val inputStream = requireActivity().contentResolver.openInputStream(
                        imgUri!!
                    )
                    val selectedImage = BitmapFactory.decodeStream(inputStream)
                    binding.imgAdd.setImageBitmap(selectedImage)
                } catch (e: FileNotFoundException) {
                    e.printStackTrace()
                    Toast.makeText(requireActivity(), "Something went wrong", Toast.LENGTH_LONG)
                        .show()
                }
            } else {
                Toast.makeText(requireActivity(), "You haven't picked Image", Toast.LENGTH_LONG)
                    .show()
            }
        }
    }
}
