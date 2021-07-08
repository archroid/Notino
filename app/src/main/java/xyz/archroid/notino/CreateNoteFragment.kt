package xyz.archroid.notino

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_create_note.*
import java.text.SimpleDateFormat
import java.util.*


class CreateNoteFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
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
        val currentDate = simpleDateFormat.format(Date())

        tv_dateTime.text = currentDate

        btn_done.setOnClickListener {
            //Save note
            saveNote()
        }
        btn_back.setOnClickListener {
            replaceFragment(HomeFragment.newInstance() , false)
        }
    }

    private fun saveNote() {

        //Validate variables
        if (et_noteTitle.text.isNullOrEmpty()){
            Toast.makeText(context,"Title required",Toast.LENGTH_SHORT).show();
        }
        if (et_noteSubtitle.text.isNullOrEmpty()){
            Toast.makeText(context,"Subtitle required",Toast.LENGTH_SHORT).show();
        }

        if (et_noteDesc.text.isNullOrEmpty()){
            Toast.makeText(context,"Description required",Toast.LENGTH_SHORT).show();
        }


    }

    fun replaceFragment(fragment: Fragment, isTransition: Boolean) {

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