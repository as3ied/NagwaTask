package com.example.nagwatask.main.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.nagwatask.databinding.VidItemBinding
import com.example.nagwatask.main.dataclass.Video
import com.example.nagwatask.main.listeners.OnUrlClickListener

class MainAdapter(private val listener: OnUrlClickListener) :
    RecyclerView.Adapter<MainViewHolder>() {

    var vids = mutableListOf<Video>()
    fun setMovieList(vids: List<Video>) {
        this.vids = vids.toMutableList()
        notifyDataSetChanged()
    }

    fun setStatus(status: String, videoId: Int, progress: Int) {
        for (i in vids.indices){
            if (vids[i].id == videoId) {
                vids[i].progress = progress
                vids[i].state = status
                notifyItemChanged(i)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        val binding = VidItemBinding.inflate(inflater, parent, false)
        return MainViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val video = vids[position]
        holder.bind(video)
        holder.binding.img.setOnClickListener {
            listener.onUrlClicked(video)
        }
    }

    override fun getItemCount(): Int {
        return vids.size
    }
}

class MainViewHolder(val binding: VidItemBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(
        videoItem: Video) {
        binding.videoItem = videoItem


    }
}
