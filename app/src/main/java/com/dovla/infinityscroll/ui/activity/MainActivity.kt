package com.dovla.infinityscroll.ui.activity

import android.app.AlarmManager
import android.app.AlarmManager.*
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_IMMUTABLE
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dovla.infinityscroll.broadcast_receiver.AlarmReceiver
import com.dovla.infinityscroll.databinding.ActivityMainBinding
import com.dovla.infinityscroll.ui.adapter.RecyclerViewAdapter
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var recyclerViewAdapter: RecyclerViewAdapter? = null
    private var rowsArrayList: ArrayList<String?> = ArrayList()

    private lateinit var alarmManager: AlarmManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        alarmManager = applicationContext.getSystemService(ALARM_SERVICE) as AlarmManager
        populateData()
        initAdapter()
        initScrollListener()

        schedulePushNotifications()
    }

    private fun populateData() {
        var i = 0
        while (i < 10) {
            rowsArrayList.add("Item $i")
            i++
        }
    }

    private fun initAdapter() {
        recyclerViewAdapter = RecyclerViewAdapter(rowsArrayList)
        binding.recyclerView.adapter = recyclerViewAdapter
    }

    private fun initScrollListener() {
        binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                if (layoutManager.findLastCompletelyVisibleItemPosition() == rowsArrayList.size - 1) {
                    loadMore()
                }
            }
        })
    }

    private fun loadMore() {
        rowsArrayList.add(null)
        val handler = Handler(Looper.getMainLooper())
        handler.post {
            rowsArrayList.removeAt(rowsArrayList.size - 1)
            val scrollPosition: Int = rowsArrayList.size
            recyclerViewAdapter!!.notifyItemRemoved(scrollPosition)
            var currentSize = scrollPosition
            val nextLimit = currentSize + 1
            while (currentSize - 1 < nextLimit) {
                rowsArrayList.add("Item $currentSize")
                currentSize++
            }
            recyclerViewAdapter!!.notifyDataSetChanged()
        }
    }

    private fun schedulePushNotifications() {
        val calendar = GregorianCalendar.getInstance().apply {
//            add(Calendar.HOUR_OF_DAY, 8)
            add(Calendar.MINUTE, 1)
        }

        val intent = Intent(applicationContext, AlarmReceiver::class.java)
        val pendingIntent =
            PendingIntent.getBroadcast(applicationContext, 0, intent, FLAG_IMMUTABLE)

        val c: Calendar = Calendar.getInstance()
        Log.d("TAG", "dateNow: ${c.time}")
        Log.d("TAG", "dateSchedule: ${calendar.time}")

        alarmManager.set(RTC_WAKEUP, calendar.timeInMillis, pendingIntent)
    }
}