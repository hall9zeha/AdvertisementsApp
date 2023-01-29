package com.barryzeha.bannersapp.ui.slideshow

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.barryzeha.bannersapp.databinding.FragmentNativeAdBinding


import com.barryzeha.bannersapp.ui.adapters.ImageAdapter
import com.barryzeha.bannersapp.ui.viewModel.ImagesViewModel

class NativeAdFragment : Fragment() {

    private var _binding: FragmentNativeAdBinding? = null
    private val viewModel:ImagesViewModel by viewModels()
    private lateinit var adapter:ImageAdapter
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentNativeAdBinding.inflate(inflater, container, false)
        val root: View = binding.root
        setUpAdapter()
        viewModel.callRandomList(20)
        setUpObservers()

        return root
    }
    private fun setUpAdapter(){
        adapter= ImageAdapter(requireActivity())
        //adapter.clear()
        binding.rvImages.apply {
            setHasFixedSize(true)
            layoutManager = StaggeredGridLayoutManager(2,LinearLayoutManager.VERTICAL)
            adapter = this@NativeAdFragment.adapter
        }

    }
    private fun setUpObservers(){
        viewModel.getRandomList().observe(viewLifecycleOwner){
            binding.pbNativeFragment.visibility=View.GONE
            binding.tvLoading.visibility=View.GONE
            adapter.add(it)
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}