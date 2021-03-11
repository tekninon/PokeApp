package com.example.pokeapp.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;

import com.example.pokeapp.common.Common;

public class PokemonApi {
    private final Retrofit retrofit;
    private String baseURL = "https://pokeapi.co/api/v2/";


    public PokemonApi(){
        this.retrofit = new Retrofit.Builder()
                .baseUrl(baseURL)
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
    }

    public PokemonApiInterface getPokeService(){
        return this.retrofit.create(PokemonApiInterface.class);
    }
}
