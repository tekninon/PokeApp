package com.example.pokeapp.models;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@JsonIgnoreProperties({"base_experience","is_default","order","weight","abilities","forms",
        "game_indices", "held_items", "location_area_encounters", "moves", "species", "sprites",
        "stats", "types","past_types"})
public class Pokemon {

    private int id;
    private String name;
    private float height;

    public Pokemon(){}

    public Pokemon(int id, String name, float height) {
        this.id = id;
        this.name = name;
        this.height = height;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }
}
