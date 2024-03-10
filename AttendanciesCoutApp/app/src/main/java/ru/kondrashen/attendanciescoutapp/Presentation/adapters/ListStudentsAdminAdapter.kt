package ru.kondrashen.attendanciescoutapp.Presentation.adapters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import ru.kondrashen.attendanciescoutapp.R
import ru.kondrashen.attendanciescoutapp.databinding.ListItemStudentForAdminBinding
import ru.kondrashen.attendanciescoutapp.repository.data_class.StudentAttendanceCount

class ListStudentsAdminAdapter(students: MutableList<StudentAttendanceCount>, findNavController: NavController): RecyclerView.Adapter<ListStudentsAdminAdapter.ViewHolder>(), Filterable {
    private var students: MutableList<StudentAttendanceCount> = mutableListOf()
    private var studentsFilter: MutableList<StudentAttendanceCount> = mutableListOf()
    private val navController: NavController
    init {
        this.students = students
        this.studentsFilter = students
        this.navController = findNavController
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val binding = ListItemStudentForAdminBinding.inflate(LayoutInflater.from(parent.context),
            parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return students.size
    }
    inner class ViewHolder(private val item: ListItemStudentForAdminBinding) : RecyclerView.ViewHolder(item.root), View.OnClickListener{
        private lateinit var student: StudentAttendanceCount

        fun bindItem(student: StudentAttendanceCount){
            this.student = student
            item.listItemStudfioTextView.text = student.FIO
            item.listItemStudattendanciesTextView.text = student.summ.toString()
            item.listItemHideId.text = student.studentId.toString()
            item.root.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val bundle = Bundle()
            bundle.putInt("attendSum", student.summ)
            bundle.putInt("studId", Integer.parseInt(item.listItemHideId.text.toString()))
            println("Проверка ${Integer.parseInt(item.listItemHideId.text.toString())}")
            navController.navigate(R.id.action_FirstFragmentAdmin_to_SecondFragmentAdmin, bundle)
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val student = students[position]
        holder.bindItem(student)
    }

    override fun getFilter(): Filter {
        return  object  : Filter(){
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val resultOfFiltration = FilterResults()
                if(constraint == null || constraint.length <0){
                    resultOfFiltration.count = studentsFilter.size
                    resultOfFiltration.values = studentsFilter
                }else{
                    var str = constraint.toString().lowercase()
                    var studentAttendanceCounts = mutableListOf<StudentAttendanceCount>()
                    for(item in studentsFilter){
                        if(item.FIO.lowercase().contains(str)||item.summ.toString().contains(str)){
                            studentAttendanceCounts.add(item)
                        }
                    }
                    resultOfFiltration.count = studentAttendanceCounts.size
                    resultOfFiltration.values = studentAttendanceCounts
                }
                return resultOfFiltration
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                students =  results!!.values as MutableList<StudentAttendanceCount>
                notifyDataSetChanged()
            }

        }
    }

}
