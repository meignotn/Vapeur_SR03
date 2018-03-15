package com.meignotte.nicolas.vapeur;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by nicolas on 31/05/17.
 */
public class ReviewAdapter extends ArrayAdapter<Review> {

    private ArrayList<Review> dataSet;
    Context mContext;

    // View lookup cache
    private static class ViewHolder {
        TextView txtName;
        TextView txtMark;
        TextView txtReview;
    }

    public ReviewAdapter(ArrayList<Review> data, Context context) {
        super(context, R.layout.review_item, data);
        this.dataSet = data;
        this.mContext=context;

    }


    private int lastPosition = -1;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        final Review dataModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();

            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.review_item, parent, false);


            viewHolder.txtName = (TextView) convertView.findViewById(R.id.name);
            viewHolder.txtMark = (TextView) convertView.findViewById(R.id.mark);
            viewHolder.txtReview = (TextView) convertView.findViewById(R.id.review);

            result=convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }

        int redcolor = 255-255 * dataModel.mark/10;
        int bluecolor = 255 * dataModel.mark/10;
        int greencolor;
        if(redcolor>bluecolor)
            greencolor = 255 - redcolor;
        else
            greencolor = 255- bluecolor;

        convertView.setBackgroundColor(Color.argb(100,redcolor,greencolor,bluecolor));
        lastPosition = position;

        viewHolder.txtName.setText(dataModel.getAuthor());
        viewHolder.txtReview.setText(dataModel.getReview());
        viewHolder.txtMark.setText(dataModel.getMark() + "/10");

        return convertView;
    }
}