package com.tlh.afinal.screens.in_app.cart

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.tlh.afinal.R
import com.tlh.afinal.databinding.FragmentCartBinding
import com.tlh.afinal.model.service.ConfigurationService
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class CartFragment : Fragment() {

    private var _binding: FragmentCartBinding? = null
    private val binding get() = _binding!!


    @Inject
    lateinit var configService: ConfigurationService

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        fetchRemoteConfig()
    }

    private fun fetchRemoteConfig() {
        lifecycleScope.launch {
            try {
                val isSuccess = configService.fetchConfiguration()
                if (isSuccess) {
                    applyRemoteConfig()
                } else {

                    Log.e("CartFragment", "Failed to fetch remote config")
                }
            } catch (e: Exception) {
                // Hata durumunu handle et
                Log.e("CartFragment", "Error fetching remote config: ${e.message}")
            }
        }
    }

    private fun applyRemoteConfig() {

        val showTaskEditButton = configService.isShowTaskEditButtonConfig

        // binding.editButton.visibility = if (showTaskEditButton) View.VISIBLE else View.GONE
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
