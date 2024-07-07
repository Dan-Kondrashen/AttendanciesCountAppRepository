package ru.kondrashen.attendanciescoutapp.Presentation.fragments

import android.app.AlertDialog
import android.app.Dialog
import android.content.ContentResolver
import android.content.ContentUris
import android.content.Context
import android.content.DialogInterface
import android.database.Cursor
import android.icu.text.SimpleDateFormat
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult
import ru.kondrashen.attendanciescoutapp.R
import ru.kondrashen.attendanciescoutapp.databinding.DialogDateBinding
import ru.kondrashen.attendanciescoutapp.databinding.ListStudentsInGroupBinding
import java.io.File


import java.util.Date
import java.util.GregorianCalendar

class SelectDateFragment: DialogFragment() {

    private  lateinit var datePicker: DatePicker
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view = layoutInflater.inflate(R.layout.dialog_date, null)
        val formater = SimpleDateFormat("yyyy-MM-dd")
        datePicker = view.findViewById(R.id.dialog_calendar_item)
        return AlertDialog.Builder(
            requireActivity())
            .setView(view)
            .setTitle(R.string.date_info)
            .setPositiveButton(android.R.string.ok){
               item: DialogInterface, id: Int ->

                val date = formater.format(GregorianCalendar(datePicker.year, datePicker.month, datePicker.dayOfMonth).time)
                sendData(StudentsListFragment.GET_DATE, date)
            }
            .create()

    }
    private fun sendData(key: String, date: String){
        val bundle = Bundle()
        bundle.putString(key, date)
        setFragmentResult(key, bundle)
    }
}