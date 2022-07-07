package com.example.flowdevice

import android.content.Context
import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.SearchView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.flowdevice.databinding.FragmentDevicesBinding

data class Device(
    val name: String,
    val OS: String,
    val avatar: Int
)

fun generateDevices(): List<Device> {
    return listOf(
        Device(
            "iPhone SE 2020",
            "iOS 15.2",
            R.drawable.iphone_se,
        ),
        Device(
            "iPhone 12",
            "iOS 16",
            R.drawable.iphone_12,
        ),
        Device(
            "Xiaomi 9t",
            "Android 12",
            R.drawable.xiaomi_9t,
        ),
        Device(
            "Samsung A3",
            "Android 5",
            R.drawable.samsung_a3,
        ),
        Device(
            "Pixel 6",
            "Android 13",
            R.drawable.pixel_6_google_,
        ),
        Device(
            "iPad",
            "iOS 14",
            R.drawable.ipad14,
        ),
        Device(
            "Samsung Galaxy A32",
            "Android 11",
            R.drawable.samsung_galaxy_a32,
        ),
        Device(
            "ipad 4",
            "iOS 10",
            R.drawable.ipad_4,
        ),
        Device(
            "Xiaomi Redmi Note 8 Pro",
            "Android 9",
            R.drawable.xiaomi_redmi_note_8_pro,
        )
    )
}

class DevicesAdapter(
    context: Context,
    var devices: List<Device>
) : RecyclerView.Adapter<DeviceViewHolder>() {

    private val inflater: LayoutInflater =
        LayoutInflater.from(context) //LayoutInflater для создания View

    override fun getItemCount(): Int = devices.size   //сколько будет элементов в списке

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeviceViewHolder {
        val view = inflater.inflate(R.layout.list_item_device, parent, false)
        return DeviceViewHolder(view)  //Создание View
    }

    override fun onBindViewHolder(holder: DeviceViewHolder, position: Int) {
        holder.bind(getItem(position)) //вызывается каждый раз, чтобы обновлять данные
    }

    private fun getItem(position: Int): Device =
        devices[position]  //метод, где по позиции будем возваращать данные об устройстве
}


class DeviceViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val name: TextView = itemView.findViewById(R.id.name)
    private val os: TextView = itemView.findViewById(R.id.OS)
    private val avatar: ImageView = itemView.findViewById(R.id.avatar)

    fun bind(device: Device) {
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


class DevicesFragment : Fragment(R.layout.fragment_devices) {

    lateinit var binding: FragmentDevicesBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDevicesBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val devices = generateDevices()  //список девайсов
        val adapter = DevicesAdapter(requireContext(), devices) //адаптер(активити, список)
        val gridLayoutManager = GridLayoutManager(
            context,
            2,
            GridLayoutManager.VERTICAL,
            false
        ) //выкладываем View занимается layoutManager
        binding.DeviceList.adapter = adapter
        binding.DeviceList.layoutManager = gridLayoutManager
        binding.DeviceList.addItemDecoration(CharacterItemDecoration(53))

        binding.searchview.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean = false

            // 0. Проверить на пустую строку, если пустая или null - отобразить исходный список
            // 1. Пройтись по всем именам, отобрать те, которые подходят строке без учета регистра (.lowerCase() / toLowerCase())
            // 2. Сказать, что список обновлен
            override fun onQueryTextChange(query: String?): Boolean {
                return if (query == null || query == "") {
                    adapter.devices = generateDevices()
                    adapter.notifyDataSetChanged()
                    false
                } else {
                    val queryLower = query.lowercase()
                    val filteredDevices = devices.filter { device ->
                        device.name
                            .lowercase()
                            .contains(queryLower)
                    }
                    adapter.devices = filteredDevices
                    adapter.notifyDataSetChanged()
                    false
                }
            }
        })
    }
}


fun main() {
    val dogs = arrayOf("Jack", "Max", "Mini")
    val filteredArray = dogs.filter { dog ->
        false
    }

    filteredArray.forEach { dog ->
        println(dog)
    }
}



class CharacterItemDecoration(private val offset: Int) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)

        val number = parent.getChildAdapterPosition(view)

        val layoutParams = view.layoutParams as GridLayoutManager.LayoutParams
        if (layoutParams.spanIndex % 2 == 0) {
            //outRect.top = offset
            outRect.left = offset
            outRect.right = offset / 2
        } else {
            //outRect.top = offset
            outRect.right = offset
            outRect.left = offset / 2
        }
        if (number > 1) {
            outRect.top = offset / 2
        } else {
            outRect.top = offset
        }
    }
}