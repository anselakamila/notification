package com.idn.ninanadia.tugasnotification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import androidx.core.app.NotificationCompat
import com.idn.ninanadia.tugasnotification.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var notificationManager: NotificationManager? = null
    private val channel_id = "channel_1"

    private lateinit var binding: ActivityMainBinding
    private lateinit var countDownTimer: CountDownTimer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        createNotificationalChannel(channel_id, "countdown","Notif when countdown and")

        binding.btnStart.setOnClickListener{
            countDownTimer.start()
        }

        countDownTimer = object : CountDownTimer(10000,1000){
            override fun onTick(p0: Long) {
                binding.timer.text = getString(R.string.time_reamining,p0 / 1000)


            }
            override fun onFinish() {
                displayNotification()
            }
        }


    }
    private fun displayNotification() {
        val notifictionId = 45
        val alarmSound: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notifiction = NotificationCompat.Builder(this@MainActivity,channel_id)
            .setContentTitle("Countdown Timer")
            .setContentText("Your timer end")
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setAutoCancel(true)
            .setSound(alarmSound)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()
        notificationManager?.notify(notifictionId,notifiction)
    }

    private fun createNotificationalChannel(id:String, name: String, channelDescription: String){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            val importance = NotificationManager.IMPORTANCE_HIGH

            val channel = NotificationChannel(id, name, importance).apply {
                description = channelDescription
            }
            notificationManager?.createNotificationChannel(channel)
        }
    }
}
