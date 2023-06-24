package com.zikrandmehr.firebaseandroid.landing_page.sign_in

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.zikrandmehr.firebaseandroid.R
import com.zikrandmehr.firebaseandroid.databinding.FragmentSignInBinding
import com.zikrandmehr.firebaseandroid.utils.navigateWithDefaultAnimation

class SignInFragment : Fragment() {

    private var _binding: FragmentSignInBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignInBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
    }

    private fun setupViews() {
        binding.apply {
            toolbar.setNavigationOnClickListener { findNavController().navigateUp() }
            etEmail.setText("android01@new.com")
            etPassword.setText("Welcome1.")
            btnSignIn.setOnClickListener { signIn() }
        }
    }

    private fun signIn() {
        val email = binding.etEmail.text.toString()
        val password = binding.etPassword.text.toString()
        if (email.isBlank() || password.isBlank()) return

        Firebase.auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) navigateToHomeFragment()
                else showSignInErrorAlert()
            }
    }

    private fun navigateToHomeFragment() {
        val directions = SignInFragmentDirections.actionSignInFragmentToHomeFragment()
        findNavController().navigateWithDefaultAnimation(directions)
    }

    private fun showSignInErrorAlert() {
        val builder = AlertDialog.Builder(requireContext())

        builder.setMessage(getText(R.string.sign_in_error))
        builder.setPositiveButton(getText(R.string.ok)) { dialog, _ ->
            dialog.dismiss()
        }
        builder.create().show()
    }
}