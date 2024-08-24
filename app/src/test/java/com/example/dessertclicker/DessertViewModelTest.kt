package com.example.dessertclicker

import com.example.dessertclicker.data.Datasource.dessertList
import com.example.dessertclicker.data.DessertUiState
import com.example.dessertclicker.ui.DessertViewModel
import junit.framework.Assert.assertEquals
import org.junit.Test

class DessertViewModelTest {
    private var viewModel =  DessertViewModel()
    private var testUiState = DessertUiState()
    @Test
    fun onDessertClicked_updates_UI_state_correctly() {
        // Set the initial state with some values
        val initialUiState = DessertUiState(
            currentDessertIndex = 0,
            dessertsSold = 0,
            revenue = 0,
            currentDessertPrice = dessertList[0].price,
            currentDessertImageId = dessertList[0].imageId
        )

        // Simulate clicking the dessert
        viewModel.onDessertClicked()
        val nextDessertIndex = testDetermineDessertIndex(initialUiState.dessertsSold)
        // Expected UI state after the click
        val expectedUiState = initialUiState.copy(

            currentDessertIndex = nextDessertIndex,
            dessertsSold = 1,
            revenue = dessertList[initialUiState.currentDessertIndex].price,
            currentDessertPrice = dessertList[initialUiState.currentDessertIndex].price,
            currentDessertImageId = dessertList[initialUiState.currentDessertIndex].imageId
        )

        // Assert that the UI state has been updated correctly
        assertEquals(expectedUiState, viewModel.dessertUiState.value)
    }

    private fun testDetermineDessertIndex(dessertsSold: Int): Int {
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