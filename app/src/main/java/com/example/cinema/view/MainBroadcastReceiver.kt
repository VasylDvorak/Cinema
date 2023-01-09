package com.example.cinema.view

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.example.cinema.R

class MainBroadcastReceiver : BroadcastReceiver() {


    override fun onReceive(context: Context, intent: Intent) {

            Extensions.showToast(context,
                context.resources.getString(R.string.change_connection))
        }
    }

