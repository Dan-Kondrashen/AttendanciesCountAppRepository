package ru.kondrashen.attendanciescoutapp.Presentation.adapters

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.kondrashen.attendanciescoutapp.repository.data_class.Group
import ru.kondrashen.attendanciescoutapp.databinding.ListItemStudentForPrepodBinding
import ru.kondrashen.attendanciescoutapp.repository.data_class.Student
import ru.kondrashen.attendanciescoutapp.repository.data_class.StudentAttendStatus

class ListStudentsAdapter(students: MutableList<Student>): RecyclerView.Adapter<ListStudentsAdapter.ViewHolder>() {
    private var students: MutableList<Student> = mutableListOf()
    private var studentsStatus: MutableList<StudentAttendStatus> = mutableListOf()
    init {
        this.students = students
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val binding = ListItemStudentForPrepodBinding.inflate(LayoutInflater.from(parent.context),
            parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return students.size
    }
    inner class ViewHolder(private val item: ListItemStudentForPrepodBinding) : RecyclerView.ViewHolder(item.root){
        private lateinit var student: Student

        fun bindItem(student: Student){
            this.student = student
            item.listItemStudfioTextView.text = student.FIO
            val itemIsChecked = StudentAttendStatus(student.id, "Отсутствовал")
            studentsStatus.add(itemIsChecked)
            item.listItemCheckHereOrNotTextView.setOnCheckedChangeListener{ it, isChecked ->

                if (isChecked) {
                    studentsStatus.remove(itemIsChecked)
                    itemIsChecked.status = "Присутствовал"
                    studentsStatus.add(itemIsChecked)
                }
                else {
                    studentsStatus.remove(itemIsChecked)
                    itemIsChecked.status = "Отсутствовал"
                    studentsStatus.add(itemIsChecked)
                }

            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val student = students[position]
        holder.bindItem(student)
    }
    fun getCheckedItems(): List<StudentAttendStatus>{
        return studentsStatus
    }
}
