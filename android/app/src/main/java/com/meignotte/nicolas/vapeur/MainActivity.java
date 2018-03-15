package com.meignotte.nicolas.vapeur;

import android.app.LauncherActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        GetClient gc = new GetClient("http://10.0.2.2:8085/Vapeur/game");
        ListView listView =  (ListView) this.findViewById(R.id.list);
        ArrayList games = new ArrayList<Game>();
        //fetch games and display it
        try {
            String result =gc.execute().get();
            JSONArray jgames=new JSONArray(result);
            for(int i=0; i<jgames.length();i++){
                JSONObject js=jgames.getJSONObject(i);
                int resid = getResources().getIdentifier(js.getString("image").split("[.]")[0].toLowerCase(),"drawable", getPackageName());
                games.add(new Game(js.getString("title"),resid,js.getString("description").toLowerCase(),js.getInt("price"),js.getInt("id")));
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        GameAdapter adapter = new GameAdapter(games ,getApplicationContext());
        listView.setAdapter(adapter);
    }


}
