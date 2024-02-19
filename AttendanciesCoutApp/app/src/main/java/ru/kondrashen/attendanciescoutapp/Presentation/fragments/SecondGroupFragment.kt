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
import ru.kondrashen.attendanciescoutapp.R
import ru.kondrashen.attendanciescoutapp.databinding.ListUsersZGroupsBinding
import ru.kondrashen.attendanciescoutapp.repository.data_class.Group

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondGroupFragment : Fragment() {

    private var _binding: ListUsersZGroupsBinding? = null
    private val dataGroup: MainViewModel by viewModels()
    private var groups: MutableList<Group> = mutableListOf()
    private var adapter: ListGroupAdapter? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = ListUsersZGroupsBinding.inflate(inflater, container, false)
        updateUI()
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.oGroup.setOnClickListener {
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment2)
        }
        binding.ozGroup.setOnClickListener {
            findNavController().navigate(R.id.action_SecondFragment_to_ThirdFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    private fun updateUI(){
        dataGroup.getGroupsZ().observe(requireActivity()) {
            groups = it as MutableList<Group>
            if(groups.isEmpty()) {
                dataGroup.getGroupsZServ().observe(requireActivity()){
                    groups = it as MutableList<Group>
                    adapter = ListGroupAdapter(groups)
                    binding.listGroup.adapter = adapter
                }
            }
            adapter = ListGroupAdapter(groups)
            binding.listGroup.adapter = adapter
        }

    }
}