package com.changs.navigationdemo.ui.main

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.changs.navigationdemo.R
import com.changs.navigationdemo.databinding.MainFragmentBinding

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private var _binding : MainFragmentBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MainFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        binding.button.setOnClickListener {
/*            Navigation.findNavController(it).navigate(
                R.id.action_mainFragment_to_secondFragment
                아래 코드로 수정
            )*/

            val action : MainFragmentDirections.ActionMainFragmentToSecondFragment =
                MainFragmentDirections.actionMainFragmentToSecondFragment()

            action.setMessage(binding.userText.text.toString())
            Navigation.findNavController(it).navigate(action)
        }

        //binding.button.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_mainFragment_to_secondFragment, null))
    }

}