package com.example.flowdevice

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class UsersViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val name: TextView = itemView.findViewById(R.id.name)

    fun bind(user: UserViewModel.User) {
        name.text = user.name
    }
}
