package com.example.flowdevice

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


data class State(
    val friends: List<String>,
    val isRefreshing: Boolean,
    val error: String? = null
)


class FriendsViewModel() : ViewModel() {

    val state = MutableLiveData<ConfirmationState>()

    init {
//        state.observe(null, { state ->
//            state.user
//            state.device
//        })
    }


}


data class ConfirmationState(
    val user: String?,
    val device: String?
)


class UserViewModel : ViewModel() {

    data class User(
        val name: String
    )

    val liveData = MutableLiveData<List<User>>()

    private val users = generateUsers()

    init {
        liveData.value = users
    }

    private fun generateUsers(): List<User> {
        return listOf(
            User(
                "Алексей Минай",
            ),
            User(
                "Иван Андрейшев",
            ),
            User(
                "Артем Коротков",
            ),
            User(
                "Исмагилова С",
            ),
            User(
                "Яровлов",
            ),
            User(
                "Никифорова Е",
            ),
            User(
                "Григорьева Е",
            ),
            User(
                "Шабалкина А",
            ),
            User(
                "Татьянина М",
            ),
            User(
                "Исмагилов",
            ),
            User(
                "Иванова",
            ),
            User(
                "Петров О",
            ),
        )
    }
}