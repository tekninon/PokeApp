package com.example.pokeapp.api;

import com.example.pokeapp.models.Pokemon;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface PokemonApiInterface {

    String POKEMON_DETAIL = "pokemon/{pokeName}";
    String POKEMON_LIST = "pokemon?";

    @GET(POKEMON_DETAIL)
    Call<Pokemon> getPokemon(@Path("pokeName") String pokeName);


}
