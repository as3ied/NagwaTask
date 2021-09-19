package com.example.nagwatask.main.ui

import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.nagwatask.databinding.ActivityMainBinding
import com.example.nagwatask.main.App
import com.example.nagwatask.main.adapters.MainAdapter
import com.example.nagwatask.main.dataclass.Video
import com.example.nagwatask.main.downloadservice.DownloadService
import com.example.nagwatask.main.listeners.OnUrlClickListener
import javax.inject.Inject


class MainActivity : AppCompatActivity(), OnUrlClickListener {
    private val onComplete: BroadcastReceiver = object : BroadcastReceiver()
    {
        override fun onReceive(p0: Context?, p1: Intent?)
        {
            val video = p1!!.getParcelableExtra<Video>("video")
            val progress = p1!!.getIntExtra("progress", 0)
            val status = p1!!.getStringExtra("status")
                adapter.setStatus(status, video.id, progress)

        }

    }
    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var viewModel: MainViewModel
    val adapter = MainAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        (applicationContext as App).appComponent.inject(this)

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.recyclerview.adapter = adapter
        val observer = androidx.lifecycle.Observer<List<Video>> { videos ->
            adapter.setMovieList(videos)
            val intent = Intent(DownloadService.REQUEST_STATUS)
            sendBroadcast(intent)

        }
        viewModel.vidList.observe(this, observer)
        viewModel.errorMessage.observe(this, { message ->

        }
        )
        viewModel.getAllVids()
    }

    override fun onResume()
    {
        super.onResume()
        registerReceiver(onComplete, IntentFilter(DownloadService.DOWNLOAD_STATUS))
    }

    override fun onPause()
    {
        super.onPause()
        unregisterReceiver(onComplete)
    }

    override fun onUrlClicked(video: Video) {
        val intent = Intent(this, DownloadService::class.java)
        intent.putExtra("video", video)
        if (Build.VERSION.SDK_INT >= 26) {
            this.startForegroundService(intent)
        } else {
            this.startService(intent)
        }
    }



}