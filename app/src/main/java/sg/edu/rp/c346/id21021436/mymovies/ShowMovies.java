package sg.edu.rp.c346.id21021436.mymovies;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import java.io.Serializable;
import java.util.ArrayList;


public class ShowMovies extends AppCompatActivity {
    ListView lv;
    Button btnGivenRating;
    Spinner spnRating;

    //this boolean variable captures the current state of the Button (to allow the function to flip between PG-13 and All Movies)
    boolean btnStatus = false;

    ArrayList<Movie> alMovieList = new ArrayList<>();

    DBHelper dbh = new DBHelper(ShowMovies.this);

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_movies);


        lv = findViewById(R.id.listMovies);
        btnGivenRating = findViewById(R.id.btnGivenRating);
        spnRating = findViewById(R.id.spnRating);

        alMovieList.addAll(dbh.getAllMovies());
        CustomAdapter aaMovieList = new CustomAdapter(this, R.layout.row, alMovieList);
        aaMovieList.notifyDataSetChanged();
        lv.setAdapter(aaMovieList);

        btnGivenRating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //the button text and function will flip between Given Rating and All Movies
                if (!btnStatus){
                    String givenRating = spnRating.getSelectedItem().toString();
                    alMovieList.clear();
                    alMovieList.addAll(dbh.getMoviesByRating(givenRating));
//                    alMovieList.addAll(dbh.getPG13());
                    aaMovieList.notifyDataSetChanged();
                    lv.setAdapter(aaMovieList);
                    btnGivenRating.setText("Show All Movies");
                    btnStatus = !btnStatus;
                }
                else {
                    alMovieList.clear();
                    alMovieList.addAll(dbh.getAllMovies());
                    aaMovieList.notifyDataSetChanged();
                    lv.setAdapter(aaMovieList);
                    btnGivenRating.setText("Show All Movies with this Rating");
                    btnStatus = !btnStatus;
                }





                //this is a way to make a button disappear programmatically, used in an earlier version
//    btnFiveStars.setVisibility(View.GONE);


            }
        });

        lv.setClickable(true);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Movie moviePicked = (Movie) lv.getItemAtPosition(position);
                Intent intentToModifyMovie = new Intent(ShowMovies.this, ModifyMovie.class);
                intentToModifyMovie.putExtra("moviePicked", (Serializable) moviePicked);
                startActivity(intentToModifyMovie);
                finish();

            }
        });

    }

}
