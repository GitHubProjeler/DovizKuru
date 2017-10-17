package com.trkaynak.dovizkuru;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    TextView textView, textView3;
    EditText editText;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView) findViewById(R.id.textView);

        textView3 = (TextView) findViewById(R.id.textView3);
        editText = (EditText) findViewById(R.id.editText);
        button = (Button) findViewById(R.id.button);

        Veri indirilen = new Veri();

        try{
            String urlUsd = "http://api.fixer.io/latest?base=USD";
            indirilen.execute(urlUsd);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void gonder(View view){
        Veri indirilen = new Veri();

        try{
            String urlUsd = "http://api.fixer.io/latest?base=";
            String secilen = editText.getText().toString();
            indirilen.execute(urlUsd+secilen);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private class Veri extends AsyncTask <String, Void, String>{

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

           try{
               JSONObject jsonObject = new JSONObject(s);
               String base = jsonObject.getString("base");
               String date = jsonObject.getString("date");
               String rates = jsonObject.getString("rates");
               JSONObject jsonObject1 = new JSONObject(rates);
               String Tl = jsonObject1.getString("TRY");

               textView3.setText("Tarih: "+date);
               textView.setText(base+" "+Tl+" TRY");


           }catch (Exception e){

           }
        }

        @Override
        protected String doInBackground(String... deger) {

            String result = "";
            URL url ;
            HttpURLConnection httpURLConnection;

            try {
                url = new URL(deger[0]);
                httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);

                int data = inputStreamReader.read();

                while ( data > 0) {
                    char karakter = (char) data;
                    result += karakter;
                    data = inputStreamReader.read();

                }
                    return result;
            }
            catch (Exception e){
                e.printStackTrace();
                return null;
            }

        }
    }
}
