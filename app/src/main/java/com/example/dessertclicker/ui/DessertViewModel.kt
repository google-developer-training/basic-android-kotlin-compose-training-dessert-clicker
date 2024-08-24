package com.example.dessertclicker.ui

import androidx.lifecycle.ViewModel
import com.example.dessertclicker.data.DessertUiState
import com.example.dessertclicker.data.Datasource.dessertList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class DessertViewModel : ViewModel() {

    private val _dessertUiState = MutableStateFlow(DessertUiState())
    val dessertUiState: StateFlow<DessertUiState> = _dessertUiState.asStateFlow()

    fun onDessertClicked(){
        _dessertUiState.update { cupCakeUiState ->
            val dessertsSold: Int = if (cupCakeUiState.currentDessertIndex >= 13){
                cupCakeUiState.dessertsSold
            }else{
                cupCakeUiState.dessertsSold + 1
            }
            val nextDessertIndex = determineDessertIndex(dessertsSold)
            if(cupCakeUiState.currentDessertIndex >= 13){
                cupCakeUiState.copy(
                    currentDessertIndex = nextDessertIndex,
                    dessertsSold = dessertsSold,
                    revenue = cupCakeUiState.revenue,
                    currentDessertPrice = dessertList[nextDessertIndex].price,
                    currentDessertImageId = dessertList[nextDessertIndex].imageId
                )
            }else{
                cupCakeUiState.copy(
                    currentDessertIndex = nextDessertIndex,
                    dessertsSold = dessertsSold,
                    revenue = cupCakeUiState.revenue + cupCakeUiState.currentDessertPrice,
                    currentDessertPrice = dessertList[nextDessertIndex].price,
                    currentDessertImageId = dessertList[nextDessertIndex].imageId
                )
            }

        }

    }

    private fun determineDessertIndex(dessertsSold: Int): Int {
        var dessertIndex = 0
        for (index in dessertList.indices) {
            if (dessertsSold >= dessertList[index].startProductionAmount) {
                dessertIndex = index
            }
        }
        return if (dessertIndex <= 12) dessertIndex
        else 13
    }
}