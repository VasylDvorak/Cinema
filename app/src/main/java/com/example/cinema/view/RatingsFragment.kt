package com.example.cinema.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.cinema.R


class RatingsFragment : Fragment(), View.OnClickListener {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

      //  val button1 = findViewById<Button>(R.id.button1)
      //  val button2 = findViewById<Button>(R.id.button2)
      //  button1.setOnClickListener(this)
     //   button2.setOnClickListener(this)

        return inflater.inflate(R.layout.fragment_ratings, container, false)
    }

    companion object {

        fun newInstance(param1: String, param2: String) =
            RatingsFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }

    override fun onClick(v: View?) {



        when (v?.getId()) {
        //    R.id.button1 -> showRatingsMovie()
        //    R.id.button2 -> secondFun()
        }
        TODO("Not yet implemented")
    }
    private fun firstFun() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun secondFun() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
    private fun showRatingsMovie(){}
}