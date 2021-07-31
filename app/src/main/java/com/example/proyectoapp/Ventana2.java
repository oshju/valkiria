package com.example.proyectoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Ventana2 extends AppCompatActivity {

    private EditText caja1;
    private EditText caja2;
    private EditText caja3;

    private String nom,num,loc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ventana2);

        this.caja1=findViewById(R.id.txtnombre);
        this.caja2=findViewById(R.id.txtapellido);
        this.caja3=findViewById(R.id.txtemail);

    }
    public void goVentana3(View view) {
            Intent i =new Intent(this,Ventana3.class);
            startActivity(i);
    }

    public void toast1() {
        Context context = getApplicationContext();
        CharSequence text = "Registro no realizado!";
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }
    public void toast3() {
        Context context = getApplicationContext();
        CharSequence text = "paso a pagina 3 realizado!!";
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    public void hola(){

    }

    public void leerServicio(View view) {
        try {
            String url = "https://apidepartamentospgs.azurewebsites.net/api/Departamentos";
            nom=caja1.getText().toString();
            caja2.getText().toString();
            caja3.getText().toString();
            new HttpAsyncTask().execute(url);
        } catch (Exception e) {
// manage exceptions
            //txtdatos.setText(e.getMessage());
        }
    }




    private class HttpAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {

            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(urls[0]);

            try {
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(3);
                nameValuePairs.add(new BasicNameValuePair("numero", "99"));
                nameValuePairs.add(new BasicNameValuePair("nombre", "88"));
                nameValuePairs.add(new BasicNameValuePair("localidad", "77"));
                //añadir id value,nombre de variable findviewByid de las cajas de texto

                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                // Execute HTTP Post Request
                HttpResponse response = httpclient.execute(httppost);

            } catch (ClientProtocolException e) {
                // TODO Auto-generated catch block
            } catch (IOException e) {
                // TODO Auto-generated catch block
            }
            return "Executed";
            // return recuperarContenido(urls[0]);
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {


        }
    }





}