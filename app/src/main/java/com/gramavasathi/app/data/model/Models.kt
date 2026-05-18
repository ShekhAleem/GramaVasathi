package com.gramavasathi.app.data.model

// ─────────────────────────────────────────
// FarmStay Model
// ─────────────────────────────────────────
data class FarmStay(
    val id: String = "",
    val name: String = "",
    val hostName: String = "",
    val village: String = "",
    val district: String = "",
    val description: String = "",
    val pricePerNight: Int = 0,
    val activities: List<String> = emptyList(),
    val imageUrl: String = "",
    val rating: Float = 0f,
    val reviewCount: Int = 0,
    val hostReadinessScore: Int = 0,
    val isVerified: Boolean = false,
    val amenities: List<String> = emptyList(),
    val availableDates: List<String> = emptyList()
)

// ─────────────────────────────────────────
// Booking Model
// ─────────────────────────────────────────
data class Booking(
    val id: String = "",
    val farmStayId: String = "",
    val farmStayName: String = "",
    val guestName: String = "",
    val checkIn: String = "",
    val checkOut: String = "",
    val guests: Int = 1,
    val totalPrice: Int = 0,
    val status: BookingStatus = BookingStatus.PENDING
)

enum class BookingStatus { PENDING, CONFIRMED, CANCELLED }

// ─────────────────────────────────────────
// Host Readiness Checklist Item
// ─────────────────────────────────────────
data class ChecklistItem(
    val id: Int,
    val category: String,
    val title: String,
    val description: String,
    val points: Int,
    var isCompleted: Boolean = false
)

// ─────────────────────────────────────────
// Sample / Mock Data
// ─────────────────────────────────────────
object SampleData {

    val activities = listOf(
        "Cow Milking", "Field Plowing", "Local Cooking",
        "Birdwatching", "Pottery Making", "Organic Farming",
        "Bullock Cart Ride", "Folk Music", "Fishing"
    )

    val farmStays = listOf(
        FarmStay(
            id = "1",
            name = "Siri Mane Farm",
            hostName = "Ramaiah & Family",
            village = "Hoskote",
            district = "Bangalore Rural",
            description = "Experience authentic Karnataka village life. Wake up to roosters, milk cows, and enjoy fresh ragi mudde cooked on a wood fire. Our century-old mango grove is a paradise.",
            pricePerNight = 1500,
            activities = listOf("Cow Milking", "Organic Farming", "Local Cooking", "Birdwatching"),
            imageUrl = "https://images.unsplash.com/photo-1500595046743-cd271d694d30?w=800",
            rating = 4.8f,
            reviewCount = 34,
            hostReadinessScore = 92,
            isVerified = true,
            amenities = listOf("Clean Sheets", "Safe Water", "Western Toilet", "Mosquito Net", "Hot Water")
        ),
        FarmStay(
            id = "2",
            name = "Nandi Bhoomi",
            hostName = "Venkatesh Gowda",
            village = "Doddaballapur",
            district = "Bangalore Rural",
            description = "Nestled at the foothills of Nandi Hills. Perfect for sunrise treks, organic strawberry picking, and stargazing on clear nights. Traditional Vokkaligas hospitality.",
            pricePerNight = 2000,
            activities = listOf("Field Plowing", "Birdwatching", "Folk Music", "Bullock Cart Ride"),
            imageUrl = "https://images.unsplash.com/photo-1464822759023-fed622ff2c3b?w=800",
            rating = 4.6f,
            reviewCount = 21,
            hostReadinessScore = 85,
            isVerified = true,
            amenities = listOf("Clean Sheets", "Safe Water", "Western Toilet", "Hot Water")
        ),
        FarmStay(
            id = "3",
            name = "Malnad Mavina Mane",
            hostName = "Subbaiah & Kamala",
            village = "Sakleshpur",
            district = "Hassan",
            description = "Surrounded by coffee and cardamom estates. Pluck fresh coffee berries, learn traditional honey extraction, and relish Malnad cuisine — authentic as it gets.",
            pricePerNight = 2500,
            activities = listOf("Local Cooking", "Pottery Making", "Fishing", "Organic Farming"),
            imageUrl = "https://images.unsplash.com/photo-1586348943529-beaae6c28db9?w=800",
            rating = 4.9f,
            reviewCount = 57,
            hostReadinessScore = 97,
            isVerified = true,
            amenities = listOf("Clean Sheets", "Safe Water", "Western Toilet", "Hot Water", "Mosquito Net", "Wi-Fi")
        ),
        FarmStay(
            id = "4",
            name = "Cauvery Delta Stay",
            hostName = "Krishnamurthy",
            village = "Srirangapatna",
            district = "Mandya",
            description = "On the banks of the Cauvery, surrounded by sugarcane fields. Fish in the river, visit the historic Tipu Sultan's island, and enjoy fresh jaggery straight from the mill.",
            pricePerNight = 1200,
            activities = listOf("Fishing", "Bullock Cart Ride", "Local Cooking"),
            imageUrl = "https://images.unsplash.com/photo-1506905925346-21bda4d32df4?w=800",
            rating = 4.4f,
            reviewCount = 18,
            hostReadinessScore = 78,
            isVerified = false,
            amenities = listOf("Clean Sheets", "Safe Water", "Mosquito Net")
        )
    )

    val checklistItems = listOf(
        ChecklistItem(1, "Hygiene", "Clean Bed Sheets", "Fresh, washed bed sheets for each guest", 10),
        ChecklistItem(2, "Hygiene", "Safe Drinking Water", "Filtered or boiled water available 24/7", 15),
        ChecklistItem(3, "Sanitation", "Western Toilet", "At least one western-style toilet for guests", 15),
        ChecklistItem(4, "Hygiene", "Clean Bathroom", "Bathroom cleaned daily with soap & towels provided", 10),
        ChecklistItem(5, "Safety", "Mosquito Nets", "Mosquito nets on all windows and beds", 10),
        ChecklistItem(6, "Comfort", "Hot Water", "Hot water available for bathing", 8),
        ChecklistItem(7, "Comfort", "Mosquito Repellent", "Coils or repellent provided", 5),
        ChecklistItem(8, "Food", "Hygienic Kitchen", "Kitchen is clean, vessels are washed properly", 10),
        ChecklistItem(9, "Food", "Guest Meal Options", "Can accommodate vegetarian / non-vegetarian preferences", 7),
        ChecklistItem(10, "Communication", "Basic English Signs", "Key instructions written in English for city guests", 5),
        ChecklistItem(11, "Safety", "First Aid Kit", "Basic first aid kit available", 5),
        ChecklistItem(12, "Connectivity", "Mobile Network", "Good mobile network or Wi-Fi available", 5),
    )

    val culturalGuide = listOf(
        Pair("Greeting", "Greet with 'Namaskara' (ನಮಸ್ಕಾರ) — hands folded. It is more respectful than a handshake."),
        Pair("Dress Code", "Wear modest, full-length clothing. Avoid shorts or sleeveless tops in the village."),
        Pair("Footwear", "Remove footwear before entering any home or place of worship. This is sacred etiquette."),
        Pair("Meal Time", "Eating with hands is traditional and normal. It's considered respectful to finish the food served."),
        Pair("Photography", "Always ask before photographing people, animals, or rituals. It's a matter of respect."),
        Pair("Water", "Do not waste water — it is precious in rural areas, especially in summer months."),
        Pair("Noise", "Villages wake up early (5 AM) and sleep early (9 PM). Be mindful of noise after dark."),
        Pair("Bargaining", "Do not bargain aggressively with farmers or artisans. Fair pricing supports their livelihood.")
    )
}
