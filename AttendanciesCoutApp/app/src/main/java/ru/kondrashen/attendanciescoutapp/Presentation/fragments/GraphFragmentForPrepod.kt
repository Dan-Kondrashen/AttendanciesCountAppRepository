package ru.kondrashen.attendanciescoutapp.Presentation.fragments

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet
import ru.kondrashen.attendanciescoutapp.Domain.MainViewModel
import ru.kondrashen.attendanciescoutapp.R
import ru.kondrashen.attendanciescoutapp.databinding.GroupAttendanciesGraphBinding
import ru.kondrashen.attendanciescoutapp.repository.data_class.Student
import ru.kondrashen.attendanciescoutapp.repository.data_class.relations.GroupWithSubjects

class GraphFragmentForPrepod: Fragment() {
    private var _binding: GroupAttendanciesGraphBinding? = null
    private var dataSet: MutableList<IBarDataSet> = mutableListOf()
    private var labels: MutableList<String> = mutableListOf()
    private val dataGroup: MainViewModel by viewModels()
    private var students: MutableList<Student> = mutableListOf()
    private var studentsIn: MutableList<Int> = mutableListOf() //
    private var studentsOut: MutableList<Int> = mutableListOf()
    private var adapterSpinner: ArrayAdapter<String>? = null
    private var subjectId: Int = 1
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = GroupAttendanciesGraphBinding.inflate(inflater, container, false)
        updateUI()
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Настройки гистограммы
        val ax = binding.barChartOfGroup.xAxis
        ax.granularity =1F
        ax.setCenterAxisLabels(true)
        val leftAx = binding.barChartOfGroup.axisLeft
        leftAx.valueFormatter = IndexAxisValueFormatter(labels)
        binding.barChartOfGroup.axisRight.isEnabled = false
        val items1 = mutableListOf<BarEntry>()
        val items2 = mutableListOf<BarEntry>()
        val labels = mutableListOf<String>()
        val userId = arguments?.getInt("id")
        val groupId = arguments?.getInt("groupId")
        binding.subjectSelector.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                dataGroup.getSubjectsId(binding.subjectSelector.selectedItem.toString())
                    .observe(requireActivity()) {
                        subjectId = it
                        dataSet.clear()
                        studentsIn.clear()
                        studentsOut.clear()
                        items1.clear()
                        items2.clear()
                        labels.clear()
                        students = dataGroup.getStudentsInRoomStatic(groupId!!) as MutableList<Student>
                        for (i in students) {
                            studentsIn.add(dataGroup.countStudentAttend(i.id, subjectId, "Присутствовал"))
                            studentsOut.add(dataGroup.countStudentAttend(i.id, subjectId, "Отсутствовал"))
                            labels.add(i.FIO)
                        }
                        var j =0
                        for (i in studentsIn) {
                            items1.add(BarEntry(j.toFloat(), i.toFloat()))
                            j+=2
                        }
                        val set1 = BarDataSet(items1,"Посещения")
                        set1.color = Color.BLUE
                        println(items1)
                        var k =0
                        for (i in studentsOut) {
                            items2.add(BarEntry(k.toFloat(), i.toFloat()))
                            k+=2
                        }
                        println(items2)

                        val set2 = BarDataSet(items2,"Пропуски")
                        set2.color = Color.MAGENTA

                        binding.barChartOfGroup.invalidate()
                        dataSet.add(set1)
                        dataSet.add(set2)
                        ax.valueFormatter = IndexAxisValueFormatter(labels)
                        println(BarData(dataSet))
                        binding.barChartOfGroup.apply {

                            data = BarData(dataSet)
                            data.barWidth = 0.46F
                            groupBars(0F, 0.04F, 0.02F)
                        }
                    }
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }

    }
    fun updateUI(){
        val userId = arguments?.getInt("id")?: 1
        val groupId = arguments?.getInt("groupId")?: 1
        dataGroup.getSubjectsInRoom(groupId).observe(requireActivity()){
            val subjectsGr = it as MutableList<GroupWithSubjects>
            val result = ArrayList<String>()
            for (i in subjectsGr.first().subjects) {
                result.add(i.name)
            }
            adapterSpinner = ArrayAdapter(requireActivity(), R.layout.spiner_dropdown_item, result)
            binding.subjectSelector.adapter = adapterSpinner
        }

    }

}