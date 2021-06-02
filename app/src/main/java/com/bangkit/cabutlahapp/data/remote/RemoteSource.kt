package com.bangkit.cabutlahapp.data.remote

import android.content.Context
import android.widget.Toast
import androidx.core.content.contentValuesOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bangkit.cabutlahapp.retrofit.ApiConfig
import com.bangkit.cabutlahapp.retrofit.MapResponse
import com.bangkit.cabutlahapp.retrofit.ResultsItem
import com.google.protobuf.Api
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RemoteSource {

    companion object {
        @Volatile
        private var instance: RemoteSource? = null

        fun getInstance(): RemoteSource =
            instance ?: synchronized(this) {
                instance ?: RemoteSource().apply { instance = this }
            }

    }

    fun getNearbyPlaces(
        context: Context,
        type: String,
        location: String,
        output: String,
        key: String
    ): LiveData<MapResponse> {
        val result = MutableLiveData<MapResponse>()

        val responses = ApiConfig.getApiService().getPlaces(output, location, type, 1500, key)
        responses.enqueue(object : Callback<MapResponse> {
            override fun onResponse(call: Call<MapResponse>, response: Response<MapResponse>) {
                try {
                    if (response.isSuccessful) {
                        result.postValue(response.body())
                    }
                } catch (e: Exception) {
                    Toast.makeText(context, "ResponseError: ${e.message}", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<MapResponse>, t: Throwable) {
                Toast.makeText(context, "ResponseFailure: ${t.message}", Toast.LENGTH_LONG).show()
            }

        })

        return result
    }
}