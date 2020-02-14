package com.example.Omotecconnect;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CourseDetailAdapter extends RecyclerView.Adapter<CourseDetailAdapter.ViewHolder> {

    //Imageloader to load image
    private Context context;

    //List to store all superheroes
    List<String> categories;

    JSONObject details;

    ArrayAdapter arrayAdapter;

    //Constructor of this class
    public CourseDetailAdapter(List<String> superHeroes, JSONObject details, Context context){
        super();
        //Getting all superheroes
        this.categories = superHeroes;
        this.details = details;
        this.context = context;
    }

    @Override
    public CourseDetailAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.course_det_card, parent, false);
        CourseDetailAdapter.ViewHolder viewHolder = new CourseDetailAdapter.ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final CourseDetailAdapter.ViewHolder holder, final int position) {


        final String name = categories.get(position);

        Picasso.get()
                .load(AppConfig.URL_HOME_IMG+name+".png")
                .resize(200, 200)
                .into(holder.imageView);

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,name, Toast.LENGTH_SHORT).show();
            }
        });
        holder.category.setText(name);

        try {
            String det = details.getString(name);
            List<String> dets = new ArrayList<String>(Arrays.asList(det.substring(1,det.length()-1).replace("\"","").split(",")));

            arrayAdapter = new ArrayAdapter<String>(context,R.layout.cat_list_view,R.id.label,dets);
            holder.recycle.setAdapter(arrayAdapter);

            setListViewHeightBasedOnChildren(holder.recycle);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //Showing data on the views
        // holder.imageView.setImageUrl(superHero.getId(), imageLoader);

    }

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null)
            return;

        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.UNSPECIFIED);
        int totalHeight = 0;
        View view = null;
        Log.e("items", String.valueOf(listAdapter.getCount()));
        for (int i = 0; i < listAdapter.getCount(); i++) {
            view = listAdapter.getView(i, view, listView);
            if (i == 0)
                view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth, WindowManager.LayoutParams.WRAP_CONTENT));

            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight()+30;
            Log.e("height", String.valueOf(totalHeight));
        }
        Log.e("total height", String.valueOf(totalHeight));
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + ((listView.getDividerHeight()) * (listAdapter.getCount() - 1));
        Log.e("divider height", String.valueOf(listView.getDividerHeight()));
        listView.setLayoutParams(params);
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        //Views
        public ImageView imageView;
        public TextView category;
        public ListView recycle;

        //Initializing Views
        public ViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.imageView4);
            category = (TextView) itemView.findViewById(R.id.category);
            recycle = (ListView) itemView.findViewById(R.id.recy);


        }
    }
}
