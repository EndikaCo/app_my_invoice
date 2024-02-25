package com.endcodev.myinvoice

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyInvoiceApp : Application(){
    override fun onCreate() {
        super.onCreate()
    }
}