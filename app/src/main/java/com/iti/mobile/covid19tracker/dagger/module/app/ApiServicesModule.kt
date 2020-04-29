package com.iti.mobile.covid19tracker.dagger.module.app

import com.iti.mobile.covid19tracker.utils.BASE_URL
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

@Module
class ApiServicesModule{
    // @ApplicationScope
//    @Provides
//    fun getApiService(retrofit: Retrofit): ApiServices {
//        return retrofit.create(ApiServices::class.java)
//    }

    //@ApplicationScope
    @Provides
    fun getRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }
}