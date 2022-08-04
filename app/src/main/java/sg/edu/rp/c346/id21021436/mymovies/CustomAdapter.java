package sg.edu.rp.c346.id21021436.mymovies;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class CustomAdapter extends ArrayAdapter {

    Context parent_context;
    int layout_id;
    ArrayList<Movie> movieList;

    public CustomAdapter(Context context, int resource, ArrayList<Movie> objects){
        super(context, resource, objects);

        parent_context = context;
        layout_id = resource;
        movieList = objects;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Obtain the LayoutInflater object
        LayoutInflater inflater = (LayoutInflater) parent_context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // "Inflate" the View for each row
        View rowView = inflater.inflate(layout_id, parent, false);

        // Obtain the UI components and do the necessary binding
        TextView tvTitle = rowView.findViewById(R.id.textViewTitle);
        TextView tvYear = rowView.findViewById(R.id.textViewYear);
        TextView tvGenre = rowView.findViewById(R.id.textViewGenre);
        ImageView ivRating = rowView.findViewById(R.id.ivRating);


        // Obtain the Song List information based on the position
        Movie currentMovie = movieList.get(position);

        // Set values to the TextView to display the corresponding information
        tvTitle.setText(currentMovie.getTitle());
        tvYear.setText(String.valueOf(currentMovie.getYear()));
        tvGenre.setText(currentMovie.getGenre());
        String rating = currentMovie.getRating();
        switch(rating){
            case "PG": ivRating.setImageResource(R.drawable.rating_pg); break;
            case "PG13": ivRating.setImageResource(R.drawable.rating_pg13); break;
            case "NC16": ivRating.setImageResource(R.drawable.rating_nc16); break;
            case "M18": ivRating.setImageResource(R.drawable.rating_m18); break;
            case "R21": ivRating.setImageResource(R.drawable.rating_r21); break;
            default: ivRating.setImageResource(R.drawable.rating_g);
        }

        return rowView;
    }


}
