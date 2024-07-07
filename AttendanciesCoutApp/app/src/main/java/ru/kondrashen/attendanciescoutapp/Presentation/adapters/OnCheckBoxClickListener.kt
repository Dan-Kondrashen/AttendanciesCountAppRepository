package ru.kondrashen.attendanciescoutapp.Presentation.adapters

import ru.kondrashen.attendanciescoutapp.repository.data_class.Student
import ru.kondrashen.attendanciescoutapp.repository.data_class.StudentAttendStatus

interface OnCheckBoxClickListener {
   fun onCheckBoxClick(studentStatus: StudentAttendStatus)
}