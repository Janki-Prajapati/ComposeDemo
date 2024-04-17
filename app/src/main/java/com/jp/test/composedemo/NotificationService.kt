package com.jp.test.composedemo

import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import kotlin.random.Random

class NotificationService(private val context: Context) {

    private val notificationManager = context.getSystemService(NotificationManager::class.java)

    fun showBasicNotification(code: String) {
        val notification = NotificationCompat.Builder(context, "compose_notification")
            .setContentTitle("Compose OTP")
            .setContentText("Your One Time Password is $code to change your password. Do not share it with anybody.")
            .setSmallIcon(R.drawable.ic_notification)
            .setPriority(NotificationManager.IMPORTANCE_HIGH)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(
            Random.nextInt(),
            notification
        )
    }

}