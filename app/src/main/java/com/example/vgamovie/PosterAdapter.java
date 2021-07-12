package com.example.vgamovie;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;


import com.squareup.picasso.Picasso;

import java.util.List;

public class PosterAdapter extends PagerAdapter {


    List<CustomPoster> posterImage;
    Context context;
    LayoutInflater layoutInflater;
    String urlGetData = "http://192.168.1.9:8080/vgamovie/getdataposter.php";
    public int movieID;
    String a ;
    SharedPreferences sp ;


    public PosterAdapter(List<CustomPoster> posterImage, Context context) {
        this.posterImage = posterImage;
        this.context = context;
        layoutInflater = LayoutInflater.from(context);

    }



    @Override
    public int getCount() {
        return posterImage.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
      container.removeView((View) object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        View view = layoutInflater.inflate(R.layout.card_item,container,false);
        TextView txtTittle = (TextView) view.findViewById(R.id.txtTittle);
        TextView txtTime = (TextView) view.findViewById(R.id.txtTime);
        ImageView imageMask = (ImageView) view.findViewById(R.id.imgMark);
        ImageView imageView = (ImageView) view.findViewById(R.id.imageViewPoster);

        Picasso.get()
                .load(posterImage.get(position).getUrl())
                .into(imageView);
        Picasso.get()
                .load(posterImage.get(position).getMask())
                .into(imageMask);
        txtTittle.setText(posterImage.get(position).getTitle());
        txtTime.setText(posterImage.get(position).getTime());
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(position==0)
                {
                    Intent intent = new Intent(context,Movie.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    int TG = position+1;
                    intent.putExtra("MovieID", TG);
                    context.startActivity(intent);
                }
                if(position==1)
                {
                    Intent intent = new Intent(context,Movie.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    int TG = position+1;
                    intent.putExtra("MovieID", TG);
                    context.startActivity(intent);
                }
                if(position==2)
                {
                    Intent intent = new Intent(context,Movie.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    int TG = position+1;
                    intent.putExtra("MovieID", TG);
                    context.startActivity(intent);
                }
                if(position==3)
                {
                    Intent intent = new Intent(context,Movie.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    int TG = position+1;
                    intent.putExtra("MovieID", TG);
                    context.startActivity(intent);
                }
                if(position==4)
                {
                    Intent intent = new Intent(context,Movie.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    int TG = position+1;
                    intent.putExtra("MovieID", TG);
                    context.startActivity(intent);
                }
                if(position==5)
                {
                    Intent intent = new Intent(context,Movie.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    int TG = position+1;
                    intent.putExtra("MovieID", TG);
                    context.startActivity(intent);
                }
                if(position==6)
                {
                    Intent intent = new Intent(context,Movie.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    int TG = position+1;
                    intent.putExtra("MovieID", TG);
                    context.startActivity(intent);
                }
                if(position==7)
                {
                    Intent intent = new Intent(context,Movie.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    int TG = position+1;
                    intent.putExtra("MovieID", TG);
                    context.startActivity(intent);
                }
                if(position==8)
                {
                    Intent intent = new Intent(context,Movie.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    int TG = position+1;
                    intent.putExtra("MovieID", TG);
                    context.startActivity(intent);
                }
                if(position==9)
                {
                    Intent intent = new Intent(context,Movie.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    int TG = position+1;
                    intent.putExtra("MovieID", TG);
                    context.startActivity(intent);
                }
                if(position==10)
                {
                    Intent intent = new Intent(context,Movie.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    int TG = position+1;
                    intent.putExtra("MovieID", TG);
                    context.startActivity(intent);
                }
                }
        });
        container.addView(view);
        return view;
    }

//    private void getDataDetailMovie(String url) {
//        RequestQueue requestQueue = Volley.newRequestQueue(context);
//        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
//            @Override
//            public void onResponse(JSONArray response) {
//                for (int i=0; i< response.length();i++)
//                {
//                    try {
//                        JSONObject object = response.getJSONObject(i);
//                        a = object.getString("MovieID");
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(context,error.toString(),Toast.LENGTH_SHORT).show();
//                    }
//                }
//        );
//        requestQueue.add(jsonArrayRequest);
//    }
}
