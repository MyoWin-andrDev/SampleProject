package com.example.khinsampleapp.screen.userdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.khinsampleapp.repository.Repository
import com.example.khinsampleapp.repository.network.model.UserInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserDetailViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private var _userInfo = MutableLiveData<UserInfo>()
    val userInfo: LiveData<UserInfo> get() = _userInfo

    fun loadData(userId: String) {
        viewModelScope.launch {
            val userListDataFlow = repository.getUserInfo(userId)
                .flowOn(Dispatchers.IO)
                .catch { error ->
                    error.printStackTrace()
                    // Possible improvement: Error handling
                }

            userListDataFlow.collect { userInfo ->
                _userInfo.value = userInfo
            }
        }
    }

}