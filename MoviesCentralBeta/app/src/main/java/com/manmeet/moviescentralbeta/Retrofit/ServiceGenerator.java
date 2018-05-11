package com.manmeet.moviescentralbeta.Retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by manmeet on 29/3/18.
 */

/**
 * This class is used to make an ServiceGenerator for the Retrofit calls.
 */
public class ServiceGenerator {
    public static final String BASE_API_URL = "https://api.themoviedb.org/";
    private static Retrofit retrofit = null;
    private static Gson gson = new GsonBuilder().create();

    private static HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);
    private static OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor);

    private static OkHttpClient okHttpClient = okHttpClientBuilder.build();
    public static <T> T createService(Class<T> serviceClass){
        if(retrofit == null){
            retrofit = new Retrofit.Builder()
                    .client(okHttpClient)
                    .baseUrl(BASE_API_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit.create(serviceClass);
    }

}
