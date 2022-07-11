package com.example.flowdevice

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.flowdevice.databinding.FragmentDevicesBinding

class DevicesFragment : Fragment(R.layout.fragment_devices) {

    private lateinit var binding: FragmentDevicesBinding
    private val viewModel: DevicesViewModel by viewModels()
    private val adapter by lazy {
        DevicesAdapter(requireContext(), listOf(), onClick = {
            viewModel.onDeviceClick(it)
        })
    } //адаптер(активити, список)

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

            override fun onQueryTextChange(query: String?): Boolean {
                viewModel.onNewSearch(query)
                return false
            }
        })
        viewModel.state.observe(viewLifecycleOwner) { devices ->
            adapter.devices = devices  // Передать в адаптер новый список
            adapter.notifyDataSetChanged() // Сказать адаптеру перерисоваться
        }
    }
}
