package com.example.cinema.model.services

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.cinema.R

const val NAME_MSG = "MSG"
const val TAGM = "MessageBroadcastReceiver"

class MessageReceiver : BroadcastReceiver() {

    private var messageId = 0

    // Сюда приходит широковещательное оповещение
    override fun onReceive(context: Context, intent: Intent) {
// Получить параметр сообщения
        var message = intent.getStringExtra(NAME_MSG)
        if (message == null) {
            message = ""
        }
        Log.d(TAGM, message)
        // создать нотификацию
        val builder = NotificationCompat.Builder(context, "2")
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle(context.resources.getString(R.string.new_request_for_movie))
            .setContentText(message)
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(messageId++, builder.build())
    }


}