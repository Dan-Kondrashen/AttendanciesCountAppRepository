package ru.kondrashen.attendanciescoutapp.Presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.kondrashen.attendanciescoutapp.Domain.MainViewModel
import ru.kondrashen.attendanciescoutapp.Presentation.adapters.ListGroupAdapter
import ru.kondrashen.attendanciescoutapp.repository.data_class.Group
import ru.kondrashen.attendanciescoutapp.R
import ru.kondrashen.attendanciescoutapp.databinding.ListUsersOGroupsBinding

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstGroupFragment : Fragment() {

    private var _binding: ListUsersOGroupsBinding? = null
    private val dataGroup: MainViewModel by viewModels()
    private var groups: MutableList<Group> = mutableListOf()
    private var adapter: ListGroupAdapter? = null
    private var isDataLoadFinished: Boolean = false
    companion object {
        private const val BUNDLE_DATA = "loadDataFinished"
    }

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View {
        if (savedInstanceState != null) {
            isDataLoadFinished = savedInstanceState.getBoolean(BUNDLE_DATA, false)
        }

        _binding = ListUsersOGroupsBinding.inflate(inflater, container, false)
//        if (isDataLoadFinished) {
//            updateUI()
//        }
//        else {
//            loadData()
//        }
        updateUI()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.zGroup.setOnClickListener{
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment2)
        }
        binding.ozGroup.setOnClickListener{
            findNavController().navigate(R.id.action_FirstFragment_to_ThirdFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean(BUNDLE_DATA,isDataLoadFinished)
    }

    private fun updateUI(){
        dataGroup.getGroupsO().observe(requireActivity()) {
            groups = it as MutableList<Group>
            if(groups.isEmpty()) {
                dataGroup.getGroupsOServ().observe(requireActivity()){
                    groups = it as MutableList<Group>
                    adapter = ListGroupAdapter(groups)
                }
            }
            adapter = ListGroupAdapter(groups)
            binding.listGroup.adapter = adapter
        }
    }

//    private fun loadData(){
//        dataGroup.getData()
//        isDataLoadFinished = true
//        updateUI()
//    }
}