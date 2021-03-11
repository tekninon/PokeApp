package com.example.pokeapp.models;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;


public class Pokemon {


    public Pokemon(){}

    public Pokemon(int id, String name, float height){
        this.id = id;
        this.name = name;
        this.height = height;
    }
    /*@SerializedName("forms")
    @Expose
    public List<Form> forms = null;*/

    @SerializedName("id")
    @Expose
    public Integer id;


    @SerializedName("name")
    @Expose
    public String name;

    @SerializedName("height")
    @Expose
    public float height;

    /*@SerializedName("sprites")
    @Expose
    public Sprites sprites;*/




    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }
    /*
    public List<Form> getForms() {
        return forms;
    }

    public void setForms(List<Form> forms) {
        this.forms = forms;
    }*/


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


    /*public Sprites getSprites() {
        return sprites;
    }

    public void setSprites(Sprites sprites) {
        this.sprites = sprites;
    }*/


}