package xyz.archroid.notino.util

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.fragment_bottom_sheet.*
import xyz.archroid.notino.R

class BottomSheetFragment : BottomSheetDialogFragment() {

    var selectedColor = "#171C26"


    companion object {
        private var noteId = -1

        //        fun newInstance(id: Int): BottomSheetFragment {
        fun newInstance(): BottomSheetFragment {
            val args = Bundle()
            val fragment = BottomSheetFragment()
            fragment.arguments = args
//            noteId = id
            return fragment
        }
    }

    @SuppressLint("RestrictedApi", "InflateParams")
    override fun setupDialog(dialog: Dialog, style: Int) {
        super.setupDialog(dialog, style)

        val view = LayoutInflater.from(context).inflate(R.layout.fragment_bottom_sheet, null)
        dialog.setContentView(view)

        val param = (view.parent as View).layoutParams as CoordinatorLayout.LayoutParams

        val behavior = param.behavior

        if (behavior is BottomSheetBehavior<*>) {
            behavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
                override fun onSlide(bottomSheet: View, slideOffset: Float) {
                }

                override fun onStateChanged(bottomSheet: View, newState: Int) {
                    var state = ""
                    when (newState) {
                        BottomSheetBehavior.STATE_DRAGGING -> {
                            state = "DRAGGING"
                        }
                        BottomSheetBehavior.STATE_SETTLING -> {
                            state = "SETTLING"
                        }
                        BottomSheetBehavior.STATE_EXPANDED -> {
                            state = "EXPANDED"
                        }
                        BottomSheetBehavior.STATE_COLLAPSED -> {
                            state = "COLLAPSED"
                        }

                        BottomSheetBehavior.STATE_HIDDEN -> {
                            state = "HIDDEN"
                            dismiss()
                            behavior.state = BottomSheetBehavior.STATE_COLLAPSED
                        }

                    }
                }

            })


        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_bottom_sheet, container, false)

    }

    private fun setListener() {

        layout_note1.setOnClickListener {
            img_note1.visibility = View.VISIBLE
            img_note2.visibility = View.GONE
            img_note3.visibility = View.GONE
            img_note4.visibility = View.GONE
            img_note5.visibility = View.GONE
            img_note6.visibility = View.GONE
            img_note7.visibility = View.GONE

            selectedColor = "#4e33ff"

            val intent = Intent("bottom_sheet_action")
            intent.putExtra("action", "Blue")
            intent.putExtra("selectedColor", selectedColor)
            LocalBroadcastManager.getInstance(requireContext()).sendBroadcast(intent)

        }

        layout_note2.setOnClickListener {
            img_note1.visibility = View.GONE
            img_note2.visibility = View.VISIBLE
            img_note3.visibility = View.GONE
            img_note4.visibility = View.GONE
            img_note5.visibility = View.GONE
            img_note6.visibility = View.GONE
            img_note7.visibility = View.GONE

            selectedColor = "#ffd633"

            layout_note1.setOnClickListener {
                val intent = Intent("bottom_sheet_action")
                intent.putExtra("action", "Yellow")
                intent.putExtra("selectedColor", selectedColor)
                LocalBroadcastManager.getInstance(requireContext()).sendBroadcast(intent)
            }
        }

        layout_note3.setOnClickListener {
            img_note1.visibility = View.GONE
            img_note2.visibility = View.GONE
            img_note3.visibility = View.VISIBLE
            img_note4.visibility = View.GONE
            img_note5.visibility = View.GONE
            img_note6.visibility = View.GONE
            img_note7.visibility = View.GONE

            selectedColor = "#ffffff"

            layout_note1.setOnClickListener {
                val intent = Intent("bottom_sheet_action")
                intent.putExtra("action", "White")
                intent.putExtra("selectedColor", selectedColor)
                LocalBroadcastManager.getInstance(requireContext()).sendBroadcast(intent)
            }
        }

        layout_note4.setOnClickListener {
            img_note1.visibility = View.GONE
            img_note2.visibility = View.GONE
            img_note3.visibility = View.GONE
            img_note4.visibility = View.VISIBLE
            img_note5.visibility = View.GONE
            img_note6.visibility = View.GONE
            img_note7.visibility = View.GONE

            selectedColor = "#ae3b76"

            layout_note1.setOnClickListener {
                val intent = Intent("bottom_sheet_action")
                intent.putExtra("action", "Purple")
                intent.putExtra("selectedColor", selectedColor)
                LocalBroadcastManager.getInstance(requireContext()).sendBroadcast(intent)
            }
        }

        layout_note5.setOnClickListener {
            img_note1.visibility = View.GONE
            img_note2.visibility = View.GONE
            img_note3.visibility = View.GONE
            img_note4.visibility = View.GONE
            img_note5.visibility = View.VISIBLE
            img_note6.visibility = View.GONE
            img_note7.visibility = View.GONE

            selectedColor = "#0aebaf"

            layout_note1.setOnClickListener {
                val intent = Intent("bottom_sheet_action")
                intent.putExtra("action", "Green")
                intent.putExtra("selectedColor", selectedColor)
                LocalBroadcastManager.getInstance(requireContext()).sendBroadcast(intent)
            }
        }

        layout_note6.setOnClickListener {
            img_note1.visibility = View.GONE
            img_note2.visibility = View.GONE
            img_note3.visibility = View.GONE
            img_note4.visibility = View.GONE
            img_note5.visibility = View.GONE
            img_note6.visibility = View.VISIBLE
            img_note7.visibility = View.GONE

            selectedColor = "#ff7746"

            layout_note1.setOnClickListener {
                val intent = Intent("bottom_sheet_action")
                intent.putExtra("action", "Orange")
                intent.putExtra("selectedColor", selectedColor)
                LocalBroadcastManager.getInstance(requireContext()).sendBroadcast(intent)
            }
        }

        layout_note7.setOnClickListener {
            img_note1.visibility = View.GONE
            img_note2.visibility = View.GONE
            img_note3.visibility = View.GONE
            img_note4.visibility = View.GONE
            img_note5.visibility = View.GONE
            img_note6.visibility = View.GONE
            img_note7.visibility = View.VISIBLE

            selectedColor = "#202734"

            layout_note1.setOnClickListener {
                val intent = Intent("bottom_sheet_action")
                intent.putExtra("action", "Black")
                intent.putExtra("selectedColor", selectedColor)
                LocalBroadcastManager.getInstance(requireContext()).sendBroadcast(intent)
            }
        }

    }


}