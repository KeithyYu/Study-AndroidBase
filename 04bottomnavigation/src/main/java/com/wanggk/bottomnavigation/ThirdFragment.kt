package com.wanggk.bottomnavigation

import android.animation.ObjectAnimator
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView

class ThirdFragment : Fragment() {

    companion object {
        fun newInstance() = ThirdFragment()
    }

    private lateinit var viewModel: ThirdViewModel

    private lateinit var mImageView: ImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_third, container, false)
        mImageView = view.findViewById(R.id.imageView)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ThirdViewModel::class.java)

        mImageView.translationX = viewModel.dx
        val translationX = ObjectAnimator.ofFloat(mImageView, "translationX", 0f, 0f)
        translationX.duration = 500

        mImageView.setOnClickListener {
            if (translationX.isRunning) {
                return@setOnClickListener
            }
            translationX.setFloatValues(mImageView.x, mImageView.x + 100)
            viewModel.dx += 100
            translationX.start()
        }
    }

}