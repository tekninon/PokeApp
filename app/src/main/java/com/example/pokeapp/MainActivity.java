package com.example.pokeapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pokeapp.api.PokemonApi;
import com.example.pokeapp.models.Pokemon;
import com.example.pokeapp.utils.StringUtils;
import com.github.tbouron.shakedetector.library.ShakeDetector;
import com.squareup.picasso.Picasso;

import androidx.appcompat.app.AppCompatActivity;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {


    TextView tvPokemonName, tvPokemonId, tvPokemonHeight;
    ImageView pokemonImg;
    PokemonApi rfConfig;
    InputMethodManager inputManager;

    Call<Pokemon> request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        rfConfig = new PokemonApi();
        inputManager = (InputMethodManager) this.getSystemService(Activity.INPUT_METHOD_SERVICE);

        tvPokemonName = findViewById(R.id.tv_pokename);
        tvPokemonId = findViewById(R.id.tv_pokeid);
        tvPokemonHeight = findViewById(R.id.tv_pokeheight);
        pokemonImg = findViewById(R.id.imagePokemon);

        ShakeDetector.create(this, new ShakeDetector.OnShakeListener() {
            @Override
            public void OnShake() {
                Toast.makeText(getApplicationContext(), "Device shaken!", Toast.LENGTH_SHORT).show();
                Random random = new Random();
                int nb = random.nextInt(152);
                request = rfConfig.getPokeService().getPokemonById(nb == 0 ? nb : nb+1);
                request.enqueue(new Callback<Pokemon>() {
                    @Override
                    public void onResponse(Call<Pokemon> call, Response<Pokemon> response) {
                        Pokemon pokemon = response.body();
                        String urlImg = StringUtils.getPokemonImageStringFromId(Integer.toString(pokemon.getId()));
                        tvPokemonName.setText(pokemon.getName());
                        tvPokemonId.setText(Integer.toString(pokemon.getId()));
                        tvPokemonHeight.setText(Float.toString(pokemon.getHeight()));

                        // Recuperer url de l'api
                        Picasso.get().load(urlImg).into(pokemonImg);

                        //etSearch.clearFocus();
                    }

                    @Override
                    public void onFailure(Call<Pokemon> call, Throwable t) {
                        Log.e("REQUEST ERROR", "Fail to find Pokemon. " + t.getMessage());
                        Toast.makeText(MainActivity.this, "Fail to find Pokemon.", Toast.LENGTH_LONG).show();
                        //etSearch.setText("");
                    }
                });
                //inputManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        ShakeDetector.start();
    }

    @Override
    protected void onStop() {
        super.onStop();
        ShakeDetector.stop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ShakeDetector.destroy();
    }

    public void openCompass(View view) {
        Intent intent = new Intent(MainActivity.this, BoussoleActivity.class);
        startActivity(intent);
    }

    public void openPodo(View view) {
        Intent intent = new Intent(MainActivity.this, PodoActivity.class);
        startActivity(intent);
    }

}