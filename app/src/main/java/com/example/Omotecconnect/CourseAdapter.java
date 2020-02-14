package com.example.Omotecconnect;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.ViewHolder> {

    //Imageloader to load image
    private Context context;

    //List to store all superheroes
    List<Course> courses;

    //Constructor of this class
    public CourseAdapter(List<Course> superHeroes, Context context){
        super();
        //Getting all superheroes
        this.courses = superHeroes;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.course_card, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        //Getting the particular item from the list
        final Course superHero =  courses.get(position);

        //Loading image from url

        holder.course_name.setText(superHero.getCourseName());

        final SessionManager session = new SessionManager(context);
        Picasso.get()
                .load(AppConfig.URL_HOME_IMG+superHero.getId()+".jpg")
                .resize(900, 600)
                .into(holder.imageView);
        //Showing data on the views
       // holder.imageView.setImageUrl(superHero.getId(), imageLoader);

        holder.card_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (session.isLoggedIn()) {
                    Intent in = new Intent(context, CourseDetailsActivity.class);
                    in.putExtra("course_name", holder.course_name.getText().toString());
                    in.putExtra("course_id", superHero.getId());
                    in.putExtra("no_sess", superHero.getSessions());
                    in.putExtra("sess_dura", superHero.getDuration());
                    context.startActivity(in);
                }
                else
                {
                    Toast.makeText(context,"Please login or signup to proceed!", Toast.LENGTH_LONG).show();
                    Intent i = new Intent(context, MainActivity.class);
                    context.startActivity(i);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return courses.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        //Views
        public ImageView imageView;
        public TextView course_name;
        FrameLayout card_layout;

        //Initializing Views
        public ViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.imageView6);
            course_name = (TextView) itemView.findViewById(R.id.course_name);
            card_layout = (FrameLayout) itemView.findViewById(R.id.card_layout);


        }
    }
}
