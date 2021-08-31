package com.github.lotashinski.drillapp.extensions

import androidx.fragment.app.Fragment
import com.github.lotashinski.drillapp.app.App
import com.github.lotashinski.drillapp.exception.GetRepositoryBuilderException
import com.github.lotashinski.drillapp.repository.RepositoryBuilder

fun Fragment.getRepositoryBuilder(): RepositoryBuilder {
    val applicationContext = requireContext().applicationContext
    return (applicationContext as? App)?.getRepositoryBuilder()
        ?: throw GetRepositoryBuilderException("Cast Application to App.class return null")
}