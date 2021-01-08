package com.example.testplantnet;

import java.io.File;
import java.io.IOException;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
}
    public void sendMessage(View view) throws IOException { // On sait que le bouton active cette méthode grâce au "OnClick paramétéré à 'SendMessage' dans les propriétés de cet élément dans 'activity_main.xml' "
        new Thread(() -> {
            // a potentially time consuming task
            URL url = null;
            try {
                url = new URL("https://my-api.plantnet.org/v2/identify/all?images=" +
                        "https://media.gerbeaud.net/2014/02/640/chene-quercus-robur.jpg" +
                        "&organs=leaf&include-related-images=false&lang=en" +
                        "&api-key=2a10dhqKV1csqtYS4gUnTxZ");
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            HttpURLConnection urlConnection = null;
            try {
                urlConnection = (HttpURLConnection) url.openConnection();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                readStream(in);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                urlConnection.disconnect();
            }
        }).start();


    }

    private void readStream(InputStream in) throws IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader bin = new BufferedReader(new InputStreamReader(in));
        //string qui contient les lignes du reader
        String inputLine;

        while ((inputLine = bin.readLine()) != null) {
            sb.append(inputLine);
    }
        System.out.println("sbtostring" + sb.toString());
    }

}