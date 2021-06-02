package com.bangkit.cabutlahapp.di

import com.bangkit.cabutlahapp.data.CabutlahRepo
import com.bangkit.cabutlahapp.data.remote.RemoteSource

object Injection {
    fun getRepo(): CabutlahRepo {
        val remote = RemoteSource.getInstance()

        return CabutlahRepo.getInstance(remote)
    }

}