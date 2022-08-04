package sg.edu.rp.c346.id21021436.mymovies;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.ArrayList;

public class ModifyMovie extends AppCompatActivity {

    //define variables

    EditText movieId, movieTitle, genre, year, rating;
    Button btnUpdate, btnDelete, btnDeleteAll, btnCancel;
    ArrayList<Movie> al;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_movie);

        movieId = findViewById(R.id.etMovieID);
        movieTitle = findViewById(R.id.etMovieTitle);
        genre = findViewById(R.id.etGenre);
        year = findViewById(R.id.etYear);
        rating = findViewById(R.id.etRating);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);
        btnDeleteAll = findViewById(R.id.btnDeleteAll);
        btnCancel = findViewById(R.id.btnCancel);

        al = new ArrayList<Movie>();


        Intent intentReceivedFromSecond = getIntent();
        Movie moviePicked = (Movie) intentReceivedFromSecond.getSerializableExtra("moviePicked");

        movieId.setText(moviePicked.get_id() + "");
        //to prevent editing of the movieId, set focusable and clickable to false
        movieId.setFocusable(false);
        movieId.setClickable(false);
        movieTitle.setText(moviePicked.getTitle() + "");
        genre.setText(moviePicked.getGenre() + "");
        year.setText(moviePicked.getYear() + "");
        rating.setText(moviePicked.getRating() + "");


        DBHelper dbh = new DBHelper(ModifyMovie.this);
        btnUpdate.setOnClickListener(v -> {

            int resultUpdate = dbh.updateMovie(moviePicked, movieTitle.getText().toString(), genre.getText().toString(), Integer.parseInt(year.getText().toString()), rating.getText().toString());
            if (resultUpdate != -1) Toast.makeText(ModifyMovie.this, "Update successful", Toast.LENGTH_SHORT).show();

            al.clear();
            al.addAll(dbh.getAllMovies());
            Intent intentGoingBackToShowMovies = new Intent(ModifyMovie.this, ShowMovies.class);
            intentGoingBackToShowMovies.putExtra("movieList", al);
            startActivity(intentGoingBackToShowMovies);
            finish();

        });

        //Delete one movie

        btnDelete.setOnClickListener(v -> {

            AlertDialog.Builder myBuilder = new AlertDialog.Builder(ModifyMovie.this);
            myBuilder.setTitle("Danger");
            myBuilder.setMessage("Are you sure you want to delete the movie " + moviePicked.getTitle() + "?");
            myBuilder.setCancelable(false);
            myBuilder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    int resultDelete = dbh.deleteMovie(moviePicked);
                    if (resultDelete != -1) Toast.makeText(ModifyMovie.this, "Delete successful", Toast.LENGTH_SHORT).show();

                    al.clear();
                    al.addAll(dbh.getAllMovies());
                    Intent intentGoingBackToShowMovies = new Intent(ModifyMovie.this, ShowMovies.class);
                    intentGoingBackToShowMovies.putExtra("movieList", al);
                    startActivity(intentGoingBackToShowMovies);
                    finish();

                }
            });


            myBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    AlertDialog.Builder myNestedBuilder = new AlertDialog.Builder(ModifyMovie.this);
                    myNestedBuilder.setTitle("Danger");
                    myNestedBuilder.setMessage("Are you sure you want to discard the changes?");
                    myNestedBuilder.setCancelable(false);
                    myNestedBuilder.setPositiveButton("Discard", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    });
                    myNestedBuilder.setNegativeButton("Do Not Discard", null);
                    myNestedBuilder.show();

                }
            });
            myBuilder.show();
        });

        //Delete all movies

        btnDeleteAll.setOnClickListener(v -> {

            AlertDialog.Builder myBuilder = new AlertDialog.Builder(ModifyMovie.this);
            myBuilder.setTitle("Danger");
            myBuilder.setMessage("Are you sure you want to delete all movies?");
            myBuilder.setCancelable(false);
            myBuilder.setPositiveButton("Delete All", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dbh.deleteAllMovies();
                    Toast.makeText(ModifyMovie.this, "All Movies Deleted!", Toast.LENGTH_SHORT).show();

                    al.clear();
                    al.addAll(dbh.getAllMovies());
                    Intent intentGoingBackToShowMovies = new Intent(ModifyMovie.this, ShowMovies.class);
                    intentGoingBackToShowMovies.putExtra("movieList", al);
                    startActivity(intentGoingBackToShowMovies);
                    finish();

                }
            });

            myBuilder.setNegativeButton("Cancel", null);
            myBuilder.show();
        });

        btnCancel.setOnClickListener(v -> {
            al.clear();
            al.addAll(dbh.getAllMovies());
            Intent intentGoingBackToShowMovies = new Intent(ModifyMovie.this, ShowMovies.class);
            intentGoingBackToShowMovies.putExtra("movieList", al);
            startActivity(intentGoingBackToShowMovies);
            finish();

        });

    }
}