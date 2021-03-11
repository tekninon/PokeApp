package com.example.pokeapp.api;

import com.example.pokeapp.models.Pokemon;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface PokemonApiInterface {

    @GET("pokemon/{name}/")
    Call<Pokemon> getPokemonByName(@Path("name") String name);

    @GET("pokemon/{id}/")
    Call<Pokemon> getPokemonById(@Path("id") int id);


}
