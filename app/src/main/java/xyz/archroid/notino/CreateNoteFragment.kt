package xyz.archroid.notino

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_create_note.*
import kotlinx.coroutines.launch
import xyz.archroid.notino.database.NotesDatabase
import xyz.archroid.notino.entities.BaseFragment
import xyz.archroid.notino.entities.Notes
import java.text.SimpleDateFormat
import java.util.*


class CreateNoteFragment : BaseFragment() {

    private var currentDate: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_create_note, container, false)
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            CreateNoteFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }

    @SuppressLint("SimpleDateFormat")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val simpleDateFormat = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
        currentDate = simpleDateFormat.format(Date())

        tv_dateTime.text = currentDate

        btn_done.setOnClickListener {
            //Save note
            saveNote()
        }
        btn_back.setOnClickListener {
            replaceFragment(HomeFragment.newInstance(), false)
        }
    }

    private fun saveNote() {

        //Validate variables
        if (et_noteTitle.text.isNullOrEmpty()) {
            Toast.makeText(context, "Title required", Toast.LENGTH_SHORT).show()
        }
        if (et_noteSubtitle.text.isNullOrEmpty()) {
            Toast.makeText(context, "Subtitle required", Toast.LENGTH_SHORT).show()
        }

        if (et_noteDesc.text.isNullOrEmpty()) {
            Toast.makeText(context, "Description required", Toast.LENGTH_SHORT).show()
        }

        launch {
            val note = Notes()
            note.title = et_noteTitle.text.toString()
            note.subTitle = et_noteSubtitle.text.toString()
            note.noteText = et_noteDesc.text.toString()
            note.dateTime = currentDate

            context?.let {
                NotesDatabase.getDatabase(it).noteDao().insertNotes(note)
                et_noteTitle.setText("")
                et_noteSubtitle.setText("")
                et_noteDesc.setText("")
            }
        }


    }

    private fun replaceFragment(fragment: Fragment, isTransition: Boolean) {

        val fragmentTransition = requireActivity().supportFragmentManager.beginTransaction()

        if (isTransition) {
            fragmentTransition.setCustomAnimations(
                android.R.anim.slide_out_right,
                android.R.anim.slide_in_left
            )
        }
        fragmentTransition.replace(R.id.frameLayout, fragment)
            .addToBackStack(fragment.javaClass.simpleName).commit()
    }
}