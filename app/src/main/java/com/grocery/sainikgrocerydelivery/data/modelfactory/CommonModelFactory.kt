package com.grocery.sainikgrocerydelivery.data.modelfactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.grocery.sainikgrocerydelivery.data.ApiHelper
import com.grocery.sainikgrocerydelivery.data.repository.MainRepository
import com.grocery.sainikgrocerydelivery.viewmodel.CommonViewModel

class CommonModelFactory(private val apiHelper: ApiHelper) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        CommonViewModel(MainRepository(apiHelper)) as T
}