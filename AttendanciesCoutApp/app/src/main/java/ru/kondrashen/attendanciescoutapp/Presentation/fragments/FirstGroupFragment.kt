package ru.kondrashen.attendanciescoutapp.Presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.NavArgs
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import ru.kondrashen.attendanciescoutapp.Domain.MainViewModel
import ru.kondrashen.attendanciescoutapp.Presentation.adapters.ListGroupAdapter
import ru.kondrashen.attendanciescoutapp.Presentation.adapters.ListGroupAdapter2
import ru.kondrashen.attendanciescoutapp.repository.data_class.Group
import ru.kondrashen.attendanciescoutapp.R
import ru.kondrashen.attendanciescoutapp.databinding.ListUsersOGroupsBinding


class FirstGroupFragment : Fragment() {

    private var _binding: ListUsersOGroupsBinding? = null
    private val dataGroup: MainViewModel by viewModels()
    private var groups: MutableList<Group> = mutableListOf()
    private var adapter: ListGroupAdapter2? = null
    private var isDataLoadFinished: Boolean = false

    companion object {
        private const val BUNDLE_DATA = "loadDataFinished"
    }

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View {
        _binding = ListUsersOGroupsBinding.inflate(inflater, container, false)
        updateUI()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val userId = arguments?.getInt("id")
//        binding.infoDop.text = userId.toString()
        val bundle = Bundle()
        bundle.putInt("id", userId!!)
        println("id1 $userId")
        binding.zGroup.setOnClickListener{
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment2, bundle)
        }
        binding.ozGroup.setOnClickListener{
            findNavController().navigate(R.id.action_FirstFragment_to_ThirdFragment, bundle)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

    }
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean(BUNDLE_DATA,isDataLoadFinished)
    }

    private fun updateUI(){
        val userId = arguments?.getInt("id")
        dataGroup.getGroupsO().observe(requireActivity()) {
            groups = it as MutableList<Group>
            if(groups.isEmpty()) {
                dataGroup.getGroupsOServ(userId!!).observe(requireActivity()){
                    groups = it as MutableList<Group>
                    adapter = ListGroupAdapter2(groups, userId!!, findNavController())
                }
            }
            adapter = ListGroupAdapter2(groups, userId!!, findNavController())
            binding.listGroup.adapter = adapter
        }
    }
}