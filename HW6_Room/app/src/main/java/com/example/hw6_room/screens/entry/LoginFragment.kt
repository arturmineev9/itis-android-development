package com.example.hw6_room.screens.entry

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.hw6_room.MainActivity
import com.example.hw6_room.R
import com.example.hw6_room.databinding.FragmentLoginBinding


class LoginFragment : Fragment() {

    private var viewBinding: FragmentLoginBinding? = null

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
        initButtons()
    }

    private fun initButtons() {
        viewBinding?.run {
            loginButton.setOnClickListener {
                val email = emailEditText.text.toString()
                val password = passwordEditText.text.toString()
                findNavController().navigate(R.id.action_loginFragment_to_mainScreenFragment)
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