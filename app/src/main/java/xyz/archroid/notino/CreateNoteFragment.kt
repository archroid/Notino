package xyz.archroid.notino

import android.Manifest
import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.BitmapFactory
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import kotlinx.android.synthetic.main.fragment_create_note.*
import kotlinx.coroutines.launch
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions
import xyz.archroid.notino.database.NotesDatabase
import xyz.archroid.notino.entities.BaseFragment
import xyz.archroid.notino.entities.Notes
import xyz.archroid.notino.util.BottomSheetFragment
import java.text.SimpleDateFormat
import java.util.*


class CreateNoteFragment : BaseFragment(), EasyPermissions.PermissionCallbacks,
    EasyPermissions.RationaleCallbacks {


    private var currentDate: String? = null
    private var selectedColor = "#81deea"
    private val READ_STORAGE_PERM = 123
    private var selectedImagePath: String? = null


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

        LocalBroadcastManager.getInstance(requireContext()).registerReceiver(
            broadcastReceiver, IntentFilter("bottom_sheet_action")
        )

        view_color.setBackgroundColor(Color.parseColor("#81deea"))

        val simpleDateFormat = SimpleDateFormat("MMMM dd, yyyy")
        currentDate = simpleDateFormat.format(Date())

        img_more.setOnClickListener {
            val bottomSheetFragment = BottomSheetFragment.newInstance()
            bottomSheetFragment.show(
                requireActivity().supportFragmentManager,
                "Bottom Sheet Fragment"
            )

        }

        btn_done.setOnClickListener {
            saveNote()
        }
        btn_back.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
    }

    private fun saveNote() {
        //Validate variables
        if (editText_title.text.isNullOrEmpty()) {
            Toast.makeText(context, "Title required", Toast.LENGTH_SHORT).show()
        }
        if (editText_subtitle.text.isNullOrEmpty()) {
            Toast.makeText(context, "Subtitle required", Toast.LENGTH_SHORT).show()
        }

        if (et_noteDesc.text.isNullOrEmpty()) {
            Toast.makeText(context, "Description required", Toast.LENGTH_SHORT).show()
        }

        launch {
            val note = Notes()
            note.title = editText_title.text.toString()
            note.subTitle = editText_subtitle.text.toString()
            note.noteText = et_noteDesc.text.toString()
            note.dateTime = currentDate
            note.color = selectedColor
            note.imgPath = selectedImagePath

            context?.let {
                NotesDatabase.getDatabase(it).noteDao().insertNotes(note)
                editText_title.setText("")
                editText_subtitle.setText("")
                et_noteDesc.setText("")
                iv_note.visibility = View.GONE
                requireActivity().supportFragmentManager.popBackStack()
            }
        }


    }


    private var broadcastReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val actionColor = intent!!.getStringExtra("action")
            when (actionColor!!) {
                "color" -> {
                    selectedColor = intent.getStringExtra("selectedColor")!!
                    when (selectedColor) {
                        "blue" -> {
                            view_color.setBackgroundColor(Color.parseColor("#81deea"))
                            selectedColor = "#81deea"
                        }

                        "red" -> {
                            view_color.setBackgroundColor(Color.parseColor("#ffab91"))
                            selectedColor = "#ffab91"
                        }

                        "yellow" -> {
                            view_color.setBackgroundColor(Color.parseColor("#ffcc80"))
                            selectedColor = "#ffcc80"
                        }

                        "green" -> {
                            view_color.setBackgroundColor(Color.parseColor("#e7ed9b"))
                            selectedColor = "#e7ed9b"
                        }

                        "purple" -> {
                            view_color.setBackgroundColor(Color.parseColor("#cf94da"))
                            selectedColor = "#cf94da"
                        }
                        "pink" -> {
                            view_color.setBackgroundColor(Color.parseColor("#f48fb1"))
                            selectedColor = "#f48fb1"
                        }

                    }
                }
                "image" -> {
                    readStorageTask()
                }
                "webUrl" -> {

                }

            }
        }

    }

    override fun onDestroy() {
        LocalBroadcastManager.getInstance(requireContext()).unregisterReceiver(broadcastReceiver)
        super.onDestroy()
    }

    //Pick and Handle Image from gallery
    private val getContent = registerForActivityResult(ActivityResultContracts.GetContent()) {
        if (it != null) {
            val inputStream = requireActivity().contentResolver.openInputStream(it)
            val bitmap = BitmapFactory.decodeStream(inputStream)
            iv_note.setImageBitmap(bitmap)
            iv_note.visibility = View.VISIBLE

            selectedImagePath = getRealPathFromURI(it)
        }
    }


    private fun getRealPathFromURI(uri: Uri?): String {
        var filePath = ""
        val wholeID = DocumentsContract.getDocumentId(uri)

        // Split at colon, use second item in the array
        val id = wholeID.split(":").toTypedArray()[1]
        val column = arrayOf(MediaStore.Images.Media.DATA)

        // where id is equal to
        val sel = MediaStore.Images.Media._ID + "=?"
        val cursor = requireContext().contentResolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            column, sel, arrayOf(id), null
        )
        val columnIndex = cursor!!.getColumnIndex(column[0])
        if (cursor.moveToFirst()) {
            filePath = cursor.getString(columnIndex)
        }
        cursor.close()
        return filePath
    }


    // Grant Storage Permission
    private fun hasReadStoragePerm(): Boolean {
        return EasyPermissions.hasPermissions(
            requireContext(),
            Manifest.permission.READ_EXTERNAL_STORAGE
        )
    }

    private fun readStorageTask() {
        if (hasReadStoragePerm()) {
            getContent.launch("image/*")
        } else {
            EasyPermissions.requestPermissions(
                requireActivity(),
                getString(R.string.read_storage_msg),
                READ_STORAGE_PERM,
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        EasyPermissions.onRequestPermissionsResult(
            requestCode,
            permissions,
            grantResults,
            requireActivity()
        )
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        if (EasyPermissions.somePermissionPermanentlyDenied(requireActivity(), perms)) {
            AppSettingsDialog.Builder(requireActivity()).build().show()
        }
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {

    }

    override fun onRationaleDenied(requestCode: Int) {

    }

    override fun onRationaleAccepted(requestCode: Int) {

    }
}