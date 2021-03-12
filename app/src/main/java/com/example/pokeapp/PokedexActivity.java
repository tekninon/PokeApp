package com.example.pokeapp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pokeapp.adapter.PokemonAdapter;
import com.example.pokeapp.database.SQLiteImplement;
import com.example.pokeapp.models.Pokemon;

import java.util.ArrayList;
import java.util.List;

public class PokedexActivity extends AppCompatActivity {

    private static final String TAG = "POKEDEX";

    private RecyclerView recyclerView;
    List<Pokemon> pokemonList;
    private PokemonAdapter pokemonAdapter;
    private SQLiteImplement db;
    private AppCompatActivity activity = PokedexActivity.this;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pokedex_activity);
        afficherPokedex();

    }

    public Activity getActivity(){
        Context context = this;
        while (context instanceof ContextWrapper){
            if (context instanceof Activity){
                return (Activity) context;
            }
            context = ((ContextWrapper) context).getBaseContext();
        }
        return null;

    }

    private void afficherPokedex(){
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        pokemonList = new ArrayList<>();
        pokemonAdapter = new PokemonAdapter(this, pokemonList);

        if (getActivity().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        } else {
            recyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        }

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(pokemonAdapter);
        pokemonAdapter.notifyDataSetChanged();
        db = new SQLiteImplement(activity);

        getPokedex();
    }


    @SuppressLint("StaticFieldLeak")
    private void getPokedex(){
        new AsyncTask<Void, Void, Void>(){
            @Override
            protected Void doInBackground(Void... params){
                pokemonList.clear();
                pokemonList.addAll(db.getAllPokemon());
                return null;
            }
            @Override
            protected void onPostExecute(Void aVoid){
                super.onPostExecute(aVoid);
                pokemonAdapter.notifyDataSetChanged();
            }
        }.execute();
    }

}
