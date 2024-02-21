package ru.kondrashen.attendanciescoutapp.Presentation.activitys

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import ru.kondrashen.attendanciescoutapp.Presentation.fragments.LoginFragment
import ru.kondrashen.attendanciescoutapp.R
import ru.kondrashen.attendanciescoutapp.databinding.ActivityMainlogBinding
import ru.kondrashen.attendanciescoutapp.databinding.ActivityMainBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainlogBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val sharedPref = getSharedPreferences("AuthPref", Context.MODE_PRIVATE)
        binding = ActivityMainlogBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val fm = supportFragmentManager.beginTransaction()
        fm.add(R.id.login_content, LoginFragment())
        fm.commit()

    }
}