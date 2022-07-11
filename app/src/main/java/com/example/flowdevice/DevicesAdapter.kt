package com.example.flowdevice

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class DevicesAdapter(
    context: Context,
    var devices: List<DevicesViewModel.Device>,
    val onClick: (String) -> Unit
) : RecyclerView.Adapter<DeviceViewHolder>() {

    private val inflater: LayoutInflater =
        LayoutInflater.from(context) //LayoutInflater для создания View

    override fun getItemCount(): Int = devices.size   //сколько будет элементов в списке

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeviceViewHolder {
        val view = inflater.inflate(R.layout.list_item_device, parent, false)
        view.setOnClickListener {
            onClick("id")
        }
        return DeviceViewHolder(view)  //Создание View
    }

    override fun onBindViewHolder(holder: DeviceViewHolder, position: Int) {
        holder.bind(getItem(position)) //вызывается каждый раз, чтобы обновлять данные
    }

    private fun getItem(position: Int): DevicesViewModel.Device =
        devices[position]  //метод, где по позиции будем возваращать данные об устройстве
}