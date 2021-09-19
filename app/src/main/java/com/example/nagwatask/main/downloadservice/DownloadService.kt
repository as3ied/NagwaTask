package com.example.nagwatask.main.downloadservice

import android.R
import android.app.*
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.example.nagwatask.main.dataclass.Video
import com.example.nagwatask.main.ui.MainActivity
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import java.util.concurrent.TimeUnit


class DownloadService : Service() {
    val queue: ArrayList<Video> = ArrayList()
    val compledetedQueue: ArrayList<Video> = ArrayList()
    var downloadingVideo: Video? = null
    val requestReceiver = object : BroadcastReceiver() {
        override fun onReceive(p0: Context?, p1: Intent?) {
            compledetedQueue.onEach { video ->
                val intent = Intent(DOWNLOAD_STATUS)
                intent.putExtra("progress", video.progress)
                intent.putExtra("video", video)
                intent.putExtra("status", "Video downloaded successfully")
                sendBroadcast(intent)
            }
            queue.onEach { video ->
                val intent = Intent(DOWNLOAD_STATUS)
                intent.putExtra("progress", video.progress)
                intent.putExtra("video", video)
                if (video.progress < 0)
                    intent.putExtra("status", "Pending")
                else intent.putExtra(
                    "status",
                    if (video.progress >= 100) "Video downloaded successfully" else "Video is downloading..(${video.progress})%"
                )
                sendBroadcast(intent)
            }
        }

    }

    fun enqueu(video: Video) {
        var isDOwnloadedBefore = false
        queue.onEach {
            if (it.id == video.id) {
                isDOwnloadedBefore = true
                return@onEach
            }
        }
        compledetedQueue.onEach {
            if (it.id == video.id) {
                isDOwnloadedBefore = true
                return@onEach
            }
        }
        if (isDOwnloadedBefore)
            return
        queue.add(video)
        val intent = Intent(DOWNLOAD_STATUS)
        intent.putExtra("progress", 0)
        intent.putExtra("video", video)
        intent.putExtra("status", "Pending")
        sendBroadcast(intent)
        startDownload()

    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        val input = intent.getParcelableExtra<Video>("video")
        enqueu(input)
        createNotificationChannel(this)
        startForeground(1, getNotification(this))
        //do heavy work on a background thread
        //stopSelf();
        return START_NOT_STICKY
    }

    override fun onCreate() {
        super.onCreate()
        try {
            registerReceiver(requestReceiver, IntentFilter(REQUEST_STATUS))
        } catch (e: Exception) {
        }
    }

    val dispobals: CompositeDisposable = CompositeDisposable()
    fun startDownload() {
        if (downloadingVideo != null)
            return
        if (queue.isEmpty()) {
            return
        } else {
            downloadingVideo = queue[0]
            queue.removeAt(0)
        }
        val endProgress = 100
        var startProgress = 0
        dispobals.add(Observable.fromCallable {
            startProgress++
        }.flatMap { progress ->
            Observable.just(progress)
        }.delay(200, TimeUnit.MILLISECONDS)
            .repeatUntil { startProgress > endProgress }
            .subscribe { progress ->
                val intent = Intent(DOWNLOAD_STATUS)
                intent.putExtra("progress", progress)
                intent.putExtra("video", downloadingVideo)
                downloadingVideo?.progress = progress
                if (progress == 100) {
                    if (downloadingVideo != null)
                        compledetedQueue.add(downloadingVideo!!)
                    downloadingVideo = null
                    startDownload()
                }
                intent.putExtra(
                    "status",
                    if (progress >= 100) "Video downloaded successfully" else "Video is downloading..($progress)%"
                )
                sendBroadcast(intent)
            })

    }

    override fun onDestroy() {
        super.onDestroy()
        dispobals.dispose()
        unregisterReceiver(requestReceiver)
    }

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    companion object {
        const val DOWNLOAD_STATUS = "com.aya.downloader"
        const val REQUEST_STATUS = "com.aya.downloader.request_status"

        fun getNotification(context: Context): Notification {
            createNotificationChannel(context)
            val notificationIntent = Intent(context, MainActivity::class.java)
            val pendingIntent = PendingIntent.getActivity(
                context,
                0, notificationIntent, 0
            )
            val notification: Notification = NotificationCompat.Builder(context, "download_channel")
                .setContentTitle("Foreground Service")
                .setContentText("Download service is running in background")
                .setSmallIcon(R.drawable.ic_notification_overlay)
                .setContentIntent(pendingIntent)
                .build()
            return notification
        }

        fun createNotificationChannel(context: Context) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val serviceChannel = NotificationChannel(
                    "download_channel",
                    "Download Service Channel",
                    NotificationManager.IMPORTANCE_DEFAULT
                )
                val manager: NotificationManager = context.getSystemService(
                    NotificationManager::class.java
                )
                manager.createNotificationChannel(serviceChannel)
            }
        }
    }
}