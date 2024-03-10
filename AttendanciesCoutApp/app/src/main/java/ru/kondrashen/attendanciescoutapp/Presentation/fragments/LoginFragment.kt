package ru.kondrashen.attendanciescoutapp.Presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import ru.kondrashen.attendanciescoutapp.Domain.LoginViewModel
import ru.kondrashen.attendanciescoutapp.Presentation.activitys.MainActivity
import ru.kondrashen.attendanciescoutapp.R
import ru.kondrashen.attendanciescoutapp.databinding.FragmentLoginBinding
import ru.kondrashen.attendanciescoutapp.repository.data_class.responces.LoginResponse

import ru.kondrashen.attendanciescoutapp.repository.data_class.UserLog

class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val dataLogin: LoginViewModel by viewModels()
    private var adapter: ArrayAdapter<String>? = null
    private var rolesName: MutableList<String> = mutableListOf()
    private var result: LoginResponse = LoginResponse("",0, "", "")

    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        updateUI()
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.login.setOnClickListener {
            dataLogin.clearDatabase()
            dataLogin.getRolesId(binding.userRole.selectedItem.toString())
                .observe(requireActivity()) {
                    val roleId = it
                    result = dataLogin.goToAccaunt(UserLog(binding.etEmail.text.toString(), binding.etPassword.text.toString(), roleId))
                    println("Вот: $result")
                    binding.result.text = result.status
                    if (result.status == "Вы успешно вошли в систему!") {
                        val intent = MainActivity.newIntent(requireActivity(), result.id, binding.userRole.selectedItem.toString())
                        startActivity(intent)
                    }
                }

        }
        binding.losePass.setOnClickListener{

        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun updateUI(){
        dataLogin.getRolesFromRoom().observe(requireActivity()){
            rolesName = it as MutableList<String>
            if (rolesName.isEmpty()) {
                dataLogin.getRolesFromServer().observe(requireActivity()){
                    rolesName = it as MutableList<String>
                    adapter = ArrayAdapter(requireActivity(), R.layout.spiner_dropdown_item, rolesName)
                }
            }
            adapter = ArrayAdapter(requireActivity(), R.layout.spiner_dropdown_item, rolesName)
            binding.userRole.adapter = adapter
        }
    }
}