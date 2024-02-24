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
import ru.kondrashen.attendanciescoutapp.databinding.ListUsersOzGroupsBinding
import ru.kondrashen.attendanciescoutapp.repository.data_class.Group

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class ThirdGroupFragment : Fragment() {

    private var _binding: ListUsersOzGroupsBinding? = null
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

        _binding = ListUsersOzGroupsBinding.inflate(inflater, container, false)
        updateUI()
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.oGroup.setOnClickListener{
            findNavController().navigate(R.id.action_ThirdFragment_to_FirstFragment)
        }
        binding.zGroup.setOnClickListener{
            findNavController().navigate(R.id.action_ThirdFragment_to_SecondFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    private fun updateUI(){
        dataGroup.getGroupsOZ().observe(requireActivity()) {
            groups = it as MutableList<Group>
            adapter = ListGroupAdapter(groups, findNavController())
            binding.listGroup.adapter = adapter
        }

    }
}