package com.example.pokeapp.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import com.example.pokeapp.common.Common;

public class PokemonApi {
    public static final String BASE_URL = Common.POKEAPI_URL;
    private static Retrofit retrofit;

    public static Retrofit getApi(){
        if(retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
