package com.example.hardmad2024_1.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hardmad2024_1.R
import com.example.hardmad2024_1.databinding.SettingsFragmentBinding
import com.example.hardmad2024_1.presentation.util.adapters.NotificationRecyclerAdapter
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat.CLOCK_24H
import java.time.LocalTime

class SettingsFragment: Fragment(R.layout.settings_fragment) {
    private lateinit var binding: SettingsFragmentBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = SettingsFragmentBinding.bind(view)

        val recyclerView = binding.notificationsContainer

        val layoutManager = LinearLayoutManager(context)
        val adapter = NotificationRecyclerAdapter()

        recyclerView.apply {
            this.layoutManager = layoutManager
            this.adapter = adapter
        }

        binding.addNotification.setOnClickListener {
            val timePicker = fragmentManager?.let { it1 ->
                MaterialTimePicker.Builder()
                    .setTimeFormat(CLOCK_24H)
                    .setInputMode(MaterialTimePicker.INPUT_MODE_CLOCK)
                    .setTheme(R.style.BaseTheme_TimePicker)
                    .build()
            }

            val bottomSheet = context?.let { it1 -> BottomSheetDialog(it1) }
            val inflater = LayoutInflater.from(context)
            val bottomSheetContent = inflater.inflate(R.layout.bottom_sheet_content, null)

            val currentTime = LocalTime.now()
            val hours = currentTime.hour
            val minutes = currentTime.minute

            bottomSheetContent.findViewById<TextView>(R.id.hours).text = String.format("%02d", hours)
            bottomSheetContent.findViewById<TextView>(R.id.minutes).text = String.format("%02d", minutes)

            val clickableLayout = bottomSheetContent.findViewById<LinearLayout>(R.id.time_section)
            clickableLayout.setOnClickListener {
                fragmentManager?.let { it1 -> timePicker?.show(it1, "timePicker") }
            }

            bottomSheetContent.findViewById<AppCompatButton>(R.id.saveBtn).setOnClickListener {
                adapter.addItem(bottomSheetContent.findViewById<TextView>(R.id.hours).text.toString() + ":" + bottomSheetContent.findViewById<TextView>(
                    R.id.minutes
                ).text)
                bottomSheet?.dismiss()
            }

            timePicker?.addOnPositiveButtonClickListener {
                bottomSheetContent.findViewById<TextView>(R.id.hours).text = String.format("%02d", timePicker.hour)
                bottomSheetContent.findViewById<TextView>(R.id.minutes).text = String.format("%02d", timePicker.minute)
            }

            bottomSheet?.setContentView(bottomSheetContent)
            bottomSheet?.show()
        }
    }
}