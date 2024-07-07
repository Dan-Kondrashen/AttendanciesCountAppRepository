package ru.kondrashen.attendanciescoutapp.Presentation.fragments

import android.icu.text.SimpleDateFormat
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import ru.kondrashen.attendanciescoutapp.Domain.MainViewModel
import ru.kondrashen.attendanciescoutapp.Presentation.adapters.ListStudentsAdapter
import ru.kondrashen.attendanciescoutapp.R
import ru.kondrashen.attendanciescoutapp.databinding.ListStudentsInGroupBinding
import ru.kondrashen.attendanciescoutapp.repository.data_class.AddAttendances
import ru.kondrashen.attendanciescoutapp.repository.data_class.Student
import ru.kondrashen.attendanciescoutapp.repository.data_class.StudentAttendStatus
import ru.kondrashen.attendanciescoutapp.repository.data_class.relations.GroupWithSubjects

import java.util.Calendar
import java.util.Date
import java.util.Locale


class StudentsListFragment : Fragment() {

    private var _binding: ListStudentsInGroupBinding? = null
    private val dataGroup: MainViewModel by viewModels()
    private var adapterSpinner: ArrayAdapter<String>? = null

    private var postedDate = "2024-02-25"
    private var students: MutableList<Student> = mutableListOf()
    private var addAttendances: MutableList<AddAttendances> = mutableListOf()
    private var subjectId: Int = 0
    private val formater = SimpleDateFormat("yyyy-MM-dd", Locale.ROOT)
    private var adapter: ListStudentsAdapter? = null
    private val cal =Calendar.getInstance()
    companion object {
        const val GET_DATE = "selectedDate"
        const val POST_LIST = "postList"
        private const val CHOSE_DATE = "DateTime"
    }


    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = ListStudentsInGroupBinding.inflate(inflater, container, false)
        updateUI()
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val userId = arguments?.getInt("id")
        val groupId = arguments?.getInt("groupId")

        binding.addData.setOnClickListener {
            if (binding.subjectSelector.selectedItem == null){
                Toast.makeText(context, "Нужно выбрать предмет перед добавлением данных по посещаемости", Toast.LENGTH_LONG).show()
            }
            else {
                dataGroup.getSubjectsId(binding.subjectSelector.selectedItem.toString())
                    .observe(requireActivity()) {
                        subjectId = it

                        val result = adapter?.getCheckedItems() as List<StudentAttendStatus>
                        println(result)

                        for (i in result)
                            addAttendances.add(AddAttendances(subjectId,i.studentId,userId!!, i.status, postedDate))
                        dataGroup.postAttendance(addAttendances, userId!!)
                    }
            }


        }
        binding.datePicker.setOnClickListener {
            val manager =parentFragmentManager
            val dialog = SelectDateFragment()
            dialog.show(manager, CHOSE_DATE)
            manager.setFragmentResultListener(GET_DATE,this){
                requestKey, bundle ->
                postedDate = bundle.getString(requestKey).toString()
                binding.dateTextView.text = postedDate
            }

        }

        val bundle = Bundle()
        println(bundle.getString("postList"))
        bundle.putInt("id", userId!!)
        bundle.putInt("groupId", groupId!!)

        binding.graph.setOnClickListener{
            dataGroup.getAttendGroupInfoFromServer(userId)
            findNavController().navigate(R.id.action_StudentFragment_to_GraphFragment, bundle)
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


        binding.dateTextView.text = formater.format(Date())
//        dataGroup.getStudentsInGroup().observe(requireActivity()) {
////            groups = it as MutableList<Group>
//            students = it as MutableList<Student>
//            adapter = ListStudentsAdapter(students)
//            binding.listStudents.adapter = adapter
//        }

    }
}