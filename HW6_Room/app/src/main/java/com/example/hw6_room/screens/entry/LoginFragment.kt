package com.example.hw6_room.screens.entry

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.hw6_room.MainActivity
import com.example.hw6_room.R
import com.example.hw6_room.databinding.FragmentLoginBinding
import com.example.hw6_room.db.repository.UserRepository
import com.example.hw6_room.di.ServiceLocator
import com.example.hw6_room.utils.SessionManager
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch


class LoginFragment : Fragment() {

    private var viewBinding: FragmentLoginBinding? = null
    private val userRepository = ServiceLocator.getUserRepository()
    private lateinit var sessionManager: SessionManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentLoginBinding.inflate(inflater, container, false)
        return viewBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as MainActivity).setBottomNavigationVisibility(false)
        sessionManager = SessionManager(requireContext())
        initButtons()
    }

    private fun initButtons() {
        viewBinding?.run {
            loginButton.setOnClickListener {
                val email = emailEditText.text.toString()
                val password = passwordEditText.text.toString()
                lifecycleScope.launch {
                    val user = userRepository.loginUser(email, password)
                    if (user != null) {
                        sessionManager.saveUserSession(user.id, user.email)
                        val message = getString(R.string.welcome_message, user.username)
                        Snackbar.make(requireView(), message, Snackbar.LENGTH_SHORT).show()
                        findNavController().navigate(R.id.action_loginFragment_to_mainScreenFragment)
                    } else {
                        Snackbar.make(requireView(), R.string.wrong_login, Snackbar.LENGTH_SHORT).show()
                    }
                }
            }
            registrationScreenButton.setOnClickListener {
                findNavController().navigate(R.id.action_loginFragment_to_registrationFragment)
            }
        }
    }




    override fun onDestroyView() {
        super.onDestroyView()
        (activity as MainActivity).setBottomNavigationVisibility(true)
        viewBinding = null
    }
}