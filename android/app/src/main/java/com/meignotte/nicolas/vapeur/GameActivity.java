package com.meignotte.nicolas.vapeur;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by nicolas on 13/06/17.
 */
public class GameActivity extends Activity {
    TextView tvName;
    TextView tvType;
    TextView tvPrice;
    TextView tvMark;
    ImageView img;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_simple);
        Intent myIntent = getIntent();

        tvName = (TextView) findViewById(R.id.title);
        tvType = (TextView) findViewById(R.id.desc);
        tvPrice = (TextView) findViewById(R.id.price);
        tvMark = (TextView) findViewById(R.id.price);
        img = (ImageView) findViewById(R.id.image);
        ListView listView =  (ListView) this.findViewById(R.id.listView);
        ArrayList<Review> reviews = new ArrayList<Review>();
        JSONArray jreviews=null;
        int somme=0;

        //Retrieve data from intent
        tvName.setText( myIntent.getStringExtra("title"));
        tvType.setText( myIntent.getStringExtra("desc"));
        tvPrice.setText("Price:"+myIntent.getStringExtra("price")+"€");
        img.setImageResource(Integer.parseInt(myIntent.getStringExtra("resid")));

        //fetch reviews and add them to adpater
        GetClient gc = new GetClient("http://10.0.2.2:8085/Vapeur/user/reviews?game="+myIntent.getStringExtra("id"));
        try {
            String result =gc.execute().get();
            jreviews=new JSONArray(result);
            for(int i=0; i<jreviews.length();i++){
                JSONObject js=jreviews.getJSONObject(i);
                JSONObject author = js.getJSONObject("author");
                String name = author.getString("nickname");
                String review = js.getString("review");
                int mark = js.getInt("mark");
                somme+=mark;
                reviews.add(new Review(mark,name,review));
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        tvMark.setText(""+somme/jreviews.length());
        ReviewAdapter adapter = new ReviewAdapter(reviews ,getApplicationContext());
        listView.setAdapter(adapter);
    }
}
