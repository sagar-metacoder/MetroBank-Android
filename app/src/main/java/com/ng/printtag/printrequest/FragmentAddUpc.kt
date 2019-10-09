package com.ng.printtag.printrequest

import android.widget.SeekBar
import com.ng.printtag.R
import com.ng.printtag.base.BaseFragment
import com.ng.printtag.databinding.FragmentAddUpcBinding


class FragmentAddUpc : BaseFragment<FragmentAddUpcBinding>() {
    private lateinit var binding: FragmentAddUpcBinding
    override fun initFragment() {
        binding = getFragmentDataBinding()
        init()
        handleClick()
    }

    private fun init() {
        binding.seekBar.progress = 1
        binding.tvValue.text = binding.seekBar.progress.toString()

        binding.seekBar.max = 24


        binding.seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {

            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                binding.tvValue.text = progress.toString()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {


            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
            }
        })

    }

    private fun handleClick() {
        binding.ivMinus.setOnClickListener {
            binding.seekBar.progress = binding.seekBar.progress - 1
        }
        binding.ivPlus.setOnClickListener {
            binding.seekBar.progress = binding.seekBar.progress + 1

        }
    }

    override fun getLayoutId(): Int = R.layout.fragment_add_upc
}