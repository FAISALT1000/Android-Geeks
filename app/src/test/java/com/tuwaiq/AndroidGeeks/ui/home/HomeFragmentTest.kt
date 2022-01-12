package com.tuwaiq.AndroidGeeks.ui.home

import org.junit.Assert.*
import org.junit.Test
import java.text.SimpleDateFormat
import java.util.*
class HomeFragmentTest{
    @Test
    fun dateFormat(){
        val dates = SimpleDateFormat("MM/dd/yyyy")
        var todaysDate: Date = Date()
        val currentDate = todaysDate
        val finalDate = todaysDate
        val date1_temp=dates.format(currentDate)
        val  date2_temp=dates.format(finalDate)
        val date1=dates.parse(date1_temp)
        val  date2=dates.parse(date2_temp)
        val difference: Long = (date1.time - date2.time)
        // val difference: Long = (finalDate.time - currentDate.time)
        val differenceDates = difference / (24 * 60 * 60 * 1000)
        val dayDifference = differenceDates.toInt()

        assertEquals(0, date1.time - date2.time)
    }
}