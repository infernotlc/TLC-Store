package com.tlh.afinal.screens.in_app.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.tlh.afinal.databinding.FragmentProfileBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ProfileViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater, container, false).apply {
            viewModel = this@ProfileFragment.viewModel
            lifecycleOwner = viewLifecycleOwner
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.profileSpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    val selectedProfile = parent.getItemAtPosition(position).toString().toInt()
                    viewModel.fetchUser(selectedProfile)
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // Do nothing
                }
            }

        viewModel.user.observe(viewLifecycleOwner) { user ->
            binding.apply {
                fullNameTextView.text = "${user.firstName} ${user.lastName}"
                usernameTextView.text = user.username
                genderTextView.text = user.gender
                bloodGroupTextView.text = user.bloodGroup
                heightTextView.text = user.height.toString()
                weightTextView.text = user.weight.toString()
                eyeColorTextView.text = user.eyeColor
                hairTextView.text = user.hair.toString()
                ipTextView.text = user.ip
                macAddressTextView.text = user.macAddress
                universityTextView.text = user.university
                companyTextView.text = user.company.toString()
                bankTextView.text = user.bank.toString()
                cryptoTextView.text = user.crypto.toString()
                einTextView.text = user.ein
                ssnTextView.text = user.ssn
                userAgentTextView.text = user.userAgent
                roleTextView.text = user.role
                emailTextView.text = user.email
                phoneTextView.text = user.phone
                birthDateTextView.text = user.birthDate
                addressTextView.text =
                    "${user.address.address}, ${user.address.city}, ${user.address.state}, ${user.address.postalCode}, ${user.address.country}"
                Glide.with(profileImageView.context)
                    .load(user.image)
                    .into(profileImageView)
                editProfile.setOnClickListener {
                    findNavController().navigate(
                        ProfileFragmentDirections.actionProfileFragmentToEditProfileFragment(
                            user.id
                        )
                    )
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
