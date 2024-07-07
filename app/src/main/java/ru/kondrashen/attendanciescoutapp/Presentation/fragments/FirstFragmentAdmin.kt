package ru.kondrashen.attendanciescoutapp.Presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.navigation.NavArgs
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import ru.kondrashen.attendanciescoutapp.Domain.MainViewModel
import ru.kondrashen.attendanciescoutapp.Presentation.adapters.ListGroupAdapter
import ru.kondrashen.attendanciescoutapp.Presentation.adapters.ListGroupAdapter2
import ru.kondrashen.attendanciescoutapp.Presentation.adapters.ListStudentsAdminAdapter
import ru.kondrashen.attendanciescoutapp.repository.data_class.Group
import ru.kondrashen.attendanciescoutapp.R
import ru.kondrashen.attendanciescoutapp.databinding.ListStudentsForAdminBinding
import ru.kondrashen.attendanciescoutapp.repository.data_class.StudentAttendanceCount


class FirstFragmentAdmin : Fragment() {

    private var _binding: ListStudentsForAdminBinding? = null
    private val dataGroup: MainViewModel by viewModels()
    private var groups: MutableList<Group> = mutableListOf()
    private var students: MutableList<StudentAttendanceCount> = mutableListOf()
    private var adapter: ListStudentsAdminAdapter? = null
    private var isDataLoadFinished: Boolean = false

    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View {
        _binding = ListStudentsForAdminBinding.inflate(inflater, container, false)
        updateUI()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val userId = arguments?.getInt("id")
        binding.searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(newText: String?): Boolean {
                adapter!!.filter.filter(newText)
                return true
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                adapter?.filter?.filter(newText)
                return true
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

//    override fun onResume() {
//        super.onResume()
//        updateUI()
//    }

    private fun updateUI() {
        val userId = arguments?.getInt("id")
        dataGroup.getAdminAttendance().observe(requireActivity()){
            students = it as MutableList<StudentAttendanceCount>
            println("Результат для адаптера $students")
            if(students.isEmpty()) {
                dataGroup.getAdminStartDataServ().observe(requireActivity()){

                    students = it as MutableList<StudentAttendanceCount>
                    println("Data $students")
                    adapter = ListStudentsAdminAdapter(students, findNavController())
                }
            }
            adapter = ListStudentsAdminAdapter(students, findNavController())
            binding.listStudents.adapter = adapter
        }

//        dataGroup.getGroupsO().observe(requireActivity()) {
//            groups = it as MutableList<Group>
//            if(groups.isEmpty()) {
//                dataGroup.getGroupsOServ(1).observe(requireActivity()){
//                    groups = it as MutableList<Group>
//                    adapter = ListGroupAdapter2(groups, userId!!, findNavController())
//                }
//            }
//            adapter = ListGroupAdapter2(groups, userId!!, findNavController())
//            binding.listStudents.adapter = adapter
//        }
    }
}