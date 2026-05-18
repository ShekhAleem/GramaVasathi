package com.gramavasathi.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gramavasathi.app.data.model.Booking
import com.gramavasathi.app.data.model.ChecklistItem
import com.gramavasathi.app.data.model.FarmStay
import com.gramavasathi.app.data.model.SampleData
import com.gramavasathi.app.data.repository.FarmStayRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.launch

class GramaVasathiViewModel : ViewModel() {

    private val repository = FarmStayRepository()

    // ── Farm Stays ──
    private val _farmStays = MutableStateFlow<List<FarmStay>>(emptyList())
    val farmStays: StateFlow<List<FarmStay>> = _farmStays.asStateFlow()

    private val _filteredFarmStays = MutableStateFlow<List<FarmStay>>(emptyList())
    val filteredFarmStays: StateFlow<List<FarmStay>> = _filteredFarmStays.asStateFlow()

    private val _selectedFarmStay = MutableStateFlow<FarmStay?>(null)
    val selectedFarmStay: StateFlow<FarmStay?> = _selectedFarmStay.asStateFlow()

    // ── Filter ──
    private val _selectedActivity = MutableStateFlow<String?>(null)
    val selectedActivity: StateFlow<String?> = _selectedActivity.asStateFlow()

    // ── Checklist ──
    private val _checklistItems = MutableStateFlow(SampleData.checklistItems.map { it.copy() })
    val checklistItems: StateFlow<List<ChecklistItem>> = _checklistItems.asStateFlow()

    val hostReadinessScore: StateFlow<Int> = _checklistItems.map { items ->
        val completed = items.filter { it.isCompleted }.sumOf { it.points }
        val total = items.sumOf { it.points }
        if (total > 0) (completed * 100) / total else 0
    }.stateIn(viewModelScope, SharingStarted.Eagerly, 0)

    // ── Booking ──
    private val _bookingResult = MutableStateFlow<String?>(null)
    val bookingResult: StateFlow<String?> = _bookingResult.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    // ── Search text ──
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    init {
        loadFarmStays()
    }

    fun loadFarmStays() {
        viewModelScope.launch {
            _isLoading.value = true
            repository.getFarmStays().collect { stays ->
                _farmStays.value = stays
                applyFilter()
                _isLoading.value = false
            }
        }
    }

    fun selectActivity(activity: String?) {
        _selectedActivity.value = activity
        applyFilter()
    }

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
        applyFilter()
    }

    private fun applyFilter() {
        var result = _farmStays.value
        val activity = _selectedActivity.value
        val query = _searchQuery.value.lowercase()

        if (!activity.isNullOrEmpty()) {
            result = result.filter { it.activities.contains(activity) }
        }
        if (query.isNotEmpty()) {
            result = result.filter {
                it.name.lowercase().contains(query) ||
                it.village.lowercase().contains(query) ||
                it.district.lowercase().contains(query)
            }
        }
        _filteredFarmStays.value = result
    }

    fun selectFarmStay(farmStay: FarmStay) {
        _selectedFarmStay.value = farmStay
    }

    fun toggleChecklistItem(itemId: Int) {
        _checklistItems.value = _checklistItems.value.map { item ->
            if (item.id == itemId) item.copy(isCompleted = !item.isCompleted) else item
        }
    }

    fun makeBooking(
        farmStayId: String,
        farmStayName: String,
        guestName: String,
        checkIn: String,
        checkOut: String,
        guests: Int,
        totalPrice: Int
    ) {
        viewModelScope.launch {
            _isLoading.value = true
            val booking = Booking(
                farmStayId = farmStayId,
                farmStayName = farmStayName,
                guestName = guestName,
                checkIn = checkIn,
                checkOut = checkOut,
                guests = guests,
                totalPrice = totalPrice
            )
            val result = repository.saveBooking(booking)
            _bookingResult.value = if (result.isSuccess) "SUCCESS" else "FAILED"
            _isLoading.value = false
        }
    }

    fun clearBookingResult() {
        _bookingResult.value = null
    }
}
