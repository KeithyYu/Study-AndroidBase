package com.wanggk.bottomnavigation

import android.animation.ObjectAnimator
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView

class FirstFragment : Fragment() {

    companion object {
        fun newInstance() = FirstFragment()
    }

    private lateinit var viewModel: FirstViewModel

    private lateinit var mImageView: ImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_first, container, false)
        mImageView = view.findViewById(R.id.imageView)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(FirstViewModel::class.java)
        mImageView.rotation = viewModel.rotationPosition.toFloat()
        val propertyAnimator = ObjectAnimator.ofFloat(mImageView, "rotation", 0f, 0f)
        propertyAnimator.duration = 500

        mImageView.setOnClickListener { view ->
            if (propertyAnimator.isRunning) {
                return@setOnClickListener
            }
            propertyAnimator.setFloatValues(mImageView.rotation, mImageView.rotation + 100)
            viewModel.rotationPosition += 100
            propertyAnimator.start()
        }
    }

}