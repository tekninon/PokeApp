package com.example.pokeapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.pokeapp.models.Pokemon;
import com.example.pokeapp.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class SQLImplement {

    public class SQLiteImplement extends SQLiteOpenHelper {

        public SQLiteImplement(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        public SQLiteImplement(Context context) {
            super(context, "database.db", null, 1);
        }


        @Override
        public void onCreate(SQLiteDatabase db) {

            //table des favoris
            final String SQL_CREATE_FAVORITE_TABLE = "CREATE TABLE " + PokemonTable.PokemonEntry.TABLE_NAME + " (" +
                    PokemonTable.PokemonEntry._ID + " INTEGER PRIMARY KEY," +
                    PokemonTable.PokemonEntry.ID_POKE + " INTEGER, " +
                    PokemonTable.PokemonEntry.NAME + " TEXT NOT NULL, " +
                    PokemonTable.PokemonEntry.URL_IMG + " TEXT NOT NULL, " +
                    "); ";


            db.execSQL(SQL_CREATE_FAVORITE_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + PokemonTable.PokemonEntry.TABLE_NAME);
            onCreate(db);
        }


        public void addToPokedex(Pokemon pokemon) {
            SQLiteDatabase db = this.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(PokemonTable.PokemonEntry.ID_POKE, pokemon.getId());
            values.put(PokemonTable.PokemonEntry.NAME, pokemon.getName());
            values.put(PokemonTable.PokemonEntry.URL_IMG, StringUtils.getPokemonImageStringFromId(Integer.toString(pokemon.getId())));
            db.insert(PokemonTable.PokemonEntry.TABLE_NAME, null, values);
            db.close();
        }


        public void deletePokemon(int poke_id) {
            SQLiteDatabase db = this.getWritableDatabase();
            db.delete(PokemonTable.PokemonEntry.TABLE_NAME, PokemonTable.PokemonEntry.ID_POKE + "=" + poke_id, null);

        }

        public void deleteAllPokemon() {
            SQLiteDatabase db = this.getWritableDatabase();
            db.execSQL("delete from " + PokemonTable.PokemonEntry.TABLE_NAME);

        }

        public List<Pokemon> getAllPokemon() {
            String[] columns = {
                    PokemonTable.PokemonEntry.ID_POKE,
                    PokemonTable.PokemonEntry.NAME,
                    PokemonTable.PokemonEntry.URL_IMG,

            };
            String sortOrder =
                    PokemonTable.PokemonEntry.ID_POKE + " ASC";
            List<Pokemon> pokemonList = new ArrayList<>();

            SQLiteDatabase db = this.getReadableDatabase();

            Cursor cursor = db.query(PokemonTable.PokemonEntry.TABLE_NAME,
                    columns,
                    null,
                    null,
                    null,
                    null,
                    sortOrder);

            if (cursor.moveToFirst()) {
                do {
                    Pokemon pokemon = new Pokemon();
                    pokemon.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(PokemonTable.PokemonEntry.ID_POKE))));
                    pokemon.setName(cursor.getString(cursor.getColumnIndex(PokemonTable.PokemonEntry.NAME)));

                    pokemonList.add(pokemon);

                } while (cursor.moveToNext());
            }
            cursor.close();
            db.close();

            return pokemonList;
        }
    }
}

