package com.example.pokeapp;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pokeapp.api.PokemonApi;
import com.example.pokeapp.api.PokemonApiInterface;
import com.example.pokeapp.models.Pokemon;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    Button buttonSearch;
    EditText etSearch;
    TextView tvPokemonName, tvPokemonId, tvPokemonHeight;
    PokemonApi rfConfig;
    InputMethodManager inputManager;

    Call<Pokemon> request;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

        rfConfig = new PokemonApi();
        inputManager = (InputMethodManager) this.getSystemService(Activity.INPUT_METHOD_SERVICE);

        etSearch = findViewById(R.id.editText_search);
        buttonSearch = findViewById(R.id.button_search);
        tvPokemonName = findViewById(R.id.tv_pokename);
        tvPokemonId = findViewById(R.id.tv_pokeid);
        tvPokemonHeight = findViewById(R.id.tv_pokeheight);

        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Request by Name
                request = rfConfig.getPokeService().getPokemon(etSearch.getText().toString());

                request.enqueue(new Callback<Pokemon>() {
                    @Override
                    public void onResponse(Call<Pokemon> call, Response<Pokemon> response) {
                        Pokemon pokemon = response.body();
                        tvPokemonName.setText(pokemon.getName());
                        tvPokemonId.setText(Integer.toString(pokemon.getId()));
                        etSearch.clearFocus();
                    }

                    @Override
                    public void onFailure(Call<Pokemon> call, Throwable t) {
                        Log.e("REQUEST ERROR", "Fail to find Pokemon. " + t.getMessage());
                        Toast.makeText(MainActivity.this, "Fail to find Pokemon.", Toast.LENGTH_LONG).show();
                        etSearch.setText("");
                    }
                });
                inputManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
            }
        });
    }

}