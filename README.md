# 🏎️ KartRush – Smart EV & Petrol Go-Kart Tracking System

KartRush is a real-time performance monitoring and analytics system designed for both Electric (EV) and Petrol (Combustion) go-karts.  
It combines Embedded Systems, Cloud Integration, GPS Tracking, and Android development to deliver a smart, data-driven racing experience.

---

## 🚀 Project Overview

KartRush collects live telemetry data from an ESP32-based hardware system and displays it in a modern Android application.  
The platform monitors speed, GPS location, temperature, and distance while also providing interactive 3D visualization of the kart.

This project transforms traditional kart racing into a smart motorsport environment using real-time analytics and cloud synchronization.

---

## 🎯 Key Features

### 🔥 Dual Mode Support
- EV Kart (Battery Monitoring)
- Petrol Kart (Engine Temperature Monitoring)

### 📊 Real-Time Performance Metrics
- Live Speed Display
- Distance Tracking
- Time Monitoring
- Acceleration Analysis

### 🌍 GPS Tracking
- Real-time location tracking
- Track movement visualization
- Route history
- Racing line analysis

### 🌡️ Temperature Monitoring
- Battery temperature (EV)
- Engine temperature (Petrol)

### ☁️ Cloud Integration
- Firebase Realtime Database
- Live data synchronization
- Secure telemetry storage

### 🎮 3D Visualization
- Interactive 3D kart model (.glb)
- Component-based visualization
- Enhanced user engagement

---

## 🏗️ System Architecture

ESP32 Sensors → Firebase Realtime Database → Android Application

1. ESP32 collects:
   - GPS Data
   - Speed Data
   - Temperature Data

2. Firebase:
   - Stores real-time telemetry
   - Syncs data to mobile app

3. Android App:
   - Displays analytics dashboard
   - Shows Google Maps tracking
   - Renders 3D kart model

---

## 🛠️ Tech Stack

### 🔧 Hardware
- ESP32
- GPS Module
- Temperature Sensors

### ☁️ Backend / Cloud
- Firebase Realtime Database

### 📱 Mobile App
- Kotlin
- Jetpack Compose
- Google Maps API
- 3D GLB Model Integration

---

## 📦 Modules

- Live Dashboard Screen
- Vehicle Selection (EV / Petrol)
- Map Tracking Screen
- Detail Analytics Screen
- 3D Model Viewer

---

## 🎯 Use Cases

- Racing performance analysis
- EV battery safety monitoring
- Engine heat management
- Racing strategy optimization
- Smart kart fleet tracking

---

## 🌟 Project Highlights

✔️ Supports both EV and Petrol karts  
✔️ Real-time cloud-based telemetry  
✔️ Embedded + Mobile + Cloud integration  
✔️ Interactive 3D visualization  
✔️ GPS-based smart tracking  

---

## 📌 Future Enhancements

- Lap comparison analytics
- AI-based racing line suggestions
- Driver performance scoring
- Multi-user kart tracking
- Offline data caching

---

## 👩‍💻 Developed By

Layna S  
Embedded Systems & Full Stack Developer  

---

## 📄 License

This project is developed for academic and research purposes.
