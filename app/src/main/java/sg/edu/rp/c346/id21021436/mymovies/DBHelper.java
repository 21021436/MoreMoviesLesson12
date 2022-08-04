package sg.edu.rp.c346.id21021436.mymovies;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "movies.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_MOVIES= "movie_table";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_MOVIE_TITLE = "movie_title";
    private static final String COLUMN_GENRE = "genre";
    private static final String COLUMN_YEAR = "year";
    private static final String COLUMN_RATING = "rating";

    public DBHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //Create database
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createMoviesTableSql = "CREATE TABLE " + TABLE_MOVIES + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_MOVIE_TITLE + " TEXT," +  COLUMN_GENRE + " TEXT," + COLUMN_YEAR + " INTEGER," + COLUMN_RATING + " TEXT ) ";
        db.execSQL(createMoviesTableSql);

    }

    //Upgrade database - drop table method (not ideal)
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MOVIES);
        onCreate(db);

    }

    //Insert a movie
    public long insertMovie(Movie movie) {
        String title = movie.getTitle();
        String genre = movie.getGenre();
        int year = movie.getYear();
        String rating = movie.getRating();
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_MOVIE_TITLE, title);
        values.put(COLUMN_GENRE, genre);
        values.put(COLUMN_YEAR, year);
        values.put(COLUMN_RATING, rating);
        long result = db.insert(TABLE_MOVIES, null, values);
        db.close();
        Log.d("SQL Insert","ID:"+ result); //id returned, shouldn’t be -1
        return result;

    }

    //Retrieve all movies
    public ArrayList<Movie> getAllMovies() {
        ArrayList<Movie> movies = new ArrayList<Movie>();

        SQLiteDatabase db = this.getReadableDatabase();

        String[] columns= {COLUMN_ID, COLUMN_MOVIE_TITLE, COLUMN_GENRE, COLUMN_YEAR, COLUMN_RATING};
        Cursor cursor = db.query(TABLE_MOVIES, columns, null, null,
                null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String movieTitle = cursor.getString(1);
                String movieGenre = cursor.getString(2);
                int movieYear = cursor.getInt(3);
                String movieRating = cursor.getString(4);
                Movie movie = new Movie (id, movieTitle, movieGenre, movieYear, movieRating);
                movies.add(movie);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return movies;
    }

//    //Retrieve PG13 movies
//    public ArrayList<Movie> getPG13() {
//        ArrayList<Movie> movies = new ArrayList<Movie>();
//
//        SQLiteDatabase db = this.getReadableDatabase();
//        String[] columns= {COLUMN_ID, COLUMN_MOVIE_TITLE, COLUMN_GENRE, COLUMN_YEAR, COLUMN_RATING};
//        String condition = COLUMN_RATING + " = ?";
//        String[] args = {"PG13"};
//        Cursor cursor = db.query(TABLE_MOVIES, columns, condition, args,
//                null, null, null, null);
//
//        if (cursor.moveToFirst()) {
//            do {
//                int id = cursor.getInt(0);
//                String movieTitle = cursor.getString(1);
//                String movieGenre = cursor.getString(2);
//                int year = cursor.getInt(3);
//                String rating = cursor.getString(4);
//                Movie movie = new Movie(id, movieTitle, movieGenre, year, rating);
//                movies.add(movie);
//            } while (cursor.moveToNext());
//        }
//        cursor.close();
//        db.close();
//        return movies;
//    }

    //Retrieve all movies of given rating
    public ArrayList<Movie> getMoviesByRating(String givenRating) {
        ArrayList<Movie> movies = new ArrayList<Movie>();

        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns= {COLUMN_ID, COLUMN_MOVIE_TITLE, COLUMN_GENRE, COLUMN_YEAR, COLUMN_RATING};
        String condition = COLUMN_RATING + " = ?";
        String[] args = {givenRating};
        Cursor cursor = db.query(TABLE_MOVIES, columns, condition, args,
                null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String movieTitle = cursor.getString(1);
                String movieGenre = cursor.getString(2);
                int year = cursor.getInt(3);
                String rating = cursor.getString(4);
                Movie movie = new Movie(id, movieTitle, movieGenre, year, rating);
                movies.add(movie);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return movies;
    }

    //Update a movie
    public int updateMovie(Movie movie, String newTitle, String newGenre, int newYear, String newRating){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_MOVIE_TITLE, newTitle);
        values.put(COLUMN_GENRE, newGenre);
        values.put(COLUMN_YEAR, newYear);
        values.put(COLUMN_RATING, newRating);
        String condition = COLUMN_ID + " = ?";
        String[] args = {movie.get_id() + ""};
        int result = db.update(TABLE_MOVIES, values, condition, args);
        db.close();
        return result;

    }

    //Delete a movie
    public int deleteMovie(Movie movie){
        SQLiteDatabase db = this.getWritableDatabase();
        String condition = COLUMN_ID + " = ?";
        String[] args = {movie.get_id() + ""};
        int result = db.delete(TABLE_MOVIES, condition, args);
        db.close();
        return result;
    }

    //Deleting *all* movies while retaining table structure
    public void deleteAllMovies(){
        SQLiteDatabase db = this.getWritableDatabase();

//        This method (which is the equivalent of TRUNCATE in SQLite still renumbers the AUTOINCREMENT from previous residual value
//        db.execSQL("DELETE FROM " + TABLE_MOVIES);
//        db.close();

        // This method (DROP and recreate) will start numbering at 1 again. It is the preferred method
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MOVIES);
        onCreate(db);
        db.close();


    }
}
