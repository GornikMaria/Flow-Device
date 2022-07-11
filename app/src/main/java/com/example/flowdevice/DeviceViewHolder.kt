package com.example.flowdevice

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class DeviceViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val name: TextView = itemView.findViewById(R.id.name)
    private val os: TextView = itemView.findViewById(R.id.OS)
    private val avatar: ImageView = itemView.findViewById(R.id.avatar)

    fun bind(device: DevicesViewModel.Device) {
        name.text = device.name
        os.text = device.OS
        loadImageAsync(device.avatar, avatar)
    }

    private fun loadImageAsync(avatar: Int, avatarView: ImageView) {
        Glide.with(itemView)
            .load(avatar)
            .into(avatarView)
    }
}