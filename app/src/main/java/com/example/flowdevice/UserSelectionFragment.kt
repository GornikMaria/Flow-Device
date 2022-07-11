package com.example.flowdevice

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.flowdevice.databinding.FragmentUserSelectionBinding

class UserSelectionFragment : Fragment(R.layout.fragment_user_selection) {

    private lateinit var binding: FragmentUserSelectionBinding
    private val viewModel: UserViewModel by viewModels()
    private val adapter by lazy { UserAdapter(requireContext()) } //адаптер(активити, список)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUserSelectionBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        binding.UserList.adapter = adapter
        binding.UserList.layoutManager = LinearLayoutManager(context)

        viewModel.liveData.observe(viewLifecycleOwner) { users ->
            adapter.users = users  // Передать в адаптер новый список
            adapter.notifyDataSetChanged()
        }
    }

    class UserAdapter(context: Context) : RecyclerView.Adapter<UsersViewHolder>() {

        var users: List<UserViewModel.User> = listOf()

        private val inflater: LayoutInflater =
            LayoutInflater.from(context) //LayoutInflater для создания View

        override fun getItemCount(): Int = users.size   //сколько будет элементов в списке

        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): UsersViewHolder {
            val view = inflater.inflate(R.layout.list_item_user, parent, false)
            return UsersViewHolder(view)  //Создание View
        }

        override fun onBindViewHolder(
            holder: UsersViewHolder,
            position: Int
        ) {
            holder.bind(getItem(position)) //вызывается каждый раз, чтобы обновлять данные
        }

        private fun getItem(position: Int): UserViewModel.User =
            users[position]  //метод, где по позиции будем возваращать данные об устройстве
    }

}
