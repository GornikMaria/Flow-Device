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
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.flowdevice.databinding.FragmentDevicesBinding



class DevicesAdapter(
    context: Context,
    var devices: List<DevicesViewModel.Device>
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

    private fun getItem(position: Int): DevicesViewModel.Device =
        devices[position]  //метод, где по позиции будем возваращать данные об устройстве
}


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

    val mViewModel: DevicesViewModel by viewModels()
    val adapter by lazy { DevicesAdapter(requireContext(), listOf()) } //адаптер(активити, список)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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
                mViewModel.onNewSearch(query)
                return false
            }
        })
//        mViewModel = ViewModelProvider(this).get(DevicesViewModel::class.java)

        mViewModel.liveData.observe(viewLifecycleOwner) { devices ->
            adapter.devices = devices  // Передать в адаптер новый список
            adapter.notifyDataSetChanged() // Сказать адаптеру перерисоваться
        }




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
            outRect.left = offset
            outRect.right = offset / 2
        } else {
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