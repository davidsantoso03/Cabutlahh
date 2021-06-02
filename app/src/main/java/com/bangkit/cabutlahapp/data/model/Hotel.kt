package com.bangkit.cabutlahapp.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Hotel(
        var id:Int?=null,
        var alamat: String? = null,
        var deskripsi: String? = null,
        var foto: String? = null,

        var latitude: Double? = null,

        var longitude: Double? = null,

        var nama: String? = null,

        var type: String? = null
) : Parcelable
