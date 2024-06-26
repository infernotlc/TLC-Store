package com.tlh.afinal.screens.in_app.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.tlh.afinal.databinding.FragmentEditProfileBinding
import com.tlh.afinal.model.in_app_service.EditProfile
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditProfileFragment : Fragment() {

    private var _binding: FragmentEditProfileBinding? = null
    private val binding get() = _binding!!

    private val viewModel: EditProfileViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEditProfileBinding.inflate(inflater, container, false).apply {
            viewModel = this@EditProfileFragment.viewModel
            lifecycleOwner = viewLifecycleOwner
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val userId = arguments?.getInt("id") ?: 1
        viewModel.fetchUser(userId)

        binding.saveButton.setOnClickListener {
            val updatedProfile = EditProfile(
                id = userId,
                username = binding.usernameEditText.text.toString(),
                email = binding.emailEditText.text.toString(),
                gender = binding.genderEditText.text.toString(),
            )
            viewModel.updateUser(updatedProfile)
        }

        viewModel.user.observe(viewLifecycleOwner) { user ->
            // Kullanıcı verilerini UI'ya yükleme işlemleri burada yapılabilir
            binding.apply {
                usernameEditText.setText(user.username)
                emailEditText.setText(user.email)
                genderEditText.setText(user.gender)
                // Diğer alanları da aynı şekilde yükleyin
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
