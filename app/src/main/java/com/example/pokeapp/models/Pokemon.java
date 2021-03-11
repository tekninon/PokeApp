package com.example.pokeapp.models;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;


public class Pokemon {



    @SerializedName("forms")
    @Expose
    public List<Form> forms = null;

    @SerializedName("id")
    @Expose
    public Integer id;


    @SerializedName("name")
    @Expose
    public String name;


    @SerializedName("sprites")
    @Expose
    public Sprites sprites;




    public List<Form> getForms() {
        return forms;
    }

    public void setForms(List<Form> forms) {
        this.forms = forms;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public Sprites getSprites() {
        return sprites;
    }

    public void setSprites(Sprites sprites) {
        this.sprites = sprites;
    }


}