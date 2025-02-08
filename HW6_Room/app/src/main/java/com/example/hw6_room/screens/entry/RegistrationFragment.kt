package com.example.hw6_room.screens.entry

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.hw6_room.MainActivity
import com.example.hw6_room.R
import com.example.hw6_room.databinding.FragmentRegistrationBinding
import com.example.hw6_room.di.ServiceLocator
import com.example.hw6_room.utils.SessionManager
import com.example.hw6_room.utils.UserValidator
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch


class RegistrationFragment : Fragment() {

    private var viewBinding: FragmentRegistrationBinding? = null
    private val userRepository = ServiceLocator.getUserRepository()
    private lateinit var sessionManager: SessionManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentRegistrationBinding.inflate(inflater, container, false)
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
            registerButton.setOnClickListener {
                val username = nameEditText.text.toString()
                val email = emailEditText.text.toString()
                val password = passwordEditText.text.toString()
                val userValidator = UserValidator()

                if (!userValidator.isValidEmail(email)) {
                    Snackbar.make(requireView(), R.string.invalid_email, Snackbar.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                if (!userValidator.isValidPassword(password)) {
                    Snackbar.make(requireView(), R.string.invalid_password, Snackbar.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                lifecycleScope.launch {
                    hideKeyboard()
                    val existingUser = userRepository.getUserByEmail(email)
                    if (existingUser != null) {
                        Snackbar.make(requireView(), R.string.user_exists, Snackbar.LENGTH_SHORT)
                            .show()
                    } else {

                        userRepository.registerUser(username, email, password)
                        val user = userRepository.loginUser(email, password)
                        if (user != null) {
                            sessionManager.saveUserSession(user.id, user.email)
                            Snackbar.make(
                                requireView(),
                                R.string.registration_successful,
                                Snackbar.LENGTH_SHORT
                            )
                                .show()

                            findNavController().navigate(R.id.action_registrationFragment_to_mainScreenFragment)
                        }

                    }
                }

            }

            loginScreenButton.setOnClickListener {
                findNavController().navigate(R.id.action_registrationFragment_to_loginFragment)
            }
        }
    }

    private fun hideKeyboard() {
        val inputMethodManager = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        val currentFocusView = view?.rootView?.findFocus()
        currentFocusView?.let {
            inputMethodManager.hideSoftInputFromWindow(it.windowToken, 0)
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        viewBinding = null
    }
}