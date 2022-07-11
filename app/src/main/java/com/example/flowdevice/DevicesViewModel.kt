package com.example.flowdevice

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DevicesViewModel : ViewModel() {

    data class Device(
        val name: String,
        val OS: String,
        val avatar: Int
    )

    // Состояние экрана
    val state = MutableLiveData<List<Device>>()

    private val devices = generateDevices()

    init {
        println("Вью модель создалась")
        state.value = devices
    }

    fun onNewSearch(query: String?) {
        if (query == null || query == "") {
            state.value = generateDevices()

        } else {
            val queryLower = query.lowercase()
            val filteredDevices = devices.filter { device ->
                device.name
                    .lowercase()
                    .contains(queryLower)
            }
            state.value = filteredDevices

        }

    }

    fun onDeviceClick(id: String) {
        // Открытие страницы с девайсами
    }

    private fun generateDevices(): List<Device> {
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
}