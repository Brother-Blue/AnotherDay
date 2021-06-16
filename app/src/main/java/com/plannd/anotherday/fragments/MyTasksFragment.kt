package com.plannd.anotherday.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.plannd.anotherday.R

/**
 * A simple [Fragment] subclass.
 * Use the [MyTasksFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MyTasksFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_tasks, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment MyTasks.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() = MyTasksFragment().apply {
                arguments = Bundle().apply {}
            }
    }
}