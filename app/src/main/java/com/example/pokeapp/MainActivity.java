package com.example.pokeapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
import com.example.pokeapp.database.SQLImplement;
import com.example.pokeapp.models.Pokemon;
import com.example.pokeapp.shaker.ShakeDetector;
import com.example.pokeapp.utils.StringUtils;

import com.squareup.picasso.Picasso;

import androidx.appcompat.app.AppCompatActivity;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    //sensor
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private ShakeDetector mShakeDetector;

    TextView tvPokemonName, tvPokemonId, tvPokemonHeight;
    ImageView pokemonImg;
    PokemonApi rfConfig;
    InputMethodManager inputManager;

    Call<Pokemon> request;

    //db
    private SQLImplement.SQLiteImplement db;


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

        // ShakeDetector initialization
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager
                .getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mShakeDetector = new ShakeDetector();
        mShakeDetector.setOnShakeListener(new ShakeDetector.OnShakeListener() {

            @Override
            public void onShake(int count) {
                /*
                 * The following method, "handleShakeEvent(count):" is a stub //
                 * method you would use to setup whatever you want done once the
                 * device has been shook.
                 */
                Toast.makeText(getApplicationContext(), "Device shaken!", Toast.LENGTH_SHORT).show();
                Random random = new Random();
                int nb = random.nextInt(152);
                request = rfConfig.getPokeService().getPokemonById(nb == 0 ? nb : nb+1);
                request.enqueue(new Callback<Pokemon>() {
                    @Override
                    public void onResponse(Call<Pokemon> call, Response<Pokemon> response) {
                        Pokemon pokemon = response.body();
                        String urlImg = StringUtils.getPokemonImageStringFromId(Integer.toString(pokemon.getId()));

                        String name = pokemon.getName();
                        String nomMaj = name.replaceFirst(".",(name.charAt(0)+"").toUpperCase());
                        System.out.println(nomMaj);
                        tvPokemonName.setText(nomMaj);

                        tvPokemonId.setText(Integer.toString(pokemon.getId()));

                        String taille = Float.toString(pokemon.getHeight() + 100);
                        String tailleEnCm = taille + " cm";
                        tvPokemonHeight.setText(tailleEnCm);

                        // Recuperer url de l'api
                        Picasso.get().load(urlImg).into(pokemonImg);

                    }

                    @Override
                    public void onFailure(Call<Pokemon> call, Throwable t) {
                        Log.e("REQUEST ERROR", "Fail to find Pokemon. " + t.getMessage());
                        Toast.makeText(MainActivity.this, "Fail to find Pokemon.", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Add the following line to register the Session Manager Listener onResume
        mSensorManager.registerListener(mShakeDetector, mAccelerometer,	SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    protected void onPause() {
        // Add the following line to unregister the Sensor Manager onPause
        mSensorManager.unregisterListener(mShakeDetector);
        super.onPause();
    }

    public void openCompass(View view) {
        Intent intent = new Intent(MainActivity.this, BoussoleActivity.class);
        startActivity(intent);
    }

    public void openPodo(View view) {
        Intent intent = new Intent(MainActivity.this, PodoActivity.class);
        startActivity(intent);
    }

    public void openPokdex(View view) {
        Intent intent = new Intent(MainActivity.this, PokedexActivity.class);
        startActivity(intent);
    }
}