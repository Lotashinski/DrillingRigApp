package com.github.lotashinski.drillapp.app.service

import com.github.lotashinski.drillapp.app.repository.RepositoryBuilder
import java.io.Closeable

interface IDatabaseService : Closeable {
    fun getRepositoryBuilder(): RepositoryBuilder
}