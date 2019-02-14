package com.dev.peralta.marqueeventos.model

import com.google.firebase.Timestamp
import com.google.firebase.firestore.GeoPoint

data class Event(val desc: String, val cat: String, val time: Timestamp, val local: GeoPoint)
