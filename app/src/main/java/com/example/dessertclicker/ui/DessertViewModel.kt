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

    fun onDessertClicked() {
       _dessertUiState.update { cupcakeUiState ->
           val dessertsSold = cupcakeUiState.dessertsSold + 1
           val nextDessertIndex = determineDessertIndex(dessertsSold)
           cupcakeUiState.copy(
               currentDessertIndex = nextDessertIndex,
               revenue = cupcakeUiState.revenue + cupcakeUiState.currentDessertPrice,
               dessertsSold = dessertsSold,
               currentDessertImageId = dessertList[nextDessertIndex].imageId,
               currentDessertPrice = dessertList[nextDessertIndex].price
           )
       }
    }

    private fun determineDessertIndex(dessertsSold: Int): Int {
        var dessertIndex = 0
        for (index in dessertList.indices) {
            if (dessertsSold >= dessertList[index].startProductionAmount) {
                dessertIndex = index
            } else {
                // The list of desserts is sorted by startProductionAmount. As you sell more
                // desserts, you'll start producing more expensive desserts as determined by
                // startProductionAmount. We know to break as soon as we see a dessert who's
                // "startProductionAmount" is greater than the amount sold.
                break
            }
        }
        return dessertIndex
    }
}