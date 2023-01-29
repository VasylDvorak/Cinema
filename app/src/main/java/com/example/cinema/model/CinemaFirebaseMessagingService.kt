package com.example.cinema.model

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.example.cinema.R
import com.example.cinema.view.geolocation_fragment.MapsFragment
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class CinemaFirebaseMessagingService : FirebaseMessagingService() {
    companion object {
        private const val PUSH_KEY_TITLE = "title"
        private const val PUSH_KEY_MESSAGE = "message"
        private const val CHANNEL_ID = "channel_id"
        private const val NOTIFICATION_ID = 37

        fun receiveTokenIfYouWant(){
            FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
                if(!task.isSuccessful){
                    return@OnCompleteListener
                }
                val token = task.result
                println("Token our notification app: "+token)
            })
        }
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        val remoteMessageData = remoteMessage.data
        if (remoteMessageData.isNotEmpty()) {
            handleDataMessage(remoteMessageData.toMap())
        }
    }


    private fun handleDataMessage(data: Map<String, String>) {
        val title = data[PUSH_KEY_TITLE]
        val message = data[PUSH_KEY_MESSAGE]
        if (!title.isNullOrBlank() && !message.isNullOrBlank()) {
            showNotification(title, message)
        }
    }

    private fun showNotification(title: String, message: String) {

        MapsFragment.newInstance(Bundle().apply { putString(MapsFragment.COUNTRY_EXTRA, message) })
        val notificationIntent = Intent(applicationContext, MapsFragment::class.java)
        val pendingIntent = PendingIntent.getActivity(applicationContext, 0,
            notificationIntent, 0)
        val notificationBuilder =
            NotificationCompat.Builder(applicationContext, CHANNEL_ID).apply {
                setSmallIcon(R.drawable.icon)
                setContentTitle(title)
                setContentText(message)
                setContentIntent(pendingIntent)
                priority = NotificationCompat.PRIORITY_DEFAULT
            }.build()

        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel(notificationManager)
        }
        notificationManager.notify(NOTIFICATION_ID, notificationBuilder)
        startForeground(1, notificationBuilder)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel(notificationManager: NotificationManager) {
        val name = getString(R.string.channel_name)
        val descriptionText = getString(R.string.chanel_description)
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
            description = descriptionText
        }
        notificationManager.createNotificationChannel(channel)
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.v("MyFirebaseMessagingService", "onNewToken" + token)
    }
}
