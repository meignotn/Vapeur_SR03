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
public class GameAdapter extends ArrayAdapter<Game> {

    private ArrayList<Game> dataSet;
    Context mContext;

    private static class ViewHolder {
        TextView txtName;
        TextView txtType;
        TextView txtVersion;
        ImageView img;

    }

    public GameAdapter(ArrayList<Game> data, Context context) {
        super(context, R.layout.game, data);
        this.dataSet = data;
        this.mContext=context;

    }


    private int lastPosition = -1;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        final Game dataModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();

            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.game, parent, false);


            viewHolder.txtName = (TextView) convertView.findViewById(R.id.title);
            viewHolder.txtType = (TextView) convertView.findViewById(R.id.desc);
            viewHolder.txtVersion = (TextView) convertView.findViewById(R.id.price);
            viewHolder.img = (ImageView) convertView.findViewById(R.id.image);

            result=convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }
        if (position % 2 ==0){
            convertView.setBackground(new ColorDrawable(Color.LTGRAY));
        }else{
            convertView.setBackground(new ColorDrawable(Color.WHITE));
        }


        lastPosition = position;

        viewHolder.txtName.setText(dataModel.getTitle());
        viewHolder.txtType.setText(dataModel.getDesc().substring(0, 150)+"...");
        viewHolder.txtVersion.setText("Price:" +"\t"+String.valueOf(dataModel.getPrice()));
        System.out.println(dataModel.resid);
        viewHolder.img.setImageResource(dataModel.resid);
        //Change to GameActivity onClick
        viewHolder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext,GameActivity.class);
                intent.putExtra("title",dataModel.getTitle());
                intent.putExtra("price",""+ dataModel.getPrice());
                intent.putExtra("resid",""+dataModel.resid);
                intent.putExtra("desc",dataModel.getDesc());
                intent.putExtra("id",""+dataModel.getId());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);
            }
        });
        return convertView;
    }
}