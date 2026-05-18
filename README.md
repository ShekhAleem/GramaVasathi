# Grama-Vasathi 🏡
### Android App Development using GenAI — Project 34 (Hospitality)
**MindMatrix VTU Internship Program**

---

## 📋 Project Overview
Grama-Vasathi is a **Rural Home-stay Accelerator** app that connects city dwellers wanting rural experiences with village families offering home-stays. It acts as a guide for both the host and the guest.

---

## 🗂️ Project Structure

```
GramaVasathi/
├── app/src/main/java/com/gramavasathi/app/
│   ├── MainActivity.kt                  ← Entry point
│   ├── data/
│   │   ├── model/Models.kt              ← FarmStay, Booking, ChecklistItem, SampleData
│   │   └── repository/FarmStayRepository.kt  ← Firebase + offline fallback
│   ├── viewmodel/
│   │   └── GramaVasathiViewModel.kt     ← State management
│   └── ui/
│       ├── theme/Theme.kt               ← Warm earthy color palette
│       └── screens/
│           ├── Navigation.kt            ← NavHost + routes
│           ├── SplashScreen.kt          ← Animated splash
│           ├── HomeScreen.kt            ← Dashboard with quick nav
│           ├── ExploreScreen.kt         ← Farm stay listing + filters
│           ├── FarmStayDetailScreen.kt  ← Detail view with host score
│           ├── BookingScreen.kt         ← Simulated calendar booking
│           ├── BookingConfirmScreen.kt  ← Confirmation screen
│           ├── HostTrainingScreen.kt    ← Wizard/Stepper checklist
│           └── CulturalGuideScreen.kt  ← Cultural tips for guests
```

---

## 🚀 Setup Instructions

### Step 1: Open in Android Studio
1. Open Android Studio (Hedgehog or later)
2. File → Open → Select the `GramaVasathi` folder
3. Wait for Gradle sync to complete

### Step 2: Firebase Setup (Optional — app works offline)
1. Go to [Firebase Console](https://console.firebase.google.com)
2. Create a new project named **GramaVasathi**
3. Add an Android app with package name: `com.gramavasathi.app`
4. Download `google-services.json` and place it in `app/` folder
5. In Firestore, create a collection called `farmstays`
6. **Without Firebase**: App uses built-in sample data automatically ✅

### Step 3: Run
1. Connect an Android device or start an emulator (API 26+)
2. Click **Run ▶** in Android Studio

---

## 📱 Features Implemented

| Feature | Screen | Status |
|---------|--------|--------|
| Splash Screen with animation | SplashScreen | ✅ |
| Home Dashboard | HomeScreen | ✅ |
| Farm Stay Directory | ExploreScreen | ✅ |
| Activity Filter (Birdwatching, Cooking, etc.) | ExploreScreen | ✅ |
| Search by village/district | ExploreScreen | ✅ |
| Farm Stay Detail View | FarmStayDetailScreen | ✅ |
| Host Readiness Score | FarmStayDetailScreen | ✅ |
| Simulated Calendar Booking | BookingScreen | ✅ |
| Booking Confirmation | BookingConfirmScreen | ✅ |
| Host Training Wizard (Stepper) | HostTrainingScreen | ✅ |
| Cultural Guide for Guests | CulturalGuideScreen | ✅ |
| Firebase Firestore integration | Repository | ✅ |
| Offline fallback with sample data | Repository | ✅ |

---

## 🎯 Success Criteria (from Project Sheet)

✅ **Host Readiness Score** — Calculated from checklist (points-based, shown as %)  
✅ **Search with Activity Filter** — Filter by Birdwatching, Cow Milking, Fishing, etc.  
✅ **Warm, welcoming, "Homy" UI** — Earthy saffron & leaf green palette, Kannada text  

---

## 🛠️ Tech Stack

- **Language**: Kotlin
- **UI**: Jetpack Compose + Material 3
- **Navigation**: Navigation Compose
- **Database**: Firebase Firestore (with sample data fallback)
- **Images**: Coil
- **Architecture**: MVVM (ViewModel + StateFlow)
- **Build**: Gradle with Version Catalog

---

## 📦 Key Dependencies

```toml
# In libs.versions.toml / app/build.gradle
- Jetpack Compose BOM 2024.06
- Navigation Compose 2.7.7
- Firebase BOM 32.7.0
- Coil 2.5.0
- Material Icons Extended
- Coroutines 1.7.3
```

---

*Built for MindMatrix VTU Internship Program — Project 34: Grama-Vasathi (Hospitality)*
