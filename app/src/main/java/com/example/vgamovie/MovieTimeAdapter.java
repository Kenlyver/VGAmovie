package com.example.vgamovie;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

public class MovieTimeAdapter extends BaseAdapter {


    private Context context;
    private int layout;
    private List<MovieTime> movieTimeList;

    public MovieTimeAdapter(Context context, int layout, List<MovieTime> movieTimeList) {
        this.context = context;
        this.layout = layout;
        this.movieTimeList = movieTimeList;
    }
    public void clearData() {
        // clear the data
        movieTimeList.clear();
    }
    @Override
    public int getCount() {
        return movieTimeList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    private class ViewHolder{
        TextView txtnameTheater, txtTime;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        if(convertView == null){
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout, null);

            holder.txtnameTheater = (TextView) convertView.findViewById(R.id.txtMovieName);
            holder.txtTime = (TextView) convertView.findViewById(R.id.txtTime1);

            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        MovieTime movieTime = movieTimeList.get(position);

        holder.txtnameTheater.setText(movieTime.getNameTheater());
        holder.txtTime.setText(movieTime.getTime());

        return convertView;
    }
}
