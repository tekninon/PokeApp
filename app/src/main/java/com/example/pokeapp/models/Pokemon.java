package com.example.pokeapp.models;


import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;


public class Pokemon implements Comparable<Pokemon> {

    @SerializedName("id")
    private int id;

    @Override
    public int compareTo(Pokemon pokemon) {
        return 0;
    }
}