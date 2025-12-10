package com.bubba.express.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bubba.express.data.repository.ProductRepository
import com.bubba.express.domain.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val productRepository : ProductRepository
) : ViewModel()