package com.tlh.afinal.model.service.module

import com.tlh.afinal.common.constants.Constants.BASE_URL
import com.tlh.afinal.data.remote.ProductAPI
import com.tlh.afinal.data.remote.ProductAPIService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideProductAPI(retrofit: Retrofit): ProductAPI {
        return retrofit.create(ProductAPI::class.java)
    }

    @Provides
    @Singleton
    fun provideProductAPIService(api: ProductAPI): ProductAPIService {
        return ProductAPIService(api)
    }
}
