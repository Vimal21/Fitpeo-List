package com.fitpeo.mvvmhilt.photo.service

import com.fitpeo.mvvmhilt.base.network.NetworkManager
import com.fitpeo.mvvmhilt.photo.model.Photo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Singleton


/**
 * Photo list api
 *
 * @constructor Create photo list api
 */
interface PhotoListApi {

    /**
     * Get the photo list items from the remote server
     *
     * @return list of photo
     */
    @GET("photos")
    fun get(@Query("_page") pageNumber : Int, @Query("_limit") pageSize : Int): Call<List<Photo>>
}

@Module
@InstallIn(SingletonComponent::class)
object PhotoListApiModule {

    /**
     * It creates the PhotoListApi instance that will inject on the
     *  photoListRepository constructor
     *
     * @return the PhotoListApi instance to the user
     */
    @Provides
    @Singleton
    fun providePhotoListApi(): PhotoListApi {
        return NetworkManager.baseUrl("https://jsonplaceholder.typicode.com/")
            .serviceClass(PhotoListApi::class.java).build()
    }
}
