package com.example.cinema.view


import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.example.cinema.R
import com.google.android.material.snackbar.Snackbar

class Extensions {
    companion object {

        fun showSnackbar(
            view: View,
            text: String,
            actionText: String,
            action: (View) -> Unit,
            length: Int = Snackbar.LENGTH_INDEFINITE
        ) {

            Snackbar.make(view, text, length).setAction(actionText, action).show()

        }

        fun showToast(
            view: View,
            text: String?,
            length: Int = Toast.LENGTH_LONG
        ) {

            Toast.makeText(
                view.context,
                view.resources.getString(R.string.looking_for) + " " + text, length
            ).show()

        }

        fun showAlertDialog(
            fragment: Fragment,
            title: Int,
            message: Int,
            icon: Int,
            positive_button: Int
        ) {
            val builder = AlertDialog.Builder(fragment.requireContext())

            builder.run {
                setTitle(title)
                //set message for alert dialog
                setMessage(message)
                setIcon(icon)
                setPositiveButton(positive_button) { dialogInterface, which ->
                    builder
                }
                show()
            }

        }

        fun divider(
            recyclerView: RecyclerView
        ) {
            val dividerItemDecoration =
                DividerItemDecoration(recyclerView.context, RecyclerView.HORIZONTAL)
            dividerItemDecoration.setDrawable(recyclerView.resources
                .getDrawable(R.drawable.divider_drawable))
            recyclerView.addItemDecoration(dividerItemDecoration)
        }


    }
}