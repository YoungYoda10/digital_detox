package com.dovla.infinityscroll.broadcast_receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import java.util.*

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val c: Calendar = Calendar.getInstance()
        Log.d("TAG", "onReceive: ${c.time}")
    }
}