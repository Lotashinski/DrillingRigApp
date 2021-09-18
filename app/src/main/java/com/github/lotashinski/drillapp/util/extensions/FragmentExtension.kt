package com.github.lotashinski.drillapp.util.extensions

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.github.lotashinski.drillapp.R
import com.github.lotashinski.drillapp.app.App
import com.github.lotashinski.drillapp.util.exception.GetRepositoryBuilderException
import com.github.lotashinski.drillapp.app.repository.RepositoryBuilder

fun Fragment.getRepositoryBuilder(): RepositoryBuilder {
    Log.d(this::class.simpleName, "call getRepositoryBuilder()")

    val applicationContext = requireContext().applicationContext
    val currentAppContextAsApp = (applicationContext as? App)
    val dbService = currentAppContextAsApp?.getDataBaseService()
    val repositoryBuilder = dbService?.getRepositoryBuilder()

    return repositoryBuilder
        ?: throw GetRepositoryBuilderException("Cast Application to App.class return null")
}