package com.example.pokeapp.utils;

public class StringUtils {
    public static String getPokemonImageStringFromId(String id) {
        return "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/" + id + ".png";
    }
}
