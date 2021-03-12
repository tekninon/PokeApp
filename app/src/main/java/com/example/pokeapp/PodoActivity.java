package com.example.pokeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.se.omapi.Session;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pokeapp.api.PokemonApi;
import com.example.pokeapp.database.SQLiteImplement;
import com.example.pokeapp.models.Pokemon;
import com.example.pokeapp.utils.StringUtils;
import com.mikhaellopez.circularprogressbar.CircularProgressBar;
import com.squareup.picasso.Picasso;

import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PodoActivity extends AppCompatActivity {



    private TextView textView;
    private double MagnitudePrevious = 0;
    private Integer stepCount = 0;
    private Float maxStep = 500f;

    //db
    private SQLiteImplement db;

    //api
    PokemonApi rfConfig;
    Call<Pokemon> request;
    private AppCompatActivity activity = PodoActivity.this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_podo);

        rfConfig = new PokemonApi();


        textView = findViewById(R.id.step_counter);
        SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        Sensor sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        final CircularProgressBar circularProgressBar = findViewById(R.id.progress_circular);

        SensorEventListener stepDetector = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
               if (sensorEvent != null) {
                   float x_acceleration = sensorEvent.values[0];
                   float y_acceleration = sensorEvent.values[1];
                   float z_acceleration = sensorEvent.values[2];

                   double Magnitude = Math.sqrt(x_acceleration*x_acceleration + y_acceleration*y_acceleration + z_acceleration*z_acceleration);
                   double MagnitudeDelta = Magnitude - MagnitudePrevious;
                   MagnitudePrevious = Magnitude;

                   if(MagnitudeDelta > 6) {
                       if(stepCount < maxStep) {
                           stepCount++;
                       }
                       else if(stepCount == 10) {
                            addNewPokemon();
                       }
                   }
                   textView.setText(stepCount.toString());

                   circularProgressBar.setProgressWithAnimation(stepCount, Long.valueOf(100));
               }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };

        sensorManager.registerListener(stepDetector, sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    protected void onPause() {
        super.onPause();

        SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.putInt("stepCount", stepCount);
        editor.apply();
    }

    protected void onStop() {
        super.onStop();

        SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.putInt("stepCount", stepCount);
        editor.apply();
    }

    protected void onResume() {
        super.onResume();

        SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
        stepCount = sharedPreferences.getInt("stepCount", 0);
    }

    public void retourArriere() {
        Intent intent = new Intent(PodoActivity.this, MainActivity.class);
        startActivity(intent);
    }

    public void addNewPokemon(){
        Random random = new Random();
        int nb = random.nextInt(152);
        request = rfConfig.getPokeService().getPokemonById(nb == 0 ? nb : nb+1);

        request.enqueue(new Callback<Pokemon>() {
            @Override
            public void onResponse(Call<Pokemon> call, Response<Pokemon> response) {
                Pokemon pokemon = response.body();

                //ajout a la base de donnée le pokemon affiché
                db = new SQLiteImplement(activity);
                db.addToPokedex(pokemon);
                Toast.makeText(getApplicationContext(), "Nouveau pokémon ajouté au pokédex!" + pokemon.getName(), Toast.LENGTH_SHORT).show();


            }

            @Override
            public void onFailure(Call<Pokemon> call, Throwable t) {
                Log.e("REQUEST ERROR", "Fail to find Pokemon. " + t.getMessage());
                Toast.makeText(PodoActivity.this, "Fail to find Pokemon.", Toast.LENGTH_LONG).show();
            }
        });
    }
}