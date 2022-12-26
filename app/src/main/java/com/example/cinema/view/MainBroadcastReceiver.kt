package com.example.cinema.view

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class MainBroadcastReceiver : BroadcastReceiver() {


    override fun onReceive(context: Context, intent: Intent) {

        if (intent.action.equals("android.intent.action.CONNECTIVITY_ACTION", true)) {
            var message = "СООБЩЕНИЕ ОТ СИСТЕМЫ " + intent.action
            Toast.makeText(
                context, message,
                Toast.LENGTH_LONG
            ).show()
        }
    }
}
