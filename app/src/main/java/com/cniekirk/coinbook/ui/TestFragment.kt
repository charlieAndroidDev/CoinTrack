package com.cniekirk.coinbook.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.cniekirk.coinbook.R

class TestFragment : Fragment() {

    companion object {
        fun newInstance() : TestFragment {
            val instance = lazy { TestFragment() }
            return instance.value
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_test, container, false)
    }

}