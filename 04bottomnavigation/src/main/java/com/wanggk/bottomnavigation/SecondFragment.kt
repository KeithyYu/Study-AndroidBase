package com.wanggk.bottomnavigation

import android.animation.ObjectAnimator
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView

class SecondFragment : Fragment() {

    companion object {
        fun newInstance() = SecondFragment()
    }

    private lateinit var viewModel: SecondViewModel

    private lateinit var mImageView: ImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_second, container, false)
        mImageView = view.findViewById(R.id.imageView)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(SecondViewModel::class.java)
        mImageView.scaleX = viewModel.mScaleX
        mImageView.scaleY = viewModel.mScaleY
        val scaleX = ObjectAnimator.ofFloat(mImageView, "scaleX", 0f, 0f)
        val scaleY = ObjectAnimator.ofFloat(mImageView, "scaleY", 0f, 0f)
        scaleX.duration = 500
        scaleY.duration = 500
        mImageView.setOnClickListener {
            if (!scaleX.isRunning) {
                scaleX.setFloatValues(mImageView.scaleX + 0.1f)
                viewModel.mScaleX += 0.1f
                scaleX.start()
            }

            if (!scaleY.isRunning) {
                scaleY.setFloatValues(mImageView.scaleY + 0.1f)
                viewModel.mScaleY += 0.1f
                scaleY.start()
            }
        }
    }

}