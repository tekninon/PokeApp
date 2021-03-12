package com.example.pokeapp.database;

import android.provider.BaseColumns;

public class PokemonTable {
    public static final class PokemonEntry implements BaseColumns {

        public static final String TABLE_NAME = "pokemon";
        public static final String ID_POKE = "poke_id";
        public static final String NAME = "poke_name";
        public static final String URL_IMG = "url_img";
    }
}
