package com.gramavasathi.app.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.gramavasathi.app.data.model.Booking
import com.gramavasathi.app.data.model.FarmStay
import com.gramavasathi.app.data.model.SampleData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

class FarmStayRepository {

    private val db: FirebaseFirestore? by lazy {
        try {
            FirebaseFirestore.getInstance()
        } catch (e: Exception) {
            null
        }
    }
    private val farmStaysCollection = db?.collection("farmstays")
    private val bookingsCollection = db?.collection("bookings")

    // ── Get all farm-stays (with offline fallback to sample data) ──
    fun getFarmStays(): Flow<List<FarmStay>> = flow {
        try {
            val snapshot = farmStaysCollection?.get()?.await()
            val farmStays = snapshot?.documents?.mapNotNull { doc ->
                doc.toObject(FarmStay::class.java)?.copy(id = doc.id)
            }
            emit(if (farmStays.isNullOrEmpty()) SampleData.farmStays else farmStays)
        } catch (e: Exception) {
            // Fallback to sample data when Firebase is not configured
            emit(SampleData.farmStays)
        }
    }

    // ── Search/filter farm-stays by activity ──
    fun getFarmStaysByActivity(activity: String): Flow<List<FarmStay>> = flow {
        try {
            val snapshot = farmStaysCollection
                ?.whereArrayContains("activities", activity)
                ?.get()?.await()
            val farmStays = snapshot?.documents?.mapNotNull { doc ->
                doc.toObject(FarmStay::class.java)?.copy(id = doc.id)
            }
            emit(if (farmStays.isNullOrEmpty()) SampleData.farmStays.filter {
                it.activities.contains(activity)
            } else farmStays)
        } catch (e: Exception) {
            emit(SampleData.farmStays.filter { it.activities.contains(activity) })
        }
    }

    // ── Get single farm-stay ──
    suspend fun getFarmStayById(id: String): FarmStay? {
        return try {
            val doc = farmStaysCollection?.document(id)?.get()?.await()
            doc?.toObject(FarmStay::class.java)?.copy(id = doc.id)
                ?: SampleData.farmStays.find { it.id == id }
        } catch (e: Exception) {
            SampleData.farmStays.find { it.id == id }
        }
    }

    // ── Save a booking (simulated) ──
    suspend fun saveBooking(booking: Booking): Result<String> {
        return try {
            val docRef = bookingsCollection?.add(booking)?.await()
            if (docRef != null) {
                Result.success(docRef.id)
            } else {
                Result.success("SIMULATED_${System.currentTimeMillis()}")
            }
        } catch (e: Exception) {
            // Simulate success when Firebase not configured
            Result.success("SIMULATED_${System.currentTimeMillis()}")
        }
    }

    // ── Seed sample data to Firestore (for admin/testing) ──
    suspend fun seedSampleData() {
        SampleData.farmStays.forEach { farmStay ->
            try {
                farmStaysCollection?.document(farmStay.id)?.set(farmStay)?.await()
            } catch (e: Exception) {
                // Ignore
            }
        }
    }
}
