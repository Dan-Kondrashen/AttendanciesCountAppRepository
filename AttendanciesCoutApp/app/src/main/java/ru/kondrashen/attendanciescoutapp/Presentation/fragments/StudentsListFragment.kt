package ru.kondrashen.attendanciescoutapp.Presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.kondrashen.attendanciescoutapp.Domain.MainViewModel
import ru.kondrashen.attendanciescoutapp.Presentation.adapters.ListGroupAdapter
import ru.kondrashen.attendanciescoutapp.Presentation.adapters.ListStudentsAdapter
import ru.kondrashen.attendanciescoutapp.R
import ru.kondrashen.attendanciescoutapp.databinding.ListStudentsInGroupBinding
import ru.kondrashen.attendanciescoutapp.repository.converters.DateConverter
import ru.kondrashen.attendanciescoutapp.repository.data_class.AddAttendances
import ru.kondrashen.attendanciescoutapp.repository.data_class.Group
import ru.kondrashen.attendanciescoutapp.repository.data_class.Student
import ru.kondrashen.attendanciescoutapp.repository.data_class.StudentAttendStatus
import ru.kondrashen.attendanciescoutapp.repository.data_class.relations.GroupWithSubjects
import java.sql.Timestamp
import java.util.Calendar
import java.util.Date


class StudentsListFragment : Fragment() {

    private var _binding: ListStudentsInGroupBinding? = null
    private val dataGroup: MainViewModel by viewModels()
    private var adapterSpinner: ArrayAdapter<String>? = null
    private var students: MutableList<Student> = mutableListOf()
    private var addAttendances: MutableList<AddAttendances> = mutableListOf()
    private var adapter: ListStudentsAdapter? = null
    private val cal =Calendar.getInstance()


    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = ListStudentsInGroupBinding.inflate(inflater, container, false)
        cal.set(Calendar.YEAR, 1988);
        cal.set(Calendar.MONTH, Calendar.JANUARY);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        updateUI()
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val userId = arguments?.getInt("id")
        binding.addData.setOnClickListener {
            val result = adapter?.getCheckedItems() as List<StudentAttendStatus>
            println(result)

            for (i in result)
                addAttendances.add(AddAttendances(1,i.studentId,userId!!, i.status, "2024-02-25"))
            dataGroup.postAttendance(addAttendances, userId!!)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    private fun updateUI(){
        val userId = arguments?.getInt("id")
        val groupId = arguments?.getInt("groupId")
        dataGroup.getStudentsInRoom(groupId!!).observe(requireActivity()){
            students = it as MutableList<Student>
            adapter = ListStudentsAdapter(students)
            binding.listStudents.adapter = adapter
        }
        dataGroup.getSubjectsInRoom(groupId).observe(requireActivity()){
            val subjectsGr = it as MutableList<GroupWithSubjects>
            val result = ArrayList<String>()
            for (i in subjectsGr.first().subjects) {
                result.add(i.name)
            }
            adapterSpinner = ArrayAdapter(requireActivity(), R.layout.spiner_dropdown_item, result)
            binding.subjectSelector.adapter = adapterSpinner
        }
//        dataGroup.getStudentsInGroup().observe(requireActivity()) {
////            groups = it as MutableList<Group>
//            students = it as MutableList<Student>
//            adapter = ListStudentsAdapter(students)
//            binding.listStudents.adapter = adapter
//        }

    }
}