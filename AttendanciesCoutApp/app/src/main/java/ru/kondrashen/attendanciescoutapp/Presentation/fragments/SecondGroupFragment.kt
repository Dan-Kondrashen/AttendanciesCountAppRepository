package ru.kondrashen.attendanciescoutapp.Presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.kondrashen.attendanciescoutapp.Domain.MainViewModel
import ru.kondrashen.attendanciescoutapp.Presentation.adapters.ListGroupAdapter3
import ru.kondrashen.attendanciescoutapp.R
import ru.kondrashen.attendanciescoutapp.databinding.ListUsersZGroupsBinding
import ru.kondrashen.attendanciescoutapp.repository.data_class.Group

class SecondGroupFragment : Fragment() {

    private var _binding: ListUsersZGroupsBinding? = null
    private val dataGroup: MainViewModel by viewModels()
    private var groups: MutableList<Group> = mutableListOf()
    private var adapter: ListGroupAdapter3? = null

    // Это было нужно для того, чтобы избежать проблем с жизненым циклом фрагиентов, т. е. фрагмент когда меняется
    // может произойти утечка данных, если не обнулить биндинг в onDestroyView. Работает оно с помощью get(), который вернет САМ null, а не NullPointerExaption
    // Это защитит от утечки на этапе компеляции(создания) фрагмента, когда данные возвращаются из BackSteck и вызове onCreate типо когда происходит подгрузка и она немного замедлена
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
        val userId = arguments?.getInt("id")
        val bundle = Bundle()
        bundle.putInt("id", userId!!)
        binding.oGroup.setOnClickListener {
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment2, bundle)
        }
        binding.ozGroup.setOnClickListener {
            findNavController().navigate(R.id.action_SecondFragment_to_ThirdFragment, bundle)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    private fun updateUI(){
        val userId = arguments?.getInt("id")
        println("id2 $userId")
        dataGroup.getGroupsZ().observe(requireActivity()) {
            groups = it as MutableList<Group>

            adapter = ListGroupAdapter3(groups, userId!!, findNavController())
            binding.listGroup.adapter = adapter
        }

    }
}