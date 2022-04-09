package com.storesoko.tenakata

import android.app.Application
import com.google.firebase.database.FirebaseDatabase


class ApplicationOffline : Application() {

    override fun onCreate() {
        super.onCreate()
        FirebaseDatabase.getInstance().setPersistenceEnabled(true)
    }
}