package com.github.lotashinski.drillapp.util.extensions

fun calculateLeadTime(function: () -> Unit) : Long {
    val startTimeInMillis = System.currentTimeMillis()
    function()
    val endTimeInMillis = System.currentTimeMillis()
    return endTimeInMillis - startTimeInMillis
}