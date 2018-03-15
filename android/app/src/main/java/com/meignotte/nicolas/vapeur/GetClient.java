package com.meignotte.nicolas.vapeur;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


/**
 * Created by nicolas on 14/10/15.
 */
public class GetClient extends AsyncTask<String,String,String> {

    private static  String targetURL ;



    GetClient(String url){
        this.targetURL=url;
    }


    protected String doInBackground(String... truc) {
        String result="";
        try {

            URL targetUrl = new URL(targetURL);

            HttpURLConnection httpConnection = (HttpURLConnection) targetUrl.openConnection();
            httpConnection.setRequestMethod("GET");
            BufferedReader responseBuffer = new BufferedReader(new InputStreamReader(
                    (httpConnection.getInputStream())));
            String output="";
            while ((output = responseBuffer.readLine()) != null) {
                result=result+output;
            }

            httpConnection.disconnect();

        } catch (MalformedURLException e) {

            e.printStackTrace();

        } catch (IOException e) {

            e.printStackTrace();

        }

        return result;
    }
}
