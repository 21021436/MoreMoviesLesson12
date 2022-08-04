package sg.edu.rp.c346.id21021436.mymovies;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    //define variables

    EditText title, genre, year;
    Spinner spnRating;
    Button btnInsert, btnShowList, btnClearAll;
    ArrayList<Movie> al;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        title = findViewById(R.id.etTitle);
        genre = findViewById(R.id.etGenre);
        year = findViewById(R.id.etYear);
        spnRating = findViewById(R.id.spnRating);
        btnInsert = findViewById(R.id.btnInsert);
        btnShowList = findViewById(R.id.btnShowList);
        btnClearAll = findViewById(R.id.btnClearAll);

        al = new ArrayList<Movie>();

        // When Insert is clicked, insert the movie into the database
        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str_MovieTitle = title.getText().toString();
                String str_Genre = genre.getText().toString();
                int int_Year = Integer.valueOf(year.getText().toString());
                String str_rating = spnRating.getSelectedItem().toString();

                DBHelper dbh = new DBHelper(MainActivity.this);

                //when making entry for new movie, use id = 1 (arbitrary, as the true ID will be set by AUTOINCREMENT feature of database)
                Movie movieNew = new Movie(1, str_MovieTitle, str_Genre, int_Year, str_rating);

                long inserted_id = dbh.insertMovie(movieNew);

                if (inserted_id != -1){
                    Toast.makeText(MainActivity.this, "Insert successful",
                            Toast.LENGTH_SHORT).show();
                }

                }

        });

        //When Show List is clicked, need to open the Show Movies Activity
        btnShowList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ShowMovies.class);
                startActivity(intent);
            }
        });

        //When "Clear All Fields" button is clicked, all fields are cleared and spinner restored to default

        btnClearAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                title.setText("");
                genre.setText("");
                year.setText("");
                spnRating.setSelection(0);
            }
        });


    }
}